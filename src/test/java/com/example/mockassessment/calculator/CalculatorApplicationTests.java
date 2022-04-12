package com.example.mockassessment.calculator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
		// using TestRestTemplate for integration testing requires server to be running
	}

	@Test
	void returnCorrectResult() throws Exception {
		// construst payload
		JsonObject payload = Json.createObjectBuilder()
				.add("oper1", 1)
				.add("oper2", 2)
				.add("ops", "plus")
				.build();

		// construct http call
		RequestBuilder req = MockMvcRequestBuilders.post("/calculate")
				.contentType(MediaType.APPLICATION_JSON)
				.header("user-Agent", "junit")
				.accept(MediaType.APPLICATION_JSON)
				.content(payload.toString());

		// make invokation
		MvcResult resp = mockMvc.perform(req).andReturn();
		MockHttpServletResponse httpResp = resp.getResponse();

		// check status code
		Assertions.assertEquals(200, httpResp.getStatus());

		// check payload
		Optional<JsonObject> opt = string2Json(httpResp.getContentAsString());
		assertTrue(opt.isPresent());

		JsonObject object = opt.get();
		for (String key : List.of("result", "timestamp", "userAgent")) {
			assertNotNull(object.get(key));
		}

		assertSame(1 + 2, object.getInt("result"));
	}

	public static Optional<JsonObject> string2Json(String s) {
		try (InputStream is = new ByteArrayInputStream(s.getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonObject data = reader.readObject();
			return Optional.of(data);
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	@Test
	void shouldFailBadOps() throws Exception {
		// construst payload
		JsonObject payload = Json.createObjectBuilder()
				.add("oper1", 1)
				.add("oper2", 2)
				.add("ops", "bad")
				.build();

		// construct http call
		RequestBuilder req = MockMvcRequestBuilders.post("/calculate")
				.contentType(MediaType.APPLICATION_JSON)
				.header("user-Agent", "junit")
				.accept(MediaType.APPLICATION_JSON)
				.content(payload.toString());

		// make invokation
		MvcResult resp = mockMvc.perform(req).andReturn();
		MockHttpServletResponse httpResp = resp.getResponse();

		// check status code
		Assertions.assertEquals(400, httpResp.getStatus());
	}
}
