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
        AT_UserRegister registraUsuario = new AT_UserRegister();
        System.out.println("Instanciada clase AT_UserRegister");
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
        AT_CheckUser cU = new AT_CheckUser();
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
        AT_GetCountries gC = new AT_GetCountries();
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
        AT_GetSportsList gsL = new AT_GetSportsList();
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
        AT_GetSportById gsbI = new AT_GetSportById();
        try {
            deporte = gsbI.execute(id).get();
            return deporte;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return deporte;
    }

    public static List<Comentario> cargaComentarios(String idForo){
        List<Comentario> listaComentarios = new ArrayList<Comentario>();
        AT_GetForumComments gfC = new AT_GetForumComments();
        try {
            listaComentarios = gfC.execute(idForo).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return listaComentarios;
    }

    public static boolean enviaComentario(Comentario comentario){
        boolean estado = false;
        AT_PullComment pC = new AT_PullComment();
        try {
            estado = pC.execute(comentario).get();
            System.out.println("Execute PullComment devuelve: "+estado);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return estado;
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