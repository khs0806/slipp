package net.slipp.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;

public class HomeControllerTest {

	@Test
	public void home() throws Exception{
		standaloneSetup(new HomeController()).build()
			.perform(get("/")) // 이 get 메소드의 '/' 요청이 들어오게되면
			.andExpect(status().isOk()) // 200코드인지 확인해서 맞으면 햅격!
			.andExpect(forwardedUrl("home"));
	}

}
