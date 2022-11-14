package br.com.harazh;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import br.com.harazh.Dao.carroDao;
import br.com.harazh.Model.carroModel;
import br.com.harazh.Uteis.card_adapter;
import br.com.harazh.Uteis.mensagem;

public class tela_editar extends AppCompatActivity {

    EditText txt_id;
    EditText txt_modelo;
    EditText txt_placa;
    EditText txt_ano;
    Button btn_editar;
    Handler handler = new Handler();
    card_adapter card_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_id = (EditText)findViewById(R.id.txt_id);
        txt_modelo = (EditText)findViewById(R.id.txt_modelo);
        txt_placa = (EditText)findViewById(R.id.txt_placa);
        txt_ano = (EditText)findViewById(R.id.txt_ano);
        btn_editar = (Button)findViewById(R.id.btn_editar);

        CarregarValores();

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_editar.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_editar.setVisibility(View.VISIBLE);

                    }
                }, 200);
                comparador();
            }
        });

    }

    public void comparador(){

        try{
            carroModel carroModel = new carroDao(this).GetPalavra(txt_placa.getText().toString());
            //Toast.makeText(this,"Placa já cadastrada",Toast.LENGTH_LONG).show();

            mensagem.Alert(this, "A placa " + txt_placa.getText() + " "+ this.getString(R.string.placa_cadastrada));
            this.txt_placa.requestFocus();

            //APAGA O TECLADO QUANDO PESQUISAR
            InputMethodManager inputMethodManager =  (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

            return;
        }catch (Exception e){
            salvar();

            InputMethodManager inputMethodManager =  (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void CarregarValores() {

        //PEGA O ID PESSOA QUE FOI PASSADO COMO PARAMETRO ENTRE AS TELAS
        Bundle extra =  this.getIntent().getExtras();
        int id = extra.getInt("id");

        carroModel carroModel = new carroDao(this).PegarCarro(id);

        this.txt_id.setText(String.valueOf(carroModel.getId()));
        this.txt_modelo.setText(carroModel.getModelo());
        this.txt_placa.setText(carroModel.getPlaca());
        this.txt_ano.setText(carroModel.getAno());
    }

    public void salvar() {
        if (txt_modelo.getText().toString().trim().equals("")) {
            mensagem.Alert(this, this.getString(R.string.modelo_obrigatorio));
            this.txt_modelo.requestFocus();
        } else if (txt_placa.getText().toString().trim().equals("")) {
            mensagem.Alert(this, this.getString(R.string.placa_obrigatorio));
            this.txt_placa.requestFocus();
        } else if (txt_ano.getText().toString().trim().equals("")) {
            mensagem.Alert(this, this.getString(R.string.ano_obrigatorio));
            this.txt_ano.requestFocus();
        } else {

            carroModel carroModel = new carroModel();
            carroModel.setId(Integer.valueOf(Integer.parseInt(txt_id.getText().toString())));
            carroModel.setModelo(txt_modelo.getText().toString().trim());
            carroModel.setPlaca(txt_placa.getText().toString().trim());
            carroModel.setAno(this.txt_ano.getText().toString().trim());
            new carroDao(this).Editar(carroModel);

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Carro alterado!");

            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    card_adapter.static_placa = txt_placa.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), tela_gerenciar.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

                }
            });
            alertDialog.show();
        }
    }

    public void onBackPressed() {
        card_adapter.static_placa = txt_placa.getText().toString();
        startActivity(new Intent(this, tela_gerenciar.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

        return;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        card_adapter.static_placa = txt_placa.getText().toString();
        startActivity(new Intent(this, tela_gerenciar.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

        return true;
    }
}