package com.tech.controllers;

import com.tech.configurations.tools.Responses;
import com.tech.controllers.superclass.BaseController;
import com.tech.models.dtos.MessageDTO;
import com.tech.models.entities.Message;
import com.tech.services.interfaces.ICRLocationService;
import com.tech.services.interfaces.IChatroomEntitiesService;
import com.tech.services.interfaces.IChatroomMembersService;
import com.tech.services.interfaces.IMessageService;
import com.tech.services.interfaces.IUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController extends BaseController {

    @Autowired
    IMessageService messageService;

    @Autowired
    IChatroomEntitiesService  chatroomEntitieService;
    
    @Autowired
    IUserService userService;
    
    @Autowired
    ICRLocationService locationService;
    
    @Autowired
    IChatroomMembersService memberService;
    
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public JSONObject incomingMessage(MessageDTO messageDTO){
        //TODO call sto validator
        JSONObject json = new JSONObject();
        
        if(!userService.checkUsername(messageDTO.getUsername())){
            json.put("response",Responses.NOT_AUTHORIZED.getData());
            return json;
        }
        
        if(!chatroomEntitieService.validateRoomnameExistance(messageDTO.getChatroom_name())){
            json.put("response",Responses.NOT_AUTHORIZED.getData());
            return json;
        }
        
        Long userID = userService.getUserByUsername(messageDTO.getUsername()).getId();
        Long roomID = chatroomEntitieService.getRoomByName(messageDTO.getChatroom_name()).getRoom_id();
        
        if(!memberService.checkIfMemberExistsInChatroom(userID, roomID)){
            json.put("response",Responses.NOT_AUTHORIZED.getData());
            return json;
        }
        
        if(!locationService.checkIfStillInside(roomID, messageDTO.getLng(), messageDTO.getLat())){
            json.put("response",Responses.OUTSIDE_RANGE.getData());
            return json;
        }
            
    }
    
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
