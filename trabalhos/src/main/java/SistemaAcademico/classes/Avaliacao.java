package SistemaAcademico.classes;

public class Avaliacao {

    private double nota;
    private Professor professorResponsavel;

    public Avaliacao(double nota, Professor professorResponsavel) {
        this.nota = nota;
        this.professorResponsavel = professorResponsavel;
    }

    public double getNota() {
        return this.nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Professor getProfessorResponsavel() {
        return this.professorResponsavel;
    }

    public void setProfessorResponsavel(Professor professorResponsavel) {
        this.professorResponsavel = professorResponsavel;
    }

}
