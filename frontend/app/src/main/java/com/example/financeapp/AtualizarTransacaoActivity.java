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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AtualizarTransacaoActivity extends AppCompatActivity {

    EditText editIdTransacao, editDescricao, editValor, editData;
    RadioGroup radioGroupTipo;
    Spinner spinnerCategoria;
    Button buttonAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_transacao);

        editIdTransacao = findViewById(R.id.editIdTransacao);
        editDescricao = findViewById(R.id.editDescricao);
        editValor = findViewById(R.id.editValor);
        editData = findViewById(R.id.editData);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        buttonAtualizar = findViewById(R.id.buttonAtualizar);

        String[] categorias = {"Contas", "Lazer", "Comida", "Transporte", "Salário", "Outros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategoria.setAdapter(adapter);

        editData.setOnClickListener(v -> {
            Calendar calendario = Calendar.getInstance();
            int ano = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(AtualizarTransacaoActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        String dataSelecionada = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                        editData.setText(dataSelecionada);
                    }, ano, mes, dia);
            dialog.show();
        });

        buttonAtualizar.setOnClickListener(v -> {
            String idStr = editIdTransacao.getText().toString().trim();
            String descricao = editDescricao.getText().toString();
            String valorStr = editValor.getText().toString();
            String dataOriginal = editData.getText().toString();
            String categoriaSelecionada = spinnerCategoria.getSelectedItem().toString();

            if (idStr.isEmpty()) {
                Toast.makeText(this, "Informe o ID da transação.", Toast.LENGTH_SHORT).show();
                return;
            }

            int idTransacao = Integer.parseInt(idStr);
            int idUsuario = IdManager.getId();

            double valor;
            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido!", Toast.LENGTH_SHORT).show();
                return;
            }

            String dataConvertida;
            try {
                SimpleDateFormat entrada = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat saida = new SimpleDateFormat("yyyy-MM-dd");
                Date data = entrada.parse(dataOriginal);
                dataConvertida = saida.format(data);
            } catch (Exception e) {
                Toast.makeText(this, "Data inválida!", Toast.LENGTH_SHORT).show();
                return;
            }

            String tipo = radioGroupTipo.getCheckedRadioButtonId() == R.id.radioEntrada ? "INCOME" : "EXPENSE";
            String categoria = mapearCategoria(categoriaSelecionada);

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("amount", valor);
                jsonBody.put("type", tipo);
                jsonBody.put("category", categoria);
                jsonBody.put("description", descricao);
                jsonBody.put("date", dataConvertida);
                jsonBody.put("userId", idUsuario);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = BASE_URL + "api/transactions/" + idTransacao;

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    jsonBody,
                    response -> {
                        Toast.makeText(this, "Transação atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    },
                    error -> {
                        Toast.makeText(this, "Erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            );

            queue.add(request);
        });
    }

    private String mapearCategoria(String categoriaPt) {
        switch (categoriaPt) {
            case "Contas":
                return "BILLS";
            case "Lazer":
                return "LEISURE";
            case "Salário":
                return "SALARY";
            case "Transporte":
                return "TRANSPORTATION";
            case "Comida":
                return "FOOD";
            default:
                return "OTHERS";
        }
    }
}
