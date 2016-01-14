/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import com.tech.models.entities.Message;
import com.tech.services.interfaces.IMessageService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author KuroiTenshi
 */
@Component
public class MessageDeleter{
    private final int minuteInMillis = 60000;
    
    @Autowired
    IMessageService MS;
    
    private void executeCleaning(){        
        List<Message> messageList = MS.getAllMessages();

        for(Message vLookUp:messageList){
            Date deletionDate = new Date(vLookUp.getDate().getTime() + (vLookUp.getTtl() * minuteInMillis));
            if(deletionDate.before(new Date())){
                MS.delete(vLookUp);
            }
        }
    }

    @Scheduled(fixedRate = 5000)
    public void run() {
        executeCleaning();            
    }
}
