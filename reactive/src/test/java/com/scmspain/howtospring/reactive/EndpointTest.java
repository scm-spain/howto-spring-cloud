package com.scmspain.howtospring.reactive;

import io.jmnarloch.spring.boot.rxjava.mvc.ObservableReturnValueHandler;
import io.jmnarloch.spring.boot.rxjava.mvc.SingleReturnValueHandler;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class EndpointTest {

  @Test
  public void shouldListUsers() throws Exception {
    UserRepository userRepository = new InMemoryUserRepository();
    MyService myService = new MyService(userRepository);
    Endpoint controller = new Endpoint(myService);

    MockMvc mockServer = standaloneSetup(controller)
        .setCustomReturnValueHandlers(
            new ObservableReturnValueHandler(),
            new SingleReturnValueHandler(),
            new org.springframework.cloud.netflix.rx.SingleReturnValueHandler()
        ).build();

    MockHttpServletRequestBuilder request = get("/users")
        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

    MvcResult mvcResult = mockServer.perform(request)
        .andExpect(request().asyncStarted())
        .andReturn();

    mockServer.perform(asyncDispatch(mvcResult))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
}