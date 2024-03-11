package com.wallaclone.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Favoritos;

@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {

}
