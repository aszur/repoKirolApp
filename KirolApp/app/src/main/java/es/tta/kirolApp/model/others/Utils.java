package es.tta.kirolApp.model.others;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by asier on 18/01/17.
 */

public class Utils{
    //Utils for language
    public static void cambiaIdioma(String idioma, Resources res){
        Locale myLocale = new Locale(idioma);
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
