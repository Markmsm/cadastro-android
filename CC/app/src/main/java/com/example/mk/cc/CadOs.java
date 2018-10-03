package com.example.mk.cc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import static android.R.id.edit;

public class CadOs extends AppCompatActivity {
    private int clientId;

    private String clientName;

    private EditText edtDescricao;
    private EditText edtMarca;
    private EditText edtDefeito;
    private EditText edtAcessorios;
    private EditText edtObservacao;
    private EditText edtVoltagem;
    private EditText edtServicoExecutado;
    private EditText edtValorServico;

    private TextView txtViewOs;
    private TextView txtViewValor;
    private TextView txtViewCreationDate;
    private TextView txtViewDelDate;

    private boolean visible;

    private FloatingActionButton floatingDelOs;

    private BdClient dbClient;
    private BdOs db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_os);

        dbClient = new BdClient(this);
        db = new BdOs(this);

        Bundle bundle = getIntent().getExtras();
        clientId = bundle.getInt("clientID");

        ObjectClient objectClient = dbClient.getClient(clientId);
        clientName = objectClient.getName();

        edtDescricao = (EditText) findViewById(R.id.edtTextDescricao);
        edtMarca = (EditText) findViewById(R.id.edtTextMarca);
        edtDefeito = (EditText) findViewById(R.id.edtTextDefeito);
        edtVoltagem = (EditText) findViewById(R.id.edtTextVoltagem);
        edtAcessorios = (EditText) findViewById(R.id.edtTextAcessorios);
        edtObservacao = (EditText) findViewById(R.id.edtTextObservacao);
        edtValorServico = (EditText) findViewById(R.id.edtValorServico);
        edtServicoExecutado = (EditText) findViewById(R.id.edtTextServicoExecutado);

        floatingDelOs = (FloatingActionButton) findViewById(R.id.floatingDelOs);

        txtViewOs = (TextView) findViewById(R.id.txtViewOs);
        txtViewValor = (TextView) findViewById(R.id.txtViewValorServico);
        txtViewCreationDate = (TextView) findViewById(R.id.txtViewCreationDate);
        txtViewDelDate = (TextView) findViewById(R.id.txtViewDelDate);

        edtServicoExecutado.setEnabled(false);
        edtValorServico.setEnabled(false);
        txtViewOs.setEnabled(false);
        txtViewValor.setEnabled(false);
        txtViewCreationDate.setEnabled(false);
        txtViewDelDate.setEnabled(false);
        floatingDelOs.setEnabled(false);

        edtServicoExecutado.setVisibility(View.GONE);
        edtValorServico.setVisibility(View.GONE);
        txtViewOs.setVisibility(View.GONE);
        txtViewValor.setVisibility(View.GONE);
        txtViewCreationDate.setVisibility(View.GONE);
        txtViewDelDate.setVisibility(View.GONE);
        floatingDelOs.setVisibility(View.GONE);

        setTitle("Abrir O.S. para " + clientName);

        //EXIBE DADOS PREENCHIDOS
        if (bundle.get("osID") != null) {
            ObjectOs objectOs = db.getOpenOs(bundle.getInt("osID"));
            if (objectOs != null) {
                setTitle("Alterar O.S. para " + clientName);

                txtViewOs.setText("O.S. " + String.valueOf(objectOs.getId()));
                edtDescricao.setText(objectOs.getDescricao());
                edtMarca.setText(objectOs.getMarca());
                edtDefeito.setText(objectOs.getDefeito());
                edtVoltagem.setText(objectOs.getVoltagem());
                edtAcessorios.setText(objectOs.getAcessorios());
                edtObservacao.setText(objectOs.getObservacao());
                edtServicoExecutado.setText(objectOs.getExecutedService());
                txtViewCreationDate.setText("Data entrada: " + objectOs.getCreationDate());

                edtServicoExecutado.setEnabled(true);
                edtValorServico.setEnabled(true);
                txtViewValor.setEnabled(true);
                txtViewCreationDate.setEnabled(true);
                floatingDelOs.setEnabled(true);

                edtServicoExecutado.setVisibility(View.VISIBLE);
                edtValorServico.setVisibility(View.VISIBLE);
                txtViewOs.setVisibility(View.VISIBLE);
                txtViewValor.setVisibility(View.VISIBLE);
                txtViewCreationDate.setVisibility(View.VISIBLE);
                floatingDelOs.setVisibility(View.VISIBLE);
            }

            //EXIBE DADOS PREENCHIDOS O.S. ENCERRADA
        } else if (bundle.get("osIDClosed") != null) {
            ObjectOs objectOs = db.getClosedOs(bundle.getInt("osIDClosed"));
            if (objectOs != null) {
                setTitle("O.S. encerrada de " + clientName);

                txtViewOs.setText("O.S. " + String.valueOf(objectOs.getId()));
                edtDescricao.setText(objectOs.getDescricao());
                edtMarca.setText(objectOs.getMarca());
                edtDefeito.setText(objectOs.getDefeito());
                edtVoltagem.setText(objectOs.getVoltagem());
                edtAcessorios.setText(objectOs.getAcessorios());
                edtObservacao.setText(objectOs.getObservacao());
                edtServicoExecutado.setText(objectOs.getExecutedService());
                edtValorServico.setText("" + objectOs.getValor());
                txtViewCreationDate.setText("Data entrada: " + objectOs.getCreationDate());
                txtViewDelDate.setText("Data saída: " + objectOs.getDeleteDate());

                edtDescricao.setEnabled(false);
                edtMarca.setEnabled(false);
                edtDefeito.setEnabled(false);
                edtVoltagem.setEnabled(false);
                edtAcessorios.setEnabled(false);
                edtServicoExecutado.setEnabled(false);
                edtValorServico.setEnabled(false);
                txtViewCreationDate.setEnabled(true);
                txtViewDelDate.setEnabled(true);
                floatingDelOs.setEnabled(false);

                edtServicoExecutado.setVisibility(View.VISIBLE);
                edtValorServico.setVisibility(View.VISIBLE);
                txtViewOs.setVisibility(View.VISIBLE);
                txtViewValor.setVisibility(View.VISIBLE);
                txtViewCreationDate.setVisibility(View.VISIBLE);
                txtViewDelDate.setVisibility(View.VISIBLE);
                floatingDelOs.setVisibility(View.GONE);
            }

        }

        //DELETA OS
        floatingDelOs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delOs();
            }
        });
    }

    //CONCLUI CADASTRO
    private void cadOs() {
        Bundle bundle = getIntent().getExtras();

        //SE OS CADASTRADA
        if (bundle.get("osID") != null) {
            ObjectOs objectOs = db.getOpenOs(bundle.getInt("osID"));
            if (objectOs != null) {
                objectOs.setDescricao(edtDescricao.getText().toString().trim());
                if (edtDescricao.getText().toString().trim().isEmpty()) {
                    edtDescricao.setError("Cadastre um equipamento!");
                    return;
                }

                objectOs.setMarca(edtMarca.getText().toString().trim());
                objectOs.setDefeito(edtDefeito.getText().toString().trim());
                if (edtDefeito.getText().toString().trim().isEmpty()) {
                    edtDefeito.setError("Especifique um defeito ou serviço a ser executado!");
                    return;
                }

                objectOs.setVoltagem(edtVoltagem.getText().toString().trim());
                objectOs.setAcessorios(edtAcessorios.getText().toString().trim());
                objectOs.setObservacao(edtObservacao.getText().toString().trim());
                db.updateOs(objectOs);
                Toast.makeText(getApplicationContext(), "O.S. alterada com sucesso!", Toast.LENGTH_SHORT).show();
            }

            //SE OS ENCERRADA
        } else if (bundle.get("osIDClosed") != null) {
            ObjectOs objectOs = db.getClosedOs(bundle.getInt("osIDClosed"));
            if (objectOs != null) {
                objectOs.setObservacao(edtObservacao.getText().toString().trim());
                db.updateOs(objectOs);
                Toast.makeText(getApplicationContext(), "O.S. alterada com sucesso!", Toast.LENGTH_SHORT).show();
            }

            //SE OS NOVA
        } else {
            ObjectOs objectOs = new ObjectOs();
            objectOs.setIdClient(clientId);
            objectOs.setDescricao(edtDescricao.getText().toString().trim());
            if (edtDescricao.getText().toString().isEmpty()) {
                edtDescricao.setError("Cadastre um equipamento!");
                return;
            }

            objectOs.setMarca(edtMarca.getText().toString().trim());
            objectOs.setDefeito(edtDefeito.getText().toString().trim());
            if (edtDefeito.getText().toString().trim().isEmpty()) {
                edtDefeito.setError("Especifique um defeito ou serviço a ser executado!");
                return;
            }

            objectOs.setVoltagem(edtVoltagem.getText().toString().trim());
            objectOs.setAcessorios(edtAcessorios.getText().toString().trim());
            objectOs.setObservacao(edtObservacao.getText().toString().trim());
            objectOs.setCreationDate(new Date().toString());
            db.insertOs(objectOs);
            Toast.makeText(getApplicationContext(), "O.S. criada com sucesso!", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(CadOs.this, ListOs.class);
        intent.putExtra("clientID", clientId);
        startActivity(intent);
    }

    private void delOs() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja encerrar esta O.S.?");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final ObjectOs objectOs = db.getOpenOs(bundle.getInt("osID"));
            if (objectOs != null &&  (!edtServicoExecutado.getText().toString().trim().equalsIgnoreCase("") && !edtValorServico.getText().toString().trim().equalsIgnoreCase(""))) {
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        objectOs.setExecutedService(edtServicoExecutado.getText().toString().trim());
                        String valor = edtValorServico.getText().toString().trim();
                        objectOs.setValor(Integer.valueOf(valor));
                        objectOs.setVisible(visible);
                        db.deleteOsVisible(objectOs);
                        Intent intent = new Intent(CadOs.this, ListOs.class);
                        intent.putExtra("clientID", clientId);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "O.S. encerrada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else if (edtServicoExecutado.getText().toString().trim().equalsIgnoreCase("") && edtValorServico.getText().toString().trim().equalsIgnoreCase("")) {
                edtServicoExecutado.setError("Especifique o serviço executado!");
                edtValorServico.setError("Especifique o valor do serviço!");
            } else if (edtServicoExecutado.getText().toString().trim().equalsIgnoreCase("")){
                edtServicoExecutado.setError("Especifique o serviço executado!");
                return;
            } else if (edtValorServico.getText().toString().trim().equalsIgnoreCase("")) {
                edtValorServico.setError("Especifique o valor do serviço!");
                return;
            }
        }
    }

    //VOLTA PARA LISTA DE O.S.
    private void backOsList() {
        Bundle bundle = getIntent().getExtras();
        clientId = bundle.getInt("clientID");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se você cancelar, nenhuma alteração será salva!");
        builder.setPositiveButton("Descartar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CadOs.this, ListOs.class);
                intent.putExtra("clientID", clientId);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //CRIA MENU DE OPÇÕES
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cad_os, menu);
        return true;
    }

    //CONTROLA MENU DE OPÇÕES
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.endAddOs:
                cadOs();
                return true;

            case android.R.id.home:
                backOsList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
