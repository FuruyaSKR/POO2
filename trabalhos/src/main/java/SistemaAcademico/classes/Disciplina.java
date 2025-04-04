package SistemaAcademico.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Disciplina {

    private int id;
    private String nome;
    private boolean ofertada;
    private int capacidadeMaxima;
    private List<Professor> professores;
    private List<Aluno> alunosMatriculados;

    public void adicionarProfessor(Professor professor) {
        if (professores.size() < 4 && !professores.contains(professor)) {
            professores.add(professor);
        } else {
            System.out.println("Lotação de professores permitidos!");
        }
    }

    public List<Aluno> listarAlunosMatriculados() {
        return new ArrayList<>(alunosMatriculados);
    }

    public List<Professor> listarProfessores() {
        return new ArrayList<>(professores);
    }

    public void adicionarAluno(Aluno aluno) {
        if (temVaga() && !alunosMatriculados.contains(aluno)) {
            alunosMatriculados.add(aluno);
        }
    }

    public boolean temVaga() {
        return alunosMatriculados.size() < capacidadeMaxima;
    }

    public Disciplina(int id, String nome, boolean ofertada) {
        this.id = id;
        this.nome = nome;
        this.ofertada = ofertada;
        this.capacidadeMaxima = 0;
        this.professores = new ArrayList<>();
        this.alunosMatriculados = new ArrayList<>();
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
        return ofertada;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Disciplina that = (Disciplina) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
