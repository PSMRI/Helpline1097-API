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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JwtUtilTest {

	private static final String SECRET = "helpline-1097-test-secret-key-that-is-long-enough";

	@Mock
	private TokenDenylist tokenDenylist;

	@InjectMocks
	private JwtUtil jwtUtil;

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(jwtUtil, "SECRET_KEY", SECRET);
	}

	private static SecretKey signingKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}

	private static String tokenWith(String subject, String jti) {
		return Jwts.builder().subject(subject).id(jti).claim("userId", "42")
				.expiration(new Date(System.currentTimeMillis() + 60000)).signWith(signingKey()).compact();
	}

	@Test
	void validateTokenReturnsClaimsForAValidToken() {
		when(tokenDenylist.isTokenDenylisted("jti-1")).thenReturn(false);

		Claims claims = jwtUtil.validateToken(tokenWith("agent", "jti-1"));

		assertNotNull(claims);
		assertEquals("agent", claims.getSubject());
		assertEquals("42", claims.get("userId", String.class));
	}

	@Test
	void validateTokenAcceptsATokenWithoutAJti() {
		assertNotNull(jwtUtil.validateToken(
				Jwts.builder().subject("agent").expiration(new Date(System.currentTimeMillis() + 60000))
						.signWith(signingKey()).compact()));
	}

	@Test
	void validateTokenRejectsADenylistedToken() {
		when(tokenDenylist.isTokenDenylisted("jti-1")).thenReturn(true);

		assertNull(jwtUtil.validateToken(tokenWith("agent", "jti-1")));
	}

	@Test
	void validateTokenRejectsGarbage() {
		assertNull(jwtUtil.validateToken("not-a-jwt"));
	}

	@Test
	void validateTokenRejectsATokenSignedWithAnotherKey() {
		String foreignToken = Jwts.builder().subject("agent")
				.expiration(new Date(System.currentTimeMillis() + 60000))
				.signWith(Keys.hmacShaKeyFor("a-completely-different-secret-key-value".getBytes())).compact();

		assertNull(jwtUtil.validateToken(foreignToken));
	}

	@Test
	void validateTokenRejectsAnExpiredToken() {
		String expired = Jwts.builder().subject("agent").expiration(new Date(System.currentTimeMillis() - 1000))
				.signWith(signingKey()).compact();

		assertNull(jwtUtil.validateToken(expired));
	}

	@Test
	void extractUsernameReturnsTheSubject() {
		assertEquals("agent", jwtUtil.extractUsername(tokenWith("agent", "jti-1")));
	}

	@Test
	void extractClaimAppliesTheGivenResolver() {
		assertEquals("jti-1", jwtUtil.extractClaim(tokenWith("agent", "jti-1"), Claims::getId));
	}

	@Test
	void extractClaimFailsOnAnUnparseableToken() {
		assertThrows(RuntimeException.class, () -> jwtUtil.extractUsername("not-a-jwt"));
	}

	@Test
	void validateTokenFailsWhenNoSecretIsConfigured() {
		ReflectionTestUtils.setField(jwtUtil, "SECRET_KEY", null);

		assertNull(jwtUtil.validateToken(tokenWith("agent", "jti-1")));
	}

	@Test
	void extractUsernameSurfacesTheMissingSecretAsAnIllegalState() {
		ReflectionTestUtils.setField(jwtUtil, "SECRET_KEY", "");

		assertThrows(IllegalStateException.class, () -> jwtUtil.extractUsername("any-token"));
	}
}
