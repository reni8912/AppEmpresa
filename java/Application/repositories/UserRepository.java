package Application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Application.models.User;
import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO USER(nombre, email, password) VALUES(:nombre, :email, :password)", nativeQuery = true)
	int registerNewUser(@Param("nombre") String nombre,
						@Param("email") String email,
						@Param("password") String password

			           );
	

	@Query(value = "SELECT password FROM USER WHERE email = :email", nativeQuery = true)
	String CheckPassword(@Param("email") String email);


	@Query(value = "SELECT * FROM USER WHERE id = :id", nativeQuery = true)
	User UserInfo(@Param("id") int id);
	
	@Query(value = "SELECT * FROM USER WHERE email = :email", nativeQuery = true)
	User UserInfoEmail(@Param("email") String email);
	
}
