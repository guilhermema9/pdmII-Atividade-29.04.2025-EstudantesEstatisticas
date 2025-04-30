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

public class EstudanteViewModel extends ViewModel {

    private String URL = "https://10.0.2.2:8080/estudantes/";
    private MutableLiveData<Estudante> estudanteLiveData;
    private ExecutorService executorService;

    public EstudanteViewModel() {
        estudanteLiveData = new MutableLiveData<>();
    }

    public LiveData<Estudante> getEstudante(int id){
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Conexao conexao = new Conexao();
                String URLEstudante = URL + id;
                InputStream inputStream = conexao.obterRespostaHttp(URLEstudante);
                String textoJson = conexao.converter(inputStream);
                if (textoJson!=null){
                    Gson gson = new Gson();
                    Type type = new TypeToken<Estudante>(){}.getType();
                    Estudante estudanteGson = gson.fromJson(textoJson, type);
                    estudanteLiveData.postValue(estudanteGson);
                } else {
                    Log.e("Erro","Erro JSON");
                }
            }
        });
        return estudanteLiveData;
    }
}
