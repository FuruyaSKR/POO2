package SistemaAcademico.classes;

import java.util.List;

public class Curso {

    private int id;
    private String nome;
    private List<Fase> fases;
    private List<Aluno> alunos;
    private List<Matricula> matriculas;

    public void adicionarAluno(Aluno aluno) {
        // TODO
    }

    public void adicionarFase(Fase fase) {
        // TODO
    }

    public List<Disciplina> listarDisciplinasPorFase(int fase) {
        // TODO
        return null;
    }

    public void ofertarDisciplina(Disciplina disciplina) {
        // TODO
    }

    public Matricula matricularAluno(Aluno aluno, Disciplina disciplina) {
        // TODO
        return null;
    }

    public Curso(int id, String nome, List<Fase> fases, List<Aluno> alunos, List<Matricula> matriculas) {
        this.id = id;
        this.nome = nome;
        this.fases = fases;
        this.alunos = alunos;
        this.matriculas = matriculas;
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
