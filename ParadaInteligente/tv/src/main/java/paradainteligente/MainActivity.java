package paradainteligente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tecpro.paradainteligente.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    public BaseAdapter adaptador;
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Map<String,String>> trips = new ArrayList<>();
        HashMap<String,String> trip =  new HashMap<>();
        trip.put("destiny","Río Cuarto");
        trip.put("route","Berrotarán - Baigorria - Elena");
        trip.put("time","15:50");
        trip.put("status","Con demora");
        trip.put("demorated","5 min.");
        trip.put("platform","44 a 55");
        trip.put("unity","152");
        trips.add(trip);
        trip.put("destiny","Río Cuarto");
        trip.put("route","Berrotarán - Baigorria - Elena");
        trip.put("time","15:50");
        trip.put("status","En demora");
        trip.put("demorated","5 min.");
        trip.put("platform","44 a 55");
        trip.put("unity","152");
        trips.add(trip);
        adaptador = new AdaptorTrips(this,trips);
        listView = (ListView) findViewById(R.id.listview_trips);
        listView.setAdapter(adaptador);
        clock();
    }

    public void clickConfiguration(View v){
        int requestCode=1;
        Intent intent = new Intent(this, Configuration.class);//lanzo actividad para configuracion
        startActivityForResult(intent, requestCode);
    }

    public void clock(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // This code will always run on the UI thread, therefore is safe to modify UI elements.
                        TextView clock = (TextView) findViewById(R.id.lbl_actual_time);
                        TextView day = (TextView) findViewById(R.id.lbl_day);
                        TextView month = (TextView) findViewById(R.id.lbl_month);

                        String dia, mes, año, hora, minutos;
                        Calendar calendario = new GregorianCalendar();
                        Date fechaHoraActual = new Date();
                        int mesE;
                        calendario.setTime(fechaHoraActual);

                        hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
                        minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
                        //segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
                        dia = calendario.get(Calendar.DATE) > 9 ? "" + calendario.get(Calendar.DATE) : "0" + calendario.get(Calendar.DATE);
                        mes = calendario.get(Calendar.MONTH) > 9 ? "" + calendario.get(Calendar.MONTH) : "0" + calendario.get(Calendar.MONTH);
                        año = calendario.get(Calendar.YEAR) > 9 ? "" + calendario.get(Calendar.YEAR) : "0" + calendario.get(Calendar.YEAR);
                        mesE = Integer.valueOf(mes) + 1;
                        clock.setText(hora + ":" + minutos);
                        month.setText(dia+"/"+mesE);
                        int weekday = calendario.get(Calendar.DAY_OF_WEEK);
                        switch (weekday){
                            case 2:
                                day.setText("LUNES");
                                break;
                            case 3:
                                day.setText("MARTES");
                                break;
                            case 4:
                                day.setText("MIERCOLES");
                                break;
                            case 5:
                                day.setText("JUEVES");
                                break;
                            case 6:
                                day.setText("VIERNES");
                                break;
                            case 7:
                                day.setText("SABADO");
                                break;
                            case 1:
                                day.setText("DOMINGO");
                                break;
                        }
                    }
                });

                      try {
                        Thread.sleep(60000);
                   } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
