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
package com.iemr.helpline1097.utils.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import com.iemr.helpline1097.utils.exception.IEMRException;
import com.iemr.helpline1097.utils.redis.RedisSessionException;
import com.iemr.helpline1097.utils.sessionobject.SessionObject;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ValidatorTest {

	@Mock
	private SessionObject sessionObject;

	private Validator validator;

	@BeforeEach
	void setUp() {
		validator = new Validator();
		validator.setSessionObject(sessionObject);
		ReflectionTestUtils.setField(Validator.class, "enableIPValidation", Boolean.FALSE);
	}

	private static JSONObject response(String ip) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("loginIPAddress", ip);
		return obj;
	}

	@Test
	void updateCacheObjStoresTheSessionAndReportsSuccess() throws Exception {
		when(sessionObject.getSessionObject("login-key")).thenReturn(null);

		JSONObject result = validator.updateCacheObj(response("10.0.0.1"), "login-key", "10.0.0.1");

		assertEquals("login-key", result.getString("key"));
		assertEquals("login success", result.getString("sessionStatus"));
		verify(sessionObject).setSessionObject(anyString(), anyString());
	}

	@Test
	void updateCacheObjStillReportsSuccessWhenTheSessionLookupFails() throws Exception {
		when(sessionObject.getSessionObject("login-key")).thenThrow(new RedisSessionException("no session"));

		JSONObject result = validator.updateCacheObj(response("10.0.0.1"), "login-key", "10.0.0.1");

		assertEquals("login success", result.getString("sessionStatus"));
	}

	@Test
	void updateCacheObjReportsTheOriginalIpWhenIpValidationRejectsTheLogin() throws Exception {
		ReflectionTestUtils.setField(Validator.class, "enableIPValidation", Boolean.TRUE);
		when(sessionObject.getSessionObject("login-key")).thenReturn("{\"loginIPAddress\":\"10.0.0.9\"}");

		JSONObject result = validator.updateCacheObj(response("10.0.0.1"), "login-key", "10.0.0.1");

		assertEquals("login success, but user logged in from 10.0.0.9", result.getString("sessionStatus"));
		assertFalse(result.has("loginIPAddress"));
	}

	@Test
	void updateCacheObjAcceptsALoginFromTheSameIpWhenIpValidationIsOn() throws Exception {
		ReflectionTestUtils.setField(Validator.class, "enableIPValidation", Boolean.TRUE);
		when(sessionObject.getSessionObject("login-key")).thenReturn("{\"loginIPAddress\":\"10.0.0.1\"}");

		JSONObject result = validator.updateCacheObj(response("10.0.0.1"), "login-key", "10.0.0.1");

		assertEquals("login success", result.getString("sessionStatus"));
	}

	@Test
	void updateCacheObjSurvivesAFailureWhileStoringTheSession() throws Exception {
		when(sessionObject.getSessionObject("login-key")).thenReturn(null);
		when(sessionObject.setSessionObject(anyString(), anyString()))
				.thenThrow(new RedisSessionException("write failed"));

		JSONObject result = validator.updateCacheObj(response("10.0.0.1"), "login-key", "10.0.0.1");

		assertEquals("session creation failed", result.getString("sessionStatus"));
	}

	@Test
	void getSessionObjectReadsThroughToTheSession() throws Exception {
		when(sessionObject.getSessionObject("login-key")).thenReturn("payload");

		assertEquals("payload", validator.getSessionObject("login-key"));
	}

	@Test
	void checkKeyExistsPassesForAKnownSession() throws Exception {
		when(sessionObject.getSessionObject("login-key")).thenReturn("{\"loginIPAddress\":\"10.0.0.1\"}");

		validator.checkKeyExists("login-key", "10.0.0.1");
	}

	@Test
	void checkKeyExistsRejectsAnUnknownSession() throws Exception {
		when(sessionObject.getSessionObject("login-key")).thenThrow(new RedisSessionException("no session"));

		IEMRException thrown = assertThrows(IEMRException.class,
				() -> validator.checkKeyExists("login-key", "10.0.0.1"));

		assertEquals("Invalid login key or session is expired", thrown.getMessage());
	}

	@Test
	void checkKeyExistsRejectsAMismatchedIpWhenIpValidationIsOn() throws Exception {
		ReflectionTestUtils.setField(Validator.class, "enableIPValidation", Boolean.TRUE);
		when(sessionObject.getSessionObject("login-key")).thenReturn("{\"loginIPAddress\":\"10.0.0.9\"}");

		assertThrows(IEMRException.class, () -> validator.checkKeyExists("login-key", "10.0.0.1"));
	}

	@Test
	void ipValidationAcceptsALoginWhenNoSessionIsStoredYet() throws Exception {
		ReflectionTestUtils.setField(Validator.class, "enableIPValidation", Boolean.TRUE);
		when(sessionObject.getSessionObject("login-key")).thenReturn(null);

		JSONObject result = validator.updateCacheObj(response("10.0.0.1"), "login-key", "10.0.0.1");

		assertEquals("login success", result.getString("sessionStatus"));
	}

	@Test
	void ipValidationAcceptsALoginWhenTheStoredSessionIsBlank() throws Exception {
		ReflectionTestUtils.setField(Validator.class, "enableIPValidation", Boolean.TRUE);
		when(sessionObject.getSessionObject("login-key")).thenReturn("   ");

		JSONObject result = validator.updateCacheObj(response("10.0.0.1"), "login-key", "10.0.0.1");

		assertEquals("login success", result.getString("sessionStatus"));
	}
}
