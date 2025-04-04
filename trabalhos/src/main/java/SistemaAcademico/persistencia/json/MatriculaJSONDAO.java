package SistemaAcademico.persistencia.json;

import SistemaAcademico.classes.Matricula;
import SistemaAcademico.persistencia.IPersistencia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class MatriculaJSONDAO implements IPersistencia<Matricula> {
    private static final String FILE_PATH = "Saida/matriculas.json";
    private final Map<Integer, Matricula> mapa = new HashMap<>();
    private final Gson gson = new Gson();

    public MatriculaJSONDAO() {
        carregarArquivo();
    }

    @Override
    public void salvar(Matricula m) {
        mapa.put(m.getId(), m);
        salvarArquivo();
    }

    @Override
    public Matricula buscarPorId(int id) {
        return mapa.get(id);
    }

    @Override
    public void atualizar(Matricula m) {
        mapa.put(m.getId(), m);
        salvarArquivo();
    }

    @Override
    public void deletar(int id) {
        mapa.remove(id);
        salvarArquivo();
    }

    @Override
    public List<Matricula> listarTodos() {
        return new ArrayList<>(mapa.values());
    }

    private void salvarArquivo() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(mapa, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarArquivo() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                Map<Integer, Matricula> data = gson.fromJson(content, new TypeToken<Map<Integer, Matricula>>() {
                }.getType());
                if (data != null)
                    mapa.putAll(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
