package com.scmspain.howtospring.reactive;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cloud.netflix.rx.SingleReturnValueHandler;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class EndpointTest {
  private MockMvc mockServer;

  @Before
  public void setUp() throws Exception {
    UserRepository userRepository = new InMemoryUserRepository();
    MyService myService = new MyService(userRepository);
    Endpoint controller = new Endpoint(myService);

    mockServer = standaloneSetup(controller)
        .setCustomReturnValueHandlers(new SingleReturnValueHandler()).build();
  }

  @Test
  public void shouldListUsers() throws Exception {
    MockHttpServletRequestBuilder request = get("/users")
        .accept(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockServer.perform(request)
        .andExpect(request().asyncStarted())
        .andReturn();

    mockServer.perform(asyncDispatch(mvcResult))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json("[{\"id\":\"xyz\",\"name\":\"Alice\",\"lastname\":\"Wonderland\"},{\"id\":\"abcd\",\"name\":\"Jonh\",\"lastname\":\"Doe\"}]"))
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0]['id']").value("xyz"))
        .andExpect(jsonPath("$[0]['name']").value("Alice"))
        .andExpect(jsonPath("$[0]['lastname']").value("Wonderland"))
        .andExpect(jsonPath("$[1]['id']").value("abcd"))
        .andExpect(jsonPath("$[1]['name']").value("Jonh"))
        .andExpect(jsonPath("$[1]['lastname']").value("Doe"))
        .andExpect(jsonPath("$..id").value(contains("xyz", "abcd")));
  }

  @Test
  public void shouldAddUser() throws Exception {
    MockHttpServletRequestBuilder request = post("/users")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":\"spm\",\"name\":\"Spider\",\"lastname\":\"Man\"}");

    MvcResult mvcResult = mockServer.perform(request)
        .andExpect(request().asyncStarted())
        .andReturn();

    mockServer.perform(asyncDispatch(mvcResult))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json("{\"id\":\"spm\",\"name\":\"Spider\",\"lastname\":\"Man\"}"));
  }
}