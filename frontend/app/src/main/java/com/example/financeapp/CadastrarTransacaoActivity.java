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

public class CadastrarTransacaoActivity extends AppCompatActivity {

    EditText editDescricao, editValor, editData;
    RadioGroup radioGroupTipo;
    Spinner spinnerCategoria;
    Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao_cadastrar);

        editDescricao = findViewById(R.id.editDescricao);
        editValor = findViewById(R.id.editValor);
        editData = findViewById(R.id.editData);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        String[] categorias = {"Contas", "Lazer", "Comida", "Transporte", "Salário", "Outros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategoria.setAdapter(adapter);

        editData.setOnClickListener(v -> {
            Calendar calendario = Calendar.getInstance();
            int ano = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(CadastrarTransacaoActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        String dataSelecionada = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                        editData.setText(dataSelecionada);
                    }, ano, mes, dia);
            dialog.show();
        });

        buttonSalvar.setOnClickListener(v -> {
            String descricao = editDescricao.getText().toString();
            String valor = editValor.getText().toString();
            String dataOriginal = editData.getText().toString();
            String categoriaSelecionada = spinnerCategoria.getSelectedItem().toString();

            String dataConvertida = "";
            try {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd");
                Date data = formatoEntrada.parse(dataOriginal);
                dataConvertida = formatoSaida.format(data);
            } catch (Exception e) {
                Toast.makeText(CadastrarTransacaoActivity.this, "Data inválida!", Toast.LENGTH_SHORT).show();
                return;
            }

            double valorNumerico;
            try {
                valorNumerico = Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                Toast.makeText(CadastrarTransacaoActivity.this, "Valor inválido!", Toast.LENGTH_SHORT).show();
                return;
            }

            String tipo = radioGroupTipo.getCheckedRadioButtonId() == R.id.radioEntrada ? "INCOME" : "EXPENSE";
            String categoria = mapearCategoria(categoriaSelecionada);

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("description", descricao);
                jsonBody.put("amount", valorNumerico);
                jsonBody.put("date", dataConvertida);
                jsonBody.put("type", tipo);
                jsonBody.put("category", categoria);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int idUsuario = IdManager.getId();
            if (idUsuario != -1) {
                RequestQueue queue = Volley.newRequestQueue(CadastrarTransacaoActivity.this);

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        BASE_URL + "api/transactions/user/" + idUsuario,
                        jsonBody,
                        response -> {
                            try {
                                String sucesso = response.getString("id");
                                if (sucesso != null) {
                                    Toast.makeText(CadastrarTransacaoActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(CadastrarTransacaoActivity.this, "Erro no formato de resposta", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> Toast.makeText(CadastrarTransacaoActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show()
                );

                queue.add(request);
            } else {
                Toast.makeText(CadastrarTransacaoActivity.this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show();
            }
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
