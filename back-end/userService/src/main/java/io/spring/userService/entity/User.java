package io.spring.userService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Table(name = "users") @Data @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 64)
    private String fullName;

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_been_date")
    private Date lastBeenDate;

    @ManyToMany
    @JoinTable(name = "user_roles")
    private Collection<Role> roles = new ArrayList<>();


    public User(String fullName, String username, String password, String email, Collection<Role> roles) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        isBlocked = true;

    }

}