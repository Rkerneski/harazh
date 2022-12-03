package br.com.harazh.Uteis;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.harazh.Dao.manutencaoDao;
import br.com.harazh.Model.manutencaoModel;
import br.com.harazh.R;
import br.com.harazh.tela_editar;
import br.com.harazh.tela_editar_manutencao;
import br.com.harazh.tela_gerenciar_servicos;
import br.com.harazh.tela_inicial;

public class gerenciar_servico_adapter extends BaseAdapter {

    private static LayoutInflater layoutInflater = null;
    private tela_gerenciar_servicos tela_gerenciar_servicos;
    List<manutencaoModel> manutencaoModels = new ArrayList();
    Handler handler = new Handler();
    int aux;
    String ano;
    tela_gerenciar_servicos telaGerenciar;

    manutencaoDao manutencaoDao;

    public gerenciar_servico_adapter(tela_gerenciar_servicos tela_gerenciar_servicos, List<manutencaoModel> manutencaoModels) {
        this.manutencaoModels = manutencaoModels;
        this.tela_gerenciar_servicos = tela_gerenciar_servicos;
        layoutInflater = (LayoutInflater)this.tela_gerenciar_servicos.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.manutencaoDao = new manutencaoDao(tela_gerenciar_servicos);
    }

    public void AtualizarLista() {
        this.manutencaoModels.clear();
        this.manutencaoModels = manutencaoDao.Pesquisar(aux,ano);
        this.notifyDataSetChanged();

        Intent intent = new Intent(gerenciar_servico_adapter.layoutInflater.getContext(), tela_gerenciar_servicos.class);
        gerenciar_servico_adapter.layoutInflater.getContext().startActivity(intent);
    }

    public void AtualizarListaTodos() {
        this.manutencaoModels.clear();
        this.manutencaoModels = manutencaoDao.Listar(aux);
        this.notifyDataSetChanged();

        Intent intent = new Intent(gerenciar_servico_adapter.layoutInflater.getContext(), tela_gerenciar_servicos.class);
        gerenciar_servico_adapter.layoutInflater.getContext().startActivity(intent);
    }

    public int getCount() {
        return manutencaoModels.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final View  viewLinhaLista= layoutInflater.inflate(R.layout.activity_gerenciar_servico_adapter, null);
        TextView textViewId = (TextView)viewLinhaLista.findViewById(R.id.txt_id);
        TextView textViewId_carro = (TextView)viewLinhaLista.findViewById(R.id.txt_id_carro);
        TextView textViewData = (TextView)viewLinhaLista.findViewById(R.id.txt_data);
        TextView textViewTitulo = (TextView)viewLinhaLista.findViewById(R.id.txt_titulo);
        TextView textViewValor = (TextView)viewLinhaLista.findViewById(R.id.txt_valor);
        ImageButton buttonExcluir = (ImageButton)viewLinhaLista.findViewById(R.id.btn_Excluir);
        ImageButton buttonEditar = (ImageButton)viewLinhaLista.findViewById(R.id.btn_Editar);
        ImageButton buttonDesc = (ImageButton)viewLinhaLista.findViewById(R.id.btn_descricao);
        textViewId.setText(String.valueOf(manutencaoModels.get(position).getId_manutencao()));
        textViewId_carro.setText(String.valueOf(manutencaoModels.get(position).getId_carro()));
        textViewData.setText("Data: " + manutencaoModels.get(position).getData());
        textViewTitulo.setText("titulo: " + manutencaoModels.get(position).getTitulo());

        textViewValor.setText("Valor: " + String.format("%.2f", Double.parseDouble(manutencaoModels.get(position).getValor())));

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

                AlertDialog.Builder confirma = new AlertDialog.Builder(gerenciar_servico_adapter.layoutInflater.getContext());
                confirma.setTitle("Aviso");
                confirma.setMessage("Deseja excluir este serviço?");
                confirma.setCancelable(false);

                confirma.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        manutencaoDao.Excluir(manutencaoModels.get(position).getId_manutencao());

                        aux =  Integer.valueOf(textViewId_carro.getText().toString());
                        ano = manutencaoModels.get(position).getData();

                        if(telaGerenciar.comparadoraDeExclusao.equals("AnoAtual")){
                            AtualizarLista();
                        }
                        else if (telaGerenciar.comparadoraDeExclusao.equals("todosAnos")){
                            AtualizarListaTodos();
                        }


                        Toast.makeText(tela_gerenciar_servicos.getBaseContext(),
                                "Serviço excluído",Toast.LENGTH_LONG).show();

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

                Intent intent = new Intent(tela_gerenciar_servicos, tela_editar_manutencao.class);
                intent.putExtra("id", manutencaoModels.get(position).getId_manutencao());
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(tela_gerenciar_servicos.getApplicationContext(), R.anim.fade_in,R.anim.mover_direita);
                ActivityCompat.startActivity(tela_gerenciar_servicos, intent, activityOptionsCompat.toBundle());

            }
        });

        buttonDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonDesc.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonDesc.setVisibility(View.VISIBLE);

                    }
                }, 200);

                AlertDialog.Builder confirma = new AlertDialog.Builder(gerenciar_servico_adapter.layoutInflater.getContext());
                confirma.setTitle("Descrição do Serviço: \n"+ manutencaoModels.get(position).getData());
                confirma.setMessage(manutencaoModels.get(position).getDescricao());
                confirma.setCancelable(false);
                confirma.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                });
                confirma.create().show();
            }
        });

        return viewLinhaLista;
    }

}

