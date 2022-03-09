package com.revo.myboard;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revo.myboard.security.dto.CredentialsDTO;

/*
 * Created By Revo
 */

public class Utils {
	// MEDIA TYPE JSON
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	//TOKEN GETTERS
	
	public static String getTokenForUser(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
		var credentialsDTO = new CredentialsDTO();
		credentialsDTO.setLogin("userToken");
		credentialsDTO.setPassword("test");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(credentialsDTO);
		return mockMvc.perform(
				MockMvcRequestBuilders.post("/login").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn().getResponse().getHeader("Authorization");
	}
	
	public static String getTokenForUserChangingPassword(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
		var credentialsDTO = new CredentialsDTO();
		credentialsDTO.setLogin("userTokenPassword");
		credentialsDTO.setPassword("test");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(credentialsDTO);
		return mockMvc.perform(
				MockMvcRequestBuilders.post("/login").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn().getResponse().getHeader("Authorization");
	}
	
	public static String getTokenForUserDeleting(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
		var credentialsDTO = new CredentialsDTO();
		credentialsDTO.setLogin("userTokenDelete");
		credentialsDTO.setPassword("test");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(credentialsDTO);
		return mockMvc.perform(
				MockMvcRequestBuilders.post("/login").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn().getResponse().getHeader("Authorization");
	}
	
	public static String getTokenForModerator(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
		var credentialsDTO = new CredentialsDTO();
		credentialsDTO.setLogin("moderatorToken");
		credentialsDTO.setPassword("test");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(credentialsDTO);
		return mockMvc.perform(
				MockMvcRequestBuilders.post("/login").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn().getResponse().getHeader("Authorization");
	}
	
	public static String getTokenForAdmin(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
		var credentialsDTO = new CredentialsDTO();
		credentialsDTO.setLogin("adminToken");
		credentialsDTO.setPassword("test");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(credentialsDTO);
		return mockMvc.perform(
				MockMvcRequestBuilders.post("/login").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn().getResponse().getHeader("Authorization");
	}
	
}
