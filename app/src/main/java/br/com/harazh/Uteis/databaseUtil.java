package br.com.harazh.Uteis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseUtil extends SQLiteOpenHelper {

    private static final String NOME_BASE_DE_DADOS   = "HARAZH.db";
    //VERSÃO DO BANCO DE DADOS
    private static final int    VERSAO_BASE_DE_DADOS = 1;

    public databaseUtil(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder stringBuilderCreateTable = new StringBuilder();

        stringBuilderCreateTable.append(" CREATE TABLE CARRO (");
        stringBuilderCreateTable.append("        id       INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTable.append("        modelo   TEXT    NOT NULL,                  ");
        stringBuilderCreateTable.append("        placa    TEXT    NOT NULL,                  ");
        stringBuilderCreateTable.append("        ano      TEXT    NOT NULL )                 ");

        db.execSQL(stringBuilderCreateTable.toString());

        StringBuilder manutencao = new StringBuilder();
        manutencao.append(" CREATE TABLE MANUTENCAO (");
        manutencao.append("        id_manutencao             INTEGER PRIMARY KEY AUTOINCREMENT, ");
        manutencao.append("        FOREIGN KEY (id_carro)    REFERENCES CARRO (id),             ");
        manutencao.append("        data                      TEXT    NOT NULL,                  ");
        manutencao.append("        descricao                 TEXT    NOT NULL,                  ");
        manutencao.append("        valor                     FLOAT        NOT NULL )            ");
        db.execSQL(manutencao.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CARRO");
        db.execSQL("DROP TABLE IF EXISTS MANUTENCAO");
        onCreate(db);
    }

    /*MÉTODO QUE VAMOS USAR NA CLASSE QUE VAI EXECUTAR AS ROTINAS NO
    BANCO DE DADOS*/
    public SQLiteDatabase GetConexaoDataBase(){

        return this.getWritableDatabase();
    }
}
