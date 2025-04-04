package SistemaAcademico.classes;

import java.util.ArrayList;
import java.util.List;

public class Fase {

    private int numero;
    private String nome;
    private List<Disciplina> disciplinas;

    public void adicionarDisciplina(Disciplina disciplina) {
        if (disciplina != null && !disciplinas.contains(disciplina)) {
            disciplinas.add(disciplina);
        }
    }

    public List<Disciplina> listarDisciplinas() {
        return new ArrayList<>(disciplinas);
    }

    public Fase(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
        this.disciplinas = new ArrayList<>();
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
