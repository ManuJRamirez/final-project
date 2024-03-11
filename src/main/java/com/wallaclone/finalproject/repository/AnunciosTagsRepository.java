package com.wallaclone.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.AnunciosTags;

@Repository
public interface AnunciosTagsRepository extends JpaRepository<AnunciosTags, Long> {

}
