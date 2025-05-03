package com.example.atividade_29042025_estudantesestatisticas.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atividade_29042025_estudantesestatisticas.R;
import com.example.atividade_29042025_estudantesestatisticas.adapter.AlunosAdapter;
import com.example.atividade_29042025_estudantesestatisticas.model.Estudante;
import com.example.atividade_29042025_estudantesestatisticas.service.CalculosEstudanteService;
import com.example.atividade_29042025_estudantesestatisticas.viewmodel.EstatisticasViewModel;

import java.util.ArrayList;
import java.util.List;

public class EstatisticaActivity extends AppCompatActivity {

    private EstatisticasViewModel estatisticasViewModel;
    private TextView textViewMediaGeral, textViewAlunoMaiorNota, textViewAlunoMenorNota, textViewMediaIdadeTurma;
    private RecyclerView recyclerViewAlunosAprovados, recyclerViewAlunosReprovados;
    private AlunosAdapter adapterAprovados, adapterReprovados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estatistica);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewMediaGeral = findViewById(R.id.textViewMediaGeral);
        textViewAlunoMaiorNota = findViewById(R.id.textViewAlunoMaiorNota);
        textViewAlunoMenorNota = findViewById(R.id.textViewAlunoMenorNota);
        textViewMediaIdadeTurma = findViewById(R.id.textViewMediaIdadeTurma);
        recyclerViewAlunosAprovados = findViewById(R.id.recyclerViewAlunosAprovados);
        recyclerViewAlunosReprovados = findViewById(R.id.recyclerViewAlunosReprovados);

        estatisticasViewModel = new ViewModelProvider(this).get(EstatisticasViewModel.class);
        estatisticasViewModel.getEstudantes().observe(this, estudantes -> {
            if (estudantes != null && !estudantes.isEmpty()){
                textViewMediaGeral.setText(CalculosEstudanteService.calculaMediaGeralTurma(estudantes));
                textViewAlunoMaiorNota.setText(CalculosEstudanteService.alunoMaiorNota(estudantes));
                textViewAlunoMenorNota.setText(CalculosEstudanteService.alunoMenorNota(estudantes));
                textViewMediaIdadeTurma.setText(CalculosEstudanteService.calculaMediaIdadeTurma(estudantes));
                List<String> listaEstudantesAprovados = CalculosEstudanteService.alunosAprovados(estudantes);
                List<String> listaEstudantesReprovados = CalculosEstudanteService.alunosReprovados(estudantes);

                recyclerViewAlunosAprovados.setLayoutManager(new LinearLayoutManager(this));
                adapterAprovados = new AlunosAdapter(listaEstudantesAprovados);
                recyclerViewAlunosAprovados.setAdapter(adapterAprovados);

                recyclerViewAlunosReprovados.setLayoutManager(new LinearLayoutManager(this));
                adapterReprovados = new AlunosAdapter(listaEstudantesReprovados);
                recyclerViewAlunosReprovados.setAdapter(adapterReprovados);
            } else {
                Toast.makeText(this, "Erro ao buscar dados da API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}