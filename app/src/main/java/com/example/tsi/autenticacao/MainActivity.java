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
    // Atributo que é referência para o gerenciador de usuários:
    private FirebaseAuth usuarios = FirebaseAuth.getInstance();

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

        // O usuário logado "fica" entre execuções do app no celular.
        // Então, logo de cara vamos ver se tem alguém logado.
        // Se tiver, indicamos isso em lblEstado:
        if (usuarios.getCurrentUser() != null) {
            lblEstado.setText(
                "Tem um usuário logado no sistema: "
                + usuarios.getCurrentUser().getEmail()
            );
        }
    }


    // Classe interna do escutador do botão NOVO CADASTRO:
    private class EscutadorBotaoNovo implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Aviso de início de processo:
            lblEstado.setText("Tentando criar novo usuário...");
            // Verifica se o usuário já está logado:
            if (usuarios.getCurrentUser() != null) {
                // Exibe mensagem de usuário já logado, em lblEstado:
                lblEstado.setText("Já tem um usuário logado!!" + usuarios.getCurrentUser().getEmail());
            } else {
                // Pega email e senha na interface:
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                // Tenta criar o usuário:
                // ATENCAO!!!
                // O teste se foi sucesso ou não é ASSINCRONO,
                // isto é, pode levar um tempo para o resultado voltar do Firebase.
                // Não pense neste processo como uma programação tradicional!!!
                usuarios.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Testa se criou o usuário com sucesso:
                            if (task.isSuccessful()) {
                                // Criou e logou com sucesso.
                                // Exibe mensagem em lblEstado:
                                lblEstado.setText("Usuário criado e logado com sucesso!" + usuarios.getCurrentUser().getEmail());
                            } else {
                                // Não conseguiu criar o usuário.
                                // Exibe mensagem em lblEstado:
                                lblEstado.setText("Criação do usuário falhou!");
                                // Exibe a mensagem de erro do Firebase num Toast:
                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                Toast.makeText(MainActivity.this, "ERRO: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        }
    }

    // Classe interna do escutador do botão ENTRAR:
    private class EscutadorBotaoEntrar implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Aviso de início de processo:
            lblEstado.setText("Tentando logar no Firebase...");
            // Verifica se o usuário já está logado:
            if (usuarios.getCurrentUser() != null) {
                // Exibe mensagem de usuário já logado, em lblEstado:
                lblEstado.setText("Já tem um usuário logado!!" + usuarios.getCurrentUser().getEmail());
            } else {
                // Pega email e senha na interface:
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                // Tenta logar:
                usuarios.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Testa se logou com sucesso:
                            if (task.isSuccessful()) {
                                // Exibe mensagem em lblEstado:
                                lblEstado.setText("Usuário logado com sucesso!" + usuarios.getCurrentUser().getEmail());
                            } else {
                                // Exibe mensagem em lblEstado:
                                lblEstado.setText("Login falhou!");
                                // Exibe a mensagem de erro do Firebase num Toast:
                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                Toast.makeText(MainActivity.this, "ERRO: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        }
    }

    // Classe interna do escutador do botão SAIR:
    private class EscutadorBotaoSair implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Aviso de início de processo:
            lblEstado.setText("Tentando deslogar no Firebase...");
            // Verifica se existe um usuário logado:
            if (usuarios.getCurrentUser() == null) {
                // Exibe mensagem que não tem usuário logado, em lblEstado:
                lblEstado.setText("Não tem um usuário logado!!");
            } else {
                // Deslogando...
                usuarios.signOut();
                // Exibe mensagem de usuário deslogado:
                lblEstado.setText("Usuário deslogado!!");
            }
        }
    }
}