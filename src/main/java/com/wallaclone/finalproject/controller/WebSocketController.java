package com.wallaclone.finalproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.wallaclone.finalproject.service.WebSocketService;
import com.wallaclone.finalproject.service.impl.WebSocketServiceImpl;

import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
@Component
public class WebSocketController {

	public static final String GIRL_PNG = "/web_socket/images/female.png";
	public static final String MALE_PNG = "/web_socket/images/male.png";
	
	private final WebSocketService webSocketService;

    public WebSocketController() {
		this.webSocketService = new WebSocketServiceImpl();
    	
    }
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		
		Map<String, List<String>> queryParams = session.getRequestParameterMap();
	    String idAnuncio = null;
	    String apodo = null;
	    if (queryParams.containsKey("id")) {
	        idAnuncio = queryParams.get("id").get(0);
	    }
	    if (queryParams.containsKey("apodo")) {
	        apodo = queryParams.get("apodo").get(0);
	    }
		
		webSocketService.onOpen(session, idAnuncio, apodo);	
		System.out.println(session.getId() + " conexion abierta.");
	}
	@OnMessage
	public void onMessage(String message, Session session) {

		webSocketService.onMessage(session, message);
		System.out.println(session.getId() + " mensaje enviado.");
	}

	@OnClose
	public void onClose(Session session){

		System.out.println(session.getId() + " conexión cerrada.");
	}
}
