package ru.zatert.springbootsecuritycrud.entities;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zatert.springbootsecuritycrud.repositories.RoleRepo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String lastname; // это айди роли на самом деле

    public User(){}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(Long id, String username, String password, String email, Collection<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public User(Long id, String username, String password, String email, String roleId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.lastname = roleId;
    }

    @ManyToMany
    @JoinTable(name="users_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private Collection<Role> roles;

}
