package com.example.mk.cc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RelatorioOsDate extends AppCompatActivity {
    private int dbCount;
    private int clientId;
    private int id;

    private String initialDate = "";
    private String finalDate = "";

    private TextView txtInitialDate;
    private TextView txtFinalDate;
    private TextView txtViewCountOs;

    private CheckBox checkBoxOpenedOs;
    private CheckBox checkBoxClosedOs;

    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePickerOs;

    private ListView listOsDate;

    private ArrayList<ObjectOs> ordens;

    private BdOs db;
    private BdClient dbClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_os_date);

        db = new BdOs(this);
        dbClient = new BdClient(this);

        checkBoxOpenedOs = (CheckBox) findViewById(R.id.checkBoxOpenedOs);
        checkBoxClosedOs = (CheckBox) findViewById(R.id.checkBoxClosedOs);

        txtInitialDate = (TextView) findViewById(R.id.txtViewInitialDateRelatorioOs);
        txtFinalDate = (TextView) findViewById(R.id.txtViewFinalDateRelatorioOs);
        txtViewCountOs = (TextView) findViewById(R.id.txtViewCountOs);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        setTitle("Relatórios O.S.");

        txtInitialDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInitialDatePicker();
                datePickerOs.show();
            }
        });

        txtFinalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinalDatePicker();
                datePickerOs.show();
            }
        });
    }

    //MOSTRA O CALENDARIO PARA SELECIONAR DATA INICIAL
    private void showInitialDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerOs = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
        datePickerOs = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                finalDate = (dateFormat.format(newDate.getTime()));
                txtFinalDate.setText(finalDate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    //MOSTRA A LISTA DE O.S. DE ACORDO COM A BUSCA
    private void showResultOsDate() {
        setTitle("Lista O.S.");

        //SE MARCAR AS DUAS
        if (checkBoxOpenedOs.isChecked() && checkBoxClosedOs.isChecked()) {

            //SE NÃO SELECIONAR NENHUMA DATA
            if (initialDate.equalsIgnoreCase("") && finalDate.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Selecione uma data inicial e a data final!", Toast.LENGTH_SHORT).show();
                return;

                //SE SELECIONAR APENAS DATA INICIAL
            } else if (finalDate.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Selecione uma data final!", Toast.LENGTH_SHORT).show();
                return;

                //SE SELECIONAR APENAS DATA FINAL
            } else if (initialDate.equalsIgnoreCase("")){
                Toast.makeText(getApplicationContext(), "Selecione uma data inicial!", Toast.LENGTH_SHORT).show();
                return;

                //SE SELECIONAR AS DUAS DATAS
            } else {
                ordens = db.getAllSelectedCreationDateOs(initialDate, finalDate);
                dbCount = ordens.size();
                txtViewCountOs.setText("Foram encontradas " + dbCount + " O.S. de " + initialDate + " até " + finalDate + ".");
            }

            //SE MARCAR O.S. ABERTAS
        } else if (checkBoxOpenedOs.isChecked()) {

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
                ordens = db.getAllSelectedCreationDateOpenedOs(initialDate, finalDate);
                dbCount = ordens.size();
                txtViewCountOs.setText("Foram encontradas " + dbCount + " O.S. em aberto de " + initialDate + " até " + finalDate + ".");
            }

            //SE MARCAR O.S. ENCERRADAS
        } else if (checkBoxClosedOs.isChecked()) {

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
                ordens = db.getAllSelectedCreationDateClosedOs(initialDate, finalDate);
                dbCount = ordens.size();
                txtViewCountOs.setText("Foram encontradas " + dbCount + " O.S. encerradas de " + initialDate + " até " + finalDate + ".");
            }

            //SE NÃO MARCAR NENHUMA
        } else if (!checkBoxOpenedOs.isChecked() && !checkBoxClosedOs.isChecked()) {

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
                Toast.makeText(getApplicationContext(), "Selecione pelo menos uma opção de O.S.!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        AdapterOs adapterOs = new AdapterOs(this,ordens);
        listOsDate = (ListView) findViewById(R.id.listViewOsDate);
        listOsDate.setAdapter(adapterOs);

        listOsDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RelatorioOsDate.this, CadOs.class);
                ObjectOs ordem = ordens.get(position);
                if (ordem.isVisible()){
                    intent.putExtra("osID", ordem.getId());
                    intent.putExtra("clientID", ordem.getIdClient());
                } else {
                    intent.putExtra("osIDClosed", ordem.getId());
                    intent.putExtra("clientID", ordem.getIdClient());
                }
                startActivity(intent);
            }
        });
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
                showResultOsDate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
