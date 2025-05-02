package com.example.atividade_29042025_estudantesestatisticas.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.atividade_29042025_estudantesestatisticas.R;
import com.example.atividade_29042025_estudantesestatisticas.service.CalculosEstudanteService;
import com.example.atividade_29042025_estudantesestatisticas.viewmodel.EstudanteViewModel;

import java.util.List;

public class EstudanteActivity extends AppCompatActivity {

    public EstudanteViewModel estudanteViewModel;
    private int idEstudante;
    private TextView textViewNome, textViewIdade, textViewMedia, textViewPresenca, textViewSituacao;


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

        idEstudante = getIntent().getIntExtra("id",idEstudante);
        idEstudante++; // itera para corrigir a posição da lista com o id do respectivo estudante

        estudanteViewModel = new ViewModelProvider(this).get(EstudanteViewModel.class);

        estudanteViewModel.getEstudante(idEstudante).observe(this, estudante -> {
            if (estudante != null ){
                textViewNome.setText(estudante.getNome());
                textViewIdade.setText(String.valueOf(estudante.getIdade()));
                textViewMedia.setText(CalculosEstudanteService.calculaMediaFinal(estudante));
                textViewPresenca.setText(CalculosEstudanteService.calculaPresenca(estudante));
                String situacao = CalculosEstudanteService.calculaSituacaoFinal(estudante);
                textViewSituacao.setText(situacao);
                if (situacao.equals("Aprovado")){
                    textViewSituacao.setTextColor(ContextCompat.getColor(this,R.color.green));
                } else {
                    textViewSituacao.setTextColor(ContextCompat.getColor(this,R.color.red));
                }

            }
        });


    }
}