package SistemaAcademico.persistencia.xml;

import SistemaAcademico.classes.Avaliacao;
import SistemaAcademico.classes.Frequencia;
import SistemaAcademico.classes.Matricula;
import SistemaAcademico.persistencia.IPersistencia;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class MatriculaXMLDAO implements IPersistencia<Matricula> {
    private static final String FILE_PATH = "Saida/matriculas.xml";
    private final Map<Integer, Matricula> mapa = new HashMap<>();

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
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("matriculas");
            doc.appendChild(root);

            for (Matricula m : mapa.values()) {
                Element matricula = doc.createElement("matricula");
                root.appendChild(matricula);

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(m.getId())));
                matricula.appendChild(id);

                Element alunoId = doc.createElement("aluno_id");
                alunoId.appendChild(doc.createTextNode(String.valueOf(m.getAluno().getId())));
                matricula.appendChild(alunoId);

                Element disciplinaId = doc.createElement("disciplina_id");
                disciplinaId.appendChild(doc.createTextNode(String.valueOf(m.getDisciplina().getId())));
                matricula.appendChild(disciplinaId);

                if (m.getCurso() != null) {
                    Element curso = doc.createElement("curso");
                    Element cursoId = doc.createElement("id");
                    cursoId.appendChild(doc.createTextNode(String.valueOf(m.getCurso().getId())));
                    curso.appendChild(cursoId);

                    Element cursoNome = doc.createElement("nome");
                    cursoNome.appendChild(doc.createTextNode(m.getCurso().getNome()));
                    curso.appendChild(cursoNome);

                    matricula.appendChild(curso);
                }

                Element situacao = doc.createElement("situacaoFinal");
                situacao.appendChild(doc.createTextNode(m.getSituacaoFinal().toString()));
                matricula.appendChild(situacao);

                Element avaliacoes = doc.createElement("avaliacoes");
                for (Avaliacao a : m.getAvaliacoes()) {
                    Element avaliacao = doc.createElement("avaliacao");

                    Element nota = doc.createElement("nota");
                    nota.appendChild(doc.createTextNode(String.valueOf(a.getNota())));
                    avaliacao.appendChild(nota);

                    if (a.getProfessorResponsavel() != null) {
                        Element prof = doc.createElement("professor");
                        Element profId = doc.createElement("id");
                        profId.appendChild(doc.createTextNode(String.valueOf(a.getProfessorResponsavel().getId())));
                        prof.appendChild(profId);

                        Element profNome = doc.createElement("nome");
                        profNome.appendChild(doc.createTextNode(a.getProfessorResponsavel().getNome()));
                        prof.appendChild(profNome);

                        avaliacao.appendChild(prof);
                    }

                    avaliacoes.appendChild(avaliacao);
                }
                matricula.appendChild(avaliacoes);

                Element frequencias = doc.createElement("frequencias");
                for (Frequencia f : m.getFrequencias()) {
                    Element frequencia = doc.createElement("frequencia");

                    Element data = doc.createElement("data");
                    data.appendChild(doc.createTextNode(f.getData().toString()));
                    frequencia.appendChild(data);

                    Element presente = doc.createElement("presente");
                    presente.appendChild(doc.createTextNode(String.valueOf(f.isPresente())));
                    frequencia.appendChild(presente);

                    if (f.getProfessorResponsavel() != null) {
                        Element prof = doc.createElement("professor");
                        Element profId = doc.createElement("id");
                        profId.appendChild(doc.createTextNode(String.valueOf(f.getProfessorResponsavel().getId())));
                        prof.appendChild(profId);

                        Element profNome = doc.createElement("nome");
                        profNome.appendChild(doc.createTextNode(f.getProfessorResponsavel().getNome()));
                        prof.appendChild(profNome);

                        frequencia.appendChild(prof);
                    }

                    frequencias.appendChild(frequencia);
                }
                matricula.appendChild(frequencias);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_PATH)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
