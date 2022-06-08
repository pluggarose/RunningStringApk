package com.example.dyplom;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yuku.ambilwarna.AmbilWarnaDialog;

public class View extends AppCompatActivity {


    //
    private String urll_hhed = "45.90.33.227";
    private TextView mTextViewResults;
    private RequestQueue mQueue;
    String[] change_params = {"Изменить", "Добавить", "Удалить"};
    int lengs_string;
    int temp_string_id;


    public int[] id, string_speed, string_color_type, string_showing, string_color;
    public String[]  string_timing_type, string_timing ;
    public String[] string_text = {""};


    public String[] main_name_rString, main_code_rString;
    public long[] main_id_rSting = {0};

    public Spinner rStringName;
    ArrayAdapter<String> admin_name_rStringAdapter;

    Spinner rString_action,massage_action;


    //Блок сообщений

    int temp_id;

    public EditText getter_string_text ;
    public TextView getter_string_speed ;
    public SeekBar string_sp_speed ;
    public Spinner getter_string_color_type ;
    public Button getter_string_color ;
    public Button send_massage, put_massage, del_all, del_conect ;
    public CheckBox String_admin_Chb;
    Spinner str_list;
    ArrayAdapter<String> string_textAdapter;

    int str_id_sel;


    private String[] type_anim = {"Одним цветом", "Градиент","Буквы разного цвета"};
    int string_speed_param, temp_showed;
    int string_color_type_param;
    int string_color_param;
    int mDefaultColor, defaultColorR, defaultColorG, defaultColorB;

    int new_massage_id;


    Button string_admin_load_dat;


    //Блок сообщений

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        del_all = findViewById(R.id.string_admin_delete_button2);
        del_conect = findViewById(R.id.string_admin_delete_button);
        getter_string_text = findViewById(R.id.string_admin_editTextTextMultiLine3);
        getter_string_speed = findViewById(R.id.string_admin_speedString2);
        string_sp_speed = findViewById( R.id.string_admin_seekBar2);
        getter_string_color_type = findViewById(R.id.string_admin_typeCol2);
        getter_string_color = findViewById(R.id.string_admin_setcolor);
        send_massage = findViewById(R.id.string_admin_post);
        put_massage = findViewById(R.id.string_admin_put);
        rString_action = findViewById(R.id.string_admin_actoin_selector);
        massage_action = findViewById(R.id.string_admin_action_spinner);
        mTextViewResults = findViewById(R.id.string_admin_stack_view);


        LinearLayout str_del = findViewById(R.id.string_admin_delete_layout);
        LinearLayout str_ed_new = findViewById(R.id.string_admin_edit_layout);
        LinearLayout rStr_new = findViewById(R.id.string_admin_action_selected_new_layout);
        LinearLayout rStr_ed_del = findViewById(R.id.string_admin_action_selected_edit_delete);
        LinearLayout rStr_ed = findViewById(R.id.string_admin_action_selected_edit_layout);
        LinearLayout rStr_del = findViewById(R.id.string_admin_action_selected_delete);
        rStringName = findViewById(R.id.string_admin_selector);
        str_list = findViewById(R.id.string_admin_stack_spinner);
        Button delete_rString_btn = findViewById(R.id.string_admin_delete_rString);
        Button post_rString_btn = findViewById(R.id.string_admin_new_button);
        String_admin_Chb = findViewById(R.id.string_admin_Chb);
        string_admin_load_dat = findViewById(R.id.string_admin_load_data);
        //Заполнение списка строк

        loadStrings();

        //Заполнение списка строк

        String_admin_Chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)
                {
                    String_admin_Chb.setText("Сообщение показываетя на устройстве");
                    temp_showed = 1;
                } else {
                    String_admin_Chb.setText("Сообщение не показываетя на устройстве");
                    temp_showed = 0;
                }
            }
        });

        //
        send_massage.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                add_masage();

            }
        });

        put_massage.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                update_masage(str_id_sel);
            }
        });
        //



        getter_string_color.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        // to make code look cleaner the color
                        // picker dialog functionality are
                        // handled in openColorPickerDialogue()
                        // function
                        openColorPickerDialogue();
                    }
                }
        );


        string_admin_load_dat.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                load_view();
            }
        });



        //Spinner типа анимации
        ArrayAdapter<String> anim_type = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type_anim );
        anim_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getter_string_color_type.setAdapter(anim_type);

        getter_string_color_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                string_color_type_param = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Spinner типа анимации

        //SeekBar скорости
        string_sp_speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                string_speed_param = i;
                getter_string_speed.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //SeekBar скорости





        //Spinner с устройством, в котором мы делаем delete/post и заполняем spinner сообщений
        rStringName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {

                temp_string_id = (int)main_id_rSting[i];

                post_rString_btn.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View view) {
                        post_rString();

                    }
                });

                //Удаляем устройства + отношения
                delete_rString_btn.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View view) {
                        for (int j : id) {
                            delete_realations(j, temp_string_id);
                        }
                        delete_rString(temp_string_id);
                    }
                });
                //Удаляем устройства + отношения



                mTextViewResults.setText("");
                get_string_by_id(temp_string_id);
                //loadStrings();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Spinner с устройством, в котором мы делаем delete/post и заполняем spinner сообщений


        //Spinner выбора сообщения
        str_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                str_id_sel=id[i];
                temp_id = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Spinner выбора сообщения

        //Удалить все сообщения
        del_all.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                delete_massage_ById(str_id_sel);
            }
        });
        //Удалить все сообщения

        //Убрать соединения
        del_conect.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                delete_realations(str_id_sel, temp_string_id );
            }
        });
        //Убрать соединения


        //Выбор действия с устройсством
        ArrayAdapter<String> rString_edit = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, change_params );
        rString_edit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rString_action.setAdapter(rString_edit);



        rString_action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                switch (i) {
                    case  (0):
                        rStr_new.setVisibility(android.view.View.GONE);
                        rStr_ed_del.setVisibility(android.view.View.VISIBLE);
                        rStr_ed.setVisibility(android.view.View.VISIBLE);
                        rStr_del.setVisibility(android.view.View.GONE);


                        break;
                    case (1):
                        rStr_new.setVisibility(android.view.View.VISIBLE);
                        rStr_ed_del.setVisibility(android.view.View.GONE);
                        rStr_ed.setVisibility(android.view.View.GONE);
                        rStr_del.setVisibility(android.view.View.GONE);

                        break;
                    case(2):
                        rStr_new.setVisibility(android.view.View.GONE);
                        rStr_ed_del.setVisibility(android.view.View.VISIBLE);
                        rStr_ed.setVisibility(android.view.View.GONE);
                        rStr_del.setVisibility(android.view.View.VISIBLE);
                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Выбор действия с устройством




        //Выбор действия с сообщением
        ArrayAdapter<String> admin_massage_action_selector = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, change_params );
        admin_massage_action_selector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        massage_action.setAdapter(admin_massage_action_selector);

        massage_action.setPrompt("Выбор бегущей строки");


        massage_action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                switch (i) {
                    case  (0):
                        str_ed_new.setVisibility(android.view.View.VISIBLE);
                        str_del.setVisibility(android.view.View.GONE);
                        put_massage.setVisibility(android.view.View.VISIBLE);
                        send_massage.setVisibility(android.view.View.GONE);
                        string_admin_load_dat.setVisibility(android.view.View.VISIBLE);
                        str_list.setVisibility(android.view.View.VISIBLE);

                        break;
                    case  (1):
                        str_ed_new.setVisibility(android.view.View.VISIBLE);
                        str_del.setVisibility(android.view.View.GONE);
                        put_massage.setVisibility(android.view.View.GONE);
                        send_massage.setVisibility(android.view.View.VISIBLE);
                        string_admin_load_dat.setVisibility(android.view.View.GONE);
                        str_list.setVisibility(android.view.View.GONE);
                        clear_view();
                        break;
                    case  (2):
                        str_ed_new.setVisibility(android.view.View.GONE);
                        str_del.setVisibility(android.view.View.VISIBLE);
                        put_massage.setVisibility(android.view.View.GONE);
                        send_massage.setVisibility(android.view.View.GONE);
                        string_admin_load_dat.setVisibility(android.view.View.GONE);
                        str_list.setVisibility(android.view.View.VISIBLE);
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Выбор действия с сообщением




        mQueue = Volley.newRequestQueue(this);


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

                        Log.i("Id", String.valueOf(main_id_rSting[i]));
                        Log.i("Id", main_name_rString[i]);
                        Log.i("Id", main_code_rString[i]);
                        Log.i("Id", String.valueOf(main_id_rSting.length));
                    }

                    admin_name_rStringAdapter = new ArrayAdapter<String>(View.this, android.R.layout.simple_spinner_item, main_name_rString);
                    admin_name_rStringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    rStringName.setAdapter(admin_name_rStringAdapter);


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



    public void post_rString()
    {
        String url = "http://"+urll_hhed+":8080/rStrings";

        EditText name_str = (EditText)findViewById(R.id.string_admin_new_id_text);
        EditText code_str = (EditText)findViewById(R.id.string_admin_new_name_text);

        String str_name = name_str.getText().toString();
        String str_code = code_str.getText().toString();


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("name",str_name);
            object.put("code",str_code);
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

    private void delete_rString(int param)
    {
        String url = "http://"+urll_hhed+":8080/rStrings/" + String.valueOf(param);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(View.this, response , Toast.LENGTH_SHORT).show();
                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(View.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        // error.

                    }
                }
        );
        queue.add(dr);
    }



    private void get_string_by_id(int tt)
    {
        String url_head = "http://"+urll_hhed+":8080/geet/string/"+ String.valueOf(tt) +"/massage";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url_head, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    id = new int[response.length()];
                    string_speed = new int[response.length()];
                    string_color_type = new int[response.length()];
                    string_text = new String[response.length()];
                    string_color = new int[response.length()];
                    string_timing_type = new String[response.length()];
                    string_timing = new String[response.length()];
                    string_showing = new int[response.length()];

                    for (int i = 0; i < response.length();i++){
                        JSONObject string_params = response.getJSONObject(i);

                        int Id = string_params.getInt("id");
                        String String_text = string_params.getString("stext");
                        int String_speed = string_params.getInt("string_speed");
                        int String_color_type = string_params.getInt("string_color_type");
                        int String_color = string_params.getInt("string_color");
                        String String_timing_type = string_params.getString("string_timing_type");
                        String String_timing = string_params.getString("string_timing");
                        int String_showing = string_params.getInt("showed");

                        id[i] = Id;
                        string_speed[i]  = String_speed;
                        string_color_type[i]  = String_color_type;
                        string_text[i]  = String_text;
                        string_color[i]  = String_color;
                        string_timing_type[i]  = String_timing_type;
                        string_timing[i]  = String_timing;
                        string_showing[i] = String_showing;

                        mTextViewResults.append("Id сообщения: " + String.valueOf(Id) + ", Текст сообщения: " + String_text + ", Скорость сообщения: " + String.valueOf(String_speed)+", Тип цвета: " + String.valueOf(String_color_type)+ ", Цвет: " + String_color + ", Время отображения: " + String_timing + ", Отображение: " + String.valueOf(String_showing) +"\n\n");

                        string_textAdapter = new ArrayAdapter<String>(View.this, android.R.layout.simple_spinner_item, string_text);
                        string_textAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        str_list.setAdapter(string_textAdapter);
                        string_textAdapter.notifyDataSetChanged();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // в случае возникновеня ошибки
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextViewResults.setText("Error");
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private void delete_realations(int param1, int param2)
    {
        String url = "http://"+urll_hhed+":8080/geet/massage/" + String.valueOf(param1) + "/string/" + String.valueOf(param2);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(View.this, response , Toast.LENGTH_SHORT).show();
                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(View.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        // error.

                    }
                }
        );
        queue.add(dr);
    }




    public void add_masage()
    {
        String url = "http://"+urll_hhed+":8080/geet/massage/";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("stext",getter_string_text.getText().toString() );
            object.put("string_speed",string_speed_param );
            object.put("string_color_type",string_color_type_param);
            object.put("string_color", string_color_param );
            object.put("string_timing_type", "not now"  );
            object.put("string_timing","not now"  );
            object.put("showed",  temp_showed);
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
                            add_realations(new_massage_id, temp_string_id);
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

    private void delete_massage_ById(int param)
    {
        String url = "http://"+urll_hhed+":8080/geet/massage/" + String.valueOf(param);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(View.this, response , Toast.LENGTH_SHORT).show();
                        // response
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(View.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        // error.

                    }
                }
        );
        queue.add(dr);
    }

    public void update_masage(int tt)
    {
        String url = "http://"+urll_hhed+":8080/geet/massage/"+ String.valueOf(tt);

        EditText name_str = (EditText)findViewById(R.id.string_admin_new_id_text);
        EditText code_str = (EditText)findViewById(R.id.string_admin_new_name_text);

        String str_name = name_str.getText().toString();
        String str_code = code_str.getText().toString();


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("stext",getter_string_text.getText().toString() );
            object.put("string_speed",string_speed_param );
            object.put("string_color_type",string_color_type_param );
            object.put("string_color", string_color_param );
            object.put("string_timing_type", "not now" );
            object.put("string_timing","not now"  );
            object.put("showed", temp_showed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object,
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

    public void openColorPickerDialogue() {
        final ColorPicker cp = new ColorPicker(View.this, defaultColorR, defaultColorG, defaultColorB);
        cp.show();
        Button okColor = (Button) cp.findViewById(R.id.okColorButton);

        okColor.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                mDefaultColor = cp.getColor();
                getter_string_color.setBackgroundColor(mDefaultColor);
                Log.i("ada", String.valueOf(mDefaultColor));
                string_color_param = 16777216 + mDefaultColor;
                cp.dismiss();
            }
        });


    }


    void load_view()
    {
        getter_string_text.setText(string_text[temp_id]);
        string_sp_speed.setProgress(string_speed[temp_id]); ;
        getter_string_color_type.setSelection(string_color_type[temp_id]);
        mDefaultColor = string_color[temp_id] - 16777216;
        string_color_param = string_color[temp_id];
        getter_string_color.setBackgroundColor(mDefaultColor);
        if (string_showing[temp_id] == 1) {
            String_admin_Chb.setChecked(true);
        }else String_admin_Chb.setChecked(false);
    }
    void clear_view()
    {
        getter_string_text.setText(" ");
        string_sp_speed.setProgress(11); ;
        getter_string_color_type.setSelection(0);
        string_color_param = mDefaultColor = 16777216 - 1589090;
        getter_string_color.setBackgroundColor(-1589090);
    }
}


