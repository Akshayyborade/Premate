package com.Premate.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

   

    @GetMapping("/home")
    @ResponseBody
    public String admindash() {
        return "hii admin how are you";
    }
}
