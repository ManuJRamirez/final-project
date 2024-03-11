package com.wallaclone.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Chats;

@Repository
public interface ChatsRepository extends JpaRepository<Chats, Long> {

}
