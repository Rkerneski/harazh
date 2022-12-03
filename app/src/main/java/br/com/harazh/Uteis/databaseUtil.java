package br.com.harazh.Uteis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseUtil extends SQLiteOpenHelper {

    private static final String NOME_BASE_DE_DADOS   = "HARAZH.db";
    //VERSÃO DO BANCO DE DADOS
    private static final int VERSAO_BASE_DE_DADOS = 1;

    public databaseUtil(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //StringBuilder stringBuilderCreateTable = new StringBuilder();

        String sqlCarro = "CREATE TABLE CARRO ( " +
                "id          INTEGER PRIMARY KEY AUTOINCREMENT," +
                "modelo      TEXT    NOT NULL, " +
                "placa       TEXT    NOT NULL, " +
                "ano         TEXT    NOT NULL ) ;";

        /*stringBuilderCreateTable.append(" CREATE TABLE CARRO (");
        stringBuilderCreateTable.append("        id       INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTable.append("        modelo   TEXT    NOT NULL,                  ");
        stringBuilderCreateTable.append("        placa    TEXT    NOT NULL,                  ");
        stringBuilderCreateTable.append("        ano      TEXT    NOT NULL )                 ");*/

        String sqlManutencao = "CREATE TABLE MANUTENCAO ( " +
                "id_manutencao             INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data                      TEXT    NOT NULL, " +
                "descricao                 TEXT    NOT NULL, " +
                "valor                     TEXT    NOT NULL, " +
                "id_carro                  INTEGER NOT NULL," +
                "titulo                    TEXT ) ;";


        db.execSQL(sqlCarro);
        db.execSQL(sqlManutencao);

        /*StringBuilder manutencao = new StringBuilder();
        stringBuilderCreateTable.append(" CREATE TABLE MANUTENCAO (");
        stringBuilderCreateTable.append("        id_manutencao             INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTable.append("        FOREIGN KEY (id_carro)    REFERENCES CARRO (id),             ");
        stringBuilderCreateTable.append("        data                      TEXT    NOT NULL,                  ");
        stringBuilderCreateTable.append("        descricao                 TEXT    NOT NULL,                  ");
        stringBuilderCreateTable.append("        valor                     TEXT    NOT NULL         )          ");*/

        //db.execSQL(stringBuilderCreateTable.toString());
        //db.execSQL(manutencao.toString());
    }

    /*SE TROCAR A VERSÃO DO BANCO DE DADOS VOCÊ PODE EXECUTAR ALGUMA ROTINA
      COMO CRIAR COLUNAS, EXCLUIR ENTRE OUTRAS */
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
