package Application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Application.models.Tarea;
import Application.models.User;
import Application.repositories.UserRepository;
import Application.services.TareaService;

@RestController
@RequestMapping("/main")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping("/nuevaTarea")
    public ResponseEntity<String> newTarea(@RequestParam("id_user") int id_user,
                                           @RequestParam("descripcion") String descripcion) {

        int resultado = tareaService.addNewTarea(id_user, descripcion);

        if (resultado != 1) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Registered", HttpStatus.OK);
        }
    }
    
    @PutMapping("/descripcion")
    public ResponseEntity<String> updateDescripcion(@RequestBody Tarea tarea) {
        if (tarea.getIdUser() != null) { // Verificar si id_user no es nulo
            int rowsUpdated = tareaService.cambiarDescripcionTarea(tarea.getIdUser().getId(), tarea.getDescripcion());

            if (rowsUpdated > 0) {
                return ResponseEntity.ok("Descripción actualizada con éxito");
            } else {
                return ResponseEntity.status(404).body("Tarea no encontrada para el id_user especificado");
            }
        } else {
            return ResponseEntity.status(400).body("El id_user de la tarea es nulo");
        }
    }

    @GetMapping("/usuario/{id_user}")
    public ResponseEntity<List<Tarea>> getTareasByUser(@PathVariable int id_user) {
        List<Tarea> tareas = tareaService.getTareasByUser(id_user);
        return ResponseEntity.ok(tareas);
    }
    
    @DeleteMapping("/tarea/{id}")
    public ResponseEntity<String> deleteTareaById(@PathVariable int id) {
        int rowsDeleted = tareaService.deleteTareaById(id);

        if (rowsDeleted > 0) {
            return ResponseEntity.ok("Tarea eliminada con éxito");
        } else {
            return ResponseEntity.status(404).body("Tarea no encontrada para el ID especificado");
        }
    }
}