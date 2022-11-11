package br.com.harazh.Uteis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.harazh.Model.carroModel;
import br.com.harazh.R;

public class array_tela_inicial extends ArrayAdapter<carroModel> {
    private ArrayList<carroModel> lista;
    private Context context;

    public array_tela_inicial(Context context, ArrayList<carroModel> lista) {
        super(context,0, lista);
        this.context = context;
        this.lista = lista;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final carroModel itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.itens_carro,null);

        TextView txt_modelo = (TextView)convertView.findViewById(R.id.txt_modelo);
        txt_modelo.setText(itemPosicao.getModelo());

        TextView txt_placa = (TextView)convertView.findViewById(R.id.txt_placa);
        txt_placa.setText(itemPosicao.getPlaca());

        TextView txt_ano = (TextView)convertView.findViewById(R.id.txt_ano);
        txt_ano.setText(itemPosicao.getAno());

        return convertView;
    }
}