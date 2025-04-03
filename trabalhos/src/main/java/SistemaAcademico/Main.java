package SistemaAcademico;

import SistemaAcademico.classes.Professor;
import SistemaAcademico.crud.ProfessorCRUD;
import SistemaAcademico.persistencia.IPersistencia;
import SistemaAcademico.persistencia.json.ProfessorJSONDAO;
// import SistemaAcademico.persistencia.xml.ProfessorXMLDAO;
import SistemaAcademico.persistencia.mysql.ProfessorMySQLDAO;

public class Main {
    public static void main(String[] args) {
        // IPersistencia<Professor> persistencia = new ProfessorJSONDAO();
        IPersistencia<Professor> persistencia = new ProfessorMySQLDAO();
        // IPersistencia<Professor> persistencia = new ProfessorXMLDAO();
        ProfessorCRUD crud = new ProfessorCRUD(persistencia);

        // Criar novo professor
        Professor novo = new Professor(1, "Maria Oliveira");
        crud.salvarProfessor(novo);

        // Buscar professor
        Professor encontrado = crud.buscarProfessor(1);
        System.out.println("Professor: " + encontrado.getNome());
    }
}
