package com.example.atividade_29042025_estudantesestatisticas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Estudante {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("nome")
    @Expose
    public String nome;
    @SerializedName("idade")
    @Expose
    public Integer idade;
    @SerializedName("notas")
    @Expose
    public List<Float> notas;
    @SerializedName("presenca")
    @Expose
    public List<Boolean> presenca;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public List<Float> getNotas() {
        return notas;
    }

    public void setNotas(List<Float> notas) {
        this.notas = notas;
    }

    public List<Boolean> getPresenca() {
        return presenca;
    }

    public void setPresenca(List<Boolean> presenca) {
        this.presenca = presenca;
    }

    @Override
    public String toString() {
        return "Estudante{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", notas=" + notas +
                ", presenca=" + presenca +
                '}';
    }
}