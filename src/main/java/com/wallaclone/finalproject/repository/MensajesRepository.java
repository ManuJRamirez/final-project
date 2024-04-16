package com.wallaclone.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Mensaje;

@Repository
public interface MensajesRepository extends JpaRepository<Mensaje, Long> {
	
	List<Mensaje> findByIdChatOrderByFecha(Long idChat);

}
