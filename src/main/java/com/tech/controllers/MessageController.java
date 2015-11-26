package com.tech.controllers;

import com.tech.models.dtos.ChatMessage;
import com.tech.services.interfaces.IUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {

    //@Autowired
    //IMessageService messageService;

    @Autowired
    IUserService userService;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public JSONObject chat(ChatMessage message) {

//        User user = userService.getUserById(Long.parseLong(message.getUserId()));
//        Message messageModel = new Message(1L, user.getId(), message.getMessage());

       // TODO Add to DB after profile implementation

        JSONObject object = new JSONObject();
        object.put("user", message.getUser());
        object.put("message", message.getMessage());

        return object;
    }

    @RequestMapping(value = "/chat",method = RequestMethod.GET)
    public String loadChat(){
        return "chat";
    }
}