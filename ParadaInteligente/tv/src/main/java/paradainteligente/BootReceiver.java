package paradainteligente;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by nico on 06/11/15.
 */
public class BootReceiver extends BroadcastReceiver
{
    /**
     * con esta clase hago que reciba la data de cuando terminó de cargar el sistema operativo, gracias
     * al permiso boot que esta en el manifiesto, aumaticamente notifica y se ejecuta esto
     * esto lanza una app
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent myIntent = new Intent(context, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

}
