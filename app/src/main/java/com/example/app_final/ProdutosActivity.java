package com.example.app_final;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.app_final.models.Produto;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity {
    private SQLiteDatabase bd;
    ListView list;
    ArrayAdapter adapter;
    ArrayList produtos = new ArrayList<Produto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        criarOuAbrirBancoDeDados();
        listarProdutos();
        list = findViewById(R.id.list);

        // adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, produtos);

        //list.setAdapter(adapter);
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

    public  void listarProdutos () {

        List lista = new ArrayList<>();
        Cursor cursor = bd.rawQuery(
                "SELECT * FROM produtos;",
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));

            produtos.add(new Produto(id, nome));
        }
    }

    public void criarOuAbrirBancoDeDados () {
        try {
            bd = openOrCreateDatabase("produtos", MODE_PRIVATE, null);

            bd.execSQL("CREATE TABLE IF NOT EXISTS produtos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome VARCHAR NOT NULL)");

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }
    }
}