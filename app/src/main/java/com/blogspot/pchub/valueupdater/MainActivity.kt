package com.blogspot.pchub.valueupdater

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal lateinit var updaterHelper: ValueUpdaterHelper

    //-----------------------------------------------------------------------------------------------------------------------------------------

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updaterHelper = ValueUpdaterHelper(1, 100, 1)

        // Edit Text updates
        et_value.setText("1")

        et_value.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // No need to do anything over here
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Also ignore this..Not interested at this point as well
            }

            override fun afterTextChanged(editable: Editable) {

                // Sounds good let's start our work here :P
                try {

                    // Get updated value
                    val updatedValue = updaterHelper.setValue(Integer.parseInt(editable.toString()))

                    Log.d("MainActivityJava", "afterTextChanged: Got updated value from setValue() : " + updatedValue)
                    if (editable.toString() != updatedValue.toString()) {

                        Log.d("MainActivityJava", "afterTextChanged: Updating to correct value : " + updatedValue)

                        // Append new string to get the cursor at the end of EditText
                        et_value.setText("")
                        et_value.append(updatedValue.toString())
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })

        b_plus.setOnClickListener({
            // Append new string to get the cursor at the end of EditText
            et_value.setText("")
            et_value.append(updaterHelper.getUpdatedValue(true).toString())
        })

        b_plus.setOnLongClickListener {
            updaterHelper.getLongPressUpdates(b_plus, updaterInterface, true)
            true
        }


        b_minus.setOnClickListener({
            // Append new string to get the cursor at the end of EditText
            et_value.setText("")
            et_value.append(updaterHelper.getUpdatedValue(false).toString())
        })

        b_minus.setOnLongClickListener {
            updaterHelper.getLongPressUpdates(b_minus, updaterInterface, false)
            true
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    private var updaterInterface: ValueUpdaterHelper.ValueUpdaterInterface = ValueUpdaterHelper.ValueUpdaterInterface { view, updatedValue ->
        et_value.setText("")
        et_value.append(updatedValue.toString())
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------
}
