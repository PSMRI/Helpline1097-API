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
package com.iemr.helpline1097.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import com.iemr.helpline1097.data.co.beneficiary.User;

class RedisConfigTest {

	private final RedisConfig redisConfig = new RedisConfig();

	@Test
	void redisKeyspaceNotificationsAreLeftAloneOnManagedRedis() {
		assertSame(ConfigureRedisAction.NO_OP, redisConfig.configureRedisAction());
	}

	@Test
	void redisTemplateUsesStringKeysAndJsonUserValues() {
		RedisConnectionFactory factory = mock(RedisConnectionFactory.class);

		RedisTemplate<String, User> template = redisConfig.redisTemplate(factory);

		assertSame(factory, template.getConnectionFactory());
		assertEquals(StringRedisSerializer.class, template.getKeySerializer().getClass());
		assertNotNull(template.getValueSerializer());
	}
}
