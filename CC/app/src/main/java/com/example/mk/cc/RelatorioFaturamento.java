package com.example.mk.cc;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RelatorioFaturamento extends AppCompatActivity {
    private int dbCount;

    private String initialDate = "";
    private String finalDate = "";

    private TextView txtInitialDate;
    private TextView txtFinalDate;
    private TextView txtCountFaturamento;

    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePickerFaturamento;

    private BdOs db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_faturamento);

        db = new BdOs(this);

        txtInitialDate = (TextView) findViewById(R.id.txtViewInitialDateFaturamento);
        txtFinalDate = (TextView) findViewById(R.id.txtViewFinalDateFaturamento);
        txtCountFaturamento = (TextView) findViewById(R.id.txtViewCountFaturamento);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        setTitle("Faturamento");

        txtInitialDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInitialDatePicker();
                datePickerFaturamento.show();
            }
        });

        txtFinalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinalDatePicker();
                datePickerFaturamento.show();
            }
        });
    }

    //MOSTRA O CALENDARIO PARA SELECIONAR DATA INICIAL
    private void showInitialDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerFaturamento = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
        datePickerFaturamento = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                finalDate = (dateFormat.format(newDate.getTime()));
                txtFinalDate.setText(finalDate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    //MOSTRA O FATURAMENTO
    private void showFaturamento() {
        dbCount = db.countFaturamento(initialDate, finalDate);
        txtCountFaturamento.setText("Seu faturamento de " + initialDate + " até " + finalDate + " foi de R$ " + dbCount + ".");
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
                showFaturamento();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
