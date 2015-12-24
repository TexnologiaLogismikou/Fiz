/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

https://github.com/rstoyanchev/spring-websocket-portfolio/blob/master/src/test/java/org/springframework/samples/portfolio/web/standalone/StandalonePortfolioControllerTests.java

 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.AbstractTest;
import com.tech.configurations.InitializeValidators;
import com.tech.controllers.dependencies.TestMessageChannel;
import com.tech.services.MessageService;
import com.tech.services.chatroom.ChatroomEntitiesService;
import com.tech.services.chatroom.ChatroomLocationService;
import com.tech.services.chatroom.ChatroomMembersService;
import com.tech.services.user.UserService;
import org.springframework.context.support.StaticApplicationContext;
import java.util.Arrays;
import javax.transaction.Transactional;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"mixalis","mixalis"})
public class MessageControllerTest extends AbstractTest {
    String uri;
    
//    @Mock
//    MessageService messageService;
//
//    @Mock
//    ChatroomEntitiesService  chatroomEntitieService;
//    
//    @Mock
//    UserService userService;
//    
//    @Mock
//    ChatroomLocationService locationService;
//    
//    @Mock
//    ChatroomMembersService memberService;
//    
//    @InjectMocks
    @Autowired
    MessageController controller;
    
    private TestAnnotationMethodHandler annotationMethodHandler;
    
    private TestMessageChannel clientOutboundChannel;
    
    public MessageControllerTest() {
    }
    
   @BeforeClass
    public static void setUpClass()
    {
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass()
    {     
        InitializeValidators.InitializeCustomValidators();
    }  
    
    @Before
//    @Override
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        
        //super.setUp(controller);
        
        InitializeValidators.InitializeCustomValidators();
        
        this.clientOutboundChannel = new TestMessageChannel();
        
        this.annotationMethodHandler = new TestAnnotationMethodHandler(
                    new TestMessageChannel(), clientOutboundChannel,
                    new SimpMessagingTemplate(new TestMessageChannel()));

        this.annotationMethodHandler.registerHandler(controller);
	this.annotationMethodHandler.setDestinationPrefixes(Arrays.asList("/app"));
	this.annotationMethodHandler.setMessageConverter(new MappingJackson2MessageConverter());
	this.annotationMethodHandler.setApplicationContext(new StaticApplicationContext());
	this.annotationMethodHandler.afterPropertiesSet();
        
        uri = "/";
    }
    
    @After
    //@Override
    public void tearDown() {
       // super.tearDown();
        
        InitializeValidators.CleanCustomValidators();
        uri = null;
    }

    /*
      private String message;
    private String username;
    private String chatroom_name;
    private float lng;
    private float lat;
    private int ttl;
    */
    @Test
    public void testIncomingMessage() {
        JSONObject json = new JSONObject();
        json.put("username","milena");
        json.put("message", "something");
        json.put("chatroom_name","hello");
        json.put("lat","1");
        json.put("lng","1");
        json.put("ttl","20");
        
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);
        headers.setDestination("/chat");
        
//        headers.setUser(new Principal() {
//            @Override
//            public String getName() {
//                return "michael";
//            }
//        });
        headers.setUser(() -> "milena"); //apla op Lamba
        
        Message<JSONObject> message = MessageBuilder.withPayload(json).setHeaders(headers).build();
        
       
        
        this.annotationMethodHandler.handleMessage(message);
        
    }

//    @Test
//    public void testMessageHistory() {
//    }
    


    private static class TestAnnotationMethodHandler extends SimpAnnotationMethodMessageHandler {

		public TestAnnotationMethodHandler(SubscribableChannel inChannel, MessageChannel outChannel,
				SimpMessageSendingOperations brokerTemplate) {

			super(inChannel, outChannel, brokerTemplate);
		}

		public void registerHandler(Object handler) {
			super.detectHandlerMethods(handler);
		}
	}
}