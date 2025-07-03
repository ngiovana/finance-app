package com.example.financeapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ExcluirTransacaoActivity extends AppCompatActivity {
    EditText editTextNomeTransacao;
    Button buttonConfirmarExclusao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_transacao);

        editTextNomeTransacao = findViewById(R.id.editTextNomeTransacao);
        buttonConfirmarExclusao = findViewById(R.id.buttonConfirmarExclusao);

        buttonConfirmarExclusao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeTransacao = editTextNomeTransacao.getText().toString().trim();

                if (nomeTransacao.isEmpty()) {
                    Toast.makeText(ExcluirTransacaoActivity.this, "Digite o nome da transação.", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(ExcluirTransacaoActivity.this)
                        .setTitle("Confirmar Exclusão")
                        .setMessage("Tem certeza que deseja excluir a transação \"" + nomeTransacao + "\"?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ExcluirTransacaoActivity.this, "Transação \"" + nomeTransacao + "\" excluída.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });
    }
}
