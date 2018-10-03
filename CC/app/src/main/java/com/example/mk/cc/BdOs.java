package com.example.mk.cc;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.BoringLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by ODETE on 30/03/2017.
 */
public class BdOs extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "OsDatabase";

    public BdOs (Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    //CRIA TABELA
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ordens" +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "client_id INTEGER, " +
                "descricao TEXT NOT NULL, " +
                "marca TEXT, " +
                "defeito TEXT NOT NULL, " +
                "voltagem TEXT, " +
                "acessorios TEXT, " +
                "observacao TEXT, " +
                "data_criacao DATE, " +
                "data_encerramento DATE, " +
                "servico_executado TEXT, " +
                "valor INTEGER, " +
                "visivel NUMBER )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbOs, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS ordens";
        dbOs.execSQL(sql);
        onCreate(dbOs);
    }

    //MÉTODO PARA INSERIR DATA
    private java.sql.Date getDateTime () {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String stringCurrentDate = formatter.format(new Date());
        Date date = null;
        try {
            date = formatter.parse(stringCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        java.sql.Date sqlDate = null;
        if (date != null) {
            sqlDate = new java.sql.Date(date.getTime());
        }

        return sqlDate;
    }

    //INSERE ORDENS DE SERVIÇO
    public void insertOs (ObjectOs objectOs) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("client_id", objectOs.getIdClient());
        values.put("descricao", objectOs.getDescricao());
        values.put("marca", objectOs.getMarca());
        values.put("defeito", objectOs.getDefeito());
        values.put("voltagem", objectOs.getVoltagem());
        values.put("acessorios", objectOs.getAcessorios());
        values.put("observacao", objectOs.getObservacao());
        values.put("data_criacao", getDateTime().toString());
        values.put("visivel", 0);
        db.insert("ordens", null, values);
        db.close();
    }

    //LÊ DADOS O.S. ABERTAS
    public ObjectOs getOpenOs (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM ordens where id=" + id + "";
        Cursor os = db.rawQuery(sql, null);
        os.moveToFirst();
        int clientId = os.getInt(os.getColumnIndex("client_id"));
        String descricao = os.getString(os.getColumnIndex("descricao"));
        String marca = os.getString(os.getColumnIndex("marca"));
        String defeito = os.getString(os.getColumnIndex("defeito"));
        String voltagem = os.getString(os.getColumnIndex("voltagem"));
        String acessorios = os.getString(os.getColumnIndex("acessorios"));
        String observacao = os.getString(os.getColumnIndex("observacao"));
        String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
        ObjectOs objectOs = new ObjectOs();
        objectOs.setId(id);
        objectOs.setIdClient(clientId);
        objectOs.setDescricao(descricao);
        objectOs.setMarca(marca);
        objectOs.setDefeito(defeito);
        objectOs.setVoltagem(voltagem);
        objectOs.setAcessorios(acessorios);
        objectOs.setObservacao(observacao);
        objectOs.setCreationDate(datacriacao);

        return objectOs;
    }

    //LÊ DADOS O.S. ENCERRADAS
    public ObjectOs getClosedOs (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM ordens where id=" + id + "";
        Cursor os = db.rawQuery(sql, null);
        os.moveToFirst();
        int clientId = os.getInt(os.getColumnIndex("client_id"));
        String descricao = os.getString(os.getColumnIndex("descricao"));
        String marca = os.getString(os.getColumnIndex("marca"));
        String defeito = os.getString(os.getColumnIndex("defeito"));
        String voltagem = os.getString(os.getColumnIndex("voltagem"));
        String acessorios = os.getString(os.getColumnIndex("acessorios"));
        String observacao = os.getString(os.getColumnIndex("observacao"));
        String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
        String dataencerramento = os.getString(os.getColumnIndex("data_encerramento"));
        String servicoexecutado = os.getString(os.getColumnIndex("servico_executado"));
        int valor = os.getInt(os.getColumnIndex("valor"));
        //int valor = Integer.parseInt(os.getString(os.getColumnIndex("valor")));
        ObjectOs objectOs = new ObjectOs();
        objectOs.setId(id);
        objectOs.setIdClient(clientId);
        objectOs.setDescricao(descricao);
        objectOs.setMarca(marca);
        objectOs.setDefeito(defeito);
        objectOs.setVoltagem(voltagem);
        objectOs.setAcessorios(acessorios);
        objectOs.setObservacao(observacao);
        objectOs.setCreationDate(datacriacao);
        objectOs.setDeleteDate(dataencerramento);
        objectOs.setExecutedService(servicoexecutado);
        objectOs.setValor(valor);

        return objectOs;
    }

    //ATUALIZA DADOS
    public boolean updateOs (ObjectOs objectOs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("descricao", objectOs.getDescricao());
        values.put("marca", objectOs.getMarca());
        values.put("defeito", objectOs.getDefeito());
        values.put("voltagem", objectOs.getVoltagem());
        values.put("acessorios", objectOs.getAcessorios());
        values.put("observacao", objectOs.getObservacao());
        db.update("ordens", values, "id = ?", new String[] {Integer.toString(objectOs.getId())});
        return true;
    }

    //TRANSFORMA O.S. EM INVISIVEL
    public boolean deleteOsVisible (ObjectOs objectOs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("data_encerramento", getDateTime().toString());
        values.put("servico_executado", objectOs.getExecutedService());
        values.put("valor", objectOs.getValor());
        //values.put("valor", Integer.valueOf(objectOs.getValor()));
        values.put("visivel", 1);
        db.update("ordens", values, "id = ?", new String[] {Integer.toString(objectOs.getId())});
        return true;
    }

//    //DELETA DADOS
//    public boolean deleteOs (int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete("ordens", "id = ?" + id + "", null) > 0;
//    }
//
//    //LÊ TODAS AS ORDENS DE SERVIÇO
//    public ArrayList<ObjectOs> getAllOs() {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                objectOs.setDeleteDate(dataexclusao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }
//
//    //LÊ TODAS AS ORDENS DE SERVIÇO EM ABERTO
//    public ArrayList<ObjectOs> getAllOpenedOs () {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens where visivel == 0 ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }

    //LÊ TODAS AS ORDENS DE SERVIÇO EM ABERTO PARA CLIENTE
    public ArrayList<ObjectOs> getAllOpenedOsForClient (int idClient) {
        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
        String sql = "SELECT * FROM ordens where visivel == 0 AND  client_id == " + idClient + " ORDER BY id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor os = db.rawQuery(sql, null);
        if (os.moveToFirst()) {
            do {
                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
                int clientId = os.getInt(os.getColumnIndex("client_id"));
                String descricao = os.getString(os.getColumnIndex("descricao"));
                String marca = os.getString(os.getColumnIndex("marca"));
                String defeito = os.getString(os.getColumnIndex("defeito"));
                String voltagem = os.getString(os.getColumnIndex("voltagem"));
                String acessorios = os.getString(os.getColumnIndex("acessorios"));
                String observacao = os.getString(os.getColumnIndex("observacao"));
                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
                ObjectOs objectOs = new ObjectOs();
                objectOs.setId(id);
                objectOs.setIdClient(clientId);
                objectOs.setDescricao(descricao);
                objectOs.setMarca(marca);
                objectOs.setDefeito(defeito);
                objectOs.setVoltagem(voltagem);
                objectOs.setAcessorios(acessorios);
                objectOs.setObservacao(observacao);
                objectOs.setCreationDate(datacriacao);
                getOs.add(objectOs);
            } while (os.moveToNext());
        }
        return getOs;
    }

//    //LÊ TODAS AS ORDENS DE SERVIÇO ENCERRADAS
//    public ArrayList<ObjectOs> getAllClosedOs () {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens where visivel == 1 ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                objectOs.setDeleteDate(dataexclusao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }

    //LÊ TODAS AS ORDENS DE SERVIÇO ENCERRADAS PARA CLIENTE
    public ArrayList<ObjectOs> getAllClosedOsForClient (int idClient) {
        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
        String sql = "SELECT * FROM ordens where visivel == 1 AND  client_id == " + idClient + " ORDER BY id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor os = db.rawQuery(sql, null);
        if (os.moveToFirst()) {
            do {
                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
                int clientId = os.getInt(os.getColumnIndex("client_id"));
                String descricao = os.getString(os.getColumnIndex("descricao"));
                String marca = os.getString(os.getColumnIndex("marca"));
                String defeito = os.getString(os.getColumnIndex("defeito"));
                String voltagem = os.getString(os.getColumnIndex("voltagem"));
                String acessorios = os.getString(os.getColumnIndex("acessorios"));
                String observacao = os.getString(os.getColumnIndex("observacao"));
                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
                String servicoexecutado = os.getString(os.getColumnIndex("servico_executado"));
                int valor = os.getInt(os.getColumnIndex("valor"));
                //int valor = Integer.parseInt(os.getString(os.getColumnIndex("valor")));
                ObjectOs objectOs = new ObjectOs();
                objectOs.setId(id);
                objectOs.setIdClient(clientId);
                objectOs.setDescricao(descricao);
                objectOs.setMarca(marca);
                objectOs.setDefeito(defeito);
                objectOs.setVoltagem(voltagem);
                objectOs.setAcessorios(acessorios);
                objectOs.setObservacao(observacao);
                objectOs.setCreationDate(datacriacao);
                objectOs.setDeleteDate(dataexclusao);
                objectOs.setExecutedService(servicoexecutado);
                objectOs.setValor(valor);
                getOs.add(objectOs);
            } while (os.moveToNext());
        }
        return getOs;
    }

    //BUSCA ORDENS DE SERVIÇO POR DATA DE CRIAÇÃO
    public ArrayList<ObjectOs> getAllSelectedCreationDateOs (String dateIni, String dateFin) {
        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
        String sql = "SELECT * FROM ordens where data_criacao >= '" + dateIni + "' AND data_criacao <= '" + dateFin + "' ORDER BY id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor os = db.rawQuery(sql, null);
        if (os.moveToFirst()) {
            do {
                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
                int clientId = os.getInt(os.getColumnIndex("client_id"));
                String descricao = os.getString(os.getColumnIndex("descricao"));
                String marca = os.getString(os.getColumnIndex("marca"));
                String defeito = os.getString(os.getColumnIndex("defeito"));
                String voltagem = os.getString(os.getColumnIndex("voltagem"));
                String acessorios = os.getString(os.getColumnIndex("acessorios"));
                String observacao = os.getString(os.getColumnIndex("observacao"));
                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
                String servicoexecutado = os.getString(os.getColumnIndex("servico_executado"));
                int valor = os.getInt(os.getColumnIndex("valor"));
                int visivel = os.getInt(os.getColumnIndex("visivel"));
                //int valor = Integer.parseInt(os.getString(os.getColumnIndex("valor")));
                ObjectOs objectOs = new ObjectOs();
                objectOs.setId(id);
                objectOs.setIdClient(clientId);
                objectOs.setDescricao(descricao);
                objectOs.setMarca(marca);
                objectOs.setDefeito(defeito);
                objectOs.setVoltagem(voltagem);
                objectOs.setAcessorios(acessorios);
                objectOs.setObservacao(observacao);
                objectOs.setCreationDate(datacriacao);
                objectOs.setDeleteDate(dataexclusao);
                objectOs.setExecutedService(servicoexecutado);
                objectOs.setValor(valor);
                objectOs.setVisible(visivel == 0);
                getOs.add(objectOs);
            } while (os.moveToNext());
        }
        return getOs;
    }

    //BUSCA ORDENS DE SERVIÇO POR DATA DE CRIAÇÃO ABERTAS
    public ArrayList<ObjectOs> getAllSelectedCreationDateOpenedOs (String dateIni, String dateFin) {
        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
        String sql = "SELECT * FROM ordens where data_criacao >= '" + dateIni + "' AND data_criacao <= '" + dateFin + "' AND visivel == 0 ORDER BY id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor os = db.rawQuery(sql, null);
        if (os.moveToFirst()) {
            do {
                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
                int clientId = os.getInt(os.getColumnIndex("client_id"));
                String descricao = os.getString(os.getColumnIndex("descricao"));
                String marca = os.getString(os.getColumnIndex("marca"));
                String defeito = os.getString(os.getColumnIndex("defeito"));
                String voltagem = os.getString(os.getColumnIndex("voltagem"));
                String acessorios = os.getString(os.getColumnIndex("acessorios"));
                String observacao = os.getString(os.getColumnIndex("observacao"));
                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
                String servicoexecutado = os.getString(os.getColumnIndex("servico_executado"));
                int visivel = os.getInt(os.getColumnIndex("visivel"));
                int valor = os.getInt(os.getColumnIndex("valor"));
                ObjectOs objectOs = new ObjectOs();
                objectOs.setId(id);
                objectOs.setIdClient(clientId);
                objectOs.setDescricao(descricao);
                objectOs.setMarca(marca);
                objectOs.setDefeito(defeito);
                objectOs.setVoltagem(voltagem);
                objectOs.setAcessorios(acessorios);
                objectOs.setObservacao(observacao);
                objectOs.setCreationDate(datacriacao);
                objectOs.setDeleteDate(dataexclusao);
                objectOs.setExecutedService(servicoexecutado);
                objectOs.setValor(valor);
                objectOs.setVisible(visivel == 0);
                getOs.add(objectOs);
            } while (os.moveToNext());
        }
        return getOs;
    }

    //BUSCA ORDENS DE SERVIÇO POR DATA DE CRIAÇÃO ENCERRADAS
    public ArrayList<ObjectOs> getAllSelectedCreationDateClosedOs (String dateIni, String dateFin) {
        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
        String sql = "SELECT * FROM ordens where data_criacao >= '" + dateIni + "' AND data_criacao <= '" + dateFin + "' AND visivel == 1 ORDER BY id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor os = db.rawQuery(sql, null);
        if (os.moveToFirst()) {
            do {
                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
                int idClient = os.getInt(os.getColumnIndex("client_id"));
                String descricao = os.getString(os.getColumnIndex("descricao"));
                String marca = os.getString(os.getColumnIndex("marca"));
                String defeito = os.getString(os.getColumnIndex("defeito"));
                String voltagem = os.getString(os.getColumnIndex("voltagem"));
                String acessorios = os.getString(os.getColumnIndex("acessorios"));
                String observacao = os.getString(os.getColumnIndex("observacao"));
                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
                String servicoexecutado = os.getString(os.getColumnIndex("servico_executado"));
                int valor = os.getInt(os.getColumnIndex("valor"));
                int visivel = os.getInt(os.getColumnIndex("visivel"));
                //int valor = Integer.parseInt(os.getString(os.getColumnIndex("valor")));
                ObjectOs objectOs = new ObjectOs();
                objectOs.setId(id);
                objectOs.setIdClient(idClient);
                objectOs.setDescricao(descricao);
                objectOs.setMarca(marca);
                objectOs.setDefeito(defeito);
                objectOs.setVoltagem(voltagem);
                objectOs.setAcessorios(acessorios);
                objectOs.setObservacao(observacao);
                objectOs.setCreationDate(datacriacao);
                objectOs.setDeleteDate(dataexclusao);
                objectOs.setExecutedService(servicoexecutado);
                objectOs.setValor(valor);
                objectOs.setVisible(visivel == 0);
                getOs.add(objectOs);
            } while (os.moveToNext());
        }
        return getOs;
    }

//    //BUSCA ORDENS DE SERVIÇO POR DATA DE CRIAÇÃO SELECIONANDO APENAS DATA INICIAL
//    public ArrayList<ObjectOs> getAllSelectedCreationDateInitialOs (String dateIni) {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens where data_criacao >= '" + dateIni + "' ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                objectOs.setDeleteDate(dataexclusao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }
//
//    //BUSCA ORDENS DE SERVIÇO POR DATA DE CRIAÇÃO SELECIONANDO APENAS DATA FINAL
//    public ArrayList<ObjectOs> getAllSelectedCreationDateFinalOs (String dateFin) {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens where data_criacao <= '" + dateFin + "' ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                objectOs.setDeleteDate(dataexclusao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }
//
//    //BUSCA ORDENS DE SERVIÇO ABERTAS POR DATA DE CRIAÇÃO SELECIONANDO APENAS DATA INICIAL
//    public ArrayList<ObjectOs> getAllSelectedCreationDateInitialOpenedOs (String dateIni) {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens where data_criacao >= '" + dateIni + "' AND visivel == 0 ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                objectOs.setDeleteDate(dataexclusao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }
//
//    //BUSCA ORDENS DE SERVIÇO ABERTAS POR DATA DE CRIAÇÃO SELECIONANDO APENAS DATA FINAL
//    public ArrayList<ObjectOs> getAllSelectedCreationDateFinalOpenedOs (String dateFin) {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens where data_criacao <= '" + dateFin + "' AND visivel == 0 ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                objectOs.setDeleteDate(dataexclusao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }
//
//    //BUSCA ORDENS DE SERVIÇO ENCERRADAS POR DATA DE CRIAÇÃO SELECIONANDO APENAS DATA INICIAL
//    public ArrayList<ObjectOs> getAllSelectedCreationDateInitialClosedOs (String dateIni) {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens where data_criacao >= '" + dateIni + "' AND visivel == 1 ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                objectOs.setDeleteDate(dataexclusao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }
//
//    //BUSCA ORDENS DE SERVIÇO ENCERRADAS POR DATA DE CRIAÇÃO SELECIONANDO APENAS DATA FINAL
//    public ArrayList<ObjectOs> getAllSelectedCreationDateFinalClosedOs (String dateFin) {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens where data_criacao <= '" + dateFin + "' AND visivel == 1 ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                objectOs.setDeleteDate(dataexclusao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }
//
//    //BUSCA ORDENS DE SERVIÇO POR DATA DE ENCERRAMENTO
//    public ArrayList<ObjectOs> getAllSelectedDeleteDateOs (String dateIni, String dateFin) {
//        ArrayList<ObjectOs> getOs = new ArrayList<ObjectOs>();
//        String sql = "SELECT * FROM ordens where data_encerramento >= '" + dateIni + "' AND data_encerramento <= '" + dateFin + "' ORDER BY id";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor os = db.rawQuery(sql, null);
//        if (os.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(os.getString(os.getColumnIndex("id")));
//                String descricao = os.getString(os.getColumnIndex("descricao"));
//                String marca = os.getString(os.getColumnIndex("marca"));
//                String defeito = os.getString(os.getColumnIndex("defeito"));
//                String voltagem = os.getString(os.getColumnIndex("voltagem"));
//                String acessorios = os.getString(os.getColumnIndex("acessorios"));
//                String observacao = os.getString(os.getColumnIndex("observacao"));
//                String datacriacao = os.getString(os.getColumnIndex("data_criacao"));
//                String dataexclusao = os.getString(os.getColumnIndex("data_encerramento"));
//                ObjectOs objectOs = new ObjectOs();
//                objectOs.setId(id);
//                objectOs.setDescricao(descricao);
//                objectOs.setMarca(marca);
//                objectOs.setDefeito(defeito);
//                objectOs.setVoltagem(voltagem);
//                objectOs.setAcessorios(acessorios);
//                objectOs.setObservacao(observacao);
//                objectOs.setCreationDate(datacriacao);
//                objectOs.setDeleteDate(dataexclusao);
//                getOs.add(objectOs);
//            } while (os.moveToNext());
//        }
//        return getOs;
//    }

    //BUSCA DEFEITOS E CONTAGEM DE OCORRENCIAS
    public Map<String, Integer> getDefectsCount( String dateIni, String dateFin) {
        ArrayList<String> defects = new ArrayList<>();
        String sql = "SELECT defeito FROM ordens where data_criacao >= '" + dateIni + "'AND data_criacao <= '" + dateFin + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor os = db.rawQuery(sql, null);
        if (os.moveToFirst()) {
            do {
                defects.add(os.getString(os.getColumnIndex("defeito")).substring(0, 1).toUpperCase().concat(os.getString(os.getColumnIndex("defeito"))).substring(1).trim());
            } while (os.moveToNext());
        }

        Map<String, Integer> occurences = new HashMap<String, Integer>();

        for (String word : defects) {
            Integer oldCount = occurences.get(word);
            if (oldCount == null) {
                oldCount = 0;
            }
            occurences.put(word, oldCount + 1);
        }
        return occurences;
    }

    //BUSCA FATURAMENTO
    public int countFaturamento(String dateIni, String dateFin) {
        String sql = "SELECT valor FROM ordens where data_encerramento >= '" + dateIni + "' AND data_encerramento <= '" + dateFin + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor os = db.rawQuery(sql, null);
        int faturamento = 0;
        if (os.moveToFirst()) {
            do {
                faturamento += os.getInt(os.getColumnIndex("valor"));
            } while (os.moveToNext());
        }
        db.close();
        return faturamento;
    }

//    //FAZ CONTAGEM DE O.S.
//    public int count() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "SELECT * FROM ordens ORDER BY id";
//        int recordCount = db.rawQuery(sql, null).getCount();
//        db.close();
//        return recordCount;
//    }
//
//    //FAZ CONTAGEM DE O.S. ABERTAS
//    public int countOpenedOs() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "SELECT * FROM ordens where visivel == 0 ORDER BY id";
//        int recordCount = db.rawQuery(sql, null).getCount();
//        db.close();
//        return recordCount;
//    }
//
//    //FAZ CONTAGEM DE O.S. ENCERRADAS
//    public int countClosedOs() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "SELECT * FROM ordens where visivel == 1 ORDER BY id";
//        int recordCount = db.rawQuery(sql, null).getCount();
//        db.close();
//        return recordCount;
//    }
}
