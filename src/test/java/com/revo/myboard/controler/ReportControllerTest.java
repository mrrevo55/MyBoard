package com.revo.myboard.controler;
/*
 * Created By Revo
 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revo.myboard.Utils;
import com.revo.myboard.report.dto.ContentDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	// GET END POINT

	@Test
	void shouldGetReportAsUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/report/4").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileGettingAsUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/report/1").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	void shouldGetReportAsModerator() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/report/4").header("Authorization",
				Utils.getTokenForModerator(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileGettingAsModerator() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/report/-1").header("Authorization",
				Utils.getTokenForModerator(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(404));
	}

	// POST REPORT END POINT

	@Test
	void shouldReportPost() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setContent("testReportPost");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/report/post/1")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	@Test
	void shouldThrow404WhileReportingPost() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setContent("testReportPost404");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/report/post/-1")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void shouldThrow400WhileReportingPost() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setContent("testReportPost400");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/report/post/4")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	// COMMENT REPORT END POINT
	
	@Test
	void shouldReportComment() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setContent("testReportComment");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/report/comment/1")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	@Test
	void shouldThrow404WhileReportingComment() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setContent("testReportComment");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/report/comment/-1")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void shouldThrow400WhileReportingComment() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setContent("testReportComment");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/report/comment/2")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	

	// CHECK END POINT

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldSetCheckedReport() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/report/check/1"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldThrow400WhileChecking() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/report/check/2"))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldThrow404WhileChecking() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/report/check/-1"))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	// NOT CHECKED END POINT

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldGetAllNotChecked() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/report/notchecked"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	// DELETE END POINT

	@Test
	@WithMockUser(roles = "ADMIN")
	void shouldDeleteReport() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/report/delete/3"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void shouldThrow404WhileDeleting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/report/delete/-1"))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

}
