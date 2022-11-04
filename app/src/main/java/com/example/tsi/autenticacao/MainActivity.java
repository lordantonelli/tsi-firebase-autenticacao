package com.example.tsi.autenticacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity {

    // Atributos relativos aos objetos da interface:
    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnEntrar;
    private Button btnSair;
    private TextView lblEstado;
    private Button btnNovo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Ligando atributos com os objetos da interface:
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnSair = findViewById(R.id.btnSair);
        lblEstado = findViewById(R.id.lblEstado);
        btnNovo = findViewById(R.id.btnNovo);
        // Cria e associa o escutador do botão ENTRAR:
        btnEntrar.setOnClickListener(new EscutadorBotaoEntrar());
        // Cria e associa o escutador do botão NOVO CADASTRO:
        btnNovo.setOnClickListener(new EscutadorBotaoNovo());
        // Cria e associa o escutador do botão SAIR:
        btnSair.setOnClickListener(new EscutadorBotaoSair());


    }


    // Classe interna do escutador do botão NOVO CADASTRO:
    private class EscutadorBotaoNovo implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Aviso de início de processo:
            lblEstado.setText("Tentando criar novo usuário...");


        }
    }

    // Classe interna do escutador do botão ENTRAR:
    private class EscutadorBotaoEntrar implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Aviso de início de processo:
            lblEstado.setText("Tentando logar no Firebase...");


        }
    }

    // Classe interna do escutador do botão SAIR:
    private class EscutadorBotaoSair implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Aviso de início de processo:
            lblEstado.setText("Tentando deslogar no Firebase...");


        }
    }
}