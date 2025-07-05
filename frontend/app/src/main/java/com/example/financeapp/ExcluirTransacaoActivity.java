package com.example.financeapp;

import static com.example.financeapp.Constantes.BASE_URL;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ExcluirTransacaoActivity extends AppCompatActivity {
    EditText editTextIdTransacao;
    Button buttonConfirmarExclusao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_transacao);

        editTextIdTransacao = findViewById(R.id.editTextNomeTransacao);
        buttonConfirmarExclusao = findViewById(R.id.buttonConfirmarExclusao);

        buttonConfirmarExclusao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextIdTransacao.getText().toString().trim();

                if (id.isEmpty()) {
                    Toast.makeText(ExcluirTransacaoActivity.this, "Digite o ID da transação.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new AlertDialog.Builder(ExcluirTransacaoActivity.this)
                        .setTitle("Confirmar Exclusão")
                        .setMessage("Tem certeza que deseja excluir esta transação?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RequestQueue queue = Volley.newRequestQueue(ExcluirTransacaoActivity.this);
                                String url = BASE_URL + "api/transactions/" + id;

                                StringRequest request = new StringRequest(
                                        Request.Method.DELETE,
                                        url,
                                        response -> {
                                            Toast.makeText(ExcluirTransacaoActivity.this, "Transação excluída com sucesso!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        },
                                        error -> {
                                            Toast.makeText(ExcluirTransacaoActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                );

                                queue.add(request);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });
    }
}
