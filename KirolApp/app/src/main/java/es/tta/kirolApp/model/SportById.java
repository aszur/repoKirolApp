package es.tta.kirolApp.model;

/**
 * Created by asier on 2/01/17.
 */

public class SportById {
    private int idDeporte;
    private String nombre;
    private String urlDescEs;
    private String urlDescEus;
    private String urlDescEn;
    private String urlDescCat;
    private String urlAdapEs;
    private String urlAdapEus;
    private String urlAdapEn;
    private String urlAdapCat;
    private int idForoBajo;
    private int idForoMedio;
    private int idForoAlto;

    public SportById(/*int idDeporte, String nombre,
                      String urlDescEs, String urlDescEus,
                      String urlDescEn, String urlDescCat,
                      String urlAdapEs, String urlAdapEus,
                      String urlAdapEn, String urlAdapCat,
                      int idForoBajo, int idForoMedio, int idForoAlto*/) {
        /*this.idDeporte = idDeporte;
        this.nombre = nombre;
        this.urlDescEs = urlDescEs;
        this.urlDescEus = urlDescEus;
        this.urlDescEn = urlDescEn;
        this.urlDescCat = urlDescCat;
        this.urlAdapEs = urlAdapEs;
        this.urlAdapEus = urlAdapEus;
        this.urlAdapEn = urlAdapEn;
        this.urlAdapCat = urlAdapCat;
        this.idForoBajo = idForoBajo;
        this.idForoMedio = idForoMedio;
        this.idForoAlto = idForoAlto;*/
    }

    public int getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlDescEs() {
        return urlDescEs;
    }

    public void setUrlDescEs(String urlDescEs) {
        this.urlDescEs = urlDescEs;
    }

    public String getUrlDescEus() {
        return urlDescEus;
    }

    public void setUrlDescEus(String urlDescEus) {
        this.urlDescEus = urlDescEus;
    }

    public String getUrlDescEn() {
        return urlDescEn;
    }

    public void setUrlDescEn(String urlDescEn) {
        this.urlDescEn = urlDescEn;
    }

    public String getUrlDescCat() {
        return urlDescCat;
    }

    public void setUrlDescCat(String urlDescCat) {
        this.urlDescCat = urlDescCat;
    }

    public String getUrlAdapEs() {
        return urlAdapEs;
    }

    public void setUrlAdapEs(String urlAdapEs) {
        this.urlAdapEs = urlAdapEs;
    }

    public String getUrlAdapEus() {
        return urlAdapEus;
    }

    public void setUrlAdapEus(String urlAdapEus) {
        this.urlAdapEus = urlAdapEus;
    }

    public String getUrlAdapEn() {
        return urlAdapEn;
    }

    public void setUrlAdapEn(String urlAdapEn) {
        this.urlAdapEn = urlAdapEn;
    }

    public String getUrlAdapCat() {
        return urlAdapCat;
    }

    public void setUrlAdapCat(String urlAdapCat) {
        this.urlAdapCat = urlAdapCat;
    }

    public int getIdForoBajo() {
        return idForoBajo;
    }

    public void setIdForoBajo(int idForoBajo) {
        this.idForoBajo = idForoBajo;
    }

    public int getIdForoMedio() {
        return idForoMedio;
    }

    public void setIdForoMedio(int idForoMedio) {
        this.idForoMedio = idForoMedio;
    }

    public int getIdForoAlto() {
        return idForoAlto;
    }

    public void setIdForoAlto(int idForoAlto) {
        this.idForoAlto = idForoAlto;
    }
}
