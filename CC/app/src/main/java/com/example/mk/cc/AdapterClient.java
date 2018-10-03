package com.example.mk.cc;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by M&K on 05/04/2017.
 */
public class AdapterClient extends ArrayAdapter<ObjectClient> {

    private Context mContext;

    public AdapterClient (Context context, List<ObjectClient> clients) {
        super(context, 0, clients);
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_client_list, parent, false);
        }

        ObjectClient client = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.txtViewNome);
        TextView phoneTextView = (TextView) listItemView.findViewById(R.id.txtViewTelefone);

        nameTextView.setText(client.getName());
        phoneTextView.setText(client.getPhone());

        return listItemView;
    }
}