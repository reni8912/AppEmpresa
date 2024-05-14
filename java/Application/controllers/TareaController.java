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

import Application.models.User;
import Application.repositories.UserRepository;
import Application.services.TareaService;

@RestController
@RequestMapping("/main")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping("/nuevaTarea")
    public ResponseEntity<String> newTarea(@RequestParam("idUser") int idUser,
                                           @RequestParam("descripcion") String descripcion) {

        int resultado = tareaService.addNewTarea(idUser, descripcion);

        if (resultado != 1) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Registered", HttpStatus.OK);
        }
    }
}

