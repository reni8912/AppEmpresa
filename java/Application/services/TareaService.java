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
    public int addNewTarea(int idUser, String descripcion) {
        return tareaRepository.newTarea(idUser, descripcion);
    }
}
