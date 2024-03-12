package com.wallaclone.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Categoria;

@Repository
public interface CategoriasRepository extends JpaRepository<Categoria, Long> {

}
