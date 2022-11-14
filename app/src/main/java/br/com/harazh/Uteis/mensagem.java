package br.com.harazh.Uteis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import br.com.harazh.R;
import br.com.harazh.tela_cadastro;
import br.com.harazh.tela_inicial;

public class mensagem {

    public static void Alert(Context context, String aviso){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        alertDialog.setTitle("Aviso");

        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(aviso);

        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton("OK", null);


        //MOSTRA A MENSAGEM NA TELA
        alertDialog.show();

    }
}
