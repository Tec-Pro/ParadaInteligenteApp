package com.tecpro.paradainteligente;

import android.content.Context;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by jacinto on 05/11/15.
 */
public class WebServices {
    private static String NAMESPACE = "urn:LepWebServiceIntf-ILWService"; //figura claramente en el xml del webservice
    private static String horariosProximaSalida = "HorariosProximaSalida"; //nombre del metodo del ws

    private static String VALIDATION_URI = "https://webservices.buseslep.com.ar/WebServices/WSHorariosProximaSalida.dll";//tiene que ser la uri que muestra el xml
    private static SoapSerializationEnvelope envelope = null;
    private static SoapObject request = null;
    private static HttpTransportSE httpTransportSE = null;

/*
    public static ArrayList<Map<String,Object>> getCities(Context context){
        String result;
        ArrayList<Map<String,Object>> cities = new ArrayList<>();
        request = new SoapObject(NAMESPACE, LocalidadesDesde); //le digo que metodo voy a llamar
        request.addProperty("userWS","UsuarioLep"); //paso los parametros que pide el metodo
        request.addProperty("passWS","Lep1234");
        request.addProperty("id_plataforma",1);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); //no se toda esta configuracion cual esta bien y cual mal
        envelope.enc = SoapSerializationEnvelope.ENC2003;
        envelope.setOutputSoapObject(request);
        httpTransportSE = new HttpTransportSE(VALIDATION_URI); //paso la uri donde transportaré
        try {
            try{
                httpTransportSE.call(NAMESPACE + "#" + LocalidadesDesde, envelope); //llamo al metodo, aca se puede cambiar soap_action por la concatenacion para hacerlo mas general
            }catch (Exception e){
                try {
                    httpTransportSE.call(NAMESPACE + "#" + LocalidadesDesde, envelope); //llamo al metodo, aca se puede cambiar soap_action por la concatenacion para hacerlo mas general
                }catch (java.net.UnknownHostException | java.net.SocketTimeoutException ex){
                    String message= "Ud. no posee conexión de internet; \n acceda a través de una red wi-fi o de su prestadora telefónica";
                    Intent intentDialog = new Intent(context, Dialog.class);
                    intentDialog.putExtra("message",message);
                    intentDialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentDialog);
                }
            }
            result= (String)envelope.getResponse();
            JSONArray json= new JSONObject(result).getJSONArray("Data");
            int i=0;
            while(i<json.length()){
                JSONObject jsonObject= json.getJSONObject(i);
                HashMap<String,Object> map= new HashMap<>();
                map.put("id",jsonObject.getInt("ID_Localidad"));
                map.put("name",jsonObject.getString("Localidad"));
                cities.add(map);
                i++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cities;
    }*/

}
