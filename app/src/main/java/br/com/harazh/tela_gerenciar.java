package br.com.harazh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import br.com.harazh.Dao.carroDao;
import br.com.harazh.Model.carroModel;
import br.com.harazh.Uteis.card_adapter;
import br.com.harazh.Uteis.itens_carro_adapter;

public class tela_gerenciar extends AppCompatActivity {

    ImageButton manutencao;
    ListView lista;
    card_adapter card_adapter = new card_adapter();
    Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gerenciar);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        manutencao = (ImageButton) findViewById(R.id.manutencao);
        lista = (ListView) findViewById(R.id.lista);

        dados_do_veiculo();


        manutencao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manutencao.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        manutencao.setVisibility(View.VISIBLE);

                    }
                }, 200);

                Intent intent = new Intent(tela_gerenciar.this, tela_manutencao.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.mover_direita);
                /*ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation
                        (getApplicationContext(), R.anim.fade_in,R.anim.mover_direita);
                ActivityCompat.startActivity(tela_opcoes.this, intent, activityOptionsCompat.toBundle());*/
            }
        });
    }

    private void dados_do_veiculo() {
        //pega a string da placa
        //Bundle extra =  this.getIntent().getExtras();
        //String placa = extra.getString("placa");

        carroDao carroDao = new carroDao(this);

        List<carroModel> carros = carroDao.Gerenciar(card_adapter.static_placa);
        lista.setAdapter(new itens_carro_adapter(this, carros));
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