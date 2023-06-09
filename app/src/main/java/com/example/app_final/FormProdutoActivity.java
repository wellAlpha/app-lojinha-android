package com.example.app_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FormProdutoActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    EditText nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produto);

        /* Captura componentes */
        floatingActionButton = findViewById(R.id.btnCProduto);
        nome = findViewById(R.id.nomeCriacaoProduto);

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
}