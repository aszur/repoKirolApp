package es.tta.kirolApp.model.classes;

import java.util.Date;

/**
 * Created by asier on 5/01/17.
 * This is the class to instantiate objects of comments loaded from external source.
 */

public class Comment {
    private int id;
    private String remitente;
    private String mensaje;
    private String fecha;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String  getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
