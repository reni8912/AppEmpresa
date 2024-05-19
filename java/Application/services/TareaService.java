package Application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Application.models.Tarea;
import Application.models.User;
import Application.repositories.TareaRepository;
import jakarta.transaction.Transactional;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Transactional
    public int addNewTarea(int id_user, String descripcion) {
        return tareaRepository.newTarea(id_user, descripcion);
    }
    
    @Transactional
    public int cambiarDescripcionTarea(int id_user, String descripcion) {
        return tareaRepository.updateTareaDescripcion(id_user, descripcion);
    }
    
    @Transactional
    public List<Tarea> getTareasByUser(int id_user) {
        return tareaRepository.TareafindByUserId(id_user);
    }
    @Transactional
    public int deleteTareaById(int id) {
        return tareaRepository.deleteTareaById(id);
    }
}

    

