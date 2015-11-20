package com.tech.controllers;

import com.google.gson.Gson;
import com.tech.Application;
import com.tech.models.entities.User;
import com.tech.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
@SpringApplicationConfiguration(classes=Application.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserService userServiceMock;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(userServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoadUserProfile_ShouldReturnUser() throws Exception {
        User user1 = new User((long)1,"alodapos","123");

        User userTest = userServiceMock.getUserById((long)1);

        //get results, get the user from inside (as json), and transform to user object
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/alodapos")
                .contentType("Application/json"))
                .andReturn();
        String afk = result.getResponse().getContentAsString();
        System.out.println(afk);
        ModelAndView modelAndView = result.getModelAndView();
        Gson gson = new Gson();
        System.out.println("---------------------------------------------------------");
        System.out.println(modelAndView.getModel());
        String userData = gson.toJson(modelAndView.getModel());
        User user = gson.fromJson(userData,User.class);

        assertEquals("Failure - expected " + user1.getUsername(), user1,user);
    }
}