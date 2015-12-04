package com.tech.controllers;

import com.tech.controllers.superclass.BaseController;
import com.tech.models.dtos.MessageDTO;
import com.tech.services.interfaces.IUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController extends BaseController{

    //@Autowired
    //IMessageService messageService;

    @Autowired
    IUserService userService;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public JSONObject chat(MessageDTO messageDTO) {

//        User user = userService.getUserByUsername(messageDTO.getUser());
//        Long id = 1000L;
//        Message message = new Message(id, user.getId(), messageDTO.getMessage());
//        TODO Add to DB after profile implementation

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

        JSONObject object = new JSONObject();
        object.put("user", messageDTO.getUser());
        object.put("message", messageDTO.getMessage());
        object.put("date", dateFormat.format(date));
        object.put("color", messageDTO.getColor());

        return object;
    }

    @RequestMapping(value = "/chat",method = RequestMethod.GET)
    public String loadChat(){
        return "chat";
    }
}
