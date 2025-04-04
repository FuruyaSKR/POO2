package SistemaAcademico.crud;

import SistemaAcademico.classes.Matricula;
import SistemaAcademico.persistencia.IPersistencia;

import java.util.List;

public class MatriculaCRUD {

    private IPersistencia<Matricula> persistencia;

    public MatriculaCRUD(IPersistencia<Matricula> persistencia) {
        this.persistencia = persistencia;
    }

    public void salvarMatricula(Matricula matricula) {
        persistencia.salvar(matricula);
    }

    public Matricula buscarMatricula(int id) {
        return persistencia.buscarPorId(id);
    }

    public void atualizarMatricula(Matricula matricula) {
        persistencia.atualizar(matricula);
    }

    public void deletarMatricula(int id) {
        persistencia.deletar(id);
    }

    public List<Matricula> listarTodasMatriculas() {
        return persistencia.listarTodos();
    }
}
