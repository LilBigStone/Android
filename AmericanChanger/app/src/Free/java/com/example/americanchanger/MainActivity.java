package com.example.americanchanger;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

class FreeActivity extends AppCompatActivity {

    MainViewModel model;
    float measure_value = 0;
    String stringArrayValue = "length";
    Spinner spOutput;
    Spinner spInput;

    ArrayAdapter<String> american_length_Adapter;
    ArrayAdapter<String> world_length_Adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ViewModelProviders.of(this).get(MainViewModel.class);

        model.EU_live_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                final TextView digits_EU = (TextView)findViewById(R.id.world_val);
                digits_EU.setText(s);
            }
        });
        model.US_live_data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                final TextView digits_US = (TextView)findViewById(R.id.american_val);
                digits_US.setText(s);
        }
        });

        spOutput = (Spinner)findViewById(R.id.output_spinner);
        spInput = (Spinner)findViewById(R.id.input_spinner);

        american_length_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.american_length));
        world_length_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.world_length));


        american_length_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        world_length_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spOutput.setAdapter(american_length_Adapter);
        spInput.setAdapter(world_length_Adapter);

        spOutput.setOnItemSelectedListener(onAmericanItemSelectedListener());
        spInput.setOnItemSelectedListener(onWorldItemSelectedListener());


    }

    AdapterView.OnItemSelectedListener onAmericanItemSelectedListener(){
        return new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (stringArrayValue){
                    case("length"):
                        switch (position){
                            case (0):
                                measure_value = (float) 0.000621371;
                                break;
                            case (1):
                                measure_value = (float) 1.09361296;
                                break;
                            case (2):
                                measure_value = (float) 3.28084;
                                break;
                            case (3):
                                measure_value = (float) 39.3701;
                                break;
                        }
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                measure_value = (float) 0.000621371;
            }
        };
    }



    AdapterView.OnItemSelectedListener onWorldItemSelectedListener(){
        return new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (stringArrayValue){
                    case("length"):
                        switch (position){
                            case (0):
                                measure_value *= (float) 1000;
                                break;
                            case (1):
                                measure_value *= (float) 1;
                                break;
                            case (2):
                                measure_value *= (float) 0.01;
                                break;
                            case (3):
                                measure_value *= (float) 0.001;
                                break;

                        }
                        break;

                }



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                measure_value *= (float) 1000;
            }
        };
    }

    AdapterView.OnItemSelectedListener onMeasureItemSelectedListener(){
        return new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case (0):
                        stringArrayValue = "length";
                        spOutput.setAdapter(american_length_Adapter);
                        spInput.setAdapter(world_length_Adapter);
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stringArrayValue = "length";
                spOutput.setAdapter(american_length_Adapter);
                spInput.setAdapter(world_length_Adapter);
            }
        };
    }

    public void btnAddDigit(View v){

        Button digit_btn = (Button)v;

        String btn_text = digit_btn.getText().toString();
        final TextView digits_US = (TextView)findViewById(R.id.american_val);
        final TextView digits_EU = (TextView)findViewById(R.id.world_val);
        model.EU_live_data.setValue(digits_EU.getText().toString());
        model.US_live_data.setValue(digits_US.getText().toString());

        digits_EU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(model.EU_live_data.getValue().length()!=0) {
                    float result = Float.parseFloat(model.EU_live_data.getValue());
                    result *= measure_value;
                    model.US_live_data.setValue(Float.toString(result));
                    digits_US.setText(model.US_live_data.getValue());

                }else{
                    model.US_live_data.setValue("");
                    digits_US.setText(model.US_live_data.getValue());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(v.getId() != R.id.btn_c && v.getId() != R.id.btn_back ){
            String convert_text = model.EU_live_data.getValue();
            model.EU_live_data.setValue(convert_text.concat(btn_text));
            digits_EU.setText(model.EU_live_data.getValue());
        }
        else if(v.getId() == R.id.btn_c || digits_EU.getText().length() <= 0  ){
//            model.EU_live_data.setValue(null);
            digits_EU.setText(null);
        }
        else{
            StringBuffer sb = new StringBuffer(digits_EU.getText());
            model.EU_live_data.setValue(sb.deleteCharAt(digits_EU.getText().length()-1).toString());
            digits_EU.setText(model.EU_live_data.getValue());
        }


    }
}