package com.example.demo.Entity;

import com.example.demo.helper.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

//enum role {
//    Admin,
//    Standard
//}

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;

    @Column(name = "birthdate")
    private Date birthdate;

    @Lob
    @Column(name = "images")
    private String images;

//    @Column(name = "role")

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

}
