package com.wallaclone.finalproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wallaclone.finalproject.entity.Usuario;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByApodo(String apodo);

	Optional<Usuario> getOneByApodo(String apodo);

	Optional<Usuario> findByEmail(String email);

	@Modifying
	@Query(value = "delete from usuarios where apodo = ?1", nativeQuery = true)
	void customDeleteByApodo(String apodo);

}
