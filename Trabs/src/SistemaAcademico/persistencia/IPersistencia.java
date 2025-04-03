package SistemaAcademico.persistencia;

public interface IPersistencia<T> {
    void salvar(T entidade);

    T buscarPorId(int id);

    void atualizar(T entidade);

    void deletar(int id);
}
