package com.example.financeapp;

import static com.example.financeapp.Constantes.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editTextEmail, editTextSenha;
    Button buttonLogin;
    TextView textViewCadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TokenManager.init(getApplicationContext());

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewCadastro = findViewById(R.id.textViewCadastro);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String senha = editTextSenha.getText().toString().trim();

                if (email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    fazerLogin(email, senha);
                }
            }
        });

        textViewCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });
    }

    private void fazerLogin(String email, String senha) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", senha);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+"api/auth/login",
                jsonBody,
                response -> {
                    try {
                        String sucesso = response.getString("token");
                        if (sucesso!= null) {

                            TokenManager.salvarToken(sucesso);

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Login inválido!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Erro no formato de resposta", Toast.LENGTH_SHORT).show();
                    }
                },

                error -> {
                    String mensagemErro;
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        mensagemErro = new String(error.networkResponse.data);
                    } else {
                        mensagemErro = error.toString();
                    }
                    Toast.makeText(MainActivity.this, "Erro: " + mensagemErro, Toast.LENGTH_LONG).show();
                }
                 );

        queue.add(request);
    }
}
