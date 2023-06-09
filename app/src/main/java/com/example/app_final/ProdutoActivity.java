package com.example.app_final;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app_final.models.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {
    private SQLiteDatabase bd;
    private ListView list;
    private ArrayAdapter adapter;
    private ActivityResultLauncher<Intent> launcherCriarProduto;
    List<Produto> produtos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = findViewById(R.id.list);

        criarOuAbrirBancoDeDados();

        listarProdutos();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, produtos);

        list.setAdapter(adapter);

        /*  */
        launcherCriarProduto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        String nomeProduto = data.getStringExtra("nome");
                        criarProduto(nomeProduto);
                        listarProdutos();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Produto adicionado com sucesso.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemSelecionado = parent.getItemAtPosition(position);

                if (itemSelecionado instanceof Produto) {
                    Produto produto = (Produto) itemSelecionado;

                    deleteProduto(produto.getId());
                    listarProdutos();
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crud, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Chamar a função de voltar à tela anterior
            onBackPressed();
            return true;
        } else if (id == R.id.idMenuCriacao) {
            Intent intent = new Intent(this, CreateProdutoActivity.class);
            launcherCriarProduto.launch(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void listarProdutos () {
        try {
            produtos.clear();
            bd = openOrCreateDatabase("produtos", MODE_PRIVATE, null);
            Cursor cursor = bd.rawQuery("SELECT * FROM produtos;", null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                System.out.println(id + "-" + nome);
                produtos.add(new Produto(id, nome));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            bd.close();
        }
    }

    public void criarProduto (String nome) {
        try {
            bd = openOrCreateDatabase("produtos", MODE_PRIVATE, null);
            String sql = "INSERT INTO produtos (nome) VALUES (?);";
            SQLiteStatement stml = bd.compileStatement(sql);

            stml.bindString(1, nome);

            stml.executeInsert();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            bd.close();
        }
    }
    public void deleteProduto (Integer id) {
        try {
            bd = openOrCreateDatabase("produtos", MODE_PRIVATE, null);
            String sql = "DELETE FROM produtos WHERE id= ? ;";
            SQLiteStatement stml = bd.compileStatement(sql);

            stml.bindLong(1, id);

            stml.executeUpdateDelete();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            bd.close();
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