package com.multi.campus.webSocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class socketController {

    @RequestMapping("/chatForm")
    public String chatUI(){
        return "socket/chatUI";
    }
}
