package com.wallaclone.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Chats;

@Repository
public interface ChatsRepository extends JpaRepository<Chats, Long> {

    Chats findByIdAnuncioAndIdUsuario(Long idAnuncio, Long idUsuario);
    
    List<Chats> findByIdUsuario(Long idUsuario);

}
