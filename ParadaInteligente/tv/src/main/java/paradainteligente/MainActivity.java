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
    int idBoleteria=-1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview_trips);

        /**
         * ACA DEBERÍAS OBTENER EL ID DE LA BOLETERÍA GUARDADA EN EL SHAREDPREFERENCES
         * EN CASO DE SER NULO, O NO EXISTIR EL SHARED
         * DESCOMENTAR ESTO
         *
         * clickConfiguration(null);
         *
         * SINO
         *
         * idBoleteria = ID_GUARDADO_EN_SHARED_PREFERENCES
         */
        loadListView();
        threadTimeAndUpdate();
    }

    private void loadListView(){
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

        trip2.put("destiny", "Berrotarán");
        trip2.put("route","Berrotarán - Baigorria - Elena");
        trip2.put("time","15:50");
        trip2.put("status","En horario");
        trip2.put("demorated","5 min.");
        trip2.put("platform","44 a 55");
        trip2.put("unity", "152");
        trips.add(trip2);

        trips.add(trip);
        adaptador = new AdaptorTrips(this,trips);
        listView.setAdapter(adaptador);
    }
    public void clickConfiguration(View v){
        int requestCode=1;
        Intent intent = new Intent(this, Configuration.class);//lanzo actividad para configuracion
        intent.putExtra("id_boleteria",idBoleteria);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode!=RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1:
                idBoleteria = data.getIntExtra("id_boleteria",1);
                /**
                 * ACA DEBERÍAS GUARDAR EL ID DE LA BOLETERIA
                 */
                break;
        }
    }

    public void threadTimeAndUpdate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {


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
                            month.setText(dia + "/" + mesE);
                            int weekday = calendario.get(Calendar.DAY_OF_WEEK);
                            switch (weekday) {
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
                    /**
                     * ACA PODÉS LLAMAR AL RUNNABLE DEL WEB SERVICES Y TE EVITAS CORRER OTRO THREAD
                     */

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
