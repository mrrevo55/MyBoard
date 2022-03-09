package com.revo.myboard.controler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revo.myboard.Utils;
import com.revo.myboard.comment.dto.ContentDTO;
import com.revo.myboard.comment.dto.CreateDTO;

/*
 * Created By Revo
 */

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	// GET END POINT

	@Test
	void shouldGetComment() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/comment/1")).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileGetting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/comment/-1")).andExpect(MockMvcResultMatchers.status().is(404));
	}

	// CREATE END POINT

	@Test
	void shouldCreateComment() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setContent("testComment");
		createDTO.setPost(1);
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/comment/create").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)).contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	@Test
	void shouldThrow404WhileCreating() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setContent("testComment");
		createDTO.setPost(-1);
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/comment/create").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)).contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	// EDIT END POINT
	
	@Test
	void shouldEditCommentAsUser() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setNewContent("testRenameUserChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/comment/edit/2").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)).contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void shouldThrow404WhileEditingAsUser() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setNewContent("testRenameUserChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/comment/edit/1").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)).contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void shouldEditCommentAsModerator() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setNewContent("testRenameModeratorChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/comment/edit/2").header("Authorization", Utils.getTokenForModerator(mockMvc, objectMapper)).contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void shouldThrow404WhileEditingAsModerator() throws Exception {
		var contentDTO = new ContentDTO();
		contentDTO.setNewContent("testRenameModeratorChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(contentDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/comment/edit/-1").header("Authorization", Utils.getTokenForModerator(mockMvc, objectMapper)).contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	// DELETE END POINT
	
	@Test
	void shouldDeleteCommentAsUser() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/comment/delete/3").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void shouldThrow404WhileDeletingAsUser() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/comment/delete/1").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	void shouldDeleteCommentAsModerator() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/comment/delete/4").header("Authorization", Utils.getTokenForModerator(mockMvc, objectMapper)))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void shouldThrow404WhileDeletingAsModerator() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/comment/delete/-1").header("Authorization", Utils.getTokenForModerator(mockMvc, objectMapper)))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	// GIVE LIKE END POINT
	
	@Test
	void shouldGiveLike() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/comment/like/6").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	@Test
	void shouldThrow400WhileGivingLike() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/comment/like/5").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	// REMOVE LIKE END POINT
	
	@Test
	void shouldRemoveLike() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/comment/unlike/7").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void shouldThrow404WhileRemovingLike() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/comment/unlike/8").header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper)))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
}
