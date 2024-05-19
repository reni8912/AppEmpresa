package Application.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Tarea {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_user")  
    private User id_user;
    
    private String Descripcion;

    public int getIdTarea() {
        return id;
    }

    public void setIdTarea(int id) {
        this.id = id;
    }

    public User getIdUser() {
        return id_user;
    }

    public void setIdUser(User id_user) {
        this.id_user = id_user;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Tarea(int id, User id_user, String descripcion) {
        this.id = id;
        this.id_user = id_user;
        Descripcion = descripcion;
    }
    
    public Tarea() {

    }
}

