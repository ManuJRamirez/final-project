package com.wallaclone.finalproject.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.dto.RequestEnviarEmailDto;
import com.wallaclone.finalproject.entity.Usuario;
import com.wallaclone.finalproject.exceptions.CustomException;
import com.wallaclone.finalproject.repository.UsuariosRepository;
import com.wallaclone.finalproject.service.EnviarEmailService;
import com.wallaclone.finalproject.utils.JWTUtils;

@Service
public class EnviarEmailServiceImpl implements EnviarEmailService {

	@Value("${mail.smtp.auth}")
	private String auth;

	@Value("${mail.smtp.host}")
	private String host;

	@Value("${mail.smtp.port}")
	private String port;

	@Value("${mail.smtp.email}")
	private String email;

	@Value("${mail.smtp.password}")
	private String password;
	
	@Autowired
	UsuariosRepository usuariosRepository;

	@Autowired
	private JWTUtils jwtUtils;

	@Override
	public void enviarEmailRecuperacion(RequestEnviarEmailDto request) {

		Usuario usuario = usuariosRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new CustomException("El usuario con el correo electrónico "
						+ request.getEmail() + " no se encuentra en la base de datos."));

		if (usuario != null) {
			Properties props = new Properties();
			props.put("mail.smtp.auth", auth);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email, password);
				}
			});

			try {
				String token = jwtUtils.generateTokenShort(usuario);
				String enlaceRecuperacion = "http://127.0.0.1:5500" + "/nueva-password.html?token=" + token;

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.getEmail()));
				message.setSubject("Recuperación de contraseña");
				message.setText(
						"Hola,\n\nHemos recibido una solicitud para recuperar tu contraseña. Si no realizaste esta solicitud, puedes ignorar este correo.\nPara recuperar contraseña pincha en el siguiente enlace:\n "+ enlaceRecuperacion +"\nEl enlace estará disponible durante 10 minutos.\n\nSaludos,\nEquipo de BabyTreasure");

				Transport.send(message);

			} catch (MessagingException e) {
				throw new RuntimeException("Error al enviar el correo electrónico: " + e.getMessage(), e);
			}
		} else {

		}
	}
}
