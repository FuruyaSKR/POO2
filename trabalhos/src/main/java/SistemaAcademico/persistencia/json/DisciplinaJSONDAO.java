package SistemaAcademico.persistencia.json;

import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.persistencia.IPersistencia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class DisciplinaJSONDAO implements IPersistencia<Disciplina> {

    private static final String FILE_PATH = "Saida/disciplinas.json";
    private Map<Integer, Disciplina> banco = new HashMap<>();
    private Gson gson = new Gson();

    public DisciplinaJSONDAO() {
        carregar();
    }

    @Override
    public void salvar(Disciplina disciplina) {
        banco.put(disciplina.getId(), disciplina);
        salvarEmArquivo();
    }

    @Override
    public Disciplina buscarPorId(int id) {
        return banco.get(id);
    }

    @Override
    public void atualizar(Disciplina disciplina) {
        banco.put(disciplina.getId(), disciplina);
        salvarEmArquivo();
    }

    @Override
    public void deletar(int id) {
        banco.remove(id);
        salvarEmArquivo();
    }

    @Override
    public List<Disciplina> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    private void salvarEmArquivo() {
        try {
            File dir = new File("Saida");
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
            Type type = new TypeToken<Map<Integer, Disciplina>>() {
            }.getType();
            Map<Integer, Disciplina> dados = gson.fromJson(reader, type);
            if (dados != null)
                banco = dados;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}