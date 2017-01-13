package es.tta.kirolApp.model;

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
        UserRegister registraUsuario = new UserRegister();
        System.out.println("Instanciada clase UserRegister");
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
                System.out.println("En NR, cU devuelve true");
                estado = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return estado;

    }

    public static List<Pais> cargaPaises(String cont){
        List<Pais> paises = new ArrayList<Pais>();
        GetCountries gC = new GetCountries();
        try {
            paises = gC.execute(cont).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //String request = url + cont;
        return paises;
    }
    public static List<Deporte> cargaListaDeportes(String pais){
        List<Deporte> deportes = new ArrayList<Deporte>();
        GetSportsList gsL = new GetSportsList();
        try {
            deportes = gsL.execute(pais).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return deportes;
    }

    public static DeporteXid cargaDeportesXid(String id){
        DeporteXid deporte = new DeporteXid();
        GetSportById gsbI = new GetSportById();
        try {
            deporte = gsbI.execute(id).get();
            return deporte;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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