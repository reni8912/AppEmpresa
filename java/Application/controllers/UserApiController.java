package Application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Application.models.User;
import Application.services.UserService;


    
    @RestController
    @RequestMapping("/User")
    public class UserApiController {

        @Autowired
        UserService userService;

        @GetMapping("/Info/{id}")
        public ResponseEntity<?> userInfo(@PathVariable int id) {
            User user = userService.userInfo(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }