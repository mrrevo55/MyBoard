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
import com.revo.myboard.user.Sex;
import com.revo.myboard.user.dto.DataDTO;
import com.revo.myboard.user.dto.EmailDTO;
import com.revo.myboard.user.dto.PasswordDTO;

/*
 * Created By Revo
 */

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	// GET END POINT

	@Test
	void shouldGetUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/test")).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileGetting() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/error")).andExpect(MockMvcResultMatchers.status().is(404));
	}

	// SEARCH END POINT

	@Test
	void shouldSearchUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/search/content"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	// CREDENTIALS END POINT

	@Test
	void shouldGetCredentials() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/credentials").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(200));
	}

	// CHANGE PASSWORD END POINT

	@Test
	void shouldChangePassword() throws Exception {
		var passwordDTO = new PasswordDTO();
		passwordDTO.setPassword("test2");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(passwordDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/change/password")
				.header("Authorization", Utils.getTokenForUserChangingPassword(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow400WhileChangingPassword() throws Exception {
		var passwordDTO = new PasswordDTO();
		passwordDTO.setPassword("t");
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(passwordDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/change/password")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	// EMAIL CHANGE END POINT

	@Test
	void shouldChangeEmail() throws Exception {
		var emailDTO = new EmailDTO();
		emailDTO.setEmail("newemail@email.pl");
		String json = objectMapper.writer().writeValueAsString(emailDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/change/email")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow400WhileChangingEmail() throws Exception {
		var emailDTO = new EmailDTO();
		emailDTO.setEmail("newemailemail.pl");
		String json = objectMapper.writer().writeValueAsString(emailDTO);
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/change/email")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	// CHANGE DATA END POINT

	@Test
	void shouldChangeData() throws Exception {
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(DataDTO.builder().age(18)
				.city("City").description("Description").page("Page").sex(Sex.KOBIETA.toString()).build());
		mockMvc.perform(MockMvcRequestBuilders.put("/user/change/data")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow400WhileChangingData() throws Exception {
		String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(DataDTO.builder().age(-1)
				.city("City").description("Description").page("Page").sex(Sex.KOBIETA.toString()).build());
		mockMvc.perform(MockMvcRequestBuilders.put("/user/change/data")
				.header("Authorization", Utils.getTokenForUser(mockMvc, objectMapper))
				.contentType(Utils.APPLICATION_JSON_UTF8).content(json))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	// PROFILE END POINT

	@Test
	void shouldGetProfile() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/profile").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(200));
	}

	// DELETE END POINT

	@Test
	void shouldDeleteUserAsUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/userTokenDelete").header("Authorization",
				Utils.getTokenForUserDeleting(mockMvc, objectMapper)))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow400WhileDeletingUserAsUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/test").header("Authorization",
				Utils.getTokenForUser(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	void shouldDeleteUserAsAdmin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/userDelete").header("Authorization",
				Utils.getTokenForAdmin(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	void shouldThrow404WhileDeletingUserAsAdmin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/notexistsuser").header("Authorization",
				Utils.getTokenForAdmin(mockMvc, objectMapper))).andExpect(MockMvcResultMatchers.status().is(404));
	}

	// SEXES END POINT

	@Test
	@WithMockUser
	void shouldGetSexList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/sexes")).andExpect(MockMvcResultMatchers.status().is(200));
	}

	// BAN END POINT

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldBanUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/ban/notBannedForBan"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldThrow400WhileBanning() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/ban/bannedForBan"))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldThrow404WhileBanning() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/ban/error"))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	// UNBAN END POINT

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldUnbanUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/unban/bannedForUnban"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldThrow400WhileUnbanning() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/unban/notBannedForUnban"))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

	@Test
	@WithMockUser(roles = "MODERATOR")
	void shouldThrow404WhileUnbanning() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/unban/error"))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	// GROUP END POINT

	@Test
	@WithMockUser(roles = "ADMIN")
	void shouldSetUserGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/group/test/3"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void shouldThrows404WhileSettingGroupForGroup() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/group/test/-1"))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void shouldThrows404WhileSettingGroupForUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/user/group/error/2"))
				.andExpect(MockMvcResultMatchers.status().is(404));
	}

}
