package es.tta.kirolApp.model;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by asier on 18/01/17.
 */

public class Utils {
    //Utils for language
    public static void cambiaIdioma(String idioma, Resources res){
        Locale myLocale = new Locale(idioma);
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
