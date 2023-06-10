package com.example.app_final;

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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_final.models.Vendedor;

import java.util.ArrayList;
import java.util.List;

public class VendedorActivity extends AppCompatActivity {
    private SQLiteDatabase bd;
    private ListView list;
    private ArrayAdapter adapter;
    private ActivityResultLauncher<Intent> launcherCriarCliente;
    List<Vendedor> vendedores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = findViewById(R.id.list);

        criarOuAbrirBancoDeDados();

        listarVendedores();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, vendedores);

        list.setAdapter(adapter);

        /*  */
        launcherCriarCliente = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        String nomeVendedor = data.getStringExtra("nome");
                        criarVendedor(nomeVendedor);
                        listarVendedores();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Vendedor adicionado com sucesso.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemSelecionado = parent.getItemAtPosition(position);

                if (itemSelecionado instanceof Vendedor) {
                    Vendedor cliente = (Vendedor) itemSelecionado;

                    deleteVendedor(cliente.getId());
                    listarVendedores();
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
            Intent intent = new Intent(this, CreateVendedorActivity.class);
            launcherCriarCliente.launch(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void listarVendedores() {
        try {
            vendedores.clear();
            bd = openOrCreateDatabase("vendedores", MODE_PRIVATE, null);
            Cursor cursor = bd.rawQuery("SELECT * FROM vendedores;", null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                System.out.println(id + "-" + nome);
                vendedores.add(new Vendedor(id, nome));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            bd.close();
        }
    }

    public void criarVendedor(String nome) {
        try {
            bd = openOrCreateDatabase("vendedores", MODE_PRIVATE, null);
            String sql = "INSERT INTO vendedores (nome) VALUES (?);";
            SQLiteStatement stml = bd.compileStatement(sql);

            stml.bindString(1, nome);

            stml.executeInsert();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            bd.close();
        }
    }
    public void deleteVendedor(Integer id) {
        try {
            bd = openOrCreateDatabase("vendedores", MODE_PRIVATE, null);
            String sql = "DELETE FROM vendedores WHERE id= ? ;";
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
            bd = openOrCreateDatabase("vendedores", MODE_PRIVATE, null);

            bd.execSQL("CREATE TABLE IF NOT EXISTS vendedores (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome VARCHAR NOT NULL)");

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }
    }
}