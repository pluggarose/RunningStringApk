package com.example.dyplom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private String urll_hhed = "45.90.33.227";
    private TextView contrBt, conectionBt, text1;
    private String p_pass, p_login;
    public String[] main_name_rString, main_code_rString;
    public long[] main_id_rSting;
    private RequestQueue mQueue;
    int temp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadStrings();


        //Инициализзация перехода в добавление пользоватлем
        TextView contrBt = (TextView) findViewById(R.id.ControlBt);
        contrBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ContStart = new Intent(MainActivity.this, Control.class);
                loadStrings();
                ContStart.putExtra("string_id",main_id_rSting);
                ContStart.putExtra("string_name",main_code_rString);
                startActivity(ContStart);
            }
        });
       //Инициализзация перехода в добавление пользоватлем


        //Инициализзация перехода в просмотр
        TextView viewBt = (TextView) findViewById(R.id.ViewBtn);
        viewBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadStrings();
                Intent viewStart = new Intent(MainActivity.this, com.example.dyplom.View.class);
                viewStart.putExtra("string_id",main_id_rSting);
                viewStart.putExtra("string_name",main_code_rString);


                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.activity_add_rstring, null);
                AlertDialog.Builder password_check  = new AlertDialog.Builder(MainActivity.this);

                AlertDialog pas_check = password_check.create();

                pas_check.setTitle("Введите текст");
                pas_check.setView(dialoglayout);

                pas_check.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String p_login = ((EditText) dialoglayout.findViewById(R.id.loggin_login)).getText().toString();
                        String p_pasword = ((EditText) dialoglayout.findViewById(R.id.loggin_password)).getText().toString();
                        if (p_login.equals("Admin")   && p_pasword.equals("abc"))
                        {
                            startActivity(viewStart);
                            pas_check.cancel();
                        } else {

                            ((EditText) dialoglayout.findViewById(R.id.loggin_password)).setText("");

                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Неправильный логин или пароль", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
                pas_check.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pas_check.cancel();
                    }
                });
                pas_check.show();
            }
        });
        //Инициализзация перехода в просмотр

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
                    temp = response.length();
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


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // в случае возникновеня ошибки
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "TimeoutError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_SHORT).show();
                    //TODO
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_SHORT).show();
                    //TODO
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "NetworkError", Toast.LENGTH_SHORT).show();
                    //TODO
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "ParseError", Toast.LENGTH_SHORT).show();
                    //TODO
                }
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public Long getid(int i){return main_id_rSting[i];}
    public String getname(int i){return main_name_rString[i];}
    public int getsize() {return temp;}
    public String getcode(int i){return main_code_rString[i];}
}


