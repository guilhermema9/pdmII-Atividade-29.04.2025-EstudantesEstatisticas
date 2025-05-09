package com.example.atividade_29042025_estudantesestatisticas.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.atividade_29042025_estudantesestatisticas.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ArrayAdapter<String> adapterEstudantes;
    private RecyclerView recyclerViewNome;
    private Button buttonEstatisticas, buttonCadastrarEstudante;
    private AlunosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewNome = findViewById(R.id.recyclerViewNome);
        buttonEstatisticas = findViewById(R.id.buttonEstatisticas);
        buttonCadastrarEstudante = findViewById(R.id.buttonCadastrarEstudante);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getEstudantes().observe(this, estudantes -> {
            if (estudantes != null && !estudantes.isEmpty()){
                List<String> listaEstudantes = new ArrayList<>();
                for (Estudante e : estudantes){
                    listaEstudantes.add(e.getNome());
                }
                recyclerViewNome.setLayoutManager(new LinearLayoutManager(this));
                adapter = new AlunosAdapter(listaEstudantes);

                adapter.setOnItemClickListener(new AlunosAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(MainActivity.this, EstudanteActivity.class);
                        intent.putExtra("id",position);
                        startActivity(intent);
                    }
                });
                recyclerViewNome.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Erro ao buscar dados da API", Toast.LENGTH_SHORT).show();
            }
        });

        buttonEstatisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EstatisticaActivity.class);
                startActivity(intent);
            }
        });

        buttonCadastrarEstudante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastraEstudanteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.recarregaDadosApi();
    }
}