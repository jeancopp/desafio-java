package br.com.coppieters.concrete.repository;

import br.com.coppieters.concrete.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    @Query(" select u from User u where upper(u.email) = upper(:email) ")
    Optional<User> findByEmail(@Param("email") String email);

    @Query(" select u from User u where upper(u.token) = upper(:token) ")
    Optional<User> findByToken(@Param("token") String token);


}
