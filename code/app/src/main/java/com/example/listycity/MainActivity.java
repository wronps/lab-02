package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addButton;
    LinearLayout addCityPanel;
    Button deleteButton;
    Button confirmButton;
    EditText editCityName;
    int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addCityPanel = findViewById(R.id.add_city_panel);
        editCityName = findViewById(R.id.edit_city_name);
        addButton = findViewById(R.id.button_add_city);
        deleteButton = findViewById(R.id.button_delete_city);
        confirmButton = findViewById(R.id.button_confirm);
        cityList = findViewById(R.id.city_list);
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sidney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                selectedPosition = position;
                String city = dataList.get(position);
                Toast.makeText(MainActivity.this, "Selected: " + city, Toast.LENGTH_SHORT).show();
            }
        });
        addButton.setOnClickListener(v -> {
            addCityPanel.setVisibility(View.VISIBLE);
            editCityName.setText("");
            editCityName.requestFocus();
        });
        confirmButton.setOnClickListener(v -> {
            String name = editCityName.getText().toString().trim();
            if (name.isEmpty()){
                Toast.makeText(this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            dataList.add(name);
            cityAdapter.notifyDataSetChanged();
            editCityName.setText("");
            addCityPanel.setVisibility(View.GONE);
        });

        deleteButton.setOnClickListener(v -> {
            if (selectedPosition == -1){
                Toast.makeText(this, "Please select a city first", Toast.LENGTH_SHORT).show();
                return;
            }
            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();
            cityList.clearChoices();
            cityList.requestLayout();
            selectedPosition = -1;
        });
    }


}