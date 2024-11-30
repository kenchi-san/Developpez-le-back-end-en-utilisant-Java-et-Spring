package com.openclassrooms.P3_Full_Stack_portail_locataire.repository;

import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
