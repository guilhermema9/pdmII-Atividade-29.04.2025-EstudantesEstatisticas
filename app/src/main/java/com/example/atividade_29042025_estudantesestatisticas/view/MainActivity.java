package com.example.atividade_29042025_estudantesestatisticas.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.atividade_29042025_estudantesestatisticas.R;
import com.example.atividade_29042025_estudantesestatisticas.model.Estudante;
import com.example.atividade_29042025_estudantesestatisticas.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ArrayAdapter<String> adapterEstudantes;
    private ListView listViewNome;
    private Button buttonEstatisticas;

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

        listViewNome = findViewById(R.id.listViewNome);
        buttonEstatisticas = findViewById(R.id.buttonEstatisticas);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getEstudantes().observe(this, estudantes -> {
            if (estudantes != null && !estudantes.isEmpty()){
                List<String> listaEstudantes = new ArrayList<>();
                for (Estudante e : estudantes){
                    listaEstudantes.add(e.getNome());
                }
                Log.i("IF", viewModel.getEstudantes().toString());
                adapterEstudantes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listaEstudantes);
                listViewNome.setAdapter(adapterEstudantes);
            } else {
                Log.i("ELSE", viewModel.getEstudantes().toString());
            }
        });

        listViewNome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idEstudante = position;
                Intent intent = new Intent(MainActivity.this, EstudanteActivity.class);
                intent.putExtra("id",idEstudante);
                startActivity(intent);
            }
        });

        buttonEstatisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EstatisticaActivity.class);
                startActivity(intent);
            }
        });
    }
}