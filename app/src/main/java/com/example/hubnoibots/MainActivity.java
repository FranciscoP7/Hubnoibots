package com.example.hubnoibots;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnNovo = findViewById(R.id.btnNovo);
        Button btnVerAgenda = findViewById(R.id.btnVerAgenda);
        btnNovo.setOnClickListener(view -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        });
        btnVerAgenda.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListaActivity.class);
            startActivity(intent);
        });
    }
}