package SistemaAcademico;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.classes.Professor;
import SistemaAcademico.crud.AlunoCRUD;
import SistemaAcademico.crud.ProfessorCRUD;

public class TestesInsercao {

    public static void testarProfessores(ProfessorCRUD professorCRUD) {
        System.out.println("----- Testando Professores -----");

        Professor p1 = new Professor(1, "Maria Oliveira");
        Professor p2 = new Professor(2, "Carlos Souza");
        Professor p3 = new Professor(3, "Ana Lima");
        Professor p4 = new Professor(4, "Roberto Matos");

        professorCRUD.salvarProfessor(p1);
        professorCRUD.salvarProfessor(p2);
        professorCRUD.salvarProfessor(p3);
        professorCRUD.salvarProfessor(p4);

        // UPDATE p2
        p2.setNome("Carlos Souza Atualizado");
        professorCRUD.atualizarProfessor(p2);

        // DELETE p3
        professorCRUD.deletarProfessor(3);

        System.out.println("\nListando todos os professores após operações:");
        for (Professor p : professorCRUD.listarTodosProfessores()) {
            System.out.println(p.getId() + " - " + p.getNome());
        }

    }

    public static void testarAlunos(AlunoCRUD alunoCRUD) {
        System.out.println("\n----- Testando Alunos -----");

        Aluno a1 = new Aluno(1, "João da Silva");
        Aluno a2 = new Aluno(2, "Fernanda Costa");
        Aluno a3 = new Aluno(3, "Lucas Pereira");
        Aluno a4 = new Aluno(4, "Mariana Rocha");

        alunoCRUD.salvarAluno(a1);
        alunoCRUD.salvarAluno(a2);
        alunoCRUD.salvarAluno(a3);
        alunoCRUD.salvarAluno(a4);

        // UPDATE a4
        a4.setNome("Mariana Rocha Atualizada");
        alunoCRUD.atualizarAluno(a4);

        // DELETE a1
        alunoCRUD.deletarAluno(1);

        System.out.println("\nListando todos os alunos após operações:");
        for (Aluno a : alunoCRUD.listarTodosAlunos()) {
            System.out.println(a.getId() + " - " + a.getNome());
        }
    }

}
