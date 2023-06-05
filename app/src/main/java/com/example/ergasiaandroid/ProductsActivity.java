package com.example.ergasiaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

public class ProductsActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        listView = findViewById(R.id.lv);
        ArrayList<String> products = new ArrayList<String>();

        Intent intent = getIntent();
        String value = intent.getStringExtra("products");
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");

        int FromValue = Integer.parseInt(from);
        int ToValue = Integer.parseInt(to);

        try {
            JSONObject obj = new JSONObject(value);
            JSONArray jsonProducts = obj.getJSONArray("products");


            for(int i=0;i<jsonProducts.length();i++){
                String priceStr = jsonProducts.getJSONObject(i).get("price").toString();
                int price = Integer.parseInt(priceStr);

                if((price>=FromValue)&&(price<=ToValue) ){

                    products.add(jsonProducts.getJSONObject(i).get("title").toString());
                }
            }
            products.sort(String::compareToIgnoreCase);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,products);
            listView.setAdapter(arrayAdapter);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}