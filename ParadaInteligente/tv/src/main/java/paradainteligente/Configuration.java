package paradainteligente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.tecpro.paradainteligente.R;

/**
 * Created by nico on 05/11/15.
 */
public class Configuration extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_configuration);
        //ACA LEVANTAR EL ID GUARDADO Y SETEARLO
        EditText txtId = (EditText) findViewById(R.id.txt_id_boleteria);
        txtId.setText("5");
        this.setFinishOnTouchOutside(false);
        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = this.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void clickAcept(View v){
        EditText txtId = (EditText) findViewById(R.id.txt_id_boleteria);
        if(!txtId.getText().toString().isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra("id_boleteria", Integer.valueOf(txtId.getText().toString()));
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
