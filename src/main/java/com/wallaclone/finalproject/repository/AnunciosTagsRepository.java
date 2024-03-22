package com.wallaclone.finalproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.AnuncioTags;

@Repository
public interface AnunciosTagsRepository extends JpaRepository<AnuncioTags, Long> {
	List<Optional<AnuncioTags>> findByIdAnuncio(long idAnuncio);

}
