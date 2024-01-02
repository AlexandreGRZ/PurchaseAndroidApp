package com.example.purchaseclientandroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class Language {

    public static void changeLanguage(Context context, String Language) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        if (Language.equals("en")) {
            setLocale(context,"en");
        } else {
            setLocale(context,"fr");
        }

        Log.d("Language", "Language changed to: " + configuration.locale.getLanguage());

        recreateActivity(context);
    }

    private static void setLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

        // Enregistrer la langue sélectionnée dans les préférences partagées (si nécessaire)
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("language", languageCode);
        editor.apply();
    }

    public static String getLangueFromProperties(Context context) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getAssets();

            // Ouvre le fichier de propriétés
            InputStream inputStream = assetManager.open("C:\\Users\\alexa\\IntelliJIDEAProjects\\PurchaseClientAndroid\\app\\src\\main\\assets\\config.properties");
            properties.load(inputStream);

            // Récupère la valeur de la propriété Langue
            return properties.getProperty("lang", "fr"); // "fr" est la valeur par défaut
        } catch (IOException e) {
            e.printStackTrace();
            return "fr"; // En cas d'erreur, retourne la langue par défaut
        }
    }

    private static void recreateActivity(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.recreate();
        }
    }

}
