package br.com.harazh;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import br.com.harazh.Dao.carroDao;
import br.com.harazh.Dao.manutencaoDao;
import br.com.harazh.Model.carroModel;
import br.com.harazh.Model.manutencaoModel;
import br.com.harazh.Uteis.mensagem;

public class tela_editar_manutencao extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    Button btn_data;
    EditText txt_titulo;
    EditText txt_descricao;
    EditText txt_valor;
    Button btn_editar;
    EditText txt_id;
    EditText txt_id_carro;
    Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar_manutencao);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDatePicker();
        btn_data = (Button) findViewById(R.id.btn_data);
        txt_titulo = (EditText) findViewById(R.id.txt_titulo);
        txt_descricao = (EditText) findViewById(R.id.txt_descricao);
        txt_valor = (EditText) findViewById(R.id.txt_valor);
        btn_editar = (Button) findViewById(R.id.btn_editar);
        txt_id = (EditText) findViewById(R.id.txt_id);
        txt_id_carro = (EditText) findViewById(R.id.txt_id_carro);

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
                salvar();
            }
        });
    }

    public void CarregarValores() {

        //PEGA O ID PESSOA QUE FOI PASSADO COMO PARAMETRO ENTRE AS TELAS
        Bundle extra =  this.getIntent().getExtras();
        int id = extra.getInt("id");

        manutencaoModel manutencao = new manutencaoDao(this).GetManutencao(id);

        this.txt_id.setText(String.valueOf(manutencao.getId_manutencao()));
        this.txt_id_carro.setText(String.valueOf(manutencao.getId_carro()));
        this.btn_data.setText(manutencao.getData());
        this.txt_valor.setText(String.format("%.2f",Double.parseDouble(manutencao.getValor())).replaceAll(",","."));
        this.txt_descricao.setText(manutencao.getDescricao());
        this.txt_titulo.setText(manutencao.getTitulo());
    }

    public void salvar() {
        if(txt_titulo.getText().toString().trim().equals("")){
            mensagem.Alert(this, this.getString(R.string.titulo_obrigatorio));
        }
        else if(txt_descricao.getText().toString().trim().equals("")){
            mensagem.Alert(this, this.getString(R.string.desc_obrigatorio));
            this.txt_descricao.requestFocus();
        }
        else if(txt_valor.getText().toString().trim().equals("")){
            mensagem.Alert(this, this.getString(R.string.valor_obrigatorio));
            this.txt_valor.requestFocus();
        } else {

            manutencaoModel manutencaoModel = new manutencaoModel();
            manutencaoModel.setId_manutencao(Integer.valueOf(Integer.parseInt(txt_id.getText().toString())));
            manutencaoModel.setId_carro(Integer.valueOf(Integer.parseInt(txt_id_carro.getText().toString())));
            manutencaoModel.setData(this.btn_data.getText().toString().trim().toLowerCase());
            manutencaoModel.setValor(txt_valor.getText().toString());
            manutencaoModel.setDescricao(this.txt_descricao.getText().toString().trim().toLowerCase());
            manutencaoModel.setTitulo(this.txt_titulo.getText().toString().trim().toLowerCase());
            new manutencaoDao(this).Editar(manutencaoModel);

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Manutenção alterada!");

            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getApplicationContext(), tela_gerenciar_servicos.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

                }
            });
            alertDialog.show();
        }
    }

    //para data
    private String getTodaysDate() {

        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        mes = mes + 1;
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dia, mes, ano);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int dia, int mes, int ano) {
                mes = mes + 1;
                String date = makeDateString(ano, mes, dia);
                btn_data.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH);
        int ano = cal.get(Calendar.YEAR);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, ano, mes, dia);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int dia, int mes, int ano) {
        return getMonthFormat(mes) + " " + dia + " " + ano;
    }

    private String getMonthFormat(int mes) {
        if (mes == 1)
            return "JAN";
        if (mes == 2)
            return "FEV";
        if (mes == 3)
            return "MAR";
        if (mes == 4)
            return "ABR";
        if (mes == 5)
            return "MAI";
        if (mes == 6)
            return "JUN";
        if (mes == 7)
            return "JUL";
        if (mes == 8)
            return "AGO";
        if (mes == 9)
            return "SET";
        if (mes == 10)
            return "OUT";
        if (mes == 11)
            return "NOV";
        if (mes == 12)
            return "DEZ";

        //default should never happen
        return "JAN";
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
    //final data


    public void onBackPressed() {
        startActivity(new Intent(this, tela_gerenciar_servicos.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

        return;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        startActivity(new Intent(this, tela_gerenciar_servicos.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

        return true;
    }
}