package SistemaAcademico.persistencia.xml;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.classes.Fase;
import SistemaAcademico.classes.Professor;
import SistemaAcademico.persistencia.IPersistencia;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;
import java.util.*;

public class FaseXMLDAO implements IPersistencia<Fase> {
    private static final String FILE_PATH = "saida/fases.xml";
    private final Map<Integer, Fase> mapa = new HashMap<>();

    @Override
    public void salvar(Fase fase) {
        mapa.put(fase.getNumero(), fase);
        salvarArquivo();
    }

    @Override
    public Fase buscarPorId(int id) {
        return mapa.get(id);
    }

    @Override
    public void atualizar(Fase fase) {
        mapa.put(fase.getNumero(), fase);
        salvarArquivo();
    }

    @Override
    public void deletar(int id) {
        mapa.remove(id);
        salvarArquivo();
    }

    @Override
    public List<Fase> listarTodos() {
        return new ArrayList<>(mapa.values());
    }

    private void salvarArquivo() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("fases");
            doc.appendChild(root);

            for (Fase f : mapa.values()) {
                Element fase = doc.createElement("fase");
                root.appendChild(fase);

                Element numero = doc.createElement("numero");
                numero.appendChild(doc.createTextNode(String.valueOf(f.getNumero())));
                fase.appendChild(numero);

                Element nome = doc.createElement("nome");
                nome.appendChild(doc.createTextNode(f.getNome()));
                fase.appendChild(nome);

                Element disciplinas = doc.createElement("disciplinas");
                fase.appendChild(disciplinas);

                for (Disciplina d : f.listarDisciplinas()) {
                    Element disciplina = doc.createElement("disciplina");

                    disciplina.appendChild(createElement(doc, "id", String.valueOf(d.getId())));
                    disciplina.appendChild(createElement(doc, "nome", d.getNome()));
                    disciplina.appendChild(createElement(doc, "ofertada", String.valueOf(d.isOfertada())));
                    disciplina.appendChild(
                            createElement(doc, "capacidadeMaxima", String.valueOf(d.getCapacidadeMaxima())));

                    // Professores
                    Element professores = doc.createElement("professores");
                    for (Professor p : d.listarProfessores()) {
                        Element prof = doc.createElement("professor");
                        prof.appendChild(createElement(doc, "id", String.valueOf(p.getId())));
                        prof.appendChild(createElement(doc, "nome", p.getNome()));
                        professores.appendChild(prof);
                    }
                    disciplina.appendChild(professores);

                    // Alunos
                    Element alunos = doc.createElement("alunosMatriculados");
                    for (Aluno a : d.listarAlunosMatriculados()) {
                        Element aluno = doc.createElement("aluno");
                        aluno.appendChild(createElement(doc, "id", String.valueOf(a.getId())));
                        aluno.appendChild(createElement(doc, "nome", a.getNome()));
                        alunos.appendChild(aluno);
                    }
                    disciplina.appendChild(alunos);

                    disciplinas.appendChild(disciplina);
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_PATH)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element createElement(Document doc, String tag, String value) {
        Element el = doc.createElement(tag);
        el.appendChild(doc.createTextNode(value));
        return el;
    }

}
