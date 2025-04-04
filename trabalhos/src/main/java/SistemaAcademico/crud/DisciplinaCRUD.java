package SistemaAcademico.crud;

import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.persistencia.IPersistencia;

import java.util.List;

public class DisciplinaCRUD {

    private IPersistencia<Disciplina> persistencia;

    public DisciplinaCRUD(IPersistencia<Disciplina> persistencia) {
        this.persistencia = persistencia;
    }

    public void salvarDisciplina(Disciplina disciplina) {
        persistencia.salvar(disciplina);
    }

    public Disciplina buscarDisciplina(int id) {
        return persistencia.buscarPorId(id);
    }

    public void atualizarDisciplina(Disciplina disciplina) {
        persistencia.atualizar(disciplina);
    }

    public void deletarDisciplina(int id) {
        persistencia.deletar(id);
    }

    public List<Disciplina> listarTodasDisciplinas() {
        return persistencia.listarTodos();
    }
}