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

import com.example.app_final.models.Cliente;
import com.example.app_final.models.Produto;

import java.util.ArrayList;
import java.util.List;

public class ClienteActivity extends AppCompatActivity {
    private SQLiteDatabase bd;
    private ListView list;
    private ArrayAdapter adapter;
    private ActivityResultLauncher<Intent> launcherCriarCliente;
    List<Cliente> clientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = findViewById(R.id.list);

        criarOuAbrirBancoDeDados();

        listarClientes();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, clientes);

        list.setAdapter(adapter);

        /*  */
        launcherCriarCliente = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        String nomeCliente = data.getStringExtra("nome");
                        criarCliente(nomeCliente);
                        listarClientes();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Cliente adicionado com sucesso.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemSelecionado = parent.getItemAtPosition(position);

                if (itemSelecionado instanceof Cliente) {
                    Cliente cliente = (Cliente) itemSelecionado;

                    deleteCliente(cliente.getId());
                    listarClientes();
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
            Intent intent = new Intent(this, CreateClienteActivity.class);
            launcherCriarCliente.launch(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void listarClientes () {
        try {
            clientes.clear();
            bd = openOrCreateDatabase("clientes", MODE_PRIVATE, null);
            Cursor cursor = bd.rawQuery("SELECT * FROM clientes;", null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                System.out.println(id + "-" + nome);
                clientes.add(new Cliente(id, nome));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            bd.close();
        }
    }

    public void criarCliente (String nome) {
        try {
            bd = openOrCreateDatabase("clientes", MODE_PRIVATE, null);
            String sql = "INSERT INTO clientes (nome) VALUES (?);";
            SQLiteStatement stml = bd.compileStatement(sql);

            stml.bindString(1, nome);

            stml.executeInsert();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            bd.close();
        }
    }
    public void deleteCliente (Integer id) {
        try {
            bd = openOrCreateDatabase("clientes", MODE_PRIVATE, null);
            String sql = "DELETE FROM clientes WHERE id= ? ;";
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
            bd = openOrCreateDatabase("clientes", MODE_PRIVATE, null);

            bd.execSQL("CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome VARCHAR NOT NULL)");

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }
    }
}