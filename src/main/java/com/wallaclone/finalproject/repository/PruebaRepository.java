package com.wallaclone.finalproject.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Prueba;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Long>{

}
