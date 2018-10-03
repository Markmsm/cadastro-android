package com.example.mk.cc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ListClient extends AppCompatActivity {
    private int dbCount;

    private TextView txtCountClient;

    private ListView listClient;

    private FloatingActionButton floatingCadClient;

    private ArrayList clients;

    private View emptyViewClient;

    private BdClient db;
    private BdOs dbOs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        db = new BdClient(this);
        dbOs = new BdOs(this);

        //POPULAR BANCO
//        ObjectClient client1 = new ObjectClient();
//        client1.setId(1);
//        client1.setName("Eduardo");
//        client1.setAdress("Rua José Teste, 545");
//        client1.setPhone("(51) 998.954.324");
//        client1.setEmail("eduardo@teste.com.br");
//        client1.setCpf("02503558712");
//
//        ObjectOs os1 = new ObjectOs();
//        os1.setId(1);
//        os1.setIdClient(1);
//        os1.setDescricao("Notebook");
//        os1.setMarca("Apple");
//        os1.setDefeito("Não liga");
//        os1.setVoltagem("Bivolt");
//        os1.setAcessorios("Fonte");
//        os1.setObservacao("Tampa arranhada");
//        os1.setCreationDate("2016-05-07");
//        os1.setVisible(true);
//
//        ObjectOs os2 = new ObjectOs();
//        os2.setId(2);
//        os2.setIdClient(1);
//        os2.setDescricao("Cpu");
//        os2.setDefeito("Formatar c/bkp win10");
//        os2.setCreationDate("2016-10-22");
//        os2.setDeleteDate("2016-10-24");
//        os2.setExecutedService("Formatação c/bkp win10");
//        os2.setValor(75);
//        os2.setVisible(false);

//        ObjectOs os3 = new ObjectOs();

//        db.insertClient(client1);
//        dbOs.insertOs(os1);
//        dbOs.insertOs(os2);
//        dbOs.insertOs(os3);

        clients = db.getAllCadClient();
        dbCount = clients.size();

        AdapterClient adapterClient = new AdapterClient(this, clients);

        txtCountClient = (TextView) findViewById(R.id.txtViewCountClient);

        listClient = (ListView) findViewById(R.id.listClient);

        emptyViewClient = findViewById(R.id.emptyListViewClient);

        listClient.setEmptyView(emptyViewClient);
        listClient.setAdapter(adapterClient);

        floatingCadClient = (FloatingActionButton) findViewById(R.id.floatingCadClient);

        setTitle("Lista de clientes");

        if (clients.size() != 0) {
            txtCountClient.setText("Há " + dbCount + " clientes cadastrados.");
        }

        //VAI PARA LISTA DE ORDENS DE SERVIÇO
        listClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListClient.this, ListOs.class);
                ObjectClient objectClient = (ObjectClient) clients.get(position);
                intent.putExtra("clientID", objectClient.getId());
                startActivity(intent);
            }
        });

        //VAI PARA TELA DE EDIÇÃO DE CLIENTE
        listClient.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListClient.this, CadClient.class);
                ObjectClient objectClient = (ObjectClient) clients.get(position);
                intent.putExtra("clientID", objectClient.getId());
                startActivity(intent);
                return true;
            }
        });

        //VAI PARA TELA DE INSERÇÃO DE CLIENTE
        floatingCadClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListClient.this, CadClient.class);
                startActivity(intent);
            }
        });
    }

    //BUSCA CLIENTE
    private void searchClient() {

    }

    //VAI PARA TELA DE RELATÓRIO DE O.S.
    private void goToRelatorioOsView() {
        Intent intent = new Intent(ListClient.this, RelatorioOsDate.class);
        startActivity(intent);
    }

    // VAI PARA TELA DE RELATÓRIO DE DEFEITOS
    private void goToRelatorioDefects() {
        Intent intent = new Intent(ListClient.this, RelatorioDefects.class);
        startActivity(intent);
    }

    //VAI PARA TELA DE RELATÓRIO DE FATURAMENTO
    private void goToRelatorioFaturamento() {
        Intent intent = new Intent(ListClient.this, RelatorioFaturamento.class);
        startActivity(intent);
    }

    //CRIA MENU DE OPÇÕES
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_list, menu);
        return true;
    }

    //CONTROLA MENU DE OPÇÕES
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showRelatorioOs:
                item.setVisible(false);
                goToRelatorioOsView();
                return true;

            case R.id.showRelatorioDefeitos:
                item.setVisible(false);
                goToRelatorioDefects();
                return true;

            case R.id.showRelatorioFaturamento:
                item.setVisible(false);
                goToRelatorioFaturamento();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}