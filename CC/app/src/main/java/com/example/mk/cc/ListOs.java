package com.example.mk.cc;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListOs extends AppCompatActivity {
    private int clientId;

    private String clientName;

    private ListView listOs;

    private FloatingActionButton floatingAddOs;

    private ArrayList ordens;

    private View emptyViewOs;

    private BdClient dbClient;
    private BdOs db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_os);

        dbClient = new BdClient(this);
        db = new BdOs(this);

        Bundle bundle = getIntent().getExtras();
        clientId = bundle.getInt("clientID");

        ordens = db.getAllOpenedOsForClient(bundle.getInt("clientID"));

        AdapterOs adapterOs = new AdapterOs(this, ordens);

        listOs = (ListView) findViewById(R.id.listOs);

        emptyViewOs = findViewById(R.id.emptyListViewOs);

        listOs.setEmptyView(emptyViewOs);
        listOs.setAdapter(adapterOs);

        floatingAddOs = (FloatingActionButton) findViewById(R.id.floatingAddOs);

        ObjectClient objectClient = dbClient.getClient(bundle.getInt("clientID"));
        clientName = objectClient.getName();
        setTitle("Lista O.S. de " + clientName);

        //VAI PARA TELA DE EDIÇÃO DE ORDEM DE SERVIÇO
        listOs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListOs.this, CadOs.class);
                ObjectOs objectOs = (ObjectOs) ordens.get(position);
                intent.putExtra("osID", objectOs.getId());
                intent.putExtra("clientID", clientId);
                startActivity(intent);
            }
        });

        //VAI PARA TELA DE CADASTRO DE ORDEM DE SERVIÇO
        floatingAddOs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOs.this, CadOs.class);
                intent.putExtra("clientID", clientId);
                startActivity(intent);
            }
        });
    }

    //ATUALIZA LISTVIEW
    private void showDelOs() {
        setTitle("Lista O.S. encerradas de " + clientName);
        dbClient = new BdClient(this);
        db = new BdOs(this);

        Bundle bundle = getIntent().getExtras();
        ordens = db.getAllClosedOsForClient(bundle.getInt("clientID"));
        
        AdapterOs adapterOs = new AdapterOs(this, ordens);

        listOs = (ListView) findViewById(R.id.listOs);

        listOs.setAdapter(adapterOs);

        floatingAddOs.setEnabled(false);
        floatingAddOs.setVisibility(View.GONE);

        //VAI PARA TELA DE DADOS DE ORDEM DE SERVIÇO
        listOs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListOs.this, CadOs.class);
                ObjectOs objectOs = (ObjectOs) ordens.get(position);
                intent.putExtra("osIDClosed", objectOs.getId());
                intent.putExtra("clientID", clientId);
                startActivity(intent);
            }
        });
    }

    //CRIA MENU DE OPÇÕES
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_os_list, menu);
        return true;
    }

    // CONTROLA MENU DE OPÇÕES
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showListDelOs:
                item.setVisible(false);
                showDelOs();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}