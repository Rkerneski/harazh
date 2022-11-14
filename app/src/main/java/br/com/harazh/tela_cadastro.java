package br.com.harazh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.harazh.Dao.carroDao;
import br.com.harazh.Model.carroModel;
import br.com.harazh.Uteis.mensagem;

public class tela_cadastro extends AppCompatActivity {

    EditText txt_modelo;
    EditText txt_placa;
    EditText txt_ano;
    Button btn_salvar;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_modelo = (EditText)findViewById(R.id.txt_modelo);
        txt_placa = (EditText)findViewById(R.id.txt_placa);
        txt_ano = (EditText)findViewById(R.id.txt_ano);
        btn_salvar = (Button)findViewById(R.id.btn_salvar);

        txt_modelo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                txt_modelo.setHint("");

                return false;
            }
        });

        txt_placa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                txt_placa.setHint("");

                return false;
            }
        });

        txt_ano.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                txt_ano.setHint("");

                return false;
            }
        });

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_salvar.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_salvar.setVisibility(View.VISIBLE);

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

    public void salvar(){
        if(txt_modelo.getText().toString().trim().equals("")){
        mensagem.Alert(this, this.getString(R.string.modelo_obrigatorio));
        this.txt_modelo.requestFocus();
        }
        else if(txt_placa.getText().toString().trim().equals("")){
            mensagem.Alert(this, this.getString(R.string.placa_obrigatorio));
            this.txt_placa.requestFocus();
        }
        else if(txt_ano.getText().toString().trim().equals("")){
            mensagem.Alert(this, this.getString(R.string.ano_obrigatorio));
            this.txt_ano.requestFocus();
        }
        else{
            carroModel carroModel = new carroModel();
            carroModel.setModelo(this.txt_modelo.getText().toString().trim().toLowerCase());
            carroModel.setPlaca(this.txt_placa.getText().toString().trim().toLowerCase());
            carroModel.setAno(this.txt_ano.getText().toString().trim().toLowerCase());
            new carroDao(this).Salvar(carroModel);

            AlertDialog.Builder confirma = new AlertDialog.Builder(tela_cadastro.this);
            confirma.setTitle("Carro Cadastrado!");
            confirma.setMessage("Deseja salvar mais um carro?");

            confirma.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    txt_modelo.requestFocus();
                    txt_modelo.setText("");
                    txt_modelo.setHint("Modelo");
                    txt_ano.setText("");
                    txt_ano.setHint("Ano");
                    txt_placa.setText("");
                    txt_placa.setHint("Placa");
                }
            });

            confirma.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(tela_cadastro.this, tela_inicial.class);
                    startActivity(intent);
                }
            });
            confirma.show();
            //mensagem.Alert(this, this.getString(R.string.registro_salvo));
        }
    }


    public void onBackPressed() {
        startActivity(new Intent(this, tela_inicial.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

        return;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        startActivity(new Intent(this, tela_inicial.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

        return true;
    }

}