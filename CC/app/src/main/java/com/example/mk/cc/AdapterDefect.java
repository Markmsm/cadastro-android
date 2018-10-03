package com.example.mk.cc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class AdapterDefect extends ArrayAdapter<ObjectDefect> {

    private Context oContext;
    public AdapterDefect (Context context, List<ObjectDefect> defeitos) {
        super(context, 0, defeitos);
        this.oContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_defect, parent, false);
        }

        ObjectDefect defeito = getItem(position);

        TextView defeitoTextView = (TextView) listItemView.findViewById(R.id.txtViewRelatorioDefeito);
        TextView quantidadeTextView = (TextView) listItemView.findViewById(R.id.txtViewCountRelatorioDefeito);

        defeitoTextView.setText(defeito.getNome());
        quantidadeTextView.setText("" + defeito.getContagem());

        return listItemView;
    }
}