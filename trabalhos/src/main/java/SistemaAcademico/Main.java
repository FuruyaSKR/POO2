package SistemaAcademico;

import SistemaAcademico.classes.Professor;
import SistemaAcademico.crud.ProfessorCRUD;
import SistemaAcademico.persistencia.IPersistencia;
import SistemaAcademico.persistencia.json.ProfessorJSONDAO;

public class Main {
    public static void main(String[] args) {
        IPersistencia<Professor> persistencia = new ProfessorJSONDAO();
        ProfessorCRUD crud = new ProfessorCRUD(persistencia);

        Professor p1 = new Professor(1, "Carlos Silva");
        crud.salvarProfessor(p1);

        Professor p2 = crud.buscarProfessor(1);
        System.out.println("Professor encontrado: " + p2.getNome());
    }
}
