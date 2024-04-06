package com.wallaclone.finalproject.service;

import java.io.IOException;

import com.wallaclone.finalproject.dto.RequestLoginDto;
import com.wallaclone.finalproject.dto.RequestNuevaPasswordDto;
import com.wallaclone.finalproject.dto.RequestNuevoAnuncioDto;
import com.wallaclone.finalproject.dto.RequestSignupDto;
import com.wallaclone.finalproject.dto.ResponseDefaultImagenDto;
import com.wallaclone.finalproject.dto.ResponseTokenDto;
import com.wallaclone.finalproject.dto.ResponseUsuarioDto;
import com.wallaclone.finalproject.dto.ResponseNuevoAnuncioDto;

public interface AuthService {

	public void signUp(RequestSignupDto registrationRequest);

	public ResponseTokenDto signIn(RequestLoginDto signinRequest);
	
	public ResponseNuevoAnuncioDto nuevoAnuncio(RequestNuevoAnuncioDto request, String refreshedToken);
	
	public ResponseDefaultImagenDto getDefaultImagen() throws IOException ;
	
	public ResponseTokenDto borrarAnuncio(String id, String refreshedToken);

	public void nuevaPassword(RequestNuevaPasswordDto request);
	
	public ResponseNuevoAnuncioDto actualizarAnuncio(String id, RequestNuevoAnuncioDto request, String refreshedToken);
	
	public ResponseUsuarioDto obtenerUsuarioPorApodo(String apodo, String refreshedToken);
	
}
