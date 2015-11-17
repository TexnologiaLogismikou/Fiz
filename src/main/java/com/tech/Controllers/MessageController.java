package com.tech.Controllers;

import com.tech.Models.ChatMessage;
import com.tech.Models.Message;
import com.tech.Models.User;
import com.tech.services.IMessageService;
import com.tech.services.IUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

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
//
//        Message messageModel = new Message(1L, user.getId(), message.getMessage());

       // TODO Add to DB after profile implementation

        JSONObject object = new JSONObject();
        object.put("user", message.getUser());
        object.put("message", message.getMessage());

        return object;
    }

}