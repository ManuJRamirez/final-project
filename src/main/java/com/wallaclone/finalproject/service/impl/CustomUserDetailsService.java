package com.wallaclone.finalproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.repository.UsuariosRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UsuariosRepository usuariosRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuariosRepository.findByEmail(username).orElseThrow();
	}
}
