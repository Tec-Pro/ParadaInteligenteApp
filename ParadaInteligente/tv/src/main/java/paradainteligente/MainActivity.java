package paradainteligente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.tecpro.paradainteligente.R;
import com.tecpro.paradainteligente.WebServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class MainActivity extends Activity {

    public BaseAdapter adaptador;
    ListView listView;
    int idBoleteria=-1;
    PreferencesUsing dataId;
    AsyncCallerHorariosProximaSalida AsynCall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview_trips);
        focusButtonConfiguration();

        dataId = new PreferencesUsing(MainActivity.this);
        dataId.init();
        if (dataId.getId().equals("-")){
            clickConfiguration(null);
        } else {
            idBoleteria = Integer.valueOf(dataId.getId());
        }

        AsynCall = new AsyncCallerHorariosProximaSalida(this);

        this.listView.requestFocus();

        threadTimeAndUpdate();
    }


    private void focusButtonConfiguration(){
        ImageButton btnConfig = (ImageButton) findViewById(R.id.btn_configuration);
        btnConfig.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ((ImageButton) v).setImageResource(R.drawable.ic_launcher);

                }
                else{
                    ((ImageButton) v).setImageResource(android.R.drawable.ic_menu_preferences);

                }
            }
        });
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
                this.listView.requestFocus();
                dataId.setId(String.valueOf(idBoleteria));
                AsynCall = new AsyncCallerHorariosProximaSalida(getApplicationContext());
                AsynCall.execute();
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
                    AsynCall = new AsyncCallerHorariosProximaSalida(getApplicationContext());
                    AsynCall.execute();
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private class AsyncCallerHorariosProximaSalida extends AsyncTask<String, Void, ArrayList<Map<String,String>> > {

        Context context; //contexto para largar la activity aca adentro

        private AsyncCallerHorariosProximaSalida(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected ArrayList<Map<String,String>> doInBackground(String... params) {
            return WebServices.getHorariosProximaSalida(idBoleteria, getApplicationContext());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(ArrayList<Map<String,String>> result) {
            if (result !=null )
                adaptador = new AdaptorTrips(context,result);
            listView.setAdapter(adaptador);
        }
    }

}
