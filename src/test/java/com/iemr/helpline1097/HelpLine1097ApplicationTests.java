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
package com.iemr.helpline1097;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.iemr.helpline1097.utils.IEMRApplBeans;

class HelpLine1097ApplicationTests {

	@Test
	void mainDelegatesToSpringApplication() {
		String[] args = { "--server.port=0" };

		try (MockedStatic<SpringApplication> springApplication = Mockito.mockStatic(SpringApplication.class)) {
			HelpLine1097Application.main(args);

			springApplication.verify(() -> SpringApplication.run(HelpLine1097Application.class, args));
		}
	}

	@Test
	void servletInitializerRegistersTheApplicationSource() {
		SpringApplicationBuilder builder = mock(SpringApplicationBuilder.class);
		when(builder.sources(any(Class[].class))).thenReturn(builder);

		SpringApplicationBuilder result = new HelpLine1097Application().configure(builder);

		assertSame(builder, result);
		verify(builder).sources(HelpLine1097Application.class);
	}

	@Test
	void applicationBeansAreInstantiated() {
		IEMRApplBeans beans = new HelpLine1097Application().instantiateBeans();

		assertNotNull(beans);
	}

	@Test
	void redisTemplateUsesStringKeysAndJsonUserValues() {
		RedisConnectionFactory factory = mock(RedisConnectionFactory.class);

		RedisTemplate<String, Object> template = new HelpLine1097Application().redisTemplate(factory);

		assertSame(factory, template.getConnectionFactory());
		assertEquals(StringRedisSerializer.class, template.getKeySerializer().getClass());
		assertNotNull(template.getValueSerializer());
	}
}
