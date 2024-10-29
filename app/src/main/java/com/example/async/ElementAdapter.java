package com.example.async;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ElementAdapter extends ArrayAdapter<Element> {

    public ElementAdapter(Context context, List<Element> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Element element = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.element, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.textViewName);
        TextView priceTextView = convertView.findViewById(R.id.textViewPrice);
        TextView descriptionTextView = convertView.findViewById(R.id.textViewDescription);

        nameTextView.setText(element.getName());
        priceTextView.setText(element.getPrice());
        descriptionTextView.setText(element.getDescription());

        return convertView;
    }
}