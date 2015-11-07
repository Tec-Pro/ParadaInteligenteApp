package com.tecpro.paradainteligente;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by nico on 05/11/15.
 */
public class AdaptorTrips extends BaseAdapter {
    private LayoutInflater inflador; // Crea Layouts a partir del XML
    private TextView lblDestiny,lblRoute, lblTime, lblStatus, lblDemorated, lblPlatform, lblUnity;
    public static ArrayList<Map<String,String>> trips;

    public AdaptorTrips(Context contexto, ArrayList<Map<String,String>> trips) {
        this.trips = trips;
        inflador =(LayoutInflater)contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public View getView(int posicion, View vistaReciclada,ViewGroup padre) {
        Map<String,String> trip = trips.get(posicion);
        if (vistaReciclada == null) {
            vistaReciclada = inflador.inflate(R.layout.row_trips, null);
        }
        lblDestiny = (TextView) vistaReciclada.findViewById(R.id.lbl_destiny);
        lblRoute = (TextView) vistaReciclada.findViewById(R.id.lbl_route);
        lblTime = (TextView) vistaReciclada.findViewById(R.id.lbl_time);
        lblStatus = (TextView) vistaReciclada.findViewById(R.id.lbl_status);
        lblDemorated = (TextView) vistaReciclada.findViewById(R.id.lbl_demorated);
        lblPlatform = (TextView) vistaReciclada.findViewById(R.id.lbl_platform);
        lblUnity = (TextView) vistaReciclada.findViewById(R.id.lbl_unity);
        if (trip.get("status").contains("demora")){//si esta demorado lo pongo rojo
            lblStatus.setTextColor(Color.argb(249, 249, 8, 0));
            lblDemorated.setText(trip.get("demorated"));
        }else {
            lblStatus.setTextColor(Color.BLACK);
            lblDemorated.setVisibility(View.GONE);
        }
        lblDestiny.setText(trip.get("destiny"));
        lblRoute.setText(trip.get("route"));
        lblTime.setText(trip.get("time"));
        lblStatus.setText(trip.get("status"));
        lblPlatform.setText(trip.get("platform"));
        lblUnity.setText(trip.get("unity"));
        return vistaReciclada;
    }
    public int getCount() {
        return trips.size();
    }
    public Object getItem(int posicion) {
        return trips.get(posicion);
    }
    public long getItemId(int posicion) {
        return posicion;
    }

}
