package com.wallaclone.finalproject.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallaclone.finalproject.config.ApplicationContextProvider;
import com.wallaclone.finalproject.dto.MensajeDto;
import com.wallaclone.finalproject.entity.Chat;
import com.wallaclone.finalproject.entity.Mensaje;
import com.wallaclone.finalproject.entity.Usuario;
import com.wallaclone.finalproject.exceptions.CustomException;
import com.wallaclone.finalproject.repository.ChatsRepository;
import com.wallaclone.finalproject.repository.MensajesRepository;
import com.wallaclone.finalproject.repository.UsuariosRepository;
import com.wallaclone.finalproject.service.WebSocketService;

import jakarta.websocket.Session;

@Service
public class WebSocketServiceImpl implements WebSocketService {

	private UsuariosRepository usuariosRepository = ApplicationContextProvider.getBean(UsuariosRepository.class);

	private ChatsRepository chatsRepository = ApplicationContextProvider.getBean(ChatsRepository.class);

	private MensajesRepository mensajesRepository = ApplicationContextProvider.getBean(MensajesRepository.class);

	@Override
	public void onMessage(Session session, String text) {

		Set<Session> openSessions = session.getOpenSessions();
		Map<String, Object> userProperties = session.getUserProperties();
		
		Long idChat = (Long) userProperties.get("idChat");		
		Long idUsuario = (Long) userProperties.get("idUsuario");		
		Long idAnucio = (Long) userProperties.get("idAnucio");		
		String apodo = userProperties.get("apodo").toString();		

		Chat chat;
		if(idChat != null) {
			chat = chatsRepository.findById(idChat).orElseThrow(
				() -> new CustomException("El chat con id " + idChat + " no se encuentra en la base de datos."));		
		} else {
			chat = new Chat();
			chat.setIdAnuncio(idAnucio);
			chat.setIdUsuario(idUsuario);
		}
		chat.setFechaUltimoMensaje(new Date());
		chat = chatsRepository.save(chat);

		Mensaje mensaje = new Mensaje();
		mensaje.setIdChat(chat.getId());
		mensaje.setIdUsuario(idUsuario);
		mensaje.setMensaje(text);
		mensaje.setFecha(new Date());
		mensajesRepository.save(mensaje);		
		
		MensajeDto mensajeDto = new MensajeDto();
		mensajeDto.setMensaje(text);
		mensajeDto.setUsuario(apodo);
		mensajeDto.setFecha(new Date());
		mensajeDto.setMe(true);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mensajeJson = objectMapper.writeValueAsString(mensajeDto);
			session.getBasicRemote().sendText(mensajeJson);	
		} catch (JsonProcessingException e) {
			new CustomException("Error al procesar el json: " + e.getMessage());
		} catch (IOException e) {
			new CustomException("Error: " + e.getMessage());
		}

	}

	@Override
	public void onOpen(Session session, String idAnuncio, String apodo) {

		Usuario usuario = usuariosRepository.getOneByApodo(apodo).orElseThrow(
				() -> new CustomException("El usuario " + apodo + " no se encuentra en la base de datos."));

		Long idAnuncioLong = Long.parseLong(idAnuncio);
		Chat chat = chatsRepository.findByIdAnuncioAndIdUsuario(idAnuncioLong, usuario.getId());

		if (chat != null) {
			enviarMensajesAnteriores(session, chat, apodo);
		}
		session.getUserProperties().put("idChat", chat.getId());
		session.getUserProperties().put("idUsuario", usuario.getId());
		session.getUserProperties().put("apodo", usuario.getApodo());
		session.getUserProperties().put("idAnucio", idAnuncioLong);
	}

	private void enviarMensajesAnteriores(Session session, Chat chat, String apodo) {

		List<Mensaje> mensajesAnteriores = mensajesRepository.findByIdChatOrderByFecha(chat.getId());

		for (Mensaje mensaje : mensajesAnteriores) {
			enviarMensaje(session, mensaje, apodo);
		}
	}

	private void enviarMensaje(Session session, Mensaje mensaje, String apodo) {
		MensajeDto mensajeDto = convertirAMensajeDto(mensaje, apodo);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String mensajeJson = objectMapper.writeValueAsString(mensajeDto);
			session.getBasicRemote().sendText(mensajeJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MensajeDto convertirAMensajeDto(Mensaje mensaje, String apodo) {
		MensajeDto mensajeDto = new MensajeDto();
		mensajeDto.setFecha(mensaje.getFecha());
		mensajeDto.setMensaje(mensaje.getMensaje());

		Usuario usuario = usuariosRepository.findById(mensaje.getIdUsuario()).orElseThrow(
				() -> new CustomException("El chat con id " + mensaje.getIdUsuario() + " no se encuentra en la base de datos."));	

		if (usuario == null) {
			throw new CustomException(
					"El usuario con ID " + mensaje.getIdUsuario() + " no se encuentra en la base de datos.");
		}
		mensajeDto.setUsuario(usuario.getApodo());
		mensajeDto.setMe(usuario.getApodo().equals(apodo));
		return mensajeDto;
	}

}
