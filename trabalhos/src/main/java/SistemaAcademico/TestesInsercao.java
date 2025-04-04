package SistemaAcademico;

import java.util.List;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.classes.Fase;
import SistemaAcademico.classes.Professor;
import SistemaAcademico.crud.AlunoCRUD;
import SistemaAcademico.crud.DisciplinaCRUD;
import SistemaAcademico.crud.FaseCRUD;
import SistemaAcademico.crud.ProfessorCRUD;

public class TestesInsercao {

    public static List<Professor> criarProfessores() {
        return List.of(
                new Professor(1, "Maria Oliveira"),
                new Professor(2, "Carlos Souza"),
                new Professor(3, "Ana Lima"),
                new Professor(4, "Roberto Matos"));
    }

    public static List<Aluno> criarAlunos() {
        return List.of(
                new Aluno(1, "João da Silva"),
                new Aluno(2, "Fernanda Costa"),
                new Aluno(3, "Lucas Pereira"),
                new Aluno(4, "Mariana Rocha"));
    }

    public static List<Disciplina> criarDisciplinas(List<Professor> professores, List<Aluno> alunos) {
        Disciplina d1 = new Disciplina(1, "Matemática I", true);
        d1.setCapacidadeMaxima(1);
        d1.adicionarProfessor(professores.get(1));
        d1.adicionarAluno(alunos.get(1));
        d1.adicionarAluno(alunos.get(3));

        Disciplina d2 = new Disciplina(2, "Algoritmos", true);
        d2.setCapacidadeMaxima(2);
        d2.adicionarProfessor(professores.get(1));
        d2.adicionarProfessor(professores.get(3));
        d2.adicionarAluno(alunos.get(1));
        d2.adicionarAluno(alunos.get(2));

        Disciplina d3 = new Disciplina(3, "Estrutura de Dados", false);
        d3.setCapacidadeMaxima(2);

        Disciplina d4 = new Disciplina(4, "Banco de Dados", false);
        d4.setCapacidadeMaxima(2);

        return List.of(d1, d2, d3, d4);
    }

    public static void testarProfessores(ProfessorCRUD professorCRUD, List<Professor> professores) {
        System.out.println("----- Testando Professores -----");

        for (Professor p : professores) {
            professorCRUD.salvarProfessor(p);
        }

        professores.get(1).setNome("Carlos Souza Atualizado");
        professorCRUD.atualizarProfessor(professores.get(1));

        professorCRUD.deletarProfessor(professores.get(2).getId());

        System.out.println("\nListando todos os professores após operações:");
        for (Professor p : professorCRUD.listarTodosProfessores()) {
            System.out.println(p.getId() + " - " + p.getNome());
        }
    }

    public static void testarAlunos(AlunoCRUD alunoCRUD, List<Aluno> alunos) {
        System.out.println("\n----- Testando Alunos -----");

        for (Aluno a : alunos) {
            alunoCRUD.salvarAluno(a);
        }

        alunos.get(3).setNome("Mariana Rocha Atualizada");
        alunoCRUD.atualizarAluno(alunos.get(3));

        alunoCRUD.deletarAluno(alunos.get(0).getId());

        System.out.println("\nListando todos os alunos após operações:");
        for (Aluno a : alunoCRUD.listarTodosAlunos()) {
            System.out.println(a.getId() + " - " + a.getNome());
        }
    }

    public static void testarDisciplinas(DisciplinaCRUD disciplinaCRUD, List<Disciplina> disciplinas) {
        System.out.println("\n----- Testando Disciplinas -----");

        for (Disciplina d : disciplinas) {
            disciplinaCRUD.salvarDisciplina(d);
        }

        Disciplina d2 = disciplinas.get(1);
        d2.setNome("Algoritmos e Lógica");
        disciplinaCRUD.atualizarDisciplina(d2);

        disciplinaCRUD.deletarDisciplina(disciplinas.get(2).getId());

        System.out.println("\nListando todas as disciplinas após operações:");
        for (Disciplina d : disciplinaCRUD.listarTodasDisciplinas()) {
            System.out.println(d.getId() + " - " + d.getNome() + " (Ofertada: " + d.isOfertada() + ")");
            System.out.println("Professores: " + d.listarProfessores().stream().map(Professor::getNome).toList());
            System.out.println("Tem vaga? " + d.temVaga());
            System.out.println();
        }
    }

    public static void testarFases(FaseCRUD faseCRUD, List<Disciplina> disciplinas) {
        System.out.println("\n----- Testando Fases -----");

        Fase f1 = new Fase(1, "1ª Fase");
        Fase f2 = new Fase(2, "2ª Fase");
        Fase f3 = new Fase(3, "3ª Fase");

        f1.adicionarDisciplina(disciplinas.get(0));
        f1.adicionarDisciplina(disciplinas.get(1));
        f2.adicionarDisciplina(disciplinas.get(3));

        faseCRUD.salvarFase(f1);
        faseCRUD.salvarFase(f2);

        f3.setNome("32ª Fase Atualizada");
        faseCRUD.atualizarFase(f3);

        faseCRUD.deletarFase(3);

        System.out.println("\nListando disciplinas da fase 2:");
        for (Disciplina d : f2.listarDisciplinas()) {
            System.out.println(d.getId() + " - " + d.getNome());
        }

        System.out.println("\nListando todas as fases após operações:");
        for (Fase f : faseCRUD.listarTodasFases()) {
            System.out.println(f.getNumero() + " - " + f.getNome());
        }
    }

}
