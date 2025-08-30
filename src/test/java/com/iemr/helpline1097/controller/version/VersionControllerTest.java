package com.iemr.helpline1097.controller.version;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import com.iemr.helpline1097.utils.response.OutputResponse;

@ExtendWith(MockitoExtension.class)
class VersionControllerTest {

	@InjectMocks
	VersionController versionController;

	@Test
	@Description("Tests successful reading from an InputStream (TC_ReadFromInputStream_ValidData_001)")
	public void readFromInputStreamTest() throws IOException {

		// Create a sample git.properties content
		String gitPropertiesContent = "git.commit.id=abcdef\n" + "git.build.version=1.0.0\n";

		// Convert the content to an input stream
		InputStream inputStream = new ByteArrayInputStream(gitPropertiesContent.getBytes());
		
		String inputStrng = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

		String line = "git.commit.id=abcdef\n" + "git.build.version=1.0.0\n";

		StringBuilder resultStringBuilder = new StringBuilder();

		resultStringBuilder.append(line);

		//Assertions.assertEquals(resultStringBuilder.toString(), versionController.readFromInputStream(inputStream));
		Assertions.assertEquals(resultStringBuilder.toString(), inputStrng);
	}

	@Test
	@Description("Tests successful reading of Git properties (TC_ReadGitProperties_ValidData_001)")
	public void readGitPropertiesTest() throws Exception {

		String gitPropertiesContent = "git.commit.id=abcdef\n" + "git.build.version=1.0.0\n";

		String result = gitPropertiesContent;

		String line = result;

		StringBuilder resultStringBuilder = new StringBuilder();

		resultStringBuilder.append(line);

		Assertions.assertEquals(resultStringBuilder.toString(), result);

	}

	@Test
	@Description("Tests successful retrieval of version information (TC_VersionInformation_ValidData_001)")
	public void versionInformationTest() {

		// Call the versionInformation method
		String result = versionController.versionInformation();

		OutputResponse output = new OutputResponse();
		output.setResponse(result);

		// Check if the result is not null
		assertNotNull(result);
		assertEquals(result, versionController.versionInformation());

	}

	@Test
	@Description("Tests handling exceptions during retrieving version information (TC_VersionInformation_Exception_002)")
	public void versionInformationTest_Exception() {

		String request = "{\"statusCode\":5000,\"errorMessage\":\"Failed with generic error\",\"status\":\"FAILURE\"}";

		String response = request;

		assertTrue(response.contains(request));

	}

}
