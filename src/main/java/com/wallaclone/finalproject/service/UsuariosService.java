package com.wallaclone.finalproject.service;

import com.wallaclone.finalproject.dto.SignUpResponseDto;
import com.wallaclone.finalproject.dto.UsuarioDto;

public interface UsuariosService {
	public SignUpResponseDto signUp(UsuarioDto signUpBodyDto) throws Exception;
}
