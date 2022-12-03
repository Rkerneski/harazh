package br.com.harazh.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.harazh.Model.carroModel;
import br.com.harazh.Model.manutencaoModel;
import br.com.harazh.Uteis.databaseUtil;

public class manutencaoDao {

    databaseUtil databaseUtil;

    public manutencaoDao(Context context) {

        databaseUtil = new databaseUtil(context);
    }

    public void Salvar(manutencaoModel manutencao) {

        ContentValues contentValues = new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("id_carro",  manutencao.getId_carro());
        contentValues.put("data",      manutencao.getData());
        contentValues.put("valor",     manutencao.getValor());
        contentValues.put("descricao", manutencao.getDescricao());
        contentValues.put("titulo", manutencao.getTitulo());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/

        databaseUtil.GetConexaoDataBase().insert("manutencao", null, contentValues);
    }

    public void Editar(manutencaoModel manutencao) {

        ContentValues contentValues = new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("id_carro", manutencao.getId_carro());
        contentValues.put("data", manutencao.getData());
        contentValues.put("titulo", manutencao.getTitulo());
        contentValues.put("descricao", manutencao.getDescricao());
        contentValues.put("valor", manutencao.getValor());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("manutencao", contentValues, "id_manutencao = ?", new String[]{Integer.toString(manutencao.getId_manutencao())});
    }

    //CHAMA ESTE METODO QUANDO FOR EDITAR
    @SuppressLint("Range")
    public manutencaoModel GetManutencao(int num)
    {
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM manutencao WHERE id_manutencao = " + num, null);
        cursor.moveToFirst();

        manutencaoModel manutencao = new manutencaoModel();

        manutencao.setId_manutencao(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_manutencao"))));
        manutencao.setId_carro(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_carro"))));
        manutencao.setData(cursor.getString(cursor.getColumnIndex("data")));
        manutencao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        manutencao.setValor(cursor.getString(cursor.getColumnIndex("valor")));
        manutencao.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));

        return manutencao;
    }

    @SuppressLint("Range")
    public List<manutencaoModel> Listar(int id_carro) {


        ArrayList listar = new ArrayList();

        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT ALL * FROM manutencao WHERE id_carro= '" + id_carro + "' ", null);

        cursor.moveToFirst();

        manutencaoModel manutencao;

        while (!cursor.isAfterLast()) {

            manutencao = new manutencaoModel();

            manutencao.setId_manutencao(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_manutencao"))));
            manutencao.setId_carro(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_carro"))));
            manutencao.setData(cursor.getString(cursor.getColumnIndex("data")));
            manutencao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            manutencao.setValor(cursor.getString(cursor.getColumnIndex("valor")));
            manutencao.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));

            listar.add(manutencao);

            cursor.moveToNext();
        }

        return listar;

        /*ArrayList listar = new ArrayList();

        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append("  SELECT *FROM  MANUTENCAO   ");
        stringBuilderQuery.append("  ORDER BY data       ");


        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);
        cursor.moveToFirst();

        manutencaoModel manutencao;

        while (!cursor.isAfterLast()) {

            manutencao = new manutencaoModel();

            //ADICIONANDO OS DADOS DA PESSOA
            manutencao.setId_manutencao(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_manutencao"))));
            manutencao.setId_carro(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_carro"))));
            manutencao.setData(cursor.getString(cursor.getColumnIndex("data")));
            manutencao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            manutencao.setValor(cursor.getString(cursor.getColumnIndex("valor")));
            manutencao.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));


            listar.add(manutencao);

            cursor.moveToNext();
        }

        return listar;*/

    }

    public Integer Excluir(int id) {

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("manutencao", "id_manutencao = ?", new String[]{Integer.toString(id)});
    }

    @SuppressLint("Range")
    public List<manutencaoModel> Pesquisar(int id_carro, String ano) {


        ArrayList listar = new ArrayList();

        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT ALL * FROM manutencao WHERE id_carro= '" + id_carro + "' and data like '" + "%"+ano+"%" + "' ", null);

        cursor.moveToFirst();

        manutencaoModel manutencao;

        while (!cursor.isAfterLast()) {

            manutencao = new manutencaoModel();

            manutencao.setId_manutencao(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_manutencao"))));
            manutencao.setId_carro(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_carro"))));
            manutencao.setData(cursor.getString(cursor.getColumnIndex("data")));
            manutencao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            manutencao.setValor(cursor.getString(cursor.getColumnIndex("valor")));
            manutencao.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));

            listar.add(manutencao);

            cursor.moveToNext();
        }

        return listar;

    }

    @SuppressLint("Range")
    public manutencaoModel Getsoma(int id_carro) {

        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append("  SELECT sum(cast(valor as decimal (10,2))) FROM  MANUTENCAO   ");
        stringBuilderQuery.append("  where id_carro = '" + id_carro + "'     ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);


        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOA
        manutencaoModel manutencao = new manutencaoModel();

        manutencao.setValor(String.valueOf(cursor.getFloat(0)));

        //String total = stmt.getString(0);
        //RETORNANDO A PESSOA
        return manutencao;

    }

    public manutencaoModel GetsomaTodos(int id_carro, String data) {

        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append("  SELECT sum(cast(valor as decimal (10,2))) FROM  MANUTENCAO   ");
        stringBuilderQuery.append("  where id_carro = '" + id_carro + "' AND data like '" + "%"+ data +"%" + "'    ");

        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        cursor.moveToFirst();
        manutencaoModel manutencao = new manutencaoModel();

        manutencao.setValor(String.valueOf(cursor.getFloat(0)));

        return manutencao;

    }

    @SuppressLint("Range")
    public List<manutencaoModel> Pesquisar(String palavra) {


        ArrayList listar = new ArrayList();

        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT ALL * FROM manutencao WHERE valor= '" + palavra + "' " +
                " or descricao= '" + palavra + "' or titulo= '" + palavra + "' ", null);

        cursor.moveToFirst();

        manutencaoModel manutencao;

        while (!cursor.isAfterLast()) {

            manutencao = new manutencaoModel();

            manutencao.setId_manutencao(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_manutencao"))));
            manutencao.setId_carro(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id_carro"))));
            manutencao.setData(cursor.getString(cursor.getColumnIndex("data")));
            manutencao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            manutencao.setValor(cursor.getString(cursor.getColumnIndex("valor")));
            manutencao.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));


            listar.add(manutencao);

            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return listar;

    }

    public manutencaoModel GetsomaGeral(String pesquisa) {

        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append("  SELECT sum(cast(valor as decimal (10,2))) FROM  MANUTENCAO   ");
        stringBuilderQuery.append("  where titulo = '" + pesquisa + "' OR descricao = '" + pesquisa + "'  ");

        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        cursor.moveToFirst();
        manutencaoModel manutencao = new manutencaoModel();

        manutencao.setValor(String.valueOf(cursor.getFloat(0)));

        return manutencao;

    }

    //contador de quantos serviços foram feitos
    /*
    @SuppressLint("Range")
    public manutencaoModel Getsoma(int id_carro) {

    //CAST('3.02' as decimal)
        int cursor = databaseUtil.GetConexaoDataBase().rawQuery("SELECT id_manutencao FROM manutencao WHERE id_carro= '" + id_carro + "' ", null).getCount();

        //cursor.moveToNext();

        ///CRIANDO UMA NOVA PESSOA
        manutencaoModel manutencao = new manutencaoModel();

        manutencao.setId_manutencao(Integer.valueOf(cursor));

        //String total = stmt.getString(0);
        //RETORNANDO A PESSOA
        return manutencao;

    }
     */

}
