package com.revo.myboard.controler;

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
import com.revo.myboard.category.dto.CreateDTO;
import com.revo.myboard.category.dto.NameDTO;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = { "ADMIN" })
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	// CREATE END POINT

	@Test
	void shouldCreateCategory() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setName("testCategory");
		createDTO.setSection(1);
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/category/create").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}

	@Test
	void shouldThrow400WhileCreating() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setName("testGet");
		createDTO.setSection(1);
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/category/create").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	void shouldThrow404WhileCreating() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setName("testCategory404");
		createDTO.setSection(-1);
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/category/create").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	// GET END POINT

	@Test
	void shouldGetCategory() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/category/1")).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileGetting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/category/-1")).andExpect(MockMvcResultMatchers.status().is(404));
	}

	// DELETE END POINT

	@Test
	void shouldDeleteCategory() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/category/delete/2"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileDeleting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/category/delete/-1"))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	// RENAME END POINT

	@Test
	void shouldRenameCategory() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setNewName("testRenameChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/category/rename/3").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileRenaming() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setNewName("testRenameChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/category/rename/-1").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	void shouldThrow400WhileRenaming() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setNewName("testRename400");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/category/rename/4").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
}
