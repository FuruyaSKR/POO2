package SistemaAcademico.classes;

import java.util.List;

public class Matricula {

    private Aluno aluno;
    private Disciplina disciplina;
    private SituacaoAlunoEnum situacaoFinal;
    private List<Frequencia> frequencias;
    private List<Avaliacao> avaliacoes;

    public void registrarFrequencia(Frequencia frequencia) {
        // TODO
    }

    public void registrarAvaliacao(Avaliacao avaliacao) {
        // TODO
    }

    public double calcularMedia() {
        // TODO
        return 0.0;
    }

    public double calcularFrequencia() {
        // TODO
        return 0.0;
    }

    public void atualizarSituacao() {
        // TODO
    }

    public Matricula(Aluno aluno, Disciplina disciplina, SituacaoAlunoEnum situacaoFinal, List<Frequencia> frequencias,
            List<Avaliacao> avaliacoes) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.situacaoFinal = situacaoFinal;
        this.frequencias = frequencias;
        this.avaliacoes = avaliacoes;
    }

    public Aluno getAluno() {
        return this.aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return this.disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public SituacaoAlunoEnum getSituacaoFinal() {
        return this.situacaoFinal;
    }

    public void setSituacaoFinal(SituacaoAlunoEnum situacaoFinal) {
        this.situacaoFinal = situacaoFinal;
    }

    public List<Frequencia> getFrequencias() {
        return this.frequencias;
    }

    public void setFrequencias(List<Frequencia> frequencias) {
        this.frequencias = frequencias;
    }

    public List<Avaliacao> getAvaliacoes() {
        return this.avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

}
