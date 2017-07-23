package com.blogspot.pchub.valueupdater;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by pchub on 23-07-2017.
 */

public class MainActivityJava extends AppCompatActivity implements View.OnClickListener {

    ValueUpdaterHelper updaterHelper;
    EditText et_value;

    //-----------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updaterHelper = new ValueUpdaterHelper(1,10,1);

        et_value = (EditText) findViewById(R.id.et_value);
        et_value.setText("1");

        et_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No need to do anything over here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Also ignore this..Not interested at this point as well
            }

            @Override
            public void afterTextChanged(Editable editable) {

                // Sounds good let's start our work here :P

                try{

                    // Get updated value
                    int updatedValue = updaterHelper.setValue(Integer.parseInt(editable.toString()));

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.b_plus) {

            et_value.setText(String.valueOf(updaterHelper.getUpdatedValue(true)));

        } else if (id == R.id.b_minus) {

            et_value.setText(String.valueOf(updaterHelper.getUpdatedValue(false)));
        }

    }

    //-----------------------------------------------------------------------------------------------------------------------------------------
}
