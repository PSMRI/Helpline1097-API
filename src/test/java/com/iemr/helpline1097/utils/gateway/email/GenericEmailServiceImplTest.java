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
package com.iemr.helpline1097.utils.gateway.email;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class GenericEmailServiceImplTest {

	private static final String REQUEST = "{\"to\":\"a@example.org;b@example.org\",\"from\":\"noreply@example.org\","
			+ "\"subject\":\"Feedback\",\"message\":\"Body text\"}";

	@Mock
	private JavaMailSender javaMailSender;

	private GenericEmailServiceImpl emailService;

	@BeforeEach
	void setUp() {
		emailService = new GenericEmailServiceImpl();
		emailService.setJavaMailSender(javaMailSender);
	}

	private SimpleMailMessage sentMessage() {
		ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(javaMailSender).send(captor.capture());
		return captor.getValue();
	}

	@Test
	void sendEmailWithTemplateSendsTheWholeToFieldAsOneRecipient() {
		emailService.sendEmail(REQUEST, "feedback-template");

		SimpleMailMessage message = sentMessage();
		assertArrayEquals(new String[] { "a@example.org;b@example.org" }, message.getTo());
		assertEquals("noreply@example.org", message.getFrom());
		assertEquals("Feedback", message.getSubject());
		assertEquals("Body text", message.getText());
	}

	@Test
	void sendEmailSplitsSemicolonSeparatedRecipients() {
		emailService.sendEmail(REQUEST);

		assertArrayEquals(new String[] { "a@example.org", "b@example.org" }, sentMessage().getTo());
	}

	@Test
	void sendEmailRejectsAPayloadMissingRequiredFields() {
		assertThrows(JSONException.class, () -> emailService.sendEmail("{\"to\":\"a@example.org\"}"));

		verifyNoInteractions(javaMailSender);
	}

	@Test
	void sendEmailWithAttachmentIsNotImplementedAndSendsNothing() {
		emailService.sendEmailWithAttachment(REQUEST, "feedback-template");

		verifyNoInteractions(javaMailSender);
	}
}
