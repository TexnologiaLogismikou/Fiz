/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

https://github.com/rstoyanchev/spring-websocket-portfolio/blob/master/src/test/java/org/springframework/samples/portfolio/web/standalone/StandalonePortfolioControllerTests.java

 */
package com.tech.controllers;

import com.tech.AbstractTest;
import com.tech.configurations.InitializeValidators;
import com.tech.controllers.dependencies.TestMessageChannel;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.user.User;
import com.tech.services.MessageService;
import com.tech.services.chatroom.ChatroomEntitiesService;
import com.tech.services.chatroom.ChatroomLocationService;
import com.tech.services.chatroom.ChatroomMembersService;
import com.tech.services.user.UserService;
import java.util.ArrayList;
import org.springframework.context.support.StaticApplicationContext;
import java.util.HashMap;
import java.util.List;
import javax.transaction.Transactional;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
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
    
    @Mock
    MessageService messageService;

    @Mock
    ChatroomEntitiesService  chatroomEntitieService;
    
    @Mock
    UserService userService;
    
    @Mock
    ChatroomLocationService locationService;
    
    @Mock
    ChatroomMembersService memberService;
    
    @Autowired
    @InjectMocks
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
        MockitoAnnotations.initMocks(this);
        
        //super.setUp(controller);
        
        InitializeValidators.InitializeCustomValidators();
        
        this.clientOutboundChannel = new TestMessageChannel();
        
        this.annotationMethodHandler = new TestAnnotationMethodHandler(
                    new TestMessageChannel(), clientOutboundChannel,
                    new SimpMessagingTemplate(new TestMessageChannel()));

        this.annotationMethodHandler.registerHandler(controller);
