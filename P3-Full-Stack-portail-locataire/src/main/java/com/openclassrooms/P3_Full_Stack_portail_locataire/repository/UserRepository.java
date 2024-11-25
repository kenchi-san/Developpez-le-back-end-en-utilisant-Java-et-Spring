package com.openclassrooms.P3_Full_Stack_portail_locataire.repository;

import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
