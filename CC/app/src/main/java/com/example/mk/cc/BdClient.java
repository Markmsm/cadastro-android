package com.example.mk.cc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by M&K on 12/03/2017.
 */
public class BdClient extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "ClientDatabase";

    public BdClient (Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    //CRIA TABELA
    public void onCreate (SQLiteDatabase db) {
        String sql = "CREATE TABLE clients" +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT NOT NULL, " +
                "email TEXT, " +
                "cpf TEXT NOT NULL, " +
                "data_criacao DATE, " +
                "data_exclusao DATE, " +
                "visivel NUMBER )";
        db.execSQL(sql);
    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS clients";
        db.execSQL(sql);
        onCreate(db);
    }

    //INSERE DADOS
    public void insertClient (ObjectClient objectClient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", objectClient.getName());
        values.put("endereco", objectClient.getAdress());
        values.put("telefone", objectClient.getPhone());
        values.put("email", objectClient.getEmail());
        values.put("cpf", objectClient.getCpf());
        values.put("visivel", 0);
        db.insert("clients", null, values);
        db.close();
    }

    //LÊ DADOS
    public ObjectClient getClient (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM clients where id=" + id + "";
        Cursor client = db.rawQuery(sql, null);
        client.moveToFirst();
        String nome = client.getString(client.getColumnIndex("nome"));
        String endereco = client.getString(client.getColumnIndex("endereco"));
        String telefone = client.getString(client.getColumnIndex("telefone"));
        String email = client.getString(client.getColumnIndex("email"));
        String cpf = client.getString(client.getColumnIndex("cpf"));
        ObjectClient objectClient = new ObjectClient();
        objectClient.setId(id);
        objectClient.setName(nome);
        objectClient.setAdress(endereco);
        objectClient.setPhone(telefone);
        objectClient.setEmail(email);
        objectClient.setCpf(cpf);

        return objectClient;
    }

    //ATUALIZA DADOS
    public boolean updateClient (ObjectClient objectClient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", objectClient.getName());
        values.put("endereco", objectClient.getAdress());
        values.put("telefone", objectClient.getPhone());
        values.put("email", objectClient.getEmail());
        values.put("cpf", objectClient.getCpf());
        db.update("clients", values, "id = ?", new String[] {Integer.toString(objectClient.getId())});
        return true;
    }

    //TRANSFORMA CLIENTE EM INVISIVEL
    public boolean deleteClienteVisible (ObjectClient objectClient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("visivel", 1);
        db.update("clients", values, "id = ?", new String[] {Integer.toString(objectClient.getId())});
        return true;
    }

    //DELETA DADOS
    public boolean deleteClient (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("clients", "id = ?" + id + "", null) > 0;
    }

    //LÊ TODOS OS CLIENTES CADASTRADOS
    public ArrayList<ObjectClient> getAllCadClient () {
        ArrayList<ObjectClient> getClients = new ArrayList<ObjectClient>();
        String sql = "SELECT * FROM clients where visivel == 0 ORDER BY nome";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor client = db.rawQuery(sql, null);
        if (client.moveToFirst()) {
            do {
                int id = Integer.parseInt(client.getString(client.getColumnIndex("id")));
                String nome = client.getString(client.getColumnIndex("nome"));
                String endereco = client.getString(client.getColumnIndex("endereco"));
                String telefone = client.getString(client.getColumnIndex("telefone"));
                String email = client.getString(client.getColumnIndex("email"));
                String cpf = client.getString(client.getColumnIndex("cpf"));
                ObjectClient objectClient = new ObjectClient();
                objectClient.setId(id);
                objectClient.setName(nome);
                objectClient.setAdress(endereco);
                objectClient.setPhone(telefone);
                objectClient.setEmail(email);
                objectClient.setCpf(cpf);
                getClients.add(objectClient);
            } while (client.moveToNext());
        }
        return getClients;
    }

    public int count() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM clients";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;
    }
}
