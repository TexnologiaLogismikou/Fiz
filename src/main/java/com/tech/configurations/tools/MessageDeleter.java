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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author KuroiTenshi
 */
public class MessageDeleter implements Runnable {
    private final int minuteInMillis = 60000;
    /*
    if (CB.getRoom_expiration_time().after(new Date())){// if CB is later than Today
                        return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);
                    }
    */
    @Autowired
    MessageService MS;
    
    public boolean executeCleaning(){        
        try { 
            Thread.sleep(10);
            System.out.println("Deleting Started!");
            List<Message> messageList = MS.getAllMessages();
            
            for(Message vLookUp:messageList){
            Date deletionDate = new Date(vLookUp.getDate().getTime() + (vLookUp.getTtl() * minuteInMillis));
                if(deletionDate.after(new Date())){
                    MS.delete(vLookUp);
                }
            }
            
            return true;
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageDeleter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void run() {
            System.out.println("Deleting initialized!");
        long i = 0;
        boolean flag;
        while(true){
            flag = executeCleaning();
            if(flag == false ) {
                i++;
            }
        }
    }

}
