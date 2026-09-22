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
package com.iemr.helpline1097.utils.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.ws.rs.core.MediaType;

class HttpUtilsTest {

	private HttpUtils httpUtils;
	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		httpUtils = new HttpUtils();
		restTemplate = org.mockito.Mockito.mock(RestTemplate.class);
		ReflectionTestUtils.setField(httpUtils, "rest", restTemplate);
	}

	@SuppressWarnings("unchecked")
	private void stubExchange(ResponseEntity<String> response) {
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
				.thenReturn(response);
	}

	@Test
	void getReturnsTheResponseBodyAndRecordsTheStatus() {
		stubExchange(ResponseEntity.ok("payload"));

		assertEquals("payload", httpUtils.get("http://svc/resource"));
		assertEquals(HttpStatus.OK, httpUtils.getStatus());
	}

	@Test
	void getWithHeadersForwardsAuthorizationAndContentType() {
		stubExchange(ResponseEntity.ok("payload"));
		HashMap<String, Object> headers = new HashMap<>();
		headers.put(HttpHeaders.AUTHORIZATION, "Bearer token");
		headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

		assertEquals("payload", httpUtils.get("http://svc/resource", headers));

		ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
		verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), captor.capture(), eq(String.class));
		assertEquals("Bearer token", captor.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
		assertEquals(MediaType.APPLICATION_JSON, captor.getValue().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
	}

	@Test
	void getWithHeadersDefaultsContentTypeToJson() {
		stubExchange(ResponseEntity.ok("payload"));

		httpUtils.get("http://svc/resource", new HashMap<>());

		ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
		verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), captor.capture(), eq(String.class));
		assertEquals(MediaType.APPLICATION_JSON, captor.getValue().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
	}

	@Test
	void postSendsTheJsonBody() {
		stubExchange(ResponseEntity.ok("created"));

		assertEquals("created", httpUtils.post("http://svc/resource", "{\"a\":1}"));

		ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
		verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), captor.capture(), eq(String.class));
		assertEquals("{\"a\":1}", captor.getValue().getBody());
	}

	@Test
	void postWithHeadersForwardsAuthorization() {
		stubExchange(ResponseEntity.ok("created"));
		HashMap<String, Object> headers = new HashMap<>();
		headers.put(HttpHeaders.AUTHORIZATION, "Bearer token");

		assertEquals("created", httpUtils.post("http://svc/resource", "data", headers));

		ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
		verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), captor.capture(), eq(String.class));
		assertEquals("Bearer token", captor.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
	}

	@Test
	void postPropagatesTransportFailures() {
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
				.thenThrow(new RestClientException("connection refused"));

		assertThrows(RestClientException.class, () -> httpUtils.post("http://svc/resource", "data"));
	}

	@Test
	void uploadFileSendsAPlainBodyForNonMultipartRequests() throws IOException {
		stubExchange(ResponseEntity.ok("uploaded"));
		HashMap<String, Object> headers = new HashMap<>();
		headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

		assertEquals("uploaded", httpUtils.uploadFile("http://svc/upload", "payload", headers));
	}

	@Test
	void uploadFileDefaultsToJsonWhenNoContentTypeIsGiven() throws IOException {
		stubExchange(ResponseEntity.ok("uploaded"));

		assertEquals("uploaded", httpUtils.uploadFile("http://svc/upload", "payload", new HashMap<>()));
	}

	@Test
	void uploadFileCannotTakeTheMultipartPathBecauseJerseyMultipartNeedsTheAbsentJavaxJaxRsApi(@TempDir Path tempDir)
			throws IOException {
		Path document = tempDir.resolve("doc.txt");
		Files.writeString(document, "hello");
		HashMap<String, Object> headers = new HashMap<>();
		headers.put(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA);

		// com.sun.jersey.multipart.FormDataMultiPart is a JAX-RS 1.x type and resolves
		// javax.ws.rs.core.MediaType, which this application does not ship.
		assertThrows(Error.class, () -> httpUtils.uploadFile("http://svc/upload", document.toString(), headers));
	}

	@Test
	void setStatusIsReadableThroughGetStatus() {
		httpUtils.setStatus(HttpStatus.CONFLICT);

		assertEquals(HttpStatus.CONFLICT, httpUtils.getStatus());
	}

	@Test
	void getWithoutAnAuthorizationEntrySendsNoAuthorizationHeader() {
		stubExchange(ResponseEntity.ok("payload"));

		assertEquals("payload", httpUtils.get("http://svc/resource", new HashMap<>()));

		ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
		verify(restTemplate).exchange(anyString(), eq(HttpMethod.GET), captor.capture(), eq(String.class));
		assertFalse(captor.getValue().getHeaders().containsKey(HttpHeaders.AUTHORIZATION));
	}

	@Test
	void postWithoutAnAuthorizationEntrySendsNoAuthorizationHeader() {
		stubExchange(ResponseEntity.ok("created"));

		assertEquals("created", httpUtils.post("http://svc/resource", "data", new HashMap<>()));

		ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
		verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), captor.capture(), eq(String.class));
		assertFalse(captor.getValue().getHeaders().containsKey(HttpHeaders.AUTHORIZATION));
	}

	@Test
	void uploadFileForwardsTheAuthorizationEntryWhenOneIsGiven() throws IOException {
		stubExchange(ResponseEntity.ok("uploaded"));
		HashMap<String, Object> headers = new HashMap<>();
		headers.put(HttpHeaders.AUTHORIZATION, "Bearer token");

		assertEquals("uploaded", httpUtils.uploadFile("http://svc/upload", "payload", headers));

		ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
		verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), captor.capture(), eq(String.class));
		assertEquals("Bearer token", captor.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
	}
}
