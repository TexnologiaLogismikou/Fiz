package com.tech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Andreas on 19/1/2016.
 */
@Controller
public class SecuredPingController {

    @RequestMapping(value = "/secured/ping")
    @ResponseBody
    public String securedPing() {
        return "All good. You only get this message if you're authenticated";
    }
}
