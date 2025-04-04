package SistemaAcademico.classes;

import java.util.ArrayList;
import java.util.List;

public class Matricula {

    private Aluno aluno;
    private Disciplina disciplina;
    private SituacaoAlunoEnum situacaoFinal;
    private List<Frequencia> frequencias;
    private List<Avaliacao> avaliacoes;

    public void registrarFrequencia(Frequencia frequencia) {
        if (frequencia != null) {
            frequencias.add(frequencia);
        }
    }

    public void registrarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao != null) {
            avaliacoes.add(avaliacao);
        }
    }

    public double calcularMedia() {
        if (avaliacoes.isEmpty())
            return 0.0;
        double soma = 0.0;
        for (Avaliacao a : avaliacoes) {
            soma += a.getNota();
        }
        return soma / avaliacoes.size();
    }

    public double calcularFrequencia() {
        if (frequencias.isEmpty())
            return 0.0;
        int totalAulas = frequencias.size();
        long presencas = frequencias.stream().filter(Frequencia::isPresente).count();
        return (presencas * 100.0) / totalAulas;
    }

    public void atualizarSituacao() {
        double media = calcularMedia();
        double freq = calcularFrequencia();

        if (media >= 6.0 && freq >= 75.0) {
            situacaoFinal = SituacaoAlunoEnum.APROVADO;
        } else {
            situacaoFinal = SituacaoAlunoEnum.REPROVADO;
        }
    }

    public Matricula(Aluno aluno, Disciplina disciplina) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.frequencias = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.situacaoFinal = SituacaoAlunoEnum.MATRICULADO;
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
