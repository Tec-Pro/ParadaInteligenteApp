package com.tecpro.paradainteligente;

import android.content.Context;

/**
 * Created by jacinto on 07/11/15.
 */
public class PreferencesUsing {


    private static SecurePreferences preferences;

    /*
     *PASARLE COMO PARAMETRO getApplication()
     *@param getApplication()
     */
    public PreferencesUsing(Context c){
        preferences = new SecurePreferences(c, "my-preferences", "BusesLepCordoba", true);
    }

    public void init() {
        if (preferences.getString("id") == null) {
            preferences.put("id", "-");
        }
    }

    public String getId(){
        return preferences.getString("id");
    }

    public void setId(String i){
        preferences.put("id", i);
    }
}
