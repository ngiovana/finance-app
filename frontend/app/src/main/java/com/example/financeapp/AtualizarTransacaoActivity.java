package com.example.financeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AtualizarTransacaoActivity extends AppCompatActivity {

    EditText editNomeAntigo, editNovoNome, editNovoValor, editNovaData;
    Button buttonAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_transacao);

        editNomeAntigo = findViewById(R.id.editNomeAntigo);
        editNovoNome = findViewById(R.id.editNovoNome);
        editNovoValor = findViewById(R.id.editNovoValor);
        editNovaData = findViewById(R.id.editNovaData);
        buttonAtualizar = findViewById(R.id.buttonAtualizar);

        buttonAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeAntigo = editNomeAntigo.getText().toString().trim();
                String novoNome = editNovoNome.getText().toString().trim();
                String novoValor = editNovoValor.getText().toString().trim();
                String novaData = editNovaData.getText().toString().trim();

                if (nomeAntigo.isEmpty() || novoNome.isEmpty() || novoValor.isEmpty() || novaData.isEmpty()) {
                    Toast.makeText(AtualizarTransacaoActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue queue = Volley.newRequestQueue(AtualizarTransacaoActivity.this);

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("username", nomeAntigo);
                    jsonBody.put("email", novoNome );
                    jsonBody.put("password", novoValor);
                    jsonBody.put("password", novaData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(AtualizarTransacaoActivity.this, "Transação atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
