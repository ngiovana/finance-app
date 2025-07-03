package com.example.financeapp;

import static com.example.financeapp.Constantes.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class CadastroUserActivity extends AppCompatActivity {

    EditText editTextNome, editTextEmail, editTextSenha;
    Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_user);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNome.getText().toString();
                String email = editTextEmail.getText().toString();
                String senha = editTextSenha.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(CadastroUserActivity.this);

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("username", nome);
                    jsonBody.put("email", email);
                    jsonBody.put("password", senha);
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
                                    Toast.makeText(CadastroUserActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(CadastroUserActivity.this, "Erro no formato de resposta", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> Toast.makeText(CadastroUserActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show()
                );
                queue.add(request);

            }

        });

    }
}
