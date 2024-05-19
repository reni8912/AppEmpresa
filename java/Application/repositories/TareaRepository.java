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
    @Query(value = "INSERT INTO Tarea (id_user, descripcion) VALUES (:id_user, :descripcion)", nativeQuery = true)
    int newTarea(@Param("id_user") int id_user, @Param("descripcion") String descripcion);

    @Modifying
    @Query(value = "UPDATE Tarea SET descripcion = :descripcion WHERE id_user = :id_user", nativeQuery = true)
    int updateTareaDescripcion(@Param("id_user") int id_user, @Param("descripcion") String descripcion);

    @Query(value = "SELECT * FROM Tarea WHERE id_user = :id_user", nativeQuery = true)
    List<Tarea> TareafindByUserId(@Param("id_user") int id_user);

    @Modifying
    @Query(value = "DELETE FROM Tarea WHERE id = :id", nativeQuery = true)
    int deleteTareaById(@Param("id") int id);
}
