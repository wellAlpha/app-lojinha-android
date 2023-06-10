package com.example.app_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String selectedColor = sharedPreferences.getString("selectedColor", "white");


        getWindow().getDecorView().getRootView().setBackgroundColor(Color.parseColor(selectedColor));
    }

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
            startActivity(intent);
            return true;
        } else if (id == R.id.idMenuClientes) {
            Intent intent = new Intent(MainActivity.this, ClienteActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.idMenuProdutos) {
            Intent intent = new Intent(MainActivity.this, ProdutoActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.idMenuVendedores) {
            Intent intent = new Intent(MainActivity.this, VendedorActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}