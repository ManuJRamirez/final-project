package com.wallaclone.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Imagen;

@Repository
public interface ImagenesRepository extends JpaRepository<Imagen, Long> {

	@Modifying
	@Query(value = "delete from imagenes where id_anuncio = ?1", nativeQuery = true)
	void customDelete(Long id);
}
