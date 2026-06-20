package com.satyajeet.inventory.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name = "users")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false) private String username;
    @Column(unique = true, nullable = false) private String email;
    @Column(nullable = false) private String password;
    @Enumerated(EnumType.STRING) private Role role;
    public enum Role { ADMIN, USER }
}
