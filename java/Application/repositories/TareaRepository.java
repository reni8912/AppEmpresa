package Application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Application.models.Tarea;
import Application.models.User;
import jakarta.transaction.Transactional;

@Repository
public interface TareaRepository extends CrudRepository<Tarea, Integer> {

    @Modifying
    @Query(value = "INSERT INTO Tarea (idUser, descripcion) VALUES (:idUser, :descripcion)", nativeQuery = true)
    int newTarea(@Param("idUser") int idUser, @Param("descripcion") String descripcion);
}
