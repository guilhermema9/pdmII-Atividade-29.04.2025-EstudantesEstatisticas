package com.example.atividade_29042025_estudantesestatisticas.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.atividade_29042025_estudantesestatisticas.R;
import com.example.atividade_29042025_estudantesestatisticas.model.Estudante;
import com.example.atividade_29042025_estudantesestatisticas.viewmodel.CadastraEstudanteViewModel;

public class CadastraEstudanteActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextIdade;
    private Button buttonCadastrar;
    private CadastraEstudanteViewModel viewModel;
    private Estudante estudante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastra_estudante);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNome = findViewById(R.id.editTextNome);
        editTextIdade = findViewById(R.id.editTextIdade);
        buttonCadastrar = findViewById(R.id.buttonSalvarEstudante);

        viewModel = new ViewModelProvider(this).get(CadastraEstudanteViewModel.class);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click Cadastro","Clicou 1");
                String nome = editTextNome.getText().toString();
                String idadeStr = editTextIdade.getText().toString();
                if (nome.isEmpty() || idadeStr.isEmpty()){
                    Toast.makeText(CadastraEstudanteActivity.this, "Preencha nome e idade do estudante", Toast.LENGTH_SHORT).show();
                    return;
                }
                int idade = Integer.parseInt(idadeStr);
                estudante = new Estudante(nome, idade);
                viewModel.cadastrarEstudante(estudante).observe(CadastraEstudanteActivity.this, respostaHttp ->{
                    if (respostaHttp){
                        Toast.makeText(CadastraEstudanteActivity.this, "Estudande cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CadastraEstudanteActivity.this, "Erro ao cadastrar estudante", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}