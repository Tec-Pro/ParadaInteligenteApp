package com.tecpro.paradainteligente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by nico on 05/11/15.
 */
public class Configuration extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_configuration);
        int idBoleteria = getIntent().getIntExtra("id_boleteria",-1);
        EditText txtId = (EditText) findViewById(R.id.txt_id_boleteria);
        if(idBoleteria==-1)
            txtId.setText("");
        else
            txtId.setText(String.valueOf(idBoleteria));
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