//	this.annotationMethodHandler.setDestinationPrefixes(Arrays.asList("/app")); -> Internal message.
	this.annotationMethodHandler.setMessageConverter(new MappingJackson2MessageConverter());
	this.annotationMethodHandler.setApplicationContext(new StaticApplicationContext());
	this.annotationMethodHandler.afterPropertiesSet();
        
        uri = "/";
    }
    
    @After
    public void tearDown() {
        
        InitializeValidators.CleanCustomValidators();
        uri = null;
    }

    @Test
    public void testIncomingMessageSuccess() {
        JSONObject json = new JSONObject();
        json.put("username","milena");
        json.put("message","something");
        json.put("chatroom_name","hello");
        json.put("lat","1");
        json.put("lng","1");
        json.put("ttl","20");
        
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/chat");
        
//        headers.setUser(new Principal() {
//            @Override
//            public String getName() {
//                return "milena";
//            }
//        });
        headers.setUser(() -> "milena"); //apla op Lamba
        headers.setSessionId("0");
	headers.setSessionAttributes(new HashMap<>());
        
        Message<JSONObject> message = MessageBuilder.withPayload(json).setHeaders(headers).build();     
        
        when(userService.checkUsername("milena")).thenReturn(true);
        when(chatroomEntitieService.validateRoomnameExistance("hello")).thenReturn(true);
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","something",true));
        when(chatroomEntitieService.getRoomByName("hello")).thenReturn(new ChatroomEntities(1L,2L,"hello"));
        when(memberService.checkIfMemberExistsInChatroom(1L, 1L)).thenReturn(true);
        when(locationService.checkIfStillInside(1L, 1, 1)).thenReturn(true);
        when(messageService.getNextId()).thenReturn(2L);
        doNothing().when(messageService).addMessage(any(com.tech.models.entities.Message.class));
        
        this.annotationMethodHandler.handleMessage(message);
        
        verify(userService,times(1)).checkUsername("milena");
        verify(userService,times(1)).getUserByUsername("milena");
        verify(chatroomEntitieService,times(1)).validateRoomnameExistance("hello");
        verify(chatroomEntitieService,times(1)).getRoomByName("hello");
        verify(memberService,times(1)).checkIfMemberExistsInChatroom(1L,1L);
        verify(locationService,times(1)).checkIfStillInside(1L,1,1);
        verify(messageService,times(1)).getNextId();
        verify(messageService,times(1)).addMessage(any(com.tech.models.entities.Message.class));   
    }

    @Test
    public void testMessageHistorySuccess() {
        JSONObject json = new JSONObject();
        json.put("member_name","milena");
        json.put("room_name","hello");
        json.put("lat","1");
        json.put("lng","1");
        
        List<com.tech.models.entities.Message> list = new ArrayList<>();
        list.add(new com.tech.models.entities.Message(1L,2L,"some text",1L,100));
        list.add(new com.tech.models.entities.Message(2L,3L,"some other text",1L,110));
        
        
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/chat/history");
        
        headers.setUser(() -> "milena"); //apla op Lamba
        headers.setSessionId("0");
	headers.setSessionAttributes(new HashMap<>());
        
        Message<JSONObject> message = MessageBuilder.withPayload(json).setHeaders(headers).build();     
        
        when(userService.checkUsername("milena")).thenReturn(true); //
        when(chatroomEntitieService.validateRoomnameExistance("hello")).thenReturn(true); //
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","something",true));//
        when(chatroomEntitieService.getRoomByName("hello")).thenReturn(new ChatroomEntities(1L,2L,"hello"));//
        when(memberService.checkIfMemberExistsInChatroom(1L, 1L)).thenReturn(true);//
        when(locationService.checkIfStillInside(1L, 1, 1)).thenReturn(true);//
        when(messageService.getByChatRoom(1L)).thenReturn(list);
        when(userService.getUserById(2L)).thenReturn(new User(2L,"milena","password",true,false));
        when(userService.getUserById(3L)).thenReturn(new User(3L,"iwanna","password",true,false));

        this.annotationMethodHandler.handleMessage(message);
        
        verify(userService,times(1)).checkUsername("milena");
        verify(userService,times(1)).getUserByUsername("milena");
        verify(chatroomEntitieService,times(1)).validateRoomnameExistance("hello");
        verify(chatroomEntitieService,times(1)).getRoomByName("hello");
        verify(memberService,times(1)).checkIfMemberExistsInChatroom(1L,1L);
        verify(locationService,times(1)).checkIfStillInside(1L,1,1);
        verify(messageService,times(1)).getByChatRoom(1L);
        verify(userService,times(2)).getUserById(anyLong());
    }
    
    @Test
    public void testMessageHistorySuccessAndTTLpassed() {
        JSONObject json = new JSONObject();
        json.put("member_name","milena");
        json.put("room_name","hello");
        json.put("lat","1");
        json.put("lng","1");
        
        List<com.tech.models.entities.Message> list = new ArrayList<>();
        list.add(new com.tech.models.entities.Message(1L,2L,"some text",1L,100));
        list.add(new com.tech.models.entities.Message(2L,3L,"some other text",1L,-110));
        
        
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/chat/history");
        
        headers.setUser(() -> "milena"); //apla op Lamba
        headers.setSessionId("0");
	headers.setSessionAttributes(new HashMap<>());
        
        Message<JSONObject> message = MessageBuilder.withPayload(json).setHeaders(headers).build();     
        
        when(userService.checkUsername("milena")).thenReturn(true); 
        when(chatroomEntitieService.validateRoomnameExistance("hello")).thenReturn(true); 
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","something",true));
        when(chatroomEntitieService.getRoomByName("hello")).thenReturn(new ChatroomEntities(1L,2L,"hello"));
        when(memberService.checkIfMemberExistsInChatroom(1L, 1L)).thenReturn(true);
        doNothing().when(messageService).delete(any(com.tech.models.entities.Message.class));
        when(locationService.checkIfStillInside(1L, 1, 1)).thenReturn(true);
        when(messageService.getByChatRoom(1L)).thenReturn(list);
        when(userService.getUserById(2L)).thenReturn(new User(2L,"milena","password",true,false));
        when(userService.getUserById(3L)).thenReturn(new User(3L,"iwanna","password",true,false));

        this.annotationMethodHandler.handleMessage(message);
        
        verify(userService,times(1)).checkUsername("milena");
        verify(userService,times(1)).getUserByUsername("milena");
        verify(chatroomEntitieService,times(1)).validateRoomnameExistance("hello");
        verify(chatroomEntitieService,times(1)).getRoomByName("hello");
        verify(memberService,times(1)).checkIfMemberExistsInChatroom(1L,1L);
        verify(locationService,times(1)).checkIfStillInside(1L,1,1);
        verify(messageService,times(1)).getByChatRoom(1L);
        verify(messageService,times(1)).delete(any(com.tech.models.entities.Message.class));
        verify(userService,times(1)).getUserById(anyLong());
    }
    
    @Test
    public void testMessageHistoryNotaRoomMember() {
        JSONObject json = new JSONObject();
        json.put("member_name","milena");
        json.put("room_name","hello");
        json.put("lat","1");
        json.put("lng","1");
        
        List<com.tech.models.entities.Message> list = new ArrayList<>();
        list.add(new com.tech.models.entities.Message(1L,2L,"some text",1L,100));
        list.add(new com.tech.models.entities.Message(2L,3L,"some other text",1L,110));
        
        
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/chat/history");
        
        headers.setUser(() -> "milena"); //apla op Lamba
        headers.setSessionId("0");
	headers.setSessionAttributes(new HashMap<>());
        
        Message<JSONObject> message = MessageBuilder.withPayload(json).setHeaders(headers).build();     
        
        when(userService.checkUsername("milena")).thenReturn(true); //
        when(chatroomEntitieService.validateRoomnameExistance("hello")).thenReturn(true); //
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","something",true));//
        when(chatroomEntitieService.getRoomByName("hello")).thenReturn(new ChatroomEntities(1L,2L,"hello"));//
        when(memberService.checkIfMemberExistsInChatroom(1L, 1L)).thenReturn(false);//
        when(locationService.checkIfStillInside(1L, 1, 1)).thenReturn(true);//

        this.annotationMethodHandler.handleMessage(message);
        
        verify(userService,times(1)).checkUsername("milena");
        verify(userService,times(1)).getUserByUsername("milena");
        verify(chatroomEntitieService,times(1)).validateRoomnameExistance("hello");
        verify(chatroomEntitieService,times(1)).getRoomByName("hello");
        verify(memberService,times(1)).checkIfMemberExistsInChatroom(1L,1L);
        verify(locationService,times(1)).checkIfStillInside(1L,1,1);
    }
    
    @Test
    public void testMessageHistoryOutsideRoomRange() {
        JSONObject json = new JSONObject();
        json.put("member_name","milena");
        json.put("room_name","hello");
        json.put("lat","1");
        json.put("lng","1");              
        
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/chat/history");
        
        headers.setUser(() -> "milena"); //apla op Lamba
        headers.setSessionId("0");
	headers.setSessionAttributes(new HashMap<>());
        
        Message<JSONObject> message = MessageBuilder.withPayload(json).setHeaders(headers).build();     
        
        when(userService.checkUsername("milena")).thenReturn(true); //
        when(chatroomEntitieService.validateRoomnameExistance("hello")).thenReturn(true); //
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","something",true));//
        when(chatroomEntitieService.getRoomByName("hello")).thenReturn(new ChatroomEntities(1L,2L,"hello"));//
        when(locationService.checkIfStillInside(1L, 1, 1)).thenReturn(false);//

        this.annotationMethodHandler.handleMessage(message);
        
        verify(userService,times(1)).checkUsername("milena");
        verify(userService,times(1)).getUserByUsername("milena");
        verify(chatroomEntitieService,times(1)).validateRoomnameExistance("hello");
        verify(chatroomEntitieService,times(1)).getRoomByName("hello");
        verify(memberService,times(0)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        verify(locationService,times(1)).checkIfStillInside(1L,1,1);
    }
    
    @Test
    public void testMessageHistoryUsernameFailure() {
        JSONObject json = new JSONObject();
        json.put("member_name","milena");
        json.put("room_name","hello");
        json.put("lat","1");
        json.put("lng","1");       
        
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/chat/history");
        
        headers.setUser(() -> "milena"); //apla op Lamba
        headers.setSessionId("0");
	headers.setSessionAttributes(new HashMap<>());
        
        Message<JSONObject> message = MessageBuilder.withPayload(json).setHeaders(headers).build();     
        
        when(userService.checkUsername("milena")).thenReturn(false); //
        when(chatroomEntitieService.validateRoomnameExistance("hello")).thenReturn(true); //

        this.annotationMethodHandler.handleMessage(message);
        
        verify(userService,times(1)).checkUsername("milena");
        verify(userService,times(0)).getUserByUsername(anyString());
        verify(chatroomEntitieService,times(1)).validateRoomnameExistance("hello");
        verify(chatroomEntitieService,times(0)).getRoomByName(anyString());
        verify(memberService,times(0)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        verify(locationService,times(0)).checkIfStillInside(anyLong(),anyFloat(),anyFloat());
    }
    
    @Test
    public void testMessageHistoryChatroomNameFailure() {
        JSONObject json = new JSONObject();
        json.put("member_name","milena");
        json.put("room_name","hello");
        json.put("lat","1");
        json.put("lng","1");       
        
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/chat/history");
        
        headers.setUser(() -> "milena"); //apla op Lamba
        headers.setSessionId("0");
	headers.setSessionAttributes(new HashMap<>());
        
        Message<JSONObject> message = MessageBuilder.withPayload(json).setHeaders(headers).build();     
        
        when(chatroomEntitieService.validateRoomnameExistance("hello")).thenReturn(false); //

        this.annotationMethodHandler.handleMessage(message);
        
        verify(userService,times(0)).checkUsername(anyString());
        verify(userService,times(0)).getUserByUsername(anyString());
        verify(chatroomEntitieService,times(1)).validateRoomnameExistance("hello");
        verify(chatroomEntitieService,times(0)).getRoomByName(anyString());
        verify(memberService,times(0)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        verify(locationService,times(0)).checkIfStillInside(anyLong(),anyFloat(),anyFloat());
    }
    
    @Test
    public void testMessageHistoryValidation_Failure() {
        JSONObject json = new JSONObject();
        json.put("member_name","@milena");
        json.put("room_namegd","@hello");
        json.put("lat","1");
        json.put("lng","1");       
        
        StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
        headers.setDestination("/chat/history");
        
        headers.setUser(() -> "milena"); //apla op Lamba
        headers.setSessionId("0");
	headers.setSessionAttributes(new HashMap<>());
        
        Message<JSONObject> message = MessageBuilder.withPayload(json).setHeaders(headers).build();     
        
        this.annotationMethodHandler.handleMessage(message);
        
        verify(userService,times(0)).checkUsername(anyString());
        verify(userService,times(0)).getUserByUsername(anyString());
        verify(chatroomEntitieService,times(0)).validateRoomnameExistance(anyString());
        verify(chatroomEntitieService,times(0)).getRoomByName(anyString());
        verify(memberService,times(0)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        verify(locationService,times(0)).checkIfStillInside(anyLong(),anyFloat(),anyFloat());
    }
    
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