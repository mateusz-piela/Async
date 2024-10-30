package com.example.async;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DisplayElements extends AppCompatActivity {

    ListView listView;
    ElementAdapter adapter;
    ArrayList<Element> elementList;
    Handler handler = new Handler(Looper.getMainLooper());
    ExecutorService executorService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.display_elements);

        executorService = Executors.newSingleThreadExecutor();
        startPeriodicRefresh();

        listView = findViewById(R.id.listView);
        elementList = loadData();
        adapter = new ElementAdapter(this, elementList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Element element = elementList.get(position);

            Intent intent = new Intent(DisplayElements.this, EditElements.class);
            intent.putExtra("position", position);
            intent.putExtra("name", element.getName());
            intent.putExtra("price", element.getPrice());
            intent.putExtra("description", element.getDescription());
            startActivity(intent);
        });
    }

    private void startPeriodicRefresh() {
        executorService.execute(() -> {
            try {
                while (true) {
                    TimeUnit.SECONDS.sleep(5);
                    handler.post(() -> {
                        elementList.clear();
                        elementList.addAll(loadData());
                        adapter.notifyDataSetChanged();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private ArrayList<Element> loadData() {
        SharedPreferences prefs = getSharedPreferences("app_data", MODE_PRIVATE);
        Set<String> dataSet = prefs.getStringSet("dataList", new HashSet<>());

        ArrayList<Element> elements = new ArrayList<>();
        for (String item : dataSet) {
            String[] parts = item.split(" - ");
            if (parts.length == 3) {
                elements.add(new Element(parts[0], parts[1], parts[2]));
            }
        }
        return elements;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
