package com.example.dyplom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Control extends AppCompatActivity {

    private RequestQueue mQueue;

    private String[] name_rString = {" "};
    private String[] type_anim = {"Одним цветом", "Градиент","Буквы разного цвета"};
    public String[] send_type = {"Отправить на вcе устройства", "Отправить выбранному устройству"};
     //Выбор скорости ползунок
    private int mDefaultColor = 16777216;
    private String typAnim, temp_D  ;
    private int name_counter = 3;
    private  int lengs_string;
    int defaultColorR, defaultColorG, defaultColorB;
    private String urll_hhed = "45.90.33.227";

    private int p_speed, p_rString;
    private String p_text, p_color;

    public Spinner witch_devicee, rStringNamee, typeCol;
    public TextView textView2, txt1, speedString, txt;
    public EditText masage_string;
    private SeekBar speed;
    private Button mPickColorButton, button_send_all;
    private int type_action;



    int new_massage_id, temp_string_id;
    long[] main_id_rSting;
    String[] main_name_rString, main_code_rString;
    ArrayAdapter<String> admin_name_rStringAdapter;
    int string_color_type_param;
    ArrayAdapter<String>  witch_deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        button_send_all = findViewById(R.id.button);
        speedString = (TextView) findViewById(R.id.speedString);
        speed = (SeekBar) findViewById(R.id.seekBar);
        rStringNamee = (Spinner) findViewById(R.id.rStringName);
        txt = (TextView) findViewById(R.id.textView3);
        mPickColorButton = findViewById(R.id.ColorChangee);
        witch_devicee = findViewById(R.id.witch_device);
        masage_string = findViewById(R.id.editTextTextMultiLine);

        witch_deviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, send_type);
        witch_deviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        witch_devicee.setAdapter(witch_deviceAdapter);


        loadStrings();


        //Получение текста

        //Получение текста

        button_send_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_masage();

            }
        });



        witch_devicee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type_action = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Выбор строки






        rStringNamee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                p_rString = (int) main_id_rSting[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Выбор строки



        //Тип цвета


        ArrayAdapter<String> type_animAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type_anim );
        type_animAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner typeCol = (Spinner) findViewById(R.id.typeCol);
        typeCol.setAdapter(type_animAdapter);

        typeCol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                string_color_type_param = i;
                switch (i) {
                    case  (0):
                        mPickColorButton.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.GONE);

                        break;
                    default:
                        mPickColorButton .setVisibility(View.GONE);
                        txt.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Тип цвета



        //Выбор скорости




        speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                speedString.setText("" + i);
                p_speed=i; //Для XML
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //Выбор скорости


        //Выбор цвета


        mPickColorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // to make code look cleaner the color
                        // picker dialog functionality are
                        // handled in openColorPickerDialogue()
                        // function
                        openColorPickerDialogue();
                    }
                });
        //Выбор цвета


        mQueue = Volley.newRequestQueue(this);
    }

    public void openColorPickerDialogue() {
        final ColorPicker cp = new ColorPicker(com.example.dyplom.Control.this, defaultColorR, defaultColorG, defaultColorB);
        cp.show();
        Button okColor = (Button) cp.findViewById(R.id.okColorButton);

        okColor.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                mDefaultColor= cp.getColor() + 16777216 ;
                mPickColorButton.setBackgroundColor(mDefaultColor - 16777216);
                Log.i("ada", String.valueOf(16777216 + mDefaultColor));
                cp.dismiss();
            }
        });
    }


    private void loadStrings()
    {
        mQueue = Volley.newRequestQueue(this);
        String url_head = "http://"+urll_hhed+":8080/rStrings";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url_head, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    main_id_rSting = new long[response.length()];
                    main_name_rString = new String[response.length()];
                    main_code_rString = new String[response.length()];
                    lengs_string = response.length();
                    for (int i = 0; i < response.length();i++){
                        JSONObject string_params_string_list = response.getJSONObject(i);

                        long string_id = string_params_string_list.getLong("id");
                        String string_name = string_params_string_list.getString("name");
                        String string_code = string_params_string_list.getString("code");

                        main_id_rSting[i]=string_id;
                        main_name_rString[i] = string_name;
                        main_code_rString[i] = string_code;
                    }

                    admin_name_rStringAdapter = new ArrayAdapter<String>(com.example.dyplom.Control.this, android.R.layout.simple_spinner_item, main_name_rString);
                    admin_name_rStringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    rStringNamee.setAdapter(admin_name_rStringAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // в случае возникновеня ошибки
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Не работает", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }



    public void add_masage()
    {
        String url = "http://"+urll_hhed+":8080/geet/massage/";



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("stext",masage_string.getText().toString() );
            object.put("string_speed",p_speed );
            object.put("string_color_type",string_color_type_param );
            object.put("string_color", mDefaultColor);
            object.put("string_timing_type", "not now"  );
            object.put("string_timing","not now"  );
            object.put("showed", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            new_massage_id = (int) response.getLong("id");
                            Log.i("response",String.valueOf(new_massage_id));
                            if (type_action == 0) {
                                for (int klk = 0; klk<main_id_rSting.length; klk++)
                                add_realations(new_massage_id, main_id_rSting[klk]);
                            } else if (type_action == 1)
                            {
                                add_realations(new_massage_id, p_rString);
                            }
                            Toast.makeText(getApplicationContext(), "Сообщение отправлено. Оно будет отображено после проверки модератором", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }



    public void add_realations(int tmp1, long tmp2)
    {
        String url = "http://"+urll_hhed+":8080/geet/massage/"+ String.valueOf(tmp1) + "/string";



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            object.put("id",tmp2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}
