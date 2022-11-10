package br.com.harazh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.harazh.Dao.carroDao;
import br.com.harazh.Model.carroModel;
import br.com.harazh.Uteis.array_tela_inicial;
import br.com.harazh.Uteis.card_adapter;

public class tela_inicial extends AppCompatActivity {

    ListView listarCarros;
    ImageButton btn_close;
    ImageButton btn_search;
    ListView lista;
    ListView lista2;
    Handler handler = new Handler();
    EditText txt_pesquisar;
    EditText txt_modelo_aux;
    EditText modelo;
    EditText txt_placa_aux;
    EditText txt_ano_aux;
    Boolean campo_pesquisar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        listarCarros = (ListView)findViewById(R.id.lista);
        btn_close = (ImageButton)findViewById(R.id.btn_close);
        btn_search = (ImageButton)findViewById(R.id.btn_search);
        lista = (ListView)findViewById(R.id.lista);
        lista2 = (ListView)findViewById(R.id.lista2);
        txt_pesquisar = (EditText)findViewById(R.id.txt_pesquisar);
        txt_modelo_aux = (EditText)findViewById(R.id.txt_modelo_aux);
        txt_placa_aux = (EditText)findViewById(R.id.txt_placa_aux);
        txt_ano_aux = (EditText)findViewById(R.id.txt_ano_aux);

        //this.lista_de_carros();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_search.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_search.setVisibility(View.VISIBLE);

                    }
                }, 200);
                pesquisar();
            }
        });



        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_close.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_close.setVisibility(View.VISIBLE);

                    }
                }, 200);

                apagar_teclado();

                lista.setVisibility(View.VISIBLE);
                lista2.setVisibility(View.INVISIBLE);
            }
        });

        txt_pesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){

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

    public void apagar_teclado(){
        /*InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);*/

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


    public void pesquisar(){

        try{
            /*carroModel carroModel = new carroDao(getApplicationContext()).Pesquisa(txt_pesquisar.getText().toString());
            txt_modelo_aux.setText(carroModel.getModelo());
            txt_placa_aux.setText(carroModel.getPlaca());
            txt_ano_aux.setText(carroModel.getAno());*/

            if(txt_pesquisar.getText().equals("") || txt_pesquisar.getText() == null){

            }
            else {
                carroDao carroDao = new carroDao(this);

                List<carroModel> carros = carroDao.Pesquisa(txt_pesquisar.getText().toString());
                listarCarros.setAdapter(new card_adapter(this, carros));

                if (carros.size() == 0) {
                    this.lista_de_carros();
                    return;
                }
            }

            //pesquisa_no_banco();

            //APAGA O TECLADO QUANDO PESQUISAR
            InputMethodManager inputMethodManager =  (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

            return;
        }catch (Exception e){
            lista.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Pesquisa não encontrada",Toast.LENGTH_LONG).show();
            InputMethodManager inputMethodManager =  (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void  pesquisa_no_banco(){
        ArrayList<carroModel> carroModel = new ArrayList<carroModel>();

        carroModel.add(new carroModel(txt_modelo_aux.getText().toString(), txt_placa_aux.getText().toString(),txt_ano_aux.getText().toString()));

        array_tela_inicial array_tela_inicial = new array_tela_inicial(getApplication(),carroModel);
        lista2.setAdapter(array_tela_inicial);
    }

    protected void lista_de_carros(){

        carroDao carroDao = new carroDao(this);

        List<carroModel> carros = carroDao.Listar();
        listarCarros.setAdapter(new card_adapter(this, carros));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_superior_cadastrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

         if (id == R.id.cadastrar_carro) {
            startActivity(new Intent(this, tela_cadastro.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

