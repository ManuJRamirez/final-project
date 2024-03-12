package com.wallaclone.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Imagen;

@Repository
public interface ImagenesRepository extends JpaRepository<Imagen, Long> {

}
