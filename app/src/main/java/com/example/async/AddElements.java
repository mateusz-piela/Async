package com.example.async;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class AddElements extends AppCompatActivity {

    EditText productNameEditText, productPriceEditText, productDescriptionEditText;
    Button addButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_elements);

        productNameEditText = findViewById(R.id.productNameEditText);
        productPriceEditText = findViewById(R.id.productPriceEditText);
        productDescriptionEditText = findViewById(R.id.productDescriptionEditText);
        addButton = findViewById(R.id.addItemButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productNameEditText.getText().toString();
                String price = productPriceEditText.getText().toString();
                String description = productDescriptionEditText.getText().toString();

                if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Prosze uzupelnic wszystkie dane!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Poprawnie dodano element!", Toast.LENGTH_SHORT).show();
                    saveData(name, price, description);
                    finish();
                }
            }
        });
    }

    private void saveData(String name, String price, String description) {
        SharedPreferences prefs = getSharedPreferences("app_data", MODE_PRIVATE);
        Set<String> dataSet = prefs.getStringSet("dataList", new HashSet<>());
        dataSet.add(name + " - " + price + " - " + description);
        prefs.edit().putStringSet("dataList", dataSet).apply();
    }
}
