package com.tech.controllers;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.controllers.superclass.BaseController;
import com.tech.models.dtos.MessageDTO;
import com.tech.models.dtos.MessageHistoryRequestDTO;
import com.tech.models.entities.Message;
import com.tech.services.interfaces.ICRLocationService;
import com.tech.services.interfaces.IChatroomEntitiesService;
import com.tech.services.interfaces.IChatroomMembersService;
import com.tech.services.interfaces.IMessageService;
import com.tech.services.interfaces.IUserService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MessageController extends BaseController {
    private final int minuteInMillis = 60000;
    
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
        JSONObject json = new JSONObject();
        
        Pair<Boolean,ResponseEntity> response = messageDTO.validate();
        if(!response.getLeft()){
            json.put("response",Responses.STRING_INAPPROPRIATE_FORMAT.getData());
            return json;
        }

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
        } //mexri edw elenxw ta paidia pou pira apo to DTO an einai swsta
        
        Message message = new Message(messageService.getNextId(),userID,roomID,messageDTO);
        messageService.addMessage(message); //pernaw to minima mesa sti basi
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm"); 
        
        json.put("username",messageDTO.getUsername()); //edw ftiaxnei json opou tha epistrafei me autes ts times
        json.put("message",messageDTO.getMessage());
        json.put("date",dateFormat.format(new Date()));
        json.put("chatroom",messageDTO.getChatroom_name());
        json.put("response",Responses.SUCCESS.getData());
        
        return json;
    }
    
    /**
     * Requires a Member name , a Room name , Latitude and Longitude to return results. 
     * @param DTO
     * @return Returns a JSONArray with all the messages in the chatroom currently available. If a ttl expired when this 
     *         function is running and its removed from other functions the message will be deleted here and not contained in 
     *         the Array.
     *         If the room or the member are not valid a NOT AVAILABLE response will be returned
     *         If the member is outside the rooms max range then a OUT OF RANGE response will be returned
     *         If the username doesn't belong to the room member list a UNAUTHORIZED response will be returned 
     */
    @MessageMapping("/chat/history")
    @SendTo("/topic/chat")
    public JSONArray messageHistory(@RequestBody MessageHistoryRequestDTO DTO){
        JSONArray jsonArray = new JSONArray(); 
        JSONObject jsonResponse = new JSONObject();

        Pair<Boolean,ResponseEntity> response = DTO.validate();
        if(!response.getLeft()){
            jsonResponse.put("response",Responses.NOT_AVAILABLE.getData());
            jsonResponse.put("size","0");
            jsonArray.add(jsonResponse);
            return jsonArray;
        }

        if(!chatroomEntitieService.validateRoomnameExistance(DTO.getRoom_name())){
            jsonResponse.put("response",Responses.NOT_AVAILABLE.getData());
            jsonResponse.put("size","0");
            jsonArray.add(jsonResponse);
            return jsonArray;
        }
        
        if(!userService.checkUsername(DTO.getMember_name())){
            jsonResponse.put("response",Responses.NOT_AVAILABLE.getData());
            jsonResponse.put("size","0");
            jsonArray.add(jsonResponse);
            return jsonArray;
        }
        Long roomID = chatroomEntitieService.getRoomByName(DTO.getRoom_name()).getRoom_id();
        Long memberID = userService.getUserByUsername(DTO.getMember_name()).getId();
        
        if(!locationService.checkIfStillInside(roomID,DTO.getLng(), DTO.getLat())){
            jsonResponse.put("response",Responses.OUTSIDE_RANGE.getData());
            jsonResponse.put("size","0");
            jsonArray.add(jsonResponse);
            return jsonArray;
        }
        
        if(!memberService.checkIfMemberExistsInChatroom(memberID, roomID)){ 
            jsonResponse.put("response",Responses.NOT_AUTHORIZED.getData());
            jsonResponse.put("size","0");
            jsonArray.add(jsonResponse);
            return jsonArray;           
            
        }
        
        List<Message> list = messageService.getByChatRoom(roomID);        
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        
        int count = 0;
        for(Message vLookUp:list){
            Date newDate = new Date(vLookUp.getDate().getTime() + (vLookUp.getTtl() * minuteInMillis));
            if(newDate.before(new Date())){
                messageService.delete(vLookUp);
                continue;
            }
            count++;
            JSONObject json = new JSONObject();
            json.put("username", userService.getUserById(vLookUp.getUserid()).getUsername());//TODO check if the messages are deleted after the user is deleted
            json.put("message",vLookUp.getMessage());
            json.put("date",dateFormat.format(vLookUp.getDate()));
            jsonArray.add(json);
        }
        jsonResponse.put("size",count);
        jsonResponse.put("response",Responses.SUCCESS.getData());
        jsonArray.add(jsonResponse);
        
        return jsonArray;
    }
}
