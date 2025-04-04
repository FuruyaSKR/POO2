package SistemaAcademico.persistencia.json;

import SistemaAcademico.classes.Curso;
import SistemaAcademico.persistencia.IPersistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CursoJSONDAO implements IPersistencia<Curso> {
    private static final String FILE_PATH = "Saida/cursos.json";
    private final Map<Integer, Curso> mapa = new HashMap<>();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new com.google.gson.JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT,
                        com.google.gson.JsonDeserializationContext context) {
                    return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
                }
            })
            .registerTypeAdapter(LocalDate.class, new com.google.gson.JsonSerializer<LocalDate>() {
                @Override
                public com.google.gson.JsonElement serialize(LocalDate date, java.lang.reflect.Type type,
                        com.google.gson.JsonSerializationContext context) {
                    return new com.google.gson.JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
                }
            })
            .setPrettyPrinting()
            .create();

    public CursoJSONDAO() {
        carregarArquivo();
    }

    @Override
    public void salvar(Curso curso) {
        mapa.put(curso.getId(), curso);
        salvarArquivo();
    }

    @Override
    public Curso buscarPorId(int id) {
        return mapa.get(id);
    }

    @Override
    public void atualizar(Curso curso) {
        mapa.put(curso.getId(), curso);
        salvarArquivo();
    }

    @Override
    public void deletar(int id) {
        mapa.remove(id);
        salvarArquivo();
    }

    @Override
    public List<Curso> listarTodos() {
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
                Map<Integer, Curso> data = gson.fromJson(content, new TypeToken<Map<Integer, Curso>>() {
                }.getType());
                if (data != null)
                    mapa.putAll(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
