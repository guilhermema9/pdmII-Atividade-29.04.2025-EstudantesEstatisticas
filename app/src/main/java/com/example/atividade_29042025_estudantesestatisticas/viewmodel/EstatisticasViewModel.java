package com.example.atividade_29042025_estudantesestatisticas.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atividade_29042025_estudantesestatisticas.model.Estudante;
import com.example.atividade_29042025_estudantesestatisticas.util.Conexao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EstatisticasViewModel extends ViewModel {

    private static final String URL = "https://10.0.2.2:8080/estudantes/";
    private MutableLiveData<List<Estudante>> estudantesListLiveData;
    private ExecutorService executorService;

    public EstatisticasViewModel() {
        estudantesListLiveData = new MutableLiveData<>();
        carregarEstudantesApi();
    }

    private void carregarEstudantesApi(){
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Conexao conexao = new Conexao();
            InputStream inputStream = conexao.obterRespostaHttp(URL);
            String textoJson = conexao.converter(inputStream);
            if (textoJson != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Estudante>>(){}.getType();
                List<Estudante> listaEstudantesGson = gson.fromJson(textoJson, type);

                List<Estudante> listaEstudantesCompleta = new ArrayList<>();
                for (Estudante estudante : listaEstudantesGson){

                        String urlCompleta = URL + estudante.getId();
                        InputStream inputStreamCompleta = conexao.obterRespostaHttp(urlCompleta);
                        String textoJsonCompleto = conexao.converter(inputStreamCompleta);
                        if (textoJsonCompleto != null){
                            Estudante estudanteCompleto = gson.fromJson(textoJsonCompleto, Estudante.class);
                            listaEstudantesCompleta.add(estudanteCompleto);
                        }

                }
                estudantesListLiveData.postValue(listaEstudantesCompleta);
            } else {
                Log.e("Erro","Erro JSON");
            }
        });
    }

    public LiveData<List<Estudante>> getEstudantes(){
        return estudantesListLiveData;
    }
}