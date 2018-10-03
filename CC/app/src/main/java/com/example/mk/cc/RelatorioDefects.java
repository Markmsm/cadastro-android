package com.example.mk.cc;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RelatorioDefects extends AppCompatActivity {
    private String initialDate = "";
    private String finalDate = "";

    private TextView txtInitialDate;
    private TextView txtFinalDate;
    private TextView txtDefeito;
    private TextView txtQtDefeito;

    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePickerDefects;

    private ListView listDefects;
    private Map<String, Integer> defeitos;

    private BdOs db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios_defects);

        db = new BdOs(this);

        txtInitialDate = (TextView) findViewById(R.id.txtViewInitialDateRelatorioDefeitos);
        txtFinalDate = (TextView) findViewById(R.id.txtViewFinalDateRelatorioDefeitos);
        txtDefeito = (TextView) findViewById(R.id.txtViewNomeDefeito);
        txtQtDefeito = (TextView) findViewById(R.id.txtViewCountRelatorioDefeito);

        txtDefeito.setVisibility(View.GONE);
        txtQtDefeito.setVisibility(View.GONE);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        setTitle("Relatório defeitos");

        txtInitialDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInitialDatePicker();
                datePickerDefects.show();
            }
        });

        txtFinalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinalDatePicker();
                datePickerDefects.show();
            }
        });
    }

    //MOSTRA O CALENDARIO PARA SELECIONAR DATA INICIAL
    private void showInitialDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDefects = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                initialDate = (dateFormat.format(newDate.getTime()));
                txtInitialDate.setText(initialDate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    //MOSTRA O CALENDARIO PARA SELECIONAR DATA FINAL
    private void showFinalDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDefects = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                finalDate = (dateFormat.format(newDate.getTime()));
                txtFinalDate.setText(finalDate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void showResultDefects() {
        setTitle("Lista de defeitos");

        //SE NÃO SELECIONAR NENHUMA DATA
        if (initialDate.equalsIgnoreCase("") && finalDate.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Selecione uma data inicial e a data final!", Toast.LENGTH_SHORT).show();
            return;

            //SE SELECIONAR APENAS DATA INICIAL
        } else if (finalDate.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Selecione uma data final!", Toast.LENGTH_SHORT).show();
            return;

            //SE SELECIONAR APENAS DATA FINAL
        } else if (initialDate.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Selecione uma data inicial!", Toast.LENGTH_SHORT).show();
            return;

            //SE SELECIONAR AS DUAS DATAS
        } else {
            defeitos = db.getDefectsCount(initialDate, finalDate);
        }

        List<String> keys = new ArrayList(defeitos.keySet());
        List<Integer> values = new ArrayList(defeitos.values());

        ArrayList<ObjectDefect> defectList = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            defectList.add(new ObjectDefect(keys.get(i), values.get(i)));
        }

        Collections.reverse(defectList);

        txtDefeito.setVisibility(View.VISIBLE);
        txtQtDefeito.setVisibility(View.VISIBLE);

        AdapterDefect adapterDefect = new AdapterDefect(this, defectList);
        listDefects = (ListView) findViewById(R.id.listViewDefeitos);
        listDefects.setAdapter(adapterDefect);
    }

    //CRIA MENU DE OPÇÕES
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_relatorio, menu);
        return true;
    }

    //CONTROLA MENU DE OPÇÕES
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewDateRelatorio:
                showResultDefects();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
