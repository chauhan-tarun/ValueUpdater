package com.blogspot.pchub.valueupdater;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by pchub on 23-07-2017.
 */

public class MainActivityJava extends AppCompatActivity implements View.OnClickListener {

    ValueUpdaterHelper updaterHelper;
    EditText et_value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updaterHelper = new ValueUpdaterHelper(1,10,1);

        et_value = (EditText) findViewById(R.id.et_value);
        et_value.setText("1");
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.b_plus) {

            et_value.setText(String.valueOf(updaterHelper.getUpdatedValue(true)));

        } else if (id == R.id.b_minus) {

            et_value.setText(String.valueOf(updaterHelper.getUpdatedValue(false)));
        }

    }
}
