package com.Premate.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
//   @Autowired
//    private AuthenticationManager authenticationManager;

    @GetMapping("/admin_register")
    @ResponseBody
    public String register() {
        return "registration/Admin-registration";
    }

    @GetMapping("/adminlogin")
    public String adminlogin() {
        return "login/adminlogin";
    }

//    @PostMapping("/adminLogin")
//    public String processLogin(@RequestParam String username, @RequestParam String password) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            return "redirect:/home"; // Redirect to intended page on success
//        } catch (AuthenticationException e) {
//            return "redirect:/adminlogin?error"; // Redirect to login page with error on failure
//        }
//    }
}
