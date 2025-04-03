package SistemaAcademico.classes;

import java.time.LocalDate;

public class Frequencia {

    private LocalDate data;
    private boolean presente;
    private Professor professorResponsavel;

    public Frequencia(LocalDate data, boolean presente, Professor professorResponsavel) {
        this.data = data;
        this.presente = presente;
        this.professorResponsavel = professorResponsavel;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isPresente() {
        return this.presente;
    }

    public boolean getPresente() {
        return this.presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public Professor getProfessorResponsavel() {
        return this.professorResponsavel;
    }

    public void setProfessorResponsavel(Professor professorResponsavel) {
        this.professorResponsavel = professorResponsavel;
    }
}
