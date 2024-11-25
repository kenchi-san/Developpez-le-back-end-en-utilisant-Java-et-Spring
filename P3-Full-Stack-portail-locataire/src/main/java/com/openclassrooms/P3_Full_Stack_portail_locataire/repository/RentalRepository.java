package com.openclassrooms.P3_Full_Stack_portail_locataire.repository;

import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental,Long>{
}
