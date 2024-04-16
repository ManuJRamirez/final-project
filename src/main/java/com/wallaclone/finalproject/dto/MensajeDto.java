package com.wallaclone.finalproject.dto;

import java.io.Serializable;
import java.util.Date;

public class MensajeDto implements Serializable {
	
	private static final long serialVersionUID = 623541339037027136L;
	
	private String usuario;
    private boolean me;
    private Date fecha;
    private String mensaje;

    public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
