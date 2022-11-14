package br.com.harazh.Dao;

import android.content.ContentValues;
import android.content.Context;

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
        contentValues.put("descricao", manutencao.getDescricao());
        contentValues.put("valor",     manutencao.getValor());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/

        databaseUtil.GetConexaoDataBase().insert("manutencao", null, contentValues);
    }
}
