package br.com.harazh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.harazh.Dao.carroDao;
import br.com.harazh.Dao.manutencaoDao;
import br.com.harazh.Model.carroModel;
import br.com.harazh.Model.manutencaoModel;
import br.com.harazh.Uteis.card_adapter;
import br.com.harazh.Uteis.gerenciar_servico_adapter;
import br.com.harazh.Uteis.itens_carro_adapter;

public class tela_gerenciar_servicos extends AppCompatActivity {
    ListView listarCarros, lista;
    card_adapter card_adapter = new card_adapter();
    Button txt_dados;
    itens_carro_adapter itens_carro;
    tela_gerenciar tela_gerenciar;
    TextView ano;
    ImageButton btn_verTodos;
    ImageButton btn_anoAtual;
    ImageButton btn_anoAtualSemFoco;
    ImageButton btn_verTodosSemFoco;
    ImageButton btn_PesquisarSemFoco;
    ImageButton btn_Pesquisar;
    TextView avisoListaAno, txt_pesquisar;
    public static String comparadoraDeExclusao;
    public TextView txt_total;
    public static int aux;
    ImageButton btn_voltar, btn_procurar, btn_fechar;
    Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gerenciar_servicos);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ano = (TextView) findViewById(R.id.ano);
        listarCarros = (ListView) findViewById(R.id.lista);
        txt_dados = (Button)findViewById(R.id.txt_dados);
        btn_verTodos = (ImageButton) findViewById(R.id.btn_verTodos);
        btn_anoAtual = (ImageButton) findViewById(R.id.btn_anoAtual);
        btn_anoAtualSemFoco = (ImageButton)findViewById(R.id.btn_anoAtualSemFoco);
        btn_verTodosSemFoco = (ImageButton)findViewById(R.id.btn_verTodosSemFoco);
        avisoListaAno = (TextView)findViewById(R.id.avisoListaAno);
        txt_total = (TextView)findViewById(R.id.txt_total);
        btn_PesquisarSemFoco = (ImageButton)findViewById(R.id.btn_PesquisarSemFoco);
        btn_Pesquisar = (ImageButton)findViewById(R.id.btn_Pesquisar);
        btn_voltar = (ImageButton)findViewById(R.id.btn_voltar);
        btn_procurar = (ImageButton)findViewById(R.id.btn_procurar);
        btn_fechar = (ImageButton)findViewById(R.id.btn_fechar);
        txt_pesquisar = (TextView)findViewById(R.id.txt_pesquisar);
        lista = (ListView)findViewById(R.id.lista);

        ano.setText(String.valueOf(tela_gerenciar.ano));

        txt_dados.setText(itens_carro.aux_nome_carro+"/"+itens_carro.aux_placa+"/"+itens_carro.aux_ano);
        avisoListaAno.setText("Lista de serviços do ano atual");

        //lista_de_carros();
        //comparadoraDeExclusao = "todosAnos";

        if(aux == 1){
            btn_anoAtual.setVisibility(View.INVISIBLE);
            btn_anoAtualSemFoco.setVisibility(View.VISIBLE);
            btn_verTodosSemFoco.setVisibility(View.INVISIBLE);
            btn_verTodos.setVisibility(View.VISIBLE);
            avisoListaAno.setText("Lista de serviços de todos os anos");
            comparadoraDeExclusao = "todosAnos";
            lista_todos();
        }
        else if(aux == 2){
            btn_anoAtual.setVisibility(View.VISIBLE);
            btn_anoAtualSemFoco.setVisibility(View.INVISIBLE);
            btn_verTodos.setVisibility(View.INVISIBLE);
            btn_verTodosSemFoco.setVisibility(View.VISIBLE);
            avisoListaAno.setText("Lista de serviços do ano atual");
            comparadoraDeExclusao = "AnoAtual";
            lista_de_carros();
        }
        else{
            lista_de_carros();
            comparadoraDeExclusao = "todosAnos";
        }

        btn_verTodosSemFoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ano.setText("Lista de todos os serviços");
                //ano.setVisibility(View.INVISIBLE);
                btn_anoAtual.setVisibility(View.INVISIBLE);
                btn_anoAtualSemFoco.setVisibility(View.VISIBLE);
                btn_verTodosSemFoco.setVisibility(View.INVISIBLE);
                btn_verTodos.setVisibility(View.VISIBLE);
                btn_PesquisarSemFoco.setVisibility(View.VISIBLE);
                btn_Pesquisar.setVisibility(View.INVISIBLE);
                avisoListaAno.setText("Lista de serviços de todos os anos");
                comparadoraDeExclusao = "todosAnos";
                lista_todos();
            }
        });

        btn_anoAtualSemFoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_anoAtual.setVisibility(View.VISIBLE);
                btn_anoAtualSemFoco.setVisibility(View.INVISIBLE);
                btn_verTodos.setVisibility(View.INVISIBLE);
                btn_verTodosSemFoco.setVisibility(View.VISIBLE);
                btn_PesquisarSemFoco.setVisibility(View.VISIBLE);
                btn_Pesquisar.setVisibility(View.INVISIBLE);
                avisoListaAno.setText("Lista de serviços do ano atual");
                comparadoraDeExclusao = "AnoAtual";
                lista_de_carros();
            }
        });

        btn_PesquisarSemFoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_PesquisarSemFoco.setVisibility(View.INVISIBLE);
                btn_Pesquisar.setVisibility(View.INVISIBLE);
                btn_verTodosSemFoco.setVisibility(View.INVISIBLE);
                btn_verTodos.setVisibility(View.INVISIBLE);
                btn_anoAtual.setVisibility(View.INVISIBLE);
                btn_anoAtualSemFoco.setVisibility(View.INVISIBLE);
                ano.setVisibility(View.INVISIBLE);
                btn_voltar.setVisibility(View.VISIBLE);
                btn_procurar.setVisibility(View.VISIBLE);
                btn_fechar.setVisibility(View.VISIBLE);
                txt_pesquisar.setVisibility(View.VISIBLE);
                avisoListaAno.setVisibility(View.INVISIBLE);
                txt_total.setVisibility(View.INVISIBLE);
                lista.setVisibility(View.INVISIBLE);
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_voltar.setVisibility(View.INVISIBLE);
                btn_procurar.setVisibility(View.INVISIBLE);
                btn_fechar.setVisibility(View.INVISIBLE);
                txt_pesquisar.setVisibility(View.INVISIBLE);
                btn_PesquisarSemFoco.setVisibility(View.VISIBLE);
                btn_verTodosSemFoco.setVisibility(View.VISIBLE);
                btn_anoAtual.setVisibility(View.VISIBLE);
                ano.setVisibility(View.INVISIBLE);
                lista_de_carros();
            }
        });

        btn_procurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_procurar.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_procurar.setVisibility(View.VISIBLE);

                    }
                }, 200);

                pesquisar();
            }
        });

        btn_fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_fechar.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_fechar.setVisibility(View.VISIBLE);

                    }
                }, 200);

                txt_pesquisar.setText("");
                lista_de_carros();

            }
        });
    }

    public void somaValor() {
        try{
            manutencaoModel manutencaoModel = new manutencaoDao(this).GetsomaTodos(card_adapter.static_idCarro,String.valueOf(tela_gerenciar.ano));
            //Toast.makeText(this,"Placa já cadastrada",Toast.LENGTH_LONG).show();

            txt_total.setText("Valor total R$ " + String.format("%.2f", Double.parseDouble(manutencaoModel.getValor())));

            return;
        }catch (Exception e){

        }
    }

    public void somaValorTodos() {
        try{
            manutencaoModel manutencaoModel = new manutencaoDao(this).Getsoma(card_adapter.static_idCarro);
            //Toast.makeText(this,"Placa já cadastrada",Toast.LENGTH_LONG).show();

            txt_total.setText("Valor total R$ " + String.format("%.2f", Double.parseDouble(manutencaoModel.getValor())));

            return;
        }catch (Exception e){

        }
    }

    public void lista_de_carros() {

        try{
            aux = 2;
            manutencaoDao manutencaoDao = new manutencaoDao(this);

            List<manutencaoModel> manutencao = manutencaoDao.Pesquisar(card_adapter.static_idCarro, String.valueOf(tela_gerenciar.ano));
            listarCarros.setAdapter(new gerenciar_servico_adapter(this, manutencao));

            if(manutencao.size() == 0){
                txt_total.setText("");
            }
            else{
                somaValor();
            }

        }catch (Exception e){

        }

    }

    public void lista_todos() {

        try{
            aux = 1;
            manutencaoDao manutencaoDao = new manutencaoDao(this);

            List<manutencaoModel> manutencao = manutencaoDao.Listar(card_adapter.static_idCarro);
            listarCarros.setAdapter(new gerenciar_servico_adapter(this, manutencao));

            if(manutencao.size() == 0){
                txt_total.setText("");
            }
            else{
                somaValorTodos();
            }
        }catch (Exception e){

        }

    }

    public void somaValorGeral() {
        try{
            manutencaoModel manutencaoModel = new manutencaoDao(this).GetsomaGeral(txt_pesquisar.getText().toString());
            //Toast.makeText(this,"Placa já cadastrada",Toast.LENGTH_LONG).show();

            txt_total.setText("Valor total R$ " + String.format("%.2f", Double.parseDouble(manutencaoModel.getValor())));

            return;
        }catch (Exception e){

        }
    }

    public void pesquisar() {

        try{
            manutencaoDao manutencaoDao = new manutencaoDao(this);

            List<manutencaoModel> manutencao = manutencaoDao.Pesquisar(txt_pesquisar.getText().toString().toLowerCase());
            listarCarros.setAdapter(new gerenciar_servico_adapter(this, manutencao));


            if (manutencao.size() == 0) {
                this.lista_de_carros();
                InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                Toast.makeText(getApplicationContext(), "Pesquisa não encontrada", Toast.LENGTH_LONG).show();
                return;
            }
            else{

                somaValorGeral();
                lista.setVisibility(View.VISIBLE);
                avisoListaAno.setVisibility(View.VISIBLE);
                avisoListaAno.setText("Lista de serviços gerais");
                txt_total.setVisibility(View.VISIBLE);
            }

            //pesquisa_no_banco();

            //APAGA O TECLADO QUANDO PESQUISAR
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Pesquisa não encontrada", Toast.LENGTH_LONG).show();
            return;

        }

        txt_pesquisar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(txt_pesquisar.getWindowToken(), 0);
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                            pesquisar();

                            return true;
                    }
                }
                return false;
            }
        });


        txt_pesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {

                    lista_de_carros();
                    apagar_teclado();

                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txt_pesquisar.getWindowToken(), 0);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void apagar_teclado() {

        txt_pesquisar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(txt_pesquisar.getWindowToken(), 0);
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                            pesquisar();

                            return true;
                    }
                }
                return false;
            }
        });

    }

    public void onBackPressed() {
        startActivity(new Intent(this, tela_gerenciar.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
        aux = 0;

        return;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        startActivity(new Intent(this, tela_gerenciar.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

        return true;
    }
}