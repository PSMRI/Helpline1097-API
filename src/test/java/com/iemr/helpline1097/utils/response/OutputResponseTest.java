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
package com.iemr.helpline1097.utils.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.text.ParseException;

import org.hibernate.JDBCException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import com.iemr.helpline1097.utils.exception.IEMRException;

class OutputResponseTest {

	@Test
	void aJsonObjectResponseIsPassedThroughAsAnObject() {
		OutputResponse response = new OutputResponse();

		response.setResponse("{\"feedbackID\":1}");

		assertTrue(response.isSuccess());
		assertEquals(OutputResponse.SUCCESS, response.getStatusCode());
		assertEquals("Success", response.getErrorMessage());
		assertEquals("{\"feedbackID\":1}", response.getData());
	}

	@Test
	void aJsonArrayResponseIsPassedThroughAsAnArray() {
		OutputResponse response = new OutputResponse();

		response.setResponse("[{\"feedbackID\":1}]");

		assertTrue(response.isSuccess());
		assertTrue(response.getData().startsWith("["));
	}

	@Test
	void aPlainStringResponseIsWrappedInAResponseField() {
		OutputResponse response = new OutputResponse();

		response.setResponse("callType Added");

		assertTrue(response.isSuccess());
		assertTrue(response.getData().contains("callType Added"));
	}

	@Test
	void anUnsetResponseReportsTheGenericFailure() {
		OutputResponse response = new OutputResponse();

		assertFalse(response.isSuccess());
		assertEquals(OutputResponse.GENERIC_FAILURE, response.getStatusCode());
		assertEquals("Failed with generic error", response.getErrorMessage());
		assertEquals("FAILURE", response.getStatus());
		assertNull(response.getData());
	}

	@Test
	void anIemrExceptionIsReportedAsALoginFailure() {
		OutputResponse response = new OutputResponse();

		response.setError(new IEMRException("bad login"));

		assertEquals(OutputResponse.USERID_FAILURE, response.getStatusCode());
		assertEquals("User login failed", response.getStatus());
		assertEquals("bad login", response.getErrorMessage());
	}

	@Test
	void aJsonExceptionIsReportedAsAnObjectConversionFailure() {
		OutputResponse response = new OutputResponse();

		response.setError(new JSONException("bad json"));

		assertEquals(OutputResponse.OBJECT_FAILURE, response.getStatusCode());
		assertEquals("Invalid object conversion", response.getStatus());
		assertEquals("Invalid object conversion", response.getErrorMessage());
	}

	@Test
	void databaseAndParsingFailuresAreReportedAsCodeExceptions() {
		OutputResponse sqlFailure = new OutputResponse();
		sqlFailure.setError(new SQLException("deadlock"));
		OutputResponse parseFailure = new OutputResponse();
		parseFailure.setError(new ParseException("bad date", 0));
		OutputResponse nullFailure = new OutputResponse();
		nullFailure.setError(new NullPointerException("npe"));

		assertEquals(OutputResponse.CODE_EXCEPTION, sqlFailure.getStatusCode());
		assertEquals(OutputResponse.CODE_EXCEPTION, parseFailure.getStatusCode());
		assertEquals(OutputResponse.CODE_EXCEPTION, nullFailure.getStatusCode());
		assertTrue(sqlFailure.getStatus().startsWith("Failed with critical errors"));
	}

	@Test
	void connectivityFailuresAreReportedAsEnvironmentExceptions() {
		OutputResponse ioFailure = new OutputResponse();
		ioFailure.setError(new IOException("socket closed"));
		OutputResponse connectFailure = new OutputResponse();
		connectFailure.setError(new ConnectException("refused"));

		assertEquals(OutputResponse.ENVIRONMENT_EXCEPTION, ioFailure.getStatusCode());
		assertEquals(OutputResponse.ENVIRONMENT_EXCEPTION, connectFailure.getStatusCode());
		assertTrue(ioFailure.getStatus().startsWith("Failed with connection issues"));
	}

	@Test
	void jdbcFailuresAreReportedAsDatabaseConnectionIssues() {
		OutputResponse response = new OutputResponse();

		response.setError(new JDBCException("pool exhausted", new SQLException("no connection")));

		assertEquals(OutputResponse.ENVIRONMENT_EXCEPTION, response.getStatusCode());
		assertTrue(response.getStatus().startsWith("Failed with DB connection issues"));
	}

	@Test
	void anyOtherThrowableFallsBackToTheGenericFailure() {
		OutputResponse response = new OutputResponse();

		response.setError(new IllegalStateException("boom"));

		assertEquals(OutputResponse.GENERIC_FAILURE, response.getStatusCode());
		assertEquals("boom", response.getErrorMessage());
		assertTrue(response.getStatus().startsWith("Failed with boom"));
	}

	@Test
	void anExplicitErrorCodeAndMessageAreUsedVerbatim() {
		OutputResponse response = new OutputResponse();

		response.setError(OutputResponse.PREVILAGE_FAILURE, "no access", "Forbidden");

		assertEquals(OutputResponse.PREVILAGE_FAILURE, response.getStatusCode());
		assertEquals("no access", response.getErrorMessage());
		assertEquals("Forbidden", response.getStatus());
	}

	@Test
	void anExplicitErrorMessageDoublesAsTheStatus() {
		OutputResponse response = new OutputResponse();

		response.setError(OutputResponse.PASSWORD_FAILURE, "wrong password");

		assertEquals("wrong password", response.getErrorMessage());
		assertEquals("wrong password", response.getStatus());
	}

	@Test
	void toStringOmitsNullFieldsAndSerialisesLongsAsStrings() {
		OutputResponse response = new OutputResponse();

		String json = response.toString();

		assertFalse(json.contains("\"data\""));
		assertTrue(json.contains("\"statusCode\":5000"));
	}

	@Test
	void toStringWithSerializationKeepsNullFields() {
		OutputResponse response = new OutputResponse();

		assertTrue(response.toStringWithSerialization().contains("\"data\":null"));
	}
}
