package com.wallaclone.finalproject.service;

import jakarta.websocket.Session;

public interface WebSocketService {
	
	public void onOpen(Session session, String idAnuncio, String apodo);
	
	public void onOpen(Session session, String idAnuncio, String apodo, String idChat);
	
	public void onMessage(Session session, String text);
}
