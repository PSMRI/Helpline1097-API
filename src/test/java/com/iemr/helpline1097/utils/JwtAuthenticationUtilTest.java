/*
* AMRIT – Accessible Medical Records via Integrated Technology
* Integrated EHR (Electronic Health Records) Solution
*
* Copyright (C) "Piramal Swasthya Management and Research Institute"
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.helpline1097.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.iemr.helpline1097.data.co.beneficiary.User;
import com.iemr.helpline1097.repository.co.beneficiary.UserLoginRepo;
import com.iemr.helpline1097.utils.exception.IEMRException;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JwtAuthenticationUtilTest {

	@Mock
	private CookieUtil cookieUtil;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	@Mock
	private ValueOperations<String, Object> valueOperations;

	@Mock
	private UserLoginRepo userLoginRepo;

	private JwtAuthenticationUtil authenticationUtil;

	@BeforeEach
	void setUp() {
		authenticationUtil = new JwtAuthenticationUtil(cookieUtil, jwtUtil);
		ReflectionTestUtils.setField(authenticationUtil, "redisTemplate", redisTemplate);
		ReflectionTestUtils.setField(authenticationUtil, "userLoginRepo", userLoginRepo);
		when(redisTemplate.opsForValue()).thenReturn(valueOperations);
	}

	private static Claims claimsWith(String subject, String userId) {
		Claims claims = mock(Claims.class);
		when(claims.getSubject()).thenReturn(subject);
		when(claims.get("userId", String.class)).thenReturn(userId);
		return claims;
	}

	@Test
	void validateJwtTokenReturnsTheUsernameForAValidCookie() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Claims claims = claimsWith("agent", "42");
		when(cookieUtil.getCookieValue(request, "Jwttoken")).thenReturn(Optional.of("token"));
		when(jwtUtil.validateToken("token")).thenReturn(claims);

		ResponseEntity<String> response = authenticationUtil.validateJwtToken(request);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("agent", response.getBody());
	}

	@Test
	void validateJwtTokenIsUnauthorizedWhenTheCookieIsMissing() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(cookieUtil.getCookieValue(request, "Jwttoken")).thenReturn(Optional.empty());

		ResponseEntity<String> response = authenticationUtil.validateJwtToken(request);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertTrue(response.getBody().contains("JWT Token is not set"));
	}

	@Test
	void validateJwtTokenIsUnauthorizedWhenTheTokenIsInvalid() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(cookieUtil.getCookieValue(request, "Jwttoken")).thenReturn(Optional.of("token"));
		when(jwtUtil.validateToken("token")).thenReturn(null);

		ResponseEntity<String> response = authenticationUtil.validateJwtToken(request);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertTrue(response.getBody().contains("Invalid JWT Token"));
	}

	@Test
	void validateJwtTokenIsUnauthorizedWhenTheSubjectIsBlank() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Claims claims = mock(Claims.class);
		when(claims.getSubject()).thenReturn("");
		when(cookieUtil.getCookieValue(request, "Jwttoken")).thenReturn(Optional.of("token"));
		when(jwtUtil.validateToken("token")).thenReturn(claims);

		ResponseEntity<String> response = authenticationUtil.validateJwtToken(request);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertTrue(response.getBody().contains("Username is missing"));
	}

	@Test
	void validateUserIdAndJwtTokenAcceptsAUserAlreadyCached() throws Exception {
		User cached = new User();
		cached.setUserID(42L);
		Claims claims = claimsWith("agent", "42");
		when(jwtUtil.validateToken("token")).thenReturn(claims);
		when(valueOperations.get("user_42")).thenReturn(cached);

		assertTrue(authenticationUtil.validateUserIdAndJwtToken("token"));
		verify(userLoginRepo, never()).getUserByUserID(anyLong());
	}

	@Test
	void validateUserIdAndJwtTokenLoadsAndCachesAUserMissingFromRedis() throws Exception {
		User stored = new User();
		stored.setUserID(42L);
		stored.setUserName("agent");
		Claims claims = claimsWith("agent", "42");
		when(jwtUtil.validateToken("token")).thenReturn(claims);
		when(valueOperations.get("user_42")).thenReturn(null);
		when(userLoginRepo.getUserByUserID(42L)).thenReturn(stored);

		assertTrue(authenticationUtil.validateUserIdAndJwtToken("token"));
		verify(valueOperations).set(eq("user_42"), any(User.class), eq(30L), eq(TimeUnit.MINUTES));
	}

	@Test
	void validateUserIdAndJwtTokenRejectsAnInvalidToken() {
		when(jwtUtil.validateToken("token")).thenReturn(null);

		IEMRException thrown = assertThrows(IEMRException.class,
				() -> authenticationUtil.validateUserIdAndJwtToken("token"));

		assertTrue(thrown.getMessage().contains("Invalid JWT token"));
	}

	@Test
	void validateUserIdAndJwtTokenRejectsAnUnknownUser() {
		Claims claims = claimsWith("agent", "42");
		when(jwtUtil.validateToken("token")).thenReturn(claims);
		when(valueOperations.get("user_42")).thenReturn(null);
		when(userLoginRepo.getUserByUserID(42L)).thenReturn(null);

		IEMRException thrown = assertThrows(IEMRException.class,
				() -> authenticationUtil.validateUserIdAndJwtToken("token"));

		assertTrue(thrown.getMessage().contains("Invalid User ID"));
	}

	@Test
	void validateUserIdAndJwtTokenRejectsANonNumericUserId() {
		Claims claims = claimsWith("agent", "not-a-number");
		when(jwtUtil.validateToken("token")).thenReturn(claims);
		when(valueOperations.get(anyString())).thenReturn(null);

		assertThrows(IEMRException.class, () -> authenticationUtil.validateUserIdAndJwtToken("token"));
	}
}
