package com.example.ergasiaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.lang.NumberFormatException;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        from = findViewById(R.id.editTextFrom);
        to = findViewById(R.id.editTextTo);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String FromInput = from.getText().toString();
                String ToInput = to.getText().toString();

                try{
                    int FromValue = Integer.parseInt(FromInput);
                    int ToValue = Integer.parseInt(ToInput);

                    if (FromValue >= ToValue) {
                        Toast.makeText(MainActivity.this, "The price in bracket 'From' needs to be lower than the price in bracket 'To'", Toast.LENGTH_LONG).show();
                    }else{
                        Thread thread = new Thread(new Runnable(){
                            @Override
                            public void run(){
                                try{
                                    URL url = new URL("https://dummyjson.com/products");
                                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                                    StringBuilder textBuilder = new StringBuilder();
                                    try (Reader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(StandardCharsets.UTF_8.name())))){
                                        int c = 0;
                                        while ((c = reader.read()) != -1){
                                            textBuilder.append((char) c);
                                        }
                                        Intent myIntent = new Intent(MainActivity.this,ProductsActivity.class);
                                        myIntent.putExtra("products",textBuilder.toString());
                                        myIntent.putExtra("from",FromInput);
                                        myIntent.putExtra("to",ToInput);
                                        startActivity(myIntent);
                                    }

                                } catch (MalformedURLException e){
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                        thread.start();
                    }
                }
                catch (NumberFormatException ex){
                    Toast.makeText(MainActivity.this,"Prices need to be numbers",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}