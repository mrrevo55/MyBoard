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
import com.revo.myboard.section.dto.NameDTO;

/*
 * Created By Revo
 */

@SpringBootTest
@AutoConfigureMockMvc
public class SectionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	// GET END POINT

	@Test
	void shouldGetSection() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/section/1")).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileGetting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/section/-1")).andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	void shouldGetAllSections() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/section/all")).andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	// CREATE END POINT
	
	@Test
	@WithMockUser(roles="ADMIN")
	void shouldCreateSection() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setName("sectionCreate");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/section/create").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	@Test
	@WithMockUser(roles="ADMIN")
	void shouldThrow400WhileCreating() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setName("testGet");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/section/create").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	// DELETE END POINT

	@Test
	@WithMockUser(roles="ADMIN")
	void shouldDeleteSection() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/section/delete/2"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	@WithMockUser(roles="ADMIN")
	void shouldThrow404WhileDeleting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/section/delete/-1"))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	// RENAME END POINT
	
	@Test
	@WithMockUser(roles="ADMIN")
	void shouldRenameSection() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setName("testRenameChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/section/rename/3").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	@WithMockUser(roles="ADMIN")
	void shouldThrow400WhileRenaming() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setName("testRename400");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/section/rename/4").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	@WithMockUser(roles="ADMIN")
	void shouldThrow404WhileRenaming() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setName("testRename404");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/section/rename/-1").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
}
