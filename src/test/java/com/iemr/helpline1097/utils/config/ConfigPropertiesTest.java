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
package com.iemr.helpline1097.utils.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Base64;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

class ConfigPropertiesTest {

	@BeforeEach
	void setUp() {
		new ConfigProperties();
	}

	private static Properties loadedProperties() {
		return (Properties) ReflectionTestUtils.getField(ConfigProperties.class, "properties");
	}

	@Test
	void applicationPropertiesAreLoadedOnConstruction() {
		assertNotNull(loadedProperties());
	}

	@Test
	void getPropertyByNameReadsAConfiguredValue() {
		assertEquals("feedback/createFeedback", ConfigProperties.getPropertyByName("create-feedback"));
	}

	@Test
	void getPropertyByNameIsNullForAnUnknownKey() {
		assertNull(ConfigProperties.getPropertyByName("no.such.property"));
	}

	@Test
	void getBooleanParsesAConfiguredFlag() {
		assertTrue(ConfigProperties.getBoolean("iemr.extend.expiry.time"));
		assertFalse(ConfigProperties.getBoolean("enableIPValidation"));
	}

	@Test
	void getBooleanIsFalseForAnUnknownKey() {
		assertFalse(ConfigProperties.getBoolean("no.such.property"));
	}

	@Test
	void getIntegerParsesAConfiguredNumber() {
		assertEquals(7200, ConfigProperties.getInteger("iemr.session.expiry.time"));
	}

	@Test
	void getIntegerFallsBackToZeroForAnUnparseableValue() {
		assertEquals(0, ConfigProperties.getInteger("create-feedback"));
	}

	@Test
	void getLongParsesAConfiguredNumber() {
		assertEquals(7200L, ConfigProperties.getLong("iemr.session.expiry.time"));
	}

	@Test
	void getLongFallsBackToZeroForAnUnparseableValue() {
		assertEquals(0L, ConfigProperties.getLong("create-feedback"));
	}

	@Test
	void getFloatParsesAConfiguredNumber() {
		assertEquals(7200F, ConfigProperties.getFloat("iemr.session.expiry.time"));
	}

	@Test
	void getFloatFallsBackToZeroForAnUnparseableValue() {
		assertEquals(0F, ConfigProperties.getFloat("create-feedback"));
	}

	@Test
	void getPasswordReturnsAPlainValueUnchanged() {
		assertEquals("feedback/createFeedback", ConfigProperties.getPassword("create-feedback"));
	}

	@Test
	void getPasswordDecodesTheObfuscatedPrefix() {
		String encoded = "0X10:" + Base64.getEncoder().encodeToString("s3cret".getBytes());
		loadedProperties().setProperty("test.password", encoded);

		assertEquals("s3cret", ConfigProperties.getPassword("test.password"));
	}

	@Test
	void getPasswordIsNullForAnUnknownKey() {
		assertNull(ConfigProperties.getPassword("no.such.property"));
	}

	@Test
	void redisSettingsAreUnresolvedBecauseTheyLookUpIemrPrefixedKeys() {
		// application.properties configures spring.redis.*, not iemr.redis.*.
		assertNull(ConfigProperties.getRedisUrl());
		assertEquals(0, ConfigProperties.getRedisPort());
	}

	@Test
	void sessionExpiryTimeComesFromTheConfiguredValue() {
		assertEquals(7200, ConfigProperties.getSessionExpiryTime());
	}

	@Test
	void extendExpiryTimeIsFalseBecauseItParsesTheSessionExpirySeconds() {
		// getExtendExpiryTime() reads iemr.session.expiry.time (7200), not
		// iemr.extend.expiry.time, so Boolean.parseBoolean always yields false.
		assertFalse(ConfigProperties.getExtendExpiryTime());
		assertTrue(ConfigProperties.getBoolean("iemr.extend.expiry.time"));
	}

	@Test
	void setEnvironmentStoresTheInjectedEnvironment() {
		Environment environment = mock(Environment.class);
		ConfigProperties configProperties = new ConfigProperties();

		configProperties.setEnvironment(environment);

		assertEquals(environment, ReflectionTestUtils.getField(ConfigProperties.class, "environment"));
	}
}
