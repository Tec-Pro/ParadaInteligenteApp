package com.tecpro.paradainteligente;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jacinto on 05/11/15.
 */
public class WebServices {
    private static String NAMESPACE = "urn:LepWebServiceIntf-ILWService"; //figura claramente en el xml del webservice
    private static String horariosProximaSalida = "HorariosProximaSalida"; //nombre del metodo del ws


    private static String VALIDATION_URI = "https://webservices.buseslep.com.ar/WebServices/WSHorariosProximaSalida.dll/soap/ILepWebService";//tiene que ser la uri que muestra el xml
    private static SoapSerializationEnvelope envelope = null;
    private static SoapObject request = null;
    private static HttpTransportSE httpTransportSE = null;


    public static ArrayList<Map<String,String>> getHorariosProximaSalida(int idBoleteria, Context context){
        String result;
        ArrayList<String> ret = new ArrayList<>();
        request = new SoapObject(NAMESPACE, horariosProximaSalida); //le digo que metodo voy a llamar
        request.addProperty("userWS","UsuarioLep"); //paso los parametros que pide el metodo
        request.addProperty("passWS","Lep1234");
        request.addProperty("id_Boleteria",idBoleteria);
        request.addProperty("id_plataforma", 1);


        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); //no se toda esta configuracion cual esta bien y cual mal
        envelope.enc = SoapSerializationEnvelope.ENC2003;
        envelope.setOutputSoapObject(request);
        httpTransportSE = new HttpTransportSE(VALIDATION_URI); //paso la uri donde transportaré
        try {
            try{
                httpTransportSE.call(NAMESPACE + "#" + horariosProximaSalida, envelope); //llamo al metodo, aca se puede cambiar soap_action por la concatenacion para hacerlo mas general
            }catch (Exception e){
                try {
                    httpTransportSE.call(NAMESPACE + "#" + horariosProximaSalida, envelope); //llamo al metodo, aca se puede cambiar soap_action por la concatenacion para hacerlo mas general
                }catch (java.net.UnknownHostException | java.net.SocketTimeoutException ex){
                    ex.printStackTrace();
                }
            }
            result= String.valueOf(envelope.getResponse());;
            JSONArray json= new JSONObject(result).getJSONArray("Data");
            int i=0;
            while(i<json.length()){
                JSONObject jsonObject= json.getJSONObject(i);
                 /* {"Data":[{"SalidaEstimada":"11:17 Hs","Localidades":"Cordoba Plaza  -  Villa Gral Belgrano  -  Santa Rosa Calam","Unidad":"117","Plataforma":"3 - 5 - 6 - 7","destino":" A Santa Rosa Calam","SalidaProgramada":"11:15 Hs","MinutosDemora":0,"Estado":"En Horario"},
              {"SalidaEstimada":"11:24 Hs","Localidades":"Villa Gral Belgrano  -  Cordoba Plaza","Unidad":"111","Plataforma":"3 - 5 - 6 - 7","destino":" A Cordoba Plaza","SalidaProgramada":"11:20 Hs","MinutosDemora":0,"Estado":"En Horario"},
              {"SalidaEstimada":"11:52 Hs","Localidades":"Cordoba Plaza  -  Villa Gral Belgrano  -  Santa Rosa Calam","Unidad":"116","Plataforma":"3 - 5 - 6 - 7","destino":" A Santa Rosa Calam","SalidaProgramada":"11:45 Hs","MinutosDemora":7,"Estado":"Con Demora"},
              {"SalidaEstimada":"12:05 Hs","Localidades":"Villa Gral Belgrano  -  Cordoba Plaza","Unidad":"Sin Unidad","Plataforma":"3 - 5 - 6 - 7","destino":" A Cordoba Plaza","SalidaProgramada":"12:05 Hs","MinutosDemora":0,"Estado":"En Horario"} */
                ret.add(jsonObject.getString("SalidaEstimada"));
                ret.add(jsonObject.getString("Localidades"));
                ret.add(jsonObject.getString("Unidad"));
                ret.add(jsonObject.getString("Plataforma"));
                ret.add(jsonObject.getString("destino"));
                ret.add(jsonObject.getString("SalidaProgramada"));
                ret.add(jsonObject.getString("MinutosDemora"));
                ret.add(jsonObject.getString("Estado"));

                i++;
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

        /**
         * BORRAR ESTOOOOOOO DESPUÉEEES
         *
         *
         *
         *
         */
        ArrayList<Map<String,String>> trips = new ArrayList<>();
        HashMap<String,String> trip =  new HashMap<>();
        HashMap<String,String> trip2 =  new HashMap<>();

        trip.put("destiny","Río Cuarto");
        trip.put("route","Berrotarán - Baigorria - Elena");
        trip.put("time","15:50");
        trip.put("status","Con demora");
        trip.put("demorated","5 min.");
        trip.put("platform","44 a 55");
        trip.put("unity", "152");
        trips.add(trip);
        trips.add(trip);
        trips.add(trip);
        trips.add(trip);
        trips.add(trip);

        trip2.put("destiny", "La concha de tu madre");
        trip2.put("route","Berrotarán - Baigorria - Elena");
        trip2.put("time","15:50");
        trip2.put("status","En horario");
        trip2.put("demorated","5 min.");
        trip2.put("platform","44 a 55");
        trip2.put("unity", "152");
        trips.add(trip2);

        trips.add(trip);
        return trips;
    }

}
