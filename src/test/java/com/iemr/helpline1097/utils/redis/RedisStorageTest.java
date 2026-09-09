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
package com.iemr.helpline1097.utils.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RedisStorageTest {

	private static final byte[] KEY = "session-key".getBytes(StandardCharsets.UTF_8);

	@Mock
	private LettuceConnectionFactory connectionFactory;

	@Mock
	private RedisConnection connection;

	private RedisStorage redisStorage;

	@BeforeEach
	void setUp() {
		redisStorage = new RedisStorage();
		ReflectionTestUtils.setField(redisStorage, "connection", connectionFactory);
		when(connectionFactory.getConnection()).thenReturn(connection);
	}

	@Test
	void setObjectWritesTheValueWhenNoSessionIsStored() throws Exception {
		when(connection.get(any())).thenReturn(null);

		assertEquals("session-key", redisStorage.setObject("session-key", "payload", 100));

		verify(connection).set(any(), any(), eq(Expiration.seconds(100)), eq(SetOption.UPSERT));
	}

	@Test
	void setObjectLeavesAnExistingSessionUntouched() throws Exception {
		when(connection.get(any())).thenReturn("existing".getBytes(StandardCharsets.UTF_8));

		assertEquals("session-key", redisStorage.setObject("session-key", "payload", 100));

		verify(connection, never()).set(any(), any(), any(Expiration.class), any(SetOption.class));
	}

	@Test
	void getObjectReturnsTheStoredValueAndRefreshesTheTtl() throws Exception {
		when(connection.get(any())).thenReturn("payload".getBytes(StandardCharsets.UTF_8));

		assertEquals("payload", redisStorage.getObject("session-key", true, 100));

		verify(connection).expire(any(), eq(100L));
	}

	@Test
	void getObjectFailsWhenNothingIsStored() {
		when(connection.get(any())).thenReturn(null);

		assertThrows(RedisSessionException.class, () -> redisStorage.getObject("session-key", true, 100));
	}

	@Test
	void getObjectFailsWhenTheStoredValueIsBlank() {
		when(connection.get(any())).thenReturn("   ".getBytes(StandardCharsets.UTF_8));

		assertThrows(RedisSessionException.class, () -> redisStorage.getObject("session-key", true, 100));
	}

	@Test
	void deleteObjectReturnsTheNumberOfKeysRemoved() throws Exception {
		when(connection.del(any())).thenReturn(1L);

		assertEquals(1L, redisStorage.deleteObject("session-key"));
	}

	@Test
	void updateObjectRewritesAnExistingSession() throws Exception {
		when(connection.get(any())).thenReturn("existing".getBytes(StandardCharsets.UTF_8));

		assertEquals("session-key", redisStorage.updateObject("session-key", "payload", true, 100));

		verify(connection).set(any(), any(), eq(Expiration.seconds(100)), eq(SetOption.UPSERT));
	}

	@Test
	void updateObjectFailsWhenThereIsNoSessionToUpdate() {
		when(connection.get(any())).thenReturn(null);

		assertThrows(RedisSessionException.class, () -> redisStorage.updateObject("session-key", "payload", true, 100));
	}
}
