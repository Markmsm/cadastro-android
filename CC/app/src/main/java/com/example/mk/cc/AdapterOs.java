package com.example.mk.cc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M&K on 07/04/2017.
 */
public class AdapterOs extends ArrayAdapter<ObjectOs> {

    private Context oContext;

    public AdapterOs (Context context, List<ObjectOs> ordens) {
        super(context, 0, ordens);
        this.oContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_os_list, parent, false);
        }

        ObjectOs ordem = getItem(position);

        TextView osTextView = (TextView) listItemView.findViewById(R.id.txtViewOs);
        TextView descricaoTextView = (TextView) listItemView.findViewById(R.id.txtViewDescricao);
        TextView marcaTextView = (TextView) listItemView.findViewById(R.id.txtViewMarca);
        TextView defeitoTextView = (TextView) listItemView.findViewById(R.id.txtViewDefeito);
        TextView dataEntradaTextView = (TextView) listItemView.findViewById(R.id.txtViewDataEntrada);
        TextView dataSaidaTextView = (TextView) listItemView.findViewById(R.id.txtViewDataSaida);

        osTextView.setText("O.S. " + String.valueOf(ordem.getId()));
        descricaoTextView.setText(ordem.getDescricao());
        marcaTextView.setText(ordem.getMarca());
        defeitoTextView.setText(ordem.getDefeito());
        dataEntradaTextView.setText("Data entrada: " + ordem.getCreationDate());
        if (ordem.getDeleteDate() != null) {
            dataSaidaTextView.setText("Data saída: " + ordem.getDeleteDate());
        }

        return listItemView;
    }
}