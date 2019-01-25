package br.com.coppieters.concrete.domain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserPhone implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    public User user;

    @Column(length = 3)
    private String ddd;

    @Column(length = 9)
    private String number;

    @Override
    public String toString() {
        return "UserPhone{" +"id=" + id +", ddd='" + ddd + '\'' +", number='" + number + '\'' +'}';
    }
}
