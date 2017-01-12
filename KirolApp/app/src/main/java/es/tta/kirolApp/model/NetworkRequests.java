package es.tta.kirolApp.model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by asier on 2/01/17.
 */

public class NetworkRequests {
    public NetworkRequests() {
    }


    public static boolean registra(User usuario){
        Boolean estado = false;
        RegistraUsuario registraUsuario = new RegistraUsuario();
        System.out.println("Instanciada clase RegistraUsuario");
        try {
            if(registraUsuario.execute(usuario).get()){
                estado = true;
                return estado;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return estado;
    }
    public static boolean checkUser(User usuario){
        Boolean estado = false;
        CheckUser cU = new CheckUser();
        try {
            if(cU.execute(usuario).get()){
                estado = true;
                return estado;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return estado;

    }

    public static List<Pais> cargaPaises(String cont){
        String serverIP = "http://194.30.12.79/";
        List<Pais> paises = new ArrayList<Pais>();
        String url = serverIP+"getCountries.php?idContinente=";
        Pais esp = new Pais(1, "España");
        Pais fin = new Pais(2, "Finlandia");
        paises.add(esp);
        paises.add(fin);



        String request = url + cont;
        return paises;
    }
    public static List<Deporte> cargaListaDeportes(int pais){
        String serverIP = "http://194.30.12.79/";
        List<Deporte> deportes = new ArrayList<Deporte>();
        String url = serverIP+"getSportsByCountry.php?idPais=";
        Deporte eje1 = new Deporte("Futbol", 1);
        Deporte eje2 = new Deporte("Baloncesto", 2);
        deportes.add(eje1);
        deportes.add(eje2);
        String request = url + pais;
        return deportes;
    }

    public static DeporteXid cargaDeportesXid(int id){
        DeporteXid deporte = new DeporteXid();
        String serverIP = "http://194.30.12.79/";
        String url = serverIP+"getSportById.php?sportID=";
        String request = url + id;

        /*deporte.setIdDeporte(id);
        deporte.setNombre();
        deporte.setUrlAdapCat();
        deporte.setUrlAdapEn();
        deporte.setUrlAdapEs();
        deporte.setUrlAdapEus();
        deporte.setUrlDescCat();
        deporte.setUrlDescEn();
        deporte.setUrlDescEs();
        deporte.setUrlDescEus();*/




        return deporte;
    }


}
/*
requestCountriesURL: function(cont) {
		return this.serverURL+"getCountries.php?idContinente="+cont;
	},
	requestUserURL: function() {
		return this.serverURL+"getUserById.php";
	},
	requestpullUserURL: function() {
		return this.serverURL+"putUser.php";
	},
	requestCheckUser: function() {
		return this.serverURL+"checkUser.php";
	},
	requestSportsByCountry: function(id) {
		return this.serverURL+"getSportsByCountry.php?idPais="+id;
	},
	requestSport: function(id) {
		return this.serverURL+"getSportById.php?sportID="+id;
	},
	requestForumMessages: function(idForum) {
		return this.serverURL+"getMessagesByForum.php?forumID="+idForum;
	},
	requestPullMessage: function(idForum, mensaje, emisor) {
		return this.serverURL+"putMessageByForum.php?idForo="+idForum+"&message="+mensaje+"&emisor="+emisor;
	},
	requestRemoveMessage: function(id) {
		return this.serverURL+"removeComment.php?id="+id;
	}
 */