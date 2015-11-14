package com.tech.Controllers;

import com.tech.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * handles ALL the requests in the /log-in/rd url 
 * @author KuroiTenshi
 */
@RestController
@RequestMapping("/log-in")
public class LogInController {   
    /**
     * automatically connects the service with the controller
     */
    @Autowired
    IUserService service;

    /**
     * Handles the POST method on the "/log-in" url and returns an answer 
     * @param user User data
     * @return string in HttpEntity
     */
    @RequestMapping(method = RequestMethod.POST) //TODO controllers need to be more general and will require JSON , Command as parameters -> not fields
    public HttpEntity<String> loadUser(@RequestBody User user){
        if (service.validateUser(user.getUsername(),user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Error", null, HttpStatus.NOT_FOUND); //on username failure (to find it) it will return a NOT_FOUND enum      
    }

    /**
     * Create the log in page with HTML
     * @return the html code via HttpEntity
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<String> loadHtml() {
        String HTML;
        HTML = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>Log in</title>\n" +
                "            <script type=\"text/javascript\" src=\"scripts/loginScript.js\"> </script>\n" +
                "            <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div id=\"body-wrapper\">\n" +
                "            <form id=\"form\" name=\"form\" method=\"post\" action=\"log-in.html\">\n" +
                "                <table id=\"table\">\n" +
                "                    <tr>\n" +
                "                        <td>Username: </td>\n" +
                "                        <td><input type=\"text\" name=\"username\" id=\"username\" required/></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>Password: </td>\n" +
                "                        <td><input type=\"password\" name=\"password\" id=\"password\" required/></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td></td>\n" +
                "                        <td><button type=\"button\" onclick=\"initializeLogin()\">Log in</button></td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";

        return new ResponseEntity<>(HTML, null, HttpStatus.OK);
    }
}
