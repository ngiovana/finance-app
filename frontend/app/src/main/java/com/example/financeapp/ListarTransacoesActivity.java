package com.example.financeapp;

import static com.example.financeapp.Constantes.BASE_URL;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListarTransacoesActivity extends AppCompatActivity {

    ScrollView scrollTabela;
    TableLayout tabelaTransacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_transacoes);

        scrollTabela = findViewById(R.id.scrollTabela);
        tabelaTransacoes = findViewById(R.id.tabelaTransacoes);

        int idUsuario = IdManager.getId();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL + "api/transactions/user/" + idUsuario,
                null,
                response -> popularTabela(response),
                error -> Toast.makeText(this, "Erro ao buscar transações: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    private void popularTabela(JSONArray transacoesJson) {
        try {

            tabelaTransacoes.removeViews(1, tabelaTransacoes.getChildCount() - 1);

            for (int i = 0; i < transacoesJson.length(); i++) {
                JSONObject transacao = transacoesJson.getJSONObject(i);

                String id = String.valueOf(transacao.getInt("id"));
                String descricao = transacao.getString("description");
                String valor = String.valueOf(transacao.getDouble("amount"));
                String data = transacao.getString("date");

                TableRow linha = new TableRow(this);

                adicionarCelula(linha, id);
                adicionarCelula(linha, descricao);
                adicionarCelula(linha, valor);
                adicionarCelula(linha, data);

                tabelaTransacoes.addView(linha);
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Erro ao interpretar resposta JSON", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void adicionarCelula(TableRow linha, String texto) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setPadding(16, 8, 16, 8);
        linha.addView(tv);
    }
}
