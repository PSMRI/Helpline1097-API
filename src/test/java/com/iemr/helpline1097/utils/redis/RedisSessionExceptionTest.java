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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.iemr.helpline1097.utils.exception.IEMRException;

class RedisSessionExceptionTest {

	@Test
	void messageOnlyConstructorKeepsTheMessage() {
		RedisSessionException exception = new RedisSessionException("session gone");

		assertEquals("session gone", exception.getMessage());
		assertEquals("session gone", exception.toString());
		assertTrue(exception instanceof IEMRException);
	}

	@Test
	void causeConstructorAdoptsTheCauseStackTrace() {
		RuntimeException cause = new RuntimeException("redis down");

		RedisSessionException exception = new RedisSessionException("session gone", cause);

		assertEquals("session gone", exception.getMessage());
		assertEquals(cause.getStackTrace()[0], exception.getStackTrace()[0]);
	}
}
