/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import com.tech.models.entities.Message;
import com.tech.services.MessageService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author KuroiTenshi
 */
public class MessageDeleter{
    private final int minuteInMillis = 60000;
    /*
    if (CB.getRoom_expiration_time().after(new Date())){// if CB is later than Today
                        return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);
                    }
    */
    @Autowired
    MessageService MS;
    
    public boolean executeCleaning(){        
        System.out.println("Deleting Started!");
        List<Message> messageList = MS.getAllMessages();

        for(Message vLookUp:messageList){
        Date deletionDate = new Date(vLookUp.getDate().getTime() + (vLookUp.getTtl() * minuteInMillis));
            if(deletionDate.after(new Date())){
                MS.delete(vLookUp);
            }
        }

        return true;
    }

//    @Async
    @Scheduled(fixedRate = 5000)
    public void run() {
        executeCleaning();            
    }
}
