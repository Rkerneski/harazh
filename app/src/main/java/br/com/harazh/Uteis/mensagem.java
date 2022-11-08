package br.com.harazh.Uteis;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class mensagem {

    public static void Alert(Context context, String aviso){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        //alertDialog.setTitle(R.string.app_name);

        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(aviso);

        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton("OK", null);

        //MOSTRA A MENSAGEM NA TELA
        alertDialog.show();

    }
}
