package SistemaAcademico.classes;

import java.util.List;

public class Fase {

    private int numero;
    private String nome;
    private List<Disciplina> disciplinas;

    public void adicionarDisciplina(Disciplina disciplina) {
        // TODO
    }

    public List<Disciplina> listarDisciplinas() {
        // TODO
        return null;
    }

    public Fase(int numero, String nome, List<Disciplina> disciplinas) {
        this.numero = numero;
        this.nome = nome;
        this.disciplinas = disciplinas;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
