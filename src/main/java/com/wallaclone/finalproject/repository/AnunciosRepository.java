package com.wallaclone.finalproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Anuncio;

@Repository
public interface AnunciosRepository extends JpaRepository<Anuncio, Long> {
	 Page<Anuncio> findAll(Specification<Anuncio> spec, Pageable pageable);
}
