package SistemaAcademico.classes;

import java.util.List;

public class Disciplina {

    private int id;
    private String nome;
    private boolean ofertada;
    private int capacidadeMaxima;
    private List<Professor> professores;
    private List<Aluno> alunosMatriculados;

    public void adicionarProfessor(Professor professor) {
        // TODO
    }

    public List<Professor> listarProfessores() {
        // TODO
        return null;
    }

    public boolean temVaga() {
        // TODO
        return false;
    }

    public Disciplina(int id, String nome, boolean ofertada, int capacidadeMaxima, List<Professor> professores,
            List<Aluno> alunosMatriculados) {
        this.id = id;
        this.nome = nome;
        this.ofertada = ofertada;
        this.capacidadeMaxima = capacidadeMaxima;
        this.professores = professores;
        this.alunosMatriculados = alunosMatriculados;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isOfertada() {
        return this.ofertada;
    }

    public boolean getOfertada() {
        return this.ofertada;
    }

    public void setOfertada(boolean ofertada) {
        this.ofertada = ofertada;
    }

    public int getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public List<Professor> getProfessores() {
        return this.professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    public List<Aluno> getAlunosMatriculados() {
        return this.alunosMatriculados;
    }

    public void setAlunosMatriculados(List<Aluno> alunosMatriculados) {
        this.alunosMatriculados = alunosMatriculados;
    }

}
