package com.wallaclone.finalproject.service;

import com.wallaclone.finalproject.dto.RequestLoginDto;
import com.wallaclone.finalproject.dto.RequestSignupDto;
import com.wallaclone.finalproject.dto.ResponseLoginDto;

public interface AuthService {

	public void signUp(RequestSignupDto registrationRequest);

	public ResponseLoginDto signIn(RequestLoginDto signinRequest);
}
