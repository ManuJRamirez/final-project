package com.wallaclone.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Categorias;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {

}
