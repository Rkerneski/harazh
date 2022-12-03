package br.com.harazh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Calendar;

import br.com.harazh.Dao.manutencaoDao;
import br.com.harazh.Model.manutencaoModel;
import br.com.harazh.Uteis.card_adapter;
import br.com.harazh.Uteis.mensagem;

public class tela_manutencao extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    Button btn_data;
    EditText txt_valor;
    EditText txt_descricao;
    Button btn_salvar;
    card_adapter card_adapter = new card_adapter();
    EditText txt_titulo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_manutencao);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDatePicker();
        btn_data = (Button) findViewById(R.id.btn_data);
        btn_data.setText(getTodaysDate());
        txt_valor = (EditText) findViewById(R.id.txt_valor);
        txt_descricao = (EditText) findViewById(R.id.txt_descricao);
        btn_salvar = (Button) findViewById(R.id.btn_salvar);
        txt_titulo = (EditText)findViewById(R.id.txt_titulo);

        //txt_valor.addTextChangedListener(new MascaraMonetaria(txt_valor));

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //txt_descricao.setText(txt_valor.getText().replace(0, 2, "R$").toString());
                 salvar();
            }
        });

    }

    private void salvar() {
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
        }
        else{
            manutencaoModel manutencaoModel = new manutencaoModel();
            manutencaoModel.setId_carro(card_adapter.static_idCarro);
            manutencaoModel.setData(this.btn_data.getText().toString().trim().toLowerCase());
            //manutencaoModel.setValor("05,00");
            //manutencaoModel.setValor(txt_valor.getText().replace(0, 2, "R$").toString().replaceAll(",", "."));
            manutencaoModel.setValor(txt_valor.getText().toString());
            manutencaoModel.setDescricao(this.txt_descricao.getText().toString().trim().toLowerCase());
            manutencaoModel.setTitulo(this.txt_titulo.getText().toString().trim().toLowerCase());
            new manutencaoDao(this).Salvar(manutencaoModel);

            AlertDialog.Builder confirma = new AlertDialog.Builder(tela_manutencao.this);
            confirma.setTitle("Manutenção Cadastrada!");
            confirma.setMessage("Deseja salvar mais uma manutenção?");
            confirma.setCancelable(false);

            confirma.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    txt_descricao.setText("");
                    txt_valor.setText("");
                    txt_titulo.setText("");
                }
            });

            confirma.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(tela_manutencao.this, tela_gerenciar.class);
                    startActivity(intent);
                }
            });
            confirma.show();
            //mensagem.Alert(this, this.getString(R.string.registro_salvo));
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

        int style = AlertDialog.THEME_HOLO_LIGHT;

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

    //para mascara valor
    private class MascaraMonetaria implements TextWatcher {

        final EditText campo;

        public MascaraMonetaria(EditText campo) {
            super();
            this.campo = campo;
        }

        private boolean isUpdating = false;

        // Pega a formatacao do sistema, se for brasil R$ se EUA US$

        private NumberFormat nf = NumberFormat.getCurrencyInstance();

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int after) {

            // Evita que o método seja executado varias vezes.

            // Se tirar ele entre em loop

            if (isUpdating) {
                isUpdating = false;
                return;
            }

            isUpdating = true;

            String str = s.toString();
            str = str.replaceAll("[^\\d]", "");

            try {
                // Transformamos o número que está escrito no EditText em

                // monetário.

                str = nf.format(Double.parseDouble(str) / 100);

                campo.setText(str);

                campo.setSelection(campo.getText().length());

            } catch (NumberFormatException e) {
                s = "";
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Não utilizado
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Não utilizado
        }

    }
    //final mascara valor

    public void onBackPressed() {
        startActivity(new Intent(this, tela_gerenciar.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

        return;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        startActivity(new Intent(this, tela_gerenciar.class));
        finish();
        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);

        return true;
    }

}