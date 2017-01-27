package es.tta.kirolApp.model.classes;

/**
 * Created by asier on 2/01/17.
 * This is the class to instantiate objects of counties loaded from external source. It only contains data
 * to show inside lists.
 */

public class Sport {
    private String nombre;
    private int id;

    public Sport() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
