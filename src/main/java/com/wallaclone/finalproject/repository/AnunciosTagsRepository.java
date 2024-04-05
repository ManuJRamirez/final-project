package com.wallaclone.finalproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.AnuncioTags;

@Repository
public interface AnunciosTagsRepository extends JpaRepository<AnuncioTags, Long> {
	List<Optional<AnuncioTags>> findByIdAnuncio(long idAnuncio);
	
	@Modifying
	@Query(value = "delete from anuncios_tags where id_anuncio = ?1", nativeQuery = true)
	void customDelete(Long id);

}
