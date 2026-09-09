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
package com.iemr.helpline1097.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Drives the hand-written accessors on the JPA entities: every {@code setX} is
 * called with a representative value and the matching {@code getX}/{@code isX}
 * must hand it back. Entities carry no logic beyond these pairs, so a
 * round-trip is the whole contract worth asserting on them.
 */
final class EntityAccessors {

	private EntityAccessors() {
	}

	/**
	 * Several entities render themselves through the static
	 * {@code OutputMapper.gson()}, whose shared builder is only populated by the
	 * OutputMapper constructor. Calling toString() before any OutputMapper has
	 * been built throws, so prime it once.
	 */
	static void primeOutputMapper() {
		new com.iemr.helpline1097.utils.mapper.OutputMapper();
	}

	/** Asserts a set/get round trip for every accessor pair on the entity. */
	static void assertRoundTrip(Class<?> entityType) {
		Object entity = instantiate(entityType);
		assertNotNull(entity, entityType.getSimpleName() + " must expose a no-argument constructor");

		int pairs = 0;
		for (Method setter : entityType.getMethods()) {
			if (!isSetter(setter)) {
				continue;
			}
			Method getter = findGetter(entityType, setter);
			if (getter == null) {
				continue;
			}
			Object value = sampleValueFor(setter.getParameterTypes()[0]);
			if (value == null) {
				continue;
			}
			try {
				setter.invoke(entity, value);
				assertEquals(value, getter.invoke(entity),
						entityType.getSimpleName() + "." + getter.getName() + " must return what "
								+ setter.getName() + " stored");
				pairs++;
			} catch (ReflectiveOperationException e) {
				fail(entityType.getSimpleName() + "." + setter.getName() + " failed: " + e.getCause());
			}
		}
		assertNotNull(entity.toString(), entityType.getSimpleName() + ".toString() must not be null");
		if (pairs == 0) {
			fail(entityType.getSimpleName() + " exposed no accessor pairs to exercise");
		}
	}

	/** Asserts a round trip for every entity in the given group. */
	static void assertRoundTrips(Class<?>... entityTypes) {
		for (Class<?> entityType : entityTypes) {
			assertRoundTrip(entityType);
		}
	}

	private static Object instantiate(Class<?> entityType) {
		try {
			Constructor<?> constructor = entityType.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (ReflectiveOperationException e) {
			return null;
		}
	}

	private static boolean isSetter(Method method) {
		return method.getName().startsWith("set") && method.getName().length() > 3
				&& method.getParameterCount() == 1 && Modifier.isPublic(method.getModifiers())
				&& !Modifier.isStatic(method.getModifiers()) && method.getReturnType() == void.class;
	}

	private static Method findGetter(Class<?> entityType, Method setter) {
		String property = setter.getName().substring(3);
		Class<?> valueType = setter.getParameterTypes()[0];
		for (String prefix : new String[] { "get", "is" }) {
			try {
				Method getter = entityType.getMethod(prefix + property);
				if (getter.getReturnType() == valueType) {
					return getter;
				}
			} catch (NoSuchMethodException ignored) {
				// try the next prefix
			}
		}
		return null;
	}

	private static Object sampleValueFor(Class<?> type) {
		if (type == String.class) {
			return "sample";
		}
		if (type == Integer.class || type == int.class) {
			return 7;
		}
		if (type == Long.class || type == long.class) {
			return 11L;
		}
		if (type == Short.class || type == short.class) {
			return (short) 3;
		}
		if (type == Boolean.class || type == boolean.class) {
			return Boolean.TRUE;
		}
		if (type == Double.class || type == double.class) {
			return 1.5d;
		}
		if (type == Float.class || type == float.class) {
			return 1.5f;
		}
		if (type == Character.class || type == char.class) {
			return 'x';
		}
		if (type == BigInteger.class) {
			return BigInteger.valueOf(13L);
		}
		if (type == BigDecimal.class) {
			return BigDecimal.valueOf(13L);
		}
		if (type == Timestamp.class) {
			return new Timestamp(1_700_000_000_000L);
		}
		if (type == Date.class) {
			return new Date(1_700_000_000_000L);
		}
		if (type == List.class) {
			return new ArrayList<>();
		}
		if (type == Set.class) {
			return new HashSet<>();
		}
		if (type == Map.class) {
			return new HashMap<>();
		}
		if (type.getName().startsWith("com.iemr.helpline1097.data")) {
			return instantiate(type);
		}
		return null;
	}
}
