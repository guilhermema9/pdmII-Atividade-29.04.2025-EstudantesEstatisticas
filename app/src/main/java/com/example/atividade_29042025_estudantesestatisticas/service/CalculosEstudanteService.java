package com.example.atividade_29042025_estudantesestatisticas.service;

import android.util.Log;

import com.example.atividade_29042025_estudantesestatisticas.model.Estudante;

import java.util.ArrayList;
import java.util.List;

public class CalculosEstudanteService {

    public static List<String> preencheListaNotasEstudante(Estudante estudante) {
        List<String> notas = new ArrayList<>();
        for (int i=0 ; i<estudante.getNotas().size() ; i++){
            notas.add(String.valueOf(estudante.getNotas().get(i)));
        }
        return notas;
    }

    public static String calculaPresenca(Estudante estudante) {
        double qtdePresenca=0, porcentPresenca=0;
        for (int i = 0; i < estudante.getPresenca().size(); i++) {
            if (estudante.getPresenca().get(i)){
                qtdePresenca++;
            }
        }
        porcentPresenca = (qtdePresenca / estudante.getPresenca().size()) * 100;
        return String.format("%.0f",porcentPresenca);
    }

    public static String calculaMediaFinal(Estudante estudante){
        float mediaFinal = 0, somaNotas = 0;
        for (int i=0 ; i < estudante.getNotas().size() ; i++){
            somaNotas = somaNotas + estudante.getNotas().get(i);
        }
        mediaFinal = somaNotas / estudante.getNotas().size();
        return String.format("%.2f",mediaFinal);
    }

    public static String calculaSituacaoFinal(Estudante estudante){
        float mediaFinal = Float.parseFloat(CalculosEstudanteService.calculaMediaFinal(estudante));
        float percentPresenca = Float.parseFloat(CalculosEstudanteService.calculaPresenca(estudante));
        if (mediaFinal >= 7 && percentPresenca >= 75){
            return "Aprovado";
        } else {
            return "Reprovado";
        }
    }

    // Estatisticas Activity

    public static float calculaMediaGeralTurma(List<Estudante> estudantes){
        float mediaGeral = 0 ;
        int qtdeNotas = 0;
        for (Estudante estudante : estudantes) {
            for (int i=0 ; i<estudante.getNotas().size() ; i++){
                mediaGeral = mediaGeral + estudante.getNotas().get(i);
                qtdeNotas++;
            }
        }
        return mediaGeral / qtdeNotas;
    }

    public static String alunoMaiorNota(List<Estudante> estudantes){
        String alunoMaiorNota = null;
        float maiorNota = 0;
        Log.i("Lista EStudantes Service",String.valueOf(estudantes));
        for (Estudante estudante : estudantes){
            float mediaFinal = 0, somaNotas = 0;
            for (int i=0 ; i<estudante.getNotas().size() ; i++){
                somaNotas = somaNotas + estudante.getNotas().get(i);
            }
            mediaFinal = somaNotas / estudante.getNotas().size();
            if (mediaFinal > maiorNota){
                maiorNota = mediaFinal;
                alunoMaiorNota = estudante.getNome();
            }
        }
        return alunoMaiorNota;
    }

    public static String alunoMenorNota(List<Estudante> estudantes){
        String alunoMenorNota = null;
        float menorNota = 10;
        for (Estudante estudante : estudantes){
            float mediaFinal=0, somaNotas=0;
            for (int i = 0; i < estudante.getNotas().size(); i++) {
                somaNotas = somaNotas + estudante.getNotas().get(i);
            }
            mediaFinal = somaNotas / estudante.getNotas().size();
            if (mediaFinal<menorNota){
                menorNota = mediaFinal;
                alunoMenorNota = estudante.getNome();
            }
        }
        return alunoMenorNota;
    }

    public static float calculaMediaIdadeTurma(List<Estudante> estudantes){
        float somaIdade=0;
        for (Estudante estudante : estudantes) {
            somaIdade = somaIdade + estudante.getIdade();
        }
        return somaIdade / estudantes.size();
    }

    public static List<String> alunosAprovados(List<Estudante> estudantes){
        List<String> alunosAprovados = new ArrayList<>();
        for (Estudante estudante : estudantes){
            float mediaFinal=0, somaNotas=0;
            for (int i = 0; i < estudante.getNotas().size(); i++) {
                somaNotas = somaNotas + estudante.getNotas().get(i);
            }
            mediaFinal = somaNotas / estudante.getNotas().size();
            float presenca=0;
            for (int i=0 ; i < estudante.getPresenca().size() ; i++){
                if (estudante.getPresenca().get(i) == true){
                    presenca++;
                }
            }
            float percentualPresenca=0;
            percentualPresenca = (presenca / estudante.getPresenca().size()) * 100;
            if (mediaFinal >= 7 && percentualPresenca >= 75){
                alunosAprovados.add(estudante.getNome());
            }
        }
        return alunosAprovados;
    }

    public static List<String> alunosReprovados(List<Estudante> estudantes){
        List<String> alunosReprovados = new ArrayList<>();
        for (Estudante estudante : estudantes){
            float mediaFinal=0, somaNotas=0;
            for (int i = 0; i < estudante.getNotas().size(); i++) {
                somaNotas = somaNotas + estudante.getNotas().get(i);
            }
            mediaFinal = somaNotas / estudante.getNotas().size();
            float presenca=0;
            for (int i=0 ; i < estudante.getPresenca().size() ; i++){
                if (estudante.getPresenca().get(i) == true){
                    presenca++;
                }
            }
            float percentualPresenca=0;
            percentualPresenca = (presenca / estudante.getPresenca().size()) * 100;
            if (mediaFinal < 7 || percentualPresenca < 75){
                alunosReprovados.add(estudante.getNome());
            }
        }
        return alunosReprovados;
    }
}
