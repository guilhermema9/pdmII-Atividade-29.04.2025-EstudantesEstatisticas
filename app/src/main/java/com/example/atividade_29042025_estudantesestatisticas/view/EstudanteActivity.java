package com.example.atividade_29042025_estudantesestatisticas.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.atividade_29042025_estudantesestatisticas.R;
import com.example.atividade_29042025_estudantesestatisticas.model.Estudante;
import com.example.atividade_29042025_estudantesestatisticas.service.CalculosEstudanteService;
import com.example.atividade_29042025_estudantesestatisticas.viewmodel.EstudanteViewModel;

public class EstudanteActivity extends AppCompatActivity {

    private Estudante estudante;
    private EstudanteViewModel viewModel;
    private int idEstudante;
    private TextView textViewNome, textViewIdade, textViewMedia, textViewPresenca, textViewSituacao;
    private Button buttonCadastraNota, buttonCadastraPresenca, buttonDeletarEstudante;
    private EditText editTextNota;
    private RadioGroup radioGroupPresenca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estudante);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewNome = findViewById(R.id.textViewNome);
        textViewIdade = findViewById(R.id.textViewIdade);
        textViewMedia = findViewById(R.id.textViewMedia);
        textViewPresenca = findViewById(R.id.textViewPresenca);
        textViewSituacao = findViewById(R.id.textViewSituacao);
        buttonCadastraNota = findViewById(R.id.buttonCadastrarNota);
        editTextNota = findViewById(R.id.editTextNota);
        buttonCadastraPresenca = findViewById(R.id.buttonCadastrarPresenca);
        radioGroupPresenca = findViewById(R.id.radioGroupPresenca);

        idEstudante = getIntent().getIntExtra("id",0);
        idEstudante++;

        viewModel = new ViewModelProvider(this).get(EstudanteViewModel.class);

        viewModel.getEstudante(idEstudante).observe(this, e -> {
            if (e != null ){
                estudante = e;
                textViewNome.setText(e.getNome());
                textViewIdade.setText(String.valueOf(e.getIdade()));
                textViewMedia.setText(CalculosEstudanteService.calculaMediaFinal(e));
                textViewPresenca.setText(CalculosEstudanteService.calculaPresenca(e));
                String situacao = CalculosEstudanteService.calculaSituacaoFinal(e);
                textViewSituacao.setText(situacao);
                if (situacao.equals("Aprovado")){
                    textViewSituacao.setTextColor(ContextCompat.getColor(this,R.color.green));
                } else {
                    textViewSituacao.setTextColor(ContextCompat.getColor(this,R.color.red));
                }
            }
        });

        buttonCadastraNota.setOnClickListener(v ->{
            Float nota = Float.valueOf(editTextNota.getText().toString());
            estudante.notas.add(nota);
            viewModel.atualizaEstudante(estudante).observe(EstudanteActivity.this, respostaHttp ->{
                if (respostaHttp){
                    Toast.makeText(EstudanteActivity.this, "Nota cadastradada com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EstudanteActivity.this, "Erro ao cadastrar nota", Toast.LENGTH_SHORT).show();
                }
            });
        });

        buttonCadastraPresenca.setOnClickListener(v -> {
            boolean presenca = false;
            if (radioGroupPresenca.getCheckedRadioButtonId() == R.id.radioButtonPresente){
                presenca = true;
                estudante.presenca.add(presenca);
            } else if (radioGroupPresenca.getCheckedRadioButtonId() == R.id.radioButtonAusente){
                presenca = false;
                estudante.presenca.add(presenca);
            } else {
                Toast.makeText(EstudanteActivity.this, "Selecione uma opção de presença", Toast.LENGTH_SHORT).show();
            }
            viewModel.atualizaEstudante(estudante).observe(EstudanteActivity.this, respostaHttp ->{
                if (respostaHttp){
                    Toast.makeText(EstudanteActivity.this, "Presença cadastradada com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EstudanteActivity.this, "Erro ao cadastrar presença", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}