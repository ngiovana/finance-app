package com.example.financeapp;

import static com.example.financeapp.Constantes.BASE_URL;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class TransacaoActivity extends AppCompatActivity {
    EditText editDescricao, editValor, editData;
    RadioGroup radioGroupTipo;
    Spinner spinnerCategoria;
    Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);

        editDescricao = findViewById(R.id.editDescricao);
        editValor = findViewById(R.id.editValor);
        editData = findViewById(R.id.editData);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        String[] categorias = {"Contas", "Lazer", "Comida", "Transporte", "Salário", "Outros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategoria.setAdapter(adapter);

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int ano = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                String token = TokenManager.getToken();


                DatePickerDialog dialog = new DatePickerDialog(TransacaoActivity.this,
                        (view, year, month, dayOfMonth) -> {
                            String dataSelecionada = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                            editData.setText(dataSelecionada);
                        }, ano, mes, dia);
                dialog.show();
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = editDescricao.getText().toString();
                String valor = editValor.getText().toString();
                String data = editData.getText().toString();
                String tipo = radioGroupTipo.getCheckedRadioButtonId() == R.id.radioEntrada ? "Entrada" : "Saída";
                String categoria = spinnerCategoria.getSelectedItem().toString();

                Toast.makeText(TransacaoActivity.this,
                        "Descrição: " + descricao + "\nValor: " + valor +
                                "\nData: " + data + "\nTipo: " + tipo + "\nCategoria: " + categoria,
                        Toast.LENGTH_LONG).show();

                RequestQueue queue = Volley.newRequestQueue(TransacaoActivity.this);

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("description", descricao );
                    jsonBody.put("amount", valor);
                    jsonBody.put("date", data );
                    jsonBody.put("type", tipo );
                    jsonBody.put("category", categoria );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        BASE_URL + "api/auth/register",
                        jsonBody,
                        response -> {
                            try {
                                String sucesso = response.getString("username");
                                if (sucesso!= null) {
                                    Toast.makeText(TransacaoActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(TransacaoActivity.this, "Erro no formato de resposta", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> Toast.makeText(TransacaoActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show()
                );
                queue.add(request);

            }
            
        });
    }
}


