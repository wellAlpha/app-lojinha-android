package com.example.app_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateClienteActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    EditText nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Captura componentes */
        floatingActionButton = findViewById(R.id.btnCCliente);
        nome = findViewById(R.id.nomeCriacaoCliente);

        /* Evento que captura o click no botão de salvar */
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("nome", nome.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Chamar a função de voltar à tela anterior
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}