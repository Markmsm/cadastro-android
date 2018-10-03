package com.example.mk.cc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class CadClient extends AppCompatActivity {
    private int clientId;

    private EditText edtName;
    private EditText edtAdress;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtCpf;

    private boolean visible;

    private FloatingActionButton floatingDelClient;

    private BdClient db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_client);

        db = new BdClient(this);

        edtName = (EditText) findViewById(R.id.edtTextName);
        edtAdress = (EditText) findViewById(R.id.edtTextAdress);
        edtPhone = (EditText) findViewById(R.id.edtTextPhone);
        edtEmail = (EditText) findViewById(R.id.edtTextEmail);
        edtCpf = (EditText) findViewById(R.id.edtTextCpf);

        floatingDelClient = (FloatingActionButton) findViewById(R.id.floatingDelClient);

        floatingDelClient.setEnabled(false);

        floatingDelClient.setVisibility(View.GONE);

        setTitle("Cadastrar cliente");

        //EXIBIR DADOS PREENCHIDOS
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ObjectClient objectClient = db.getClient(bundle.getInt("clientID"));
            if (objectClient != null) {
                setTitle("Editar dados");
                edtName.setText(objectClient.getName());
                edtAdress.setText(objectClient.getAdress());
                edtPhone.setText(objectClient.getPhone());
                edtEmail.setText(objectClient.getEmail());
                edtCpf.setText(objectClient.getCpf());
                floatingDelClient.setEnabled(true);
                floatingDelClient.setVisibility(View.VISIBLE);
            }
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja mesmo deletar?");

        //DELETA CLIENTE
        floatingDelClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    final ObjectClient objectClient = db.getClient(bundle.getInt("clientID"));
                    if (objectClient != null) {
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                objectClient.setVisible(visible);
                                db.deleteClienteVisible(objectClient);
                                Intent intent = new Intent(CadClient.this, ListClient.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Cliente deletado com sucesso!", Toast.LENGTH_SHORT).show();
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
                    }
                }
            }
        });
    }

    //CONCLUI CADASTRO
    private void cadClient(){
        Bundle bundle = getIntent().getExtras();

        //SE SELECIONAR CLIENTE CADASTRADO
        if (bundle !=null) {
            ObjectClient objectClient = db.getClient(bundle.getInt("clientID"));
            if (objectClient !=null) {
                objectClient.setName(edtName.getText().toString().trim());
                if (edtName.getText().toString().trim().isEmpty()) {
                    edtName.setError("O nome não pode estar em branco.");
                    return;
                }

                objectClient.setAdress(edtAdress.getText().toString().trim());
                objectClient.setPhone(edtPhone.getText().toString().trim());
                if (edtPhone.getText().toString().trim().isEmpty()) {
                    edtPhone.setError("O telefone não pode estar em branco.");
                    return;
                }

                objectClient.setEmail(edtEmail.getText().toString().trim());
                objectClient.setCpf(edtCpf.getText().toString().trim());
                if (edtCpf.getText().toString().trim().isEmpty()) {
                    edtCpf.setError("O cpf não pode estar em branco.");
                    return;
                }
                if (edtCpf.getText().toString().trim().length() != 11) {
                    edtCpf.setError("O cpf deve ter 11 caracteres!");
                    return;
                }

                db.updateClient(objectClient);
                Toast.makeText(getApplicationContext(), "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        } else {

            //SE SELECIONAR NOVO CLIENTE
            ObjectClient objectClient = new ObjectClient();
            objectClient.setName(edtName.getText().toString().trim());
            if (edtName.getText().toString().trim().isEmpty()) {
                edtName.setError("O nome não pode estar em branco.");
                return;
            }

            objectClient.setAdress(edtAdress.getText().toString().trim());
            objectClient.setPhone(edtPhone.getText().toString().trim());
            if (edtPhone.getText().toString().trim().isEmpty()) {
                edtPhone.setError("O telefone não pode estar em branco.");
                return;
            }

            objectClient.setEmail(edtEmail.getText().toString().trim());
            objectClient.setCpf(edtCpf.getText().toString().trim());
            if (edtCpf.getText().toString().trim().isEmpty()) {
                edtCpf.setError("O cpf não pode estar em branco.");
                return;
            }
            if (edtCpf.getText().toString().trim().length() != 11) {
                edtCpf.setError("O cpf deve ter 11 caracteres!");
                return;
            }

            objectClient.setCreationDate(new Date());
            db.insertClient(objectClient);
            Toast.makeText(getApplicationContext(), "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(CadClient.this, ListClient.class);
        startActivity(intent);
    }

    //VOLTA PARA LISTA DE CLIENTES
    private void backClientList() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se você cancelar, nenhuma alteração será salva!");
        builder.setPositiveButton("Descartar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CadClient.this, ListClient.class);
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
        getMenuInflater().inflate(R.menu.menu_cad_client, menu);
        return true;
    }

    //CONTROLA MENU DE OPÇÕES
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.endCadClient:
                cadClient();
                return true;

            case android.R.id.home:
                backClientList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
