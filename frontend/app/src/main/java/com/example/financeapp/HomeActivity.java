package com.example.financeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    Button buttonCadastrar, buttonListar;
    ScrollView scrollTabela;
    TableLayout tabelaTransacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Drawer setup
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        TextView textBemVindo = headerView.findViewById(R.id.textBemVindo);
        textBemVindo.setText("Bem-vindo!");

        buttonCadastrar = findViewById(R.id.buttonCadastrarTransacao);
        buttonListar = findViewById(R.id.buttonListarTransacoes);
        scrollTabela = findViewById(R.id.scrollTabela);
        tabelaTransacoes = findViewById(R.id.tabelaTransacoes);

        scrollTabela.setVisibility(View.GONE);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TransacaoActivity.class);
                startActivity(intent);
            }
        });

        buttonListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollTabela.setVisibility(View.VISIBLE);
                tabelaTransacoes.removeViews(1, tabelaTransacoes.getChildCount() - 1); // Mantém cabeçalho

                String[][] transacoes = {
                        {"Aluguel", "1200", "01/06/2025"},
                        {"Supermercado", "300", "05/06/2025"},
                        {"Cinema", "60", "10/06/2025"},
                        {"Salário", "3500", "01/06/2025"}
                };

                for (String[] transacao : transacoes) {
                    TableRow linha = new TableRow(HomeActivity.this);
                    for (String campo : transacao) {
                        TextView tv = new TextView(HomeActivity.this);
                        tv.setText(campo);
                        tv.setPadding(16, 8, 16, 8);
                        linha.addView(tv);
                    }
                    tabelaTransacoes.addView(linha);
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_logout) {
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
