package com.tech.Controllers;

import com.tech.Models.Message;
import com.tech.Models.User;
import com.tech.Repositories.IMessageRepository;
import com.tech.Repositories.IUserRepository;
import com.tech.services.IMessageService;
import com.tech.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class MessageController {
    @Autowired
    IUserService userService;

    @Autowired
    IMessageService messageService;

    @Autowired
    IMessageRepository messageRepository;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<String> load() {

        String html;
        html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>Chat</title>\n" +
                "        <script type=\"text/javascript\" src=\"scripts/chat.js\"> </script>\n" +
                "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div id=\"header\"></div>\n" +
                "        <div id=\"form-wrapper\">\n" +
                "            <form id=\"form\">\n" +
                "                <textarea id=\"textarea\" rows=\"10\" cols=\"50\"></textarea><br/>\n" +
                "                <input type=\"text\" name=\"message\" id=\"message\" size=\"42\"><button type=\"button\" onclick=\"chat()\">Send</button>\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";
        return new ResponseEntity<>(html, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<String> loadUser(@RequestParam("username") String username, @RequestParam("message") String messageString) {

        for(User user : userService.getAllUsers()) {
            if(user.getUsername().equals(username)){
                Long id = messageRepository.count()+1; //TODO
                Message message = new Message(id, user.getId(), messageString);
                messageService.addMessage(message);
                return new ResponseEntity<>(message.getMessage(), null, HttpStatus.OK);
            }
        }


        return new ResponseEntity<>("Error", null, HttpStatus.NOT_FOUND);
    }
}
