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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {

    private static final String URL = "https://10.0.2.2:8080/estudantes/";
    private MutableLiveData<List<Estudante>> estudantesListLiveData;
    private ExecutorService executorService;

    public MainViewModel() {
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
                estudantesListLiveData.postValue(listaEstudantesGson);
            } else {
                Log.e("Erro","Erro JSON");
            }
        });
    }

    public LiveData<List<Estudante>> getEstudantes(){
        return estudantesListLiveData;
    }

    public void recarregaDadosApi(){
        carregarEstudantesApi();
    }
}
