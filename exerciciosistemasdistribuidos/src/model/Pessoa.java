package model;

import java.io.Serializable;
import java.util.Objects;

//solução para o problema da da classe ser tragavel 
//Serializable faz a classe ser tragavel via o ObjectOutputStrem 
public class Pessoa implements Serializable {
	//indentifica a versão da classe serializada
    private static final long serialVersionUID = 1L;

    private String nome;
    private String dataNascimento;
    private String email;

    public Pessoa(String nome, String dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public Pessoa(String nome, String email, String dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Gera o e-mail no formato: primeiro.ultimo.ano@ufn.edu.br
    public void gerarEmail() {
        if (nome == null || nome.trim().isEmpty() || dataNascimento == null) return;

        String[] partesNome = nome.trim().toLowerCase().split("\\s+");
        String primeiroNome = partesNome[0];
        String ultimoSobrenome = partesNome.length > 1 ? partesNome[partesNome.length - 1] : primeiroNome;

        String[] partesData = dataNascimento.split("/");
        String anoNascimento = partesData.length == 3 ? partesData[2] : "";

        this.email = primeiroNome + "." + ultimoSobrenome + "." + anoNascimento + "@ufn.edu.br";
        this.nome = this.nome.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(nome.trim().toUpperCase(), pessoa.nome.trim().toUpperCase()) &&
               Objects.equals(dataNascimento, pessoa.dataNascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome.trim().toUpperCase(), dataNascimento);
    }

    @Override
    public String toString() {
        return nome + " | " + dataNascimento + " | " + email;
    }
}