package com.blogspot.pchub.valueupdater;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by pchub on 23-07-2017.
 */

public class MainActivityJava extends AppCompatActivity implements View.OnClickListener {

    ValueUpdaterHelper updaterHelper;
    EditText et_value;

    Button b_plus;
    Button b_minus;

    //-----------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updaterHelper = new ValueUpdaterHelper(1, 100, 1);

        // Edit Text updates
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
                try {

                    // Get updated value
                    int updatedValue = updaterHelper.setValue(Integer.parseInt(editable.toString()));

                    Log.d("MainActivityJava", "afterTextChanged: Got updated value from setValue() : " + updatedValue);
                    if (!editable.toString().equals(String.valueOf(updatedValue))) {

                        Log.d("MainActivityJava", "afterTextChanged: Updating to correct value : " + updatedValue);

                        // Append new string to get the cursor at the end of EditText
                        et_value.setText("");
                        et_value.append(String.valueOf(updatedValue));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // Buttons updates
        b_plus = (Button) findViewById(R.id.b_plus);
        b_minus = (Button) findViewById(R.id.b_minus);

        b_plus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                updaterHelper.getLongPressUpdates(b_plus, updaterInterface, true);
                return true;
            }
        });

        b_minus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                updaterHelper.getLongPressUpdates(b_minus, updaterInterface, false);
                return true;
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.b_plus) {

            // Append new string to get the cursor at the end of EditText
            et_value.setText("");
            et_value.append(String.valueOf(updaterHelper.getUpdatedValue(true)));

        } else if (id == R.id.b_minus) {

            // Append new string to get the cursor at the end of EditText
            et_value.setText("");
            et_value.append(String.valueOf(updaterHelper.getUpdatedValue(false)));
        }

    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    ValueUpdaterHelper.ValueUpdaterInterface updaterInterface = new ValueUpdaterHelper.ValueUpdaterInterface() {
        @Override
        public void onValueUpdate(View view, int updatedValue) {

            et_value.setText("");
            et_value.append(String.valueOf(updatedValue));

        }
    };

    //-----------------------------------------------------------------------------------------------------------------------------------------
}
