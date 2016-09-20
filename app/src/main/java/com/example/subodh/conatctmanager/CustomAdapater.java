package com.example.subodh.conatctmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by udit jain on 21-09-2016.
 */
public class CustomAdapater extends ArrayAdapter
{
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_contacts,parent,false);
        ImageView imageview = (ImageView) row.findViewById(R.id.contact_item_icon);
        TextView textViewFirstName = (TextView) row.findViewById(R.id.contact_first_name);
        TextView textViewNumber = (TextView) row.findViewById(R.id.contact_number);
        //imageview.setImageResource(images[position]);
        textViewFirstName.setText(firstNames[position]);
        textViewNumber.setText(numbers[position]);
        return row;
    }

    Context context;
    int[] images;
    String[] firstNames;
    String[] numbers;
    public CustomAdapater(Context context, /*int[] images,*/ String[] firstNames, String[] numbers) {
        super(context,R.layout.contact_list_item,R.id.contact_first_name,firstNames);
        this.context= context;
        this.firstNames=firstNames;
        this.numbers=numbers;
        //this.images= images;
    }
}
