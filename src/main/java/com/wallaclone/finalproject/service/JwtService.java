package com.wallaclone.finalproject.service;

import com.wallaclone.finalproject.entity.Usuario;

public interface JwtService {
	public String getToken(Usuario usuario);
}
