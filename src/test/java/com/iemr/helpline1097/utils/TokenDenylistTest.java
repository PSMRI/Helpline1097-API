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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TokenDenylistTest {

	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	@Mock
	private ValueOperations<String, Object> valueOperations;

	@InjectMocks
	private TokenDenylist tokenDenylist;

	@Test
	void addTokenToDenylistStoresThePrefixedKeyWithATtl() {
		when(redisTemplate.opsForValue()).thenReturn(valueOperations);

		tokenDenylist.addTokenToDenylist("jti-1", 60000L);

		verify(valueOperations).set(eq("denied_jti-1"), eq(" "), eq(60000L), eq(TimeUnit.MILLISECONDS));
	}

	@Test
	void addTokenToDenylistIgnoresNullJti() {
		tokenDenylist.addTokenToDenylist(null, 60000L);

		verify(redisTemplate, never()).opsForValue();
	}

	@Test
	void addTokenToDenylistIgnoresBlankJti() {
		tokenDenylist.addTokenToDenylist("   ", 60000L);

		verify(redisTemplate, never()).opsForValue();
	}

	@Test
	void addTokenToDenylistRejectsNullExpiry() {
		assertThrows(IllegalArgumentException.class, () -> tokenDenylist.addTokenToDenylist("jti-1", null));
	}

	@Test
	void addTokenToDenylistRejectsNonPositiveExpiry() {
		assertThrows(IllegalArgumentException.class, () -> tokenDenylist.addTokenToDenylist("jti-1", 0L));
	}

	@Test
	void addTokenToDenylistWrapsRedisFailures() {
		when(redisTemplate.opsForValue()).thenThrow(new IllegalStateException("redis down"));

		assertThrows(RuntimeException.class, () -> tokenDenylist.addTokenToDenylist("jti-1", 60000L));
	}

	@Test
	void isTokenDenylistedIsTrueWhenTheKeyExists() {
		when(redisTemplate.hasKey("denied_jti-1")).thenReturn(Boolean.TRUE);

		assertTrue(tokenDenylist.isTokenDenylisted("jti-1"));
	}

	@Test
	void isTokenDenylistedIsFalseWhenTheKeyIsAbsent() {
		when(redisTemplate.hasKey(anyString())).thenReturn(Boolean.FALSE);

		assertFalse(tokenDenylist.isTokenDenylisted("jti-1"));
	}

	@Test
	void isTokenDenylistedIsFalseForNullJti() {
		assertFalse(tokenDenylist.isTokenDenylisted(null));
	}

	@Test
	void isTokenDenylistedIsFalseForBlankJti() {
		assertFalse(tokenDenylist.isTokenDenylisted("  "));
	}

	@Test
	void isTokenDenylistedFailsOpenWhenRedisIsUnavailable() {
		when(redisTemplate.hasKey(anyString())).thenThrow(new IllegalStateException("redis down"));

		assertFalse(tokenDenylist.isTokenDenylisted("jti-1"));
	}
}
