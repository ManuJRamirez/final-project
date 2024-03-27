package com.wallaclone.finalproject.service;

import java.io.IOException;

import com.wallaclone.finalproject.dto.RequestLoginDto;
import com.wallaclone.finalproject.dto.RequestNuevoAnuncioDto;
import com.wallaclone.finalproject.dto.RequestSignupDto;
import com.wallaclone.finalproject.dto.ResponseDefaultImagenDto;
import com.wallaclone.finalproject.dto.ResponseLoginDto;
import com.wallaclone.finalproject.dto.ResponseNuevoAnuncioDto;

public interface AuthService {

	public void signUp(RequestSignupDto registrationRequest);

	public ResponseLoginDto signIn(RequestLoginDto signinRequest);
	
	public ResponseNuevoAnuncioDto nuevoAnuncio(RequestNuevoAnuncioDto request);
	
	public ResponseDefaultImagenDto getDefaultImagen() throws IOException ;
}
