package es.tta.kirolApp.presentation;

import android.os.Bundle;

/**
 * Created by asier on 23/12/16.
 */
//¿Esta es la interfaz de acceso a business? Clase auxiliar para guardar datos de presentación.

public class Data {
    /*private final static String EXTRA_USER = "es.tta.example.user";
    private final static String EXTRA_AUTH = "es.tta.example.auth";
    private final static String EXTRA_NAME = "es.tta.example.name";
    private final static String EXTRA_EXERCISE_ID = "es.tta.example.exerciseId";
    private final static String EXTRA_EXERCISE_WORDING = "es.tta.example.exerciseWording";

    private final Bundle bundle;

    public Data(Bundle bundle) {
        if(bundle == null){
            bundle = new Bundle();
        }
        this.bundle = bundle;
    }

    public Bundle getBundle(){
        return bundle;
    }

    public void putUserId(int id){
        bundle.putInt(EXTRA_USER, id);
    }

    public int getUserId(){
        return bundle.getInt(EXTRA_USER);
    }

    public void putAuthToken(String auth){
        bundle.putString(EXTRA_AUTH, auth);
    }

    public String getAuthToken(){
        return bundle.getString(EXTRA_AUTH);
    }

    public void putUserName(String name){
        bundle.putString(EXTRA_NAME, name);
    }

    public void putExercise(Exercise exercise){
        bundle.putInt(EXTRA_EXERCISE_ID, exercise.getId());
        bundle.putString(EXTRA_EXERCISE_WORDING, exercise.getWording());
    }
    public Exercise getExercise(){
        Exercise exercise = new Exercise();
        exercise.setId(bundle.getInt(EXTRA_EXERCISE_ID));
        exercise.setWording(bundle.getString(EXTRA_EXERCISE_WORDING));
        return exercise;
    }*/
}
