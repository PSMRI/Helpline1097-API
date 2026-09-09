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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.util.ReflectionTestUtils;

import com.iemr.helpline1097.utils.gateway.email.GenericEmailServiceImpl;

class IEMRApplBeansTest {

	private IEMRApplBeans beans;

	@BeforeEach
	void setUp() {
		beans = new IEMRApplBeans();
		ReflectionTestUtils.setField(beans, "redisHost", "redis.internal");
		ReflectionTestUtils.setField(beans, "redisPort", 6379);
	}

	@Test
	void validatorBeanIsProvided() {
		assertNotNull(beans.getVaidator());
	}

	@Test
	void emailServiceBeanIsTheGenericImplementation() {
		assertTrue(beans.getEmailService() instanceof GenericEmailServiceImpl);
	}

	@Test
	void mailSenderBeanIsProvided() {
		assertTrue(beans.getJavaMailSender() instanceof JavaMailSenderImpl);
	}

	@Test
	void configPropertiesBeanIsProvided() {
		assertNotNull(beans.configProperties());
	}

	@Test
	void sessionObjectBeanIsProvided() {
		assertNotNull(beans.sessionObject());
	}

	@Test
	void redisStorageBeanIsProvided() {
		assertNotNull(beans.redisStorage());
	}

	@Test
	void redisConnectionFactoryUsesTheConfiguredHostAndPort() {
		LettuceConnectionFactory factory = beans.connectionFactory();

		assertEquals("redis.internal", factory.getHostName());
		assertEquals(6379, factory.getPort());
	}
}
