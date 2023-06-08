package com.example.app_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.idMenuConfiguracao) {
            Intent intent = new Intent(MainActivity.this, ConfiguracaoActivity.class);



            Toast.makeText(this, "Conf", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return true;
        } else if (id == R.id.idMenuClientes) {
            Toast.makeText(this, "Clientes", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.idMenuProdutos) {
            Toast.makeText(this, "Produtos", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.idMenuVendedores) {
            Toast.makeText(this, "Vendedores", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}