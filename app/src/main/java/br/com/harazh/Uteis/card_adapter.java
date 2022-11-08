package br.com.harazh.Uteis;

import android.app.Activity;
import android.app.ActivityOptions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.harazh.Dao.carroDao;
import br.com.harazh.MainActivity;
import br.com.harazh.Model.carroModel;
import br.com.harazh.R;
import br.com.harazh.tela_cadastro;
import br.com.harazh.tela_inicial;
import br.com.harazh.tela_opcoes;

public class card_adapter extends BaseAdapter {

    private static LayoutInflater layoutInflater = null;
    private tela_inicial tela_inicial;
    List<carroModel> carroModels = new ArrayList();

    carroDao carroDao;

    public card_adapter(tela_inicial tela_inicial, List<carroModel> carroModels) {
        this.carroModels = carroModels;
        this.tela_inicial = tela_inicial;
        layoutInflater = (LayoutInflater)this.tela_inicial.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.carroDao = new carroDao(tela_inicial);
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
        final View  viewLinhaLista= layoutInflater.inflate(R.layout.activity_card_adapter, null);
        TextView textViewId = (TextView)viewLinhaLista.findViewById(R.id.line1);
        TextView textViewModelo = (TextView)viewLinhaLista.findViewById(R.id.line2);
        TextView textViewPlaca = (TextView)viewLinhaLista.findViewById(R.id.line3);
        TextView textViewAno = (TextView)viewLinhaLista.findViewById(R.id.line4);
        //Button buttonExcluir = (Button)viewLinhaLista.findViewById(R.id.buttonExcluir);
        //Button buttonEditar = (Button)viewLinhaLista.findViewById(R.id.buttonEditar);
        textViewId.setText(String.valueOf(carroModels.get(position).getId()));
        textViewModelo.setText("Modelo: " + carroModels.get(position).getModelo());
        textViewPlaca.setText("Placa: " + carroModels.get(position).getPlaca());
        textViewAno.setText("Ano: " + carroModels.get(position).getAno());


        viewLinhaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(tela_inicial, "Registro excluido com sucesso!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(tela_inicial, tela_opcoes.class);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(tela_inicial.getApplicationContext(), R.anim.fade_in,R.anim.mover_direita);
                ActivityCompat.startActivity(tela_inicial, intent, activityOptionsCompat.toBundle());





            }
        });

        /*buttonExcluir.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                carroDao.Excluir(carroModels.get(position).getId());
                Toast.makeText(tela_inicial, "Registro excluido com sucesso!",Toast.LENGTH_LONG).show();
                AtualizarLista();
            }
        });
        buttonEditar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(tela_inicial, tela_editar.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", carroModels.get(position).getId());
                tela_inicial.startActivity(intent);
                tela_inicial.finish();
            }
        });*/
        return viewLinhaLista;
    }
}
