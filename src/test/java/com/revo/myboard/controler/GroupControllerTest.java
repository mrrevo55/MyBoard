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
import com.revo.myboard.group.dto.AuthortiyDTO;
import com.revo.myboard.group.dto.CreateDTO;
import com.revo.myboard.group.dto.NameDTO;

/*
 * Created By Revo
 */

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
public class GroupControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	// GET END POINT

	@Test
	void shouldGetAuthorities() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/group/authorities"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldGetAllGroups() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/group/all")).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldGetGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/group/1")).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileGetting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/group/-1")).andExpect(MockMvcResultMatchers.status().is(404));
	}

	// CREATE END POINT

	@Test
	void shouldCreateGroup() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setAuthority("ADMIN");
		createDTO.setName("groupCreate");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/group/create").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}

	@Test
	void shouldThrow400WhileCreating() throws Exception {
		var createDTO = new CreateDTO();
		createDTO.setAuthority("ADMIN");
		createDTO.setName("groupGet");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(createDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/group/create").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	// DELETE END POINT

	@Test
	void shouldDeleteGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/group/delete/2"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shoulThrow404WhileDeleting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/group/delete/-1"))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	// RENAME END POINT

	@Test
	void shouldRenameGroup() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setNewName("groupRenameChanged");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/group/rename/3").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow400WhileRenaming() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setNewName("groupRename400");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/group/rename/4").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void shouldThrow404WhileRenaming() throws Exception {
		var nameDTO = new NameDTO();
		nameDTO.setNewName("groupRename404");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(nameDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/group/rename/-1").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	// CHANGE AUTHORITY END POINT

	@Test
	void shouldChangeAuthorityGroup() throws Exception {
		var authorityDTO = new AuthortiyDTO();
		authorityDTO.setNewAuthority("USER");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(authorityDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/group/change/2").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow400WhileChangingAuthority() throws Exception {
		var authorityDTO = new AuthortiyDTO();
		authorityDTO.setNewAuthority("USERERROR");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(authorityDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/group/change/2").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	void shouldThrow404WhileChangingAuthority() throws Exception {
		var authorityDTO = new AuthortiyDTO();
		authorityDTO.setNewAuthority("USERERROR");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(authorityDTO);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/group/change/-1").contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
}
