package Application.controllers;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Application.models.Login;
import Application.models.User;
import Application.services.UserService;

@RestController
public class LoginApiController {
    
    @Autowired
    UserService userService;
    
    @PostMapping("/user/login")
    public ResponseEntity<?> checkUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        
        String hashedPassword = userService.checkUserPassword(email);
        
        if (hashedPassword == null || !BCrypt.checkpw(password, hashedPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
        }
        
        User user = userService.userInfoEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found");
        }
        
        return ResponseEntity.ok(user);
    }
}




