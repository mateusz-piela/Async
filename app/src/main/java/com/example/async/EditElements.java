package com.example.async;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;


public class EditElements extends AppCompatActivity {

    private EditText editName, editPrice, editDescription;
    private int position;
    private String originalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_elements);

        editName = findViewById(R.id.productNameEditText);
        editPrice = findViewById(R.id.productPriceEditText);
        editDescription = findViewById(R.id.productDescriptionEditText);
        Button saveButton = findViewById(R.id.saveBtn);
        Button deleteButton = findViewById(R.id.deleteBtn);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        String name = intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
        String description = intent.getStringExtra("description");

        editName.setText(name);
        editPrice.setText(price);
        editDescription.setText(description);

        originalData = name + " - " + price + " - " + description;

        saveButton.setOnClickListener(v -> saveChanges());

        deleteButton.setOnClickListener(v -> deleteItem());
    }

    private void saveChanges() {
        String newName = editName.getText().toString();
        String newPrice = editPrice.getText().toString();
        String newDescription = editDescription.getText().toString();

        SharedPreferences prefs = getSharedPreferences("app_data", MODE_PRIVATE);
        Set<String> dataSet = prefs.getStringSet("dataList", new HashSet<>());

        dataSet.remove(originalData);
        dataSet.add(newName + " - " + newPrice + " - " + newDescription);

        prefs.edit().putStringSet("dataList", dataSet).apply();
        finish();
    }

    private void deleteItem() {
        SharedPreferences prefs = getSharedPreferences("app_data", MODE_PRIVATE);
        Set<String> dataSet = prefs.getStringSet("dataList", new HashSet<>());

        dataSet.remove(originalData);
        prefs.edit().putStringSet("dataList", dataSet).apply();
        finish();
    }
}