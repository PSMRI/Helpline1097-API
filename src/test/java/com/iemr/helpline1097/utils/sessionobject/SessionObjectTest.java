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
package com.iemr.helpline1097.utils.sessionobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.iemr.helpline1097.utils.redis.RedisSessionException;
import com.iemr.helpline1097.utils.redis.RedisStorage;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SessionObjectTest {

	@Mock
	private RedisStorage objectStore;

	private SessionObject sessionObject;

	@BeforeEach
	void setUp() {
		sessionObject = new SessionObject();
		sessionObject.setObjectStore(objectStore);
	}

	@Test
	void getSessionObjectReadsThroughToTheStore() throws Exception {
		when(objectStore.getObject(eq("key"), anyBoolean(), anyInt())).thenReturn("payload");

		assertEquals("payload", sessionObject.getSessionObject("key"));
	}

	@Test
	void getSessionObjectPropagatesStoreFailures() throws Exception {
		when(objectStore.getObject(anyString(), anyBoolean(), anyInt()))
				.thenThrow(new RedisSessionException("session gone"));

		assertThrows(RedisSessionException.class, () -> sessionObject.getSessionObject("key"));
	}

	@Test
	void setSessionObjectWritesThroughToTheStore() throws Exception {
		when(objectStore.setObject(eq("key"), eq("payload"), anyInt())).thenReturn("key");

		assertEquals("key", sessionObject.setSessionObject("key", "payload"));
	}

	@Test
	void updateSessionObjectAlsoIndexesTheSessionByUserName() throws Exception {
		when(objectStore.updateObject(anyString(), anyString(), anyBoolean(), anyInt())).thenReturn("key");

		assertEquals("key", sessionObject.updateSessionObject("key", "{\"userName\":\" Agent \"}"));

		verify(objectStore).updateObject(eq("agent"), eq("key"), anyBoolean(), anyInt());
		verify(objectStore).updateObject(eq("key"), anyString(), anyBoolean(), anyInt());
	}

	@Test
	void updateSessionObjectSkipsTheUserNameIndexWhenThePayloadHasNoUserName() throws Exception {
		when(objectStore.updateObject(anyString(), anyString(), anyBoolean(), anyInt())).thenReturn("key");

		assertEquals("key", sessionObject.updateSessionObject("key", "{\"other\":1}"));

		verify(objectStore, never()).updateObject(eq("agent"), anyString(), anyBoolean(), anyInt());
	}

	@Test
	void updateSessionObjectToleratesANonJsonPayload() throws Exception {
		when(objectStore.updateObject(anyString(), anyString(), anyBoolean(), anyInt())).thenReturn("key");

		assertEquals("key", sessionObject.updateSessionObject("key", "not-json"));
	}

	@Test
	void deleteSessionObjectRemovesTheKey() throws Exception {
		when(objectStore.deleteObject("key")).thenReturn(1L);

		sessionObject.deleteSessionObject("key");

		verify(objectStore).deleteObject("key");
	}
}
