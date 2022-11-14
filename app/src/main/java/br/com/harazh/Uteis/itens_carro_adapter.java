package br.com.harazh.Uteis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.harazh.Dao.carroDao;
import br.com.harazh.Model.carroModel;
import br.com.harazh.R;
import br.com.harazh.tela_cadastro;
import br.com.harazh.tela_editar;
import br.com.harazh.tela_gerenciar;
import br.com.harazh.tela_inicial;

public class itens_carro_adapter extends BaseAdapter {
    private static LayoutInflater layoutInflater = null;
    private tela_gerenciar tela_gerenciar;
    List<carroModel> carroModels = new ArrayList();
    Handler handler = new Handler();

    carroDao carroDao;

    public itens_carro_adapter(tela_gerenciar tela_gerenciar, List<carroModel> carroModels) {
        this.carroModels = carroModels;
        this.tela_gerenciar = tela_gerenciar;
        layoutInflater = (LayoutInflater)this.tela_gerenciar.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.carroDao = new carroDao(tela_gerenciar);
    }

    public void AtualizarLista() {
        this.carroModels.clear();
        this.carroModels = carroDao.Listar();
        this.notifyDataSetChanged();
    }

    public int getCount() {
        return carroModels.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final View  viewLinhaLista= layoutInflater.inflate(R.layout.itens_carro, null);
        TextView textViewId = (TextView)viewLinhaLista.findViewById(R.id.txt_id);
        TextView textViewModelo = (TextView)viewLinhaLista.findViewById(R.id.txt_modelo);
        TextView textViewPlaca = (TextView)viewLinhaLista.findViewById(R.id.txt_placa);
        TextView textViewAno = (TextView)viewLinhaLista.findViewById(R.id.txt_ano);
        Button buttonExcluir = (Button)viewLinhaLista.findViewById(R.id.btn_Excluir);
        Button buttonEditar = (Button)viewLinhaLista.findViewById(R.id.btn_Editar);
        textViewId.setText(String.valueOf(carroModels.get(position).getId()));
        textViewModelo.setText("Modelo: " + carroModels.get(position).getModelo());
        textViewPlaca.setText("Placa: " + carroModels.get(position).getPlaca());
        textViewAno.setText("Ano: " + carroModels.get(position).getAno());


       buttonExcluir.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               buttonExcluir.setVisibility(View.INVISIBLE);
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       buttonExcluir.setVisibility(View.VISIBLE);

                   }
               }, 200);

               AlertDialog.Builder confirma = new AlertDialog.Builder(itens_carro_adapter.layoutInflater.getContext());
               confirma.setTitle("Aviso");
               confirma.setMessage("Deseja excluir este carro?");

               confirma.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {


                       carroDao.Excluir(carroModels.get(position).getId());

                       Intent intent = new Intent(tela_gerenciar, tela_inicial.class);
                       ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(tela_gerenciar.getApplicationContext(), R.anim.fade_in,R.anim.mover_direita);
                       ActivityCompat.startActivity(tela_gerenciar, intent, activityOptionsCompat.toBundle());

                       Toast.makeText(tela_gerenciar.getBaseContext(),
                               textViewModelo.getText() + " excluído",Toast.LENGTH_LONG).show();

                   }
               });

               confirma.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               });
               confirma.create().show();
           }
       });

       buttonEditar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               buttonEditar.setVisibility(View.INVISIBLE);
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       buttonEditar.setVisibility(View.VISIBLE);

                   }
               }, 200);

               Intent intent = new Intent(tela_gerenciar, tela_editar.class);
               intent.putExtra("id", carroModels.get(position).getId());
               ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(tela_gerenciar.getApplicationContext(), R.anim.fade_in,R.anim.mover_direita);
               ActivityCompat.startActivity(tela_gerenciar, intent, activityOptionsCompat.toBundle());

           }
       });

        return viewLinhaLista;
    }
}
