package br.com.harazh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class tela_opcoes extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_opcoes);

        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tela_opcoes.this, tela_cadastro.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.mover_direita);
                /*ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation
                        (getApplicationContext(), R.anim.fade_in,R.anim.mover_direita);
                ActivityCompat.startActivity(tela_opcoes.this, intent, activityOptionsCompat.toBundle());*/
            }
        });
    }
}