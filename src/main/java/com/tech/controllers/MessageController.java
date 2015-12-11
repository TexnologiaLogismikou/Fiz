package com.tech.controllers;

import com.tech.controllers.superclass.BaseController;
import com.tech.models.dtos.MessageDTO;
import com.tech.models.entities.Message;
import com.tech.services.interfaces.IChatroomEntitiesService;
import com.tech.services.interfaces.IMessageService;
import com.tech.services.interfaces.IUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.messaging.handler.annotation.DestinationVariable;

@Controller
public class MessageController extends BaseController {

    @Autowired
    IMessageService messageService;

    @Autowired
    IChatroomEntitiesService  chatroomEntitieService;
    
    @Autowired
    IUserService userService;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public JSONObject chat(MessageDTO messageDTO/*, @DestinationVariable ("chatroom") String chatroom_name*/){
        JSONObject object = new JSONObject();
        //TODO validate DTO
        if(!chatroomEntitieService.checkIfChatroomEntityExists(messageDTO.getChatroom_id())){
            object.put("user","unauthorized");
            return object;            
        }
        
        if(userService.getUserById(messageDTO.getUser()) == null){ // <-- UserID .. Not UserName.. should change
            object.put("user","unauthorized");
            return object;            
        }
        
        Date date = new Date();
        
        Message mes = new Message(messageService.getNextId(),messageDTO.getChatroom_id(),messageDTO); // <- again getChatroom_id .. Not getChatroom_id.. should change
        messageService.addMessage(mes);
        chatroomEntitieService.updateLastActivity(date,messageDTO.getChatroom_id());        

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        
        object.put("user", messageDTO.getUser());
        object.put("message", messageDTO.getMessage());
        object.put("date", dateFormat.format(date));

        return object; 
    }
    
//    @MessageMapping("/chat")
//    @SendTo("/topic/chat")
//    public JSONObject chat(MessageDTO messageDTO) {
//
////        User user = userService.getUserByUsername(messageDTO.getUser());
////        Long id = 1000L;
////        Message message = new Message(id, user.getId(), messageDTO.getMessage());
////        TODO Add to DB after profile implementation
//
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
//        System.out.println("im here!");
//
//        JSONObject object = new JSONObject();
//        object.put("user", messageDTO.getUser());
//        object.put("message", messageDTO.getMessage());
//        object.put("date", dateFormat.format(date));
//
//        return object;
//    }
//
//    @RequestMapping(value = "/chat",method = RequestMethod.GET)
//    public String loadChat(){
//        return "chat";
//    }
}
