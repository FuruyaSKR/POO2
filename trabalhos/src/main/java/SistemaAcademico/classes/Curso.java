package SistemaAcademico.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Curso {

    private int id;
    private String nome;
    private List<Fase> fases;
    private List<Aluno> alunos;
    private List<Matricula> matriculas;

    public void adicionarAluno(Aluno aluno) {
        if (aluno != null && !alunos.contains(aluno)) {
            alunos.add(aluno);
        }
    }

    public void adicionarFase(Fase fase) {
        if (fase != null && !fases.contains(fase)) {
            fases.add(fase);
        }
    }

    public List<Disciplina> listarDisciplinasPorFase(int numeroFase) {
        for (Fase f : fases) {
            if (f.getNumero() == numeroFase) {
                return f.listarDisciplinas();
            }
        }
        return Collections.emptyList();
    }

    public void ofertarDisciplina(Disciplina disciplina) {
        for (Fase f : fases) {
            f.adicionarDisciplina(disciplina);
            break;
        }
    }

    // TODO Arrumar
    // public Matricula matricularAluno(Aluno aluno, Disciplina disciplina) {
    // if (aluno != null && disciplina != null && disciplina.temVaga()) {
    // disciplina.adicionarAluno(aluno);
    // Matricula matricula = new Matricula(aluno, disciplina);
    // matriculas.add(matricula);
    // return matricula;
    // }
    // return null;
    // }

    public Curso(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.fases = new ArrayList<>();
        this.alunos = new ArrayList<>();
        this.matriculas = new ArrayList<>();
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

    public List<Fase> getFases() {
        return this.fases;
    }

    public void setFases(List<Fase> fases) {
        this.fases = fases;
    }

    public List<Aluno> getAlunos() {
        return this.alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<Matricula> getMatriculas() {
        return this.matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

}
