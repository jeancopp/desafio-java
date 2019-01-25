package br.com.coppieters.concrete.domain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder(builderMethodName = "builder")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    @Column(unique = true)
    public String email;

    public String password;

    @OneToMany(mappedBy = "user")
    public List<UserPhone> phones;

    public LocalDateTime created;

    public LocalDateTime modified;

    public LocalDateTime lastLogin;

    public String token;

}
