package net.slipp.web.users;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.slipp.dao.users.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	
	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private UserController userController;
	
	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(userController).build();
	}
	
	@Test
	public void createWhenValid() throws Exception{
		this.mockMvc.perform(
				post("/users")
				.param("userId", "boydsa")
				.param("password", "1234")
				.param("name", "kimhs")
				.param("email", ""))
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void createWhenInvalid() throws Exception{
		this.mockMvc.perform(
				post("/users")
				.param("userId", "boydsa")
				.param("password", "1234")
				.param("name", "kimhs")
				.param("email", "asd"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(forwardedUrl("users/form"));
	}

}
