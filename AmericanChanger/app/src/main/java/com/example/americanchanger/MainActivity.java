package com.example.americanchanger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.example.americanchanger.BuildConfig;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MainViewModel model;
    float measure_value = 0;
    String stringArrayValue = "length";
    Spinner measureInput;
    Spinner spOutput;
    Spinner spInput;




    boolean check = true;

    ArrayAdapter<String> american_weigh_Adapter;
    ArrayAdapter<String> world_weigh_Adapter;

    ArrayAdapter<String> american_volume_Adapter;
    ArrayAdapter<String> world_volume_Adapter;

    ArrayAdapter<String> measureAdapter;
    ArrayAdapter<String> american_length_Adapter;
    ArrayAdapter<String> world_length_Adapter;
    boolean demo_check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        demo_check = BuildConfig.IS_DEMO;



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(demo_check){
            Spinner changeVisible = (Spinner)findViewById(R.id.measure_spinner);
            changeVisible.setVisibility(View.INVISIBLE);
        }

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

        if(!demo_check){
            measureInput = (Spinner)findViewById(R.id.measure_spinner);
            american_weigh_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.american_weigh));
            world_weigh_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.world_weigh));

            american_volume_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.american_volume));
            world_volume_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.world_volume));

            measureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.measure));

            measureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            american_weigh_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            world_weigh_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            american_volume_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            world_volume_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            measureInput.setAdapter(measureAdapter);
            measureInput.setOnItemSelectedListener(onMeasureItemSelectedListener());
        }

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
                switch (stringArrayValue) {
                    case ("length"):
                        switch (position) {
                            case (0):
                                if (check) {
                                    measure_value = (float) 0.000621371;
                                } else {
                                    measure_value = (float) (1 / 0.000621371);
                                }

                                break;
                            case (1):
                                if (check) {
                                    measure_value = (float) 1.09361296;
                                    ;
                                } else {
                                    measure_value = (float) (1 / 1.09361296);
                                }


                                break;
                            case (2):
                                if (check) {
                                    measure_value = (float) 3.28084;
                                    ;
                                } else {
                                    measure_value = (float) (1 / 3.28084);
                                }


                                break;
                            case (3):
                                if (check) {
                                    measure_value = (float) 39.3701;
                                    ;
                                } else {
                                    measure_value = (float) (1 / 39.3701);
                                }


                                break;
                        }
                        break;

                        case ("weight"):
                            switch (position) {
                                case (0):
                                    if (check) {
                                        measure_value = (float) 0.00110231250142;
                                    } else {
                                        measure_value = (float) (1 / 0.00110231250142);
                                    }

                                    break;
                                case (1):
                                    if (check) {
                                        measure_value = (float) 0.15747321;
                                    } else {
                                        measure_value = (float) (1 / 0.15747321);
                                    }

                                    break;
                                case (2):
                                    if (check) {
                                        measure_value = (float) 2.204625002841;
                                    } else {
                                        measure_value = (float) (1 / 2.204625002841);
                                    }

                                    break;
                                case (3):
                                    if (check) {
                                        measure_value = (float) 35.274;
                                    } else {
                                        measure_value = (float) (1 / 35.274);
                                    }

                                    break;
                            }
                            break;
                        case ("volume"):
                            switch (position) {
                                case (0):
                                    if (check) {
                                        measure_value = (float) 3.78541;
                                    } else {
                                        measure_value = (float) (1 / 3.78541);
                                    }

                                    break;
                                case (1):
                                    if (check) {
                                        measure_value = (float) 0.946353;
                                    } else {
                                        measure_value = (float) (1 / 0.946353);
                                    }

                                    break;
                                case (2):
                                    if (check) {
                                        measure_value = (float) 0.473176;
                                    } else {
                                        measure_value = (float) (1 / 0.473176);
                                    }
                                    break;
                                case (3):
                                    if (check) {
                                        measure_value = (float) 0.24;
                                    } else {
                                        measure_value = (float) (1 / 0.24);
                                    }
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
                    case("weight"):
                        switch (position){
                            case (0):
                                measure_value *= (float) 0.001;
                                break;
                            case (1):
                                measure_value *= (float) 100;
                                break;
                            case (2):
                                measure_value *= (float) 1;
                                break;
                            case (3):
                                measure_value *= (float) 1000;
                                break;

                        }
                        break;

                    case("volume"):
                        switch (position){
                            case (0):
                                measure_value *= (float) 1000;
                                break;
                            case (1):
                                measure_value *= (float) 1;
                                break;
                            case (2):
                                measure_value *= (float) 0.9;
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
                    case (1):
                        stringArrayValue = "weight";
                        if(!demo_check){
                            spOutput.setAdapter(american_weigh_Adapter);
                            spInput.setAdapter(world_weigh_Adapter);
                        }

                        break;
                    case (2):
                        stringArrayValue = "volume";
                        if(!demo_check){
                            spOutput.setAdapter(american_volume_Adapter);
                            spInput.setAdapter(world_volume_Adapter);
                        }

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

    public void btnChangeMeas(View v){


        if(check){
            if(stringArrayValue.equals("length")){
                spOutput.setAdapter(world_length_Adapter);
                spInput.setAdapter(american_length_Adapter);
            }else if(stringArrayValue.equals("weight")){
                spOutput.setAdapter(world_weigh_Adapter);
                spInput.setAdapter(american_weigh_Adapter);
            }else{
                spOutput.setAdapter(world_volume_Adapter);
                spInput.setAdapter(american_volume_Adapter);
            }
            spOutput.setOnItemSelectedListener(onWorldItemSelectedListener());
            spInput.setOnItemSelectedListener(onAmericanItemSelectedListener());
            check = false;
        }
        else {
            check = true;
            if(stringArrayValue.equals("length")){
                spOutput.setAdapter(american_length_Adapter);
                spInput.setAdapter(world_length_Adapter);
            }else if(stringArrayValue.equals("weight")){
                spOutput.setAdapter(american_weigh_Adapter);
                spInput.setAdapter(world_weigh_Adapter);
            }else{
                spOutput.setAdapter(american_volume_Adapter);
                spInput.setAdapter(world_volume_Adapter);
            }
            spOutput.setOnItemSelectedListener(onAmericanItemSelectedListener());
            spInput.setOnItemSelectedListener(onWorldItemSelectedListener());
        }


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