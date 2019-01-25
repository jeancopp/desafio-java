package br.com.coppieters.concrete.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder(builderMethodName = "builder")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserPhone> phones;

    private LocalDateTime created;

    private LocalDateTime modified;

    private LocalDateTime lastLogin;

    private String token;

}
