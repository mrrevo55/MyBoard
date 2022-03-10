package com.revo.myboard.controler;
/*
 * Created By Revo
 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revo.myboard.Utils;
import com.revo.myboard.security.dto.CodeDTO;
import com.revo.myboard.security.dto.CredentialsDTO;
import com.revo.myboard.security.dto.RegisterDTO;
import com.revo.myboard.user.dto.EmailDTO;

/*
 * Created By Revo
 */

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	//LOGIN END POINT
	
	@Test
	void shouldLoginUser() throws Exception {
		var credentialsDTO = new CredentialsDTO();
		credentialsDTO.setLogin("test");
		credentialsDTO.setPassword("test");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(credentialsDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void shouldThrow401WhileLoging() throws Exception {
		var credentialsDTO = new CredentialsDTO();
		credentialsDTO.setLogin("testError");
		credentialsDTO.setPassword("testError");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(credentialsDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(401));
	}
	
	//REGISTER END POINT
	
	@Test
	void shouldRegisterUser() throws Exception {
		var registerDTO = new RegisterDTO();
		registerDTO.setEmail("anetawolna55@wp.pl");
		registerDTO.setLogin("register");
		registerDTO.setPassword("register");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(registerDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/register").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}

	@Test
	void shouldThrow400WhileRegistering() throws Exception {
		var registerDTO = new RegisterDTO();
		registerDTO.setEmail("registerErroremail.pl");
		registerDTO.setLogin("registerError");
		registerDTO.setPassword("registerError");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(registerDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/register").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	// RESEND ACTIVATION CODE END POINT
	
	@Test
	void shouldResendActivationCode() throws Exception {
		var emailDTO = new EmailDTO();
		emailDTO.setEmail("game-tspeak@wp.pl");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(emailDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/resendActivationCode").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void shouldThrow404WhileCodeResending() throws Exception {
		var emailDTO = new EmailDTO();
		emailDTO.setEmail("notexistsemail@email.pl");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(emailDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/resendActivationCode").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void shouldThrow400WhileCodeResending() throws Exception {
		var emailDTO = new EmailDTO();
		emailDTO.setEmail("notexistsemailemail.pl");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(emailDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/resendActivationCode").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	//ACTIVE END POINT
	
	@Test
	void shouldActiveUser() throws Exception {
		var codeDTO = new CodeDTO();
		codeDTO.setCode("codeActive");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(codeDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/active").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void shouldThrow400WhileActivating() throws Exception {
		var codeDTO = new CodeDTO();
		codeDTO.setCode("123456789");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(codeDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/active").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void shouldThrow404WhileActivating() throws Exception {
		var codeDTO = new CodeDTO();
		codeDTO.setCode("notFoundCo");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(codeDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/active").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
}
