package Application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Application.services.UserService;

@RestController
@RequestMapping("main")
public class RegisterApiController {

	@Autowired
	UserService userService;
	
	@PostMapping("/userregister")
	public ResponseEntity registerNewUser(@RequestParam("nombre")String nombre,
										  @RequestParam("email")String email,
										  @RequestParam("password")String password) {
		
	
		String hashed_password = BCrypt.hashpw(password,BCrypt.gensalt());
		
		if(nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
			return new ResponseEntity<>("Please Complete all Flieds", HttpStatus.BAD_REQUEST);
		}
		
		int resultado = userService.registerNewUserSericeMethod(nombre, email, hashed_password); //Esto es la linea de codigo que crea el nuevo usuario
		
		if(resultado != 1) {
			return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("Registered", HttpStatus.OK);
		}
		
		
	}
	
}


