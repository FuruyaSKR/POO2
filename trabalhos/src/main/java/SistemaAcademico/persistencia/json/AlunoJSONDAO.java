package SistemaAcademico.persistencia.json;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.classes.Professor;
import SistemaAcademico.persistencia.IPersistencia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlunoJSONDAO implements IPersistencia<Aluno> {

    private static final String FILE_PATH = "Saida/alunos.json";
    private Map<Integer, Aluno> banco = new HashMap<>();
    private Gson gson = new Gson();

    public AlunoJSONDAO() {
        carregar();
    }

    @Override
    public void salvar(Aluno aluno) {
        banco.put(aluno.getId(), aluno);
        salvarEmArquivo();
    }

    @Override
    public Aluno buscarPorId(int id) {
        return banco.get(id);
    }

    @Override
    public void atualizar(Aluno aluno) {
        banco.put(aluno.getId(), aluno);
        salvarEmArquivo();
    }

    @Override
    public void deletar(int id) {
        banco.remove(id);
        salvarEmArquivo();
    }

    @Override
    public List<Aluno> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    private void salvarEmArquivo() {
        try {
            File dir = new File("saida");
            if (!dir.exists())
                dir.mkdirs();

            Writer writer = new FileWriter(FILE_PATH);
            gson.toJson(banco, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregar() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<Integer, Aluno>>() {
            }.getType();
            Map<Integer, Aluno> dados = gson.fromJson(reader, type);
            if (dados != null)
                banco = dados;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
