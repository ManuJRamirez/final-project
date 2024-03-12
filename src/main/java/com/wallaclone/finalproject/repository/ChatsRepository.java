package com.wallaclone.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Chat;

@Repository
public interface ChatsRepository extends JpaRepository<Chat, Long> {

}
