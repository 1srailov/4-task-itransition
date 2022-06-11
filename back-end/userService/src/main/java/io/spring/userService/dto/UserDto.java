package io.spring.userService.dto;

import io.spring.userService.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String fullName;
    private String username;
    private String password;
    private String isBlocked;
    private Date createDate;
    private Date lastBeenDate;
    private String email;
    private Collection<Role> roles = new ArrayList<>();

    public UserDto(Integer id, String fullName, String username, String password, Boolean isBlocked, Date createDate, Date lastBeenDate, String email, Collection<Role> roles) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.isBlocked = isBlocked == true ? "ACTIVE" : "BLOCKED";
        this.createDate = createDate;
        this.lastBeenDate = lastBeenDate;
        this.email = email;
        this.roles = roles;
    }

}
