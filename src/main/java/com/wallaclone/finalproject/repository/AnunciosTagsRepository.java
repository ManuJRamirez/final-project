package com.wallaclone.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.AnuncioTags;

@Repository
public interface AnunciosTagsRepository extends JpaRepository<AnuncioTags, Long> {

}
