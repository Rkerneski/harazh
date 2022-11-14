package br.com.harazh.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.harazh.Model.carroModel;
import br.com.harazh.R;
import br.com.harazh.Uteis.databaseUtil;
import br.com.harazh.Uteis.mensagem;

public class carroDao {

    databaseUtil databaseUtil;

    public carroDao(Context context) {

        databaseUtil = new databaseUtil(context);
    }

    public void Salvar(carroModel carro) {

        ContentValues contentValues = new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("modelo", carro.getModelo());
        contentValues.put("placa", carro.getPlaca());
        contentValues.put("ano", carro.getAno());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/

        databaseUtil.GetConexaoDataBase().insert("carro", null, contentValues);
    }

    @SuppressLint("Range")
    public List<carroModel> Listar() {

        ArrayList listar = new ArrayList();

        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append("  SELECT *FROM  CARRO   ");
        stringBuilderQuery.append("  ORDER BY MODELO       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();

        carroModel carro;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()) {

            /* CRIANDO UMA NOVA PESSOAS */
            carro = new carroModel();

            //ADICIONANDO OS DADOS DA PESSOA
            carro.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
            carro.setModelo(cursor.getString(cursor.getColumnIndex("modelo")));
            carro.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
            carro.setAno(cursor.getString(cursor.getColumnIndex("ano")));

            //ADICIONANDO UMA PESSOA NA LISTA
            listar.add(carro);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return listar;

    }

    public Integer Excluir(int id) {

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("carro", "id = ?", new String[]{Integer.toString(id)});
    }

    @SuppressLint("Range")
    public carroModel PegarCarro(int num) {
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM carro WHERE id = " + num, null);
        cursor.moveToFirst();

        carroModel carro = new carroModel();

        carro.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
        carro.setModelo(cursor.getString(cursor.getColumnIndex("modelo")));
        carro.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
        carro.setAno(cursor.getString(cursor.getColumnIndex("ano")));

        return carro;
    }

    public void Editar(carroModel carro) {

        ContentValues contentValues = new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("modelo", carro.getModelo());
        contentValues.put("placa", carro.getPlaca());
        contentValues.put("ano", carro.getAno());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("carro", contentValues, "id = ?", new String[]{Integer.toString(carro.getId())});
    }

    @SuppressLint("Range")
    public carroModel GetPalavra(String placa) {


        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM carro WHERE placa= '" + placa + "' ", null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        carroModel carro = new carroModel();

        carro.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
        carro.setModelo(cursor.getString(cursor.getColumnIndex("modelo")));
        carro.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
        carro.setAno(cursor.getString(cursor.getColumnIndex("ano")));

        //RETORNANDO A PESSOA
        return carro;

    }

    @SuppressLint("Range")
    public List<carroModel> Pesquisa(String palavra) {


        ArrayList listar = new ArrayList();

        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT ALL * FROM carro WHERE modelo= '" + palavra + "' " +
                " or placa= '" + palavra + "' or ano= '" + palavra + "' ", null);

        cursor.moveToFirst();

        carroModel carro;

        while (!cursor.isAfterLast()) {

            carro = new carroModel();

            //ADICIONANDO OS DADOS DA PESSOA
            carro.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
            carro.setModelo(cursor.getString(cursor.getColumnIndex("modelo")));
            carro.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
            carro.setAno(cursor.getString(cursor.getColumnIndex("ano")));

            //ADICIONANDO UMA PESSOA NA LISTA
            listar.add(carro);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return listar;

    }

    @SuppressLint("Range")
    public List<carroModel> Gerenciar (String palavra) {


        ArrayList listar = new ArrayList();

        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM carro WHERE placa like '" + palavra + "'  ", null);

        cursor.moveToFirst();

        carroModel carro;

        while (!cursor.isAfterLast()) {

            carro = new carroModel();

            //ADICIONANDO OS DADOS DA PESSOA
            carro.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
            carro.setModelo(cursor.getString(cursor.getColumnIndex("modelo")));
            carro.setPlaca(cursor.getString(cursor.getColumnIndex("placa")));
            carro.setAno(cursor.getString(cursor.getColumnIndex("ano")));

            //ADICIONANDO UMA PESSOA NA LISTA
            listar.add(carro);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return listar;

    }
}
