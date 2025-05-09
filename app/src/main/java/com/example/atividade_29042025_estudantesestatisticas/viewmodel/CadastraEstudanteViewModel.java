package com.example.atividade_29042025_estudantesestatisticas.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atividade_29042025_estudantesestatisticas.model.Estudante;
import com.example.atividade_29042025_estudantesestatisticas.util.Conexao;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CadastraEstudanteViewModel extends ViewModel {

    private static final String URL = "https://10.0.2.2:8080/estudantes/";
    private MutableLiveData<Boolean> mutableLiveData;
    private ExecutorService executorService;

    public CadastraEstudanteViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public LiveData<Boolean> cadastrarEstudante(Estudante estudante){
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Conexao conexao = new Conexao();
                Gson gson = new Gson();
                String json = gson.toJson(estudante);
                int codigoRespostaHttp = conexao.enviaDadosPost(URL,json);
                Log.i("View Model", "Resposta HTTP = " + codigoRespostaHttp);
                if (codigoRespostaHttp >= 200 && codigoRespostaHttp < 300){
                    mutableLiveData.postValue(true);
                }
            }
        });
        return mutableLiveData;
    }
}