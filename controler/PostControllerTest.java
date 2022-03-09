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
import com.revo.myboard.post.dto.CreateDTO;
import com.revo.myboard.post.dto.EditDTO;

/*
 * Created By Revo
 */

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	// GET END POINT

	@Test
	void shouldGetPost() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/post/1")).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileGetting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/post/-1")).andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	void shouldGetLastActivePosts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/post/lastActive"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldGetMostLikedPosts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/post/mostLiked"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	// SEARCH END POINT

	@Test
	void shouldSearchPosts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/post/search/content"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	// CREATE END POINT

	@Test
	void shouldCreatePost() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setCategory(1);
		createDTO.setContent("testCreate");
		createDTO.setTitle("testCreate");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/post/create")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}

	@Test
	void shouldThrow400WhileCreating() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setCategory(1);
		createDTO.setContent("testGet");
		createDTO.setTitle("testGet");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/post/create")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	void shouldThrow404WhileCreating() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setCategory(-1);
		createDTO.setContent("testCreate404");
		createDTO.setTitle("testCreate404");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/post/create")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	// DELETE END POINT

	@Test
	void shouldDeletePostAsUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/post/delete/2").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileDeletingAsUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/post/delete/1").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	void shouldDeletePostAsModerator() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/post/delete/3").header("Authorization",
				Utils.getTokenForModerator(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileDeletingAsModerator() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/post/delete/-1").header("Authorization",
				Utils.getTokenForModerator(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(404));
	}

	// EDIT TITLE END POINT

	@Test
	void shouldEditAsUser() throws Exception {
		var editDTO = new EditDTO();
		editDTO.setTitle("testRenameUserChanged");
		editDTO.setContent("testRenameUserChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/post/edit/4")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileEditingAsUser() throws Exception {
		var editDTO = new EditDTO();
		editDTO.setTitle("testRenameUserChanged");
		editDTO.setContent("testRenameUserChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/post/edit/1")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	void shouldThrow400WhileEditingAsUser() throws Exception {
		var editDTO = new EditDTO();
		editDTO.setTitle("t");
		editDTO.setContent("");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/post/edit/4")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	void shouldEditAsModerator() throws Exception {
		var editDTO = new EditDTO();
		editDTO.setTitle("testRenameModeratorChanged");
		editDTO.setContent("testRenameModeratorChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/post/edit/4")
				.header("Authorization", Utils.getTokenForModerator(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileEditingAsModerator() throws Exception {
		var editDTO = new EditDTO();
		editDTO.setTitle("testRenameModeratorChanged");
		editDTO.setContent("testRenameModeratorChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/post/edit/-1")
				.header("Authorization", Utils.getTokenForModerator(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	void shouldThrow400WhileEditingAsModerator() throws Exception {
		var editDTO = new EditDTO();
		editDTO.setTitle("t");
		editDTO.setContent("");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/post/edit/4")
				.header("Authorization", Utils.getTokenForModerator(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	// LIKE END POINT

	@Test
	void shouldGiveLike() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/post/like/6").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(201));
	}

	@Test
	void shouldThrow400WhileGivingLike() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/post/like/5").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	// REMOVE LIKE END POINT
	
	@Test
	void shouldRemoveLike() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/post/unlike/7").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileRemovingLike() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/post/unlike/8").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(404));
	}
}
