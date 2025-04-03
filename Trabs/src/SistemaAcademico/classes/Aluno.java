package SistemaAcademico.classes;

import java.util.List;

public class Aluno {

	private int id;
	private String nome;

	public SituacaoAlunoEnum getSituacaoPorDisciplina(Disciplina disciplina) {
		// TODO
		return null;
	}

	public Aluno(int id, String nome) {
		this.id = id;
		this.nome = nome;
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

}
