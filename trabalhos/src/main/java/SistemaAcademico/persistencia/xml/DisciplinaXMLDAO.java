package SistemaAcademico.persistencia.xml;

import SistemaAcademico.classes.Aluno;
import SistemaAcademico.classes.Disciplina;
import SistemaAcademico.classes.Professor;
import SistemaAcademico.persistencia.IPersistencia;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class DisciplinaXMLDAO implements IPersistencia<Disciplina> {

    private static final String FILE_PATH = "Saida/disciplinas.xml";
    private Map<Integer, Disciplina> banco = new HashMap<>();

    public DisciplinaXMLDAO() {
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

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("disciplinas");
            doc.appendChild(root);

            for (Disciplina d : banco.values()) {
                Element elem = doc.createElement("disciplina");

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(d.getId())));

                Element nome = doc.createElement("nome");
                nome.appendChild(doc.createTextNode(d.getNome()));

                Element ofertada = doc.createElement("ofertada");
                ofertada.appendChild(doc.createTextNode(String.valueOf(d.isOfertada())));

                Element capacidade = doc.createElement("capacidadeMaxima");
                capacidade.appendChild(doc.createTextNode(String.valueOf(d.getCapacidadeMaxima())));

                Element professoresElem = doc.createElement("professores");
                for (Professor p : d.listarProfessores()) {
                    Element prof = doc.createElement("professor");

                    Element profId = doc.createElement("id");
                    profId.appendChild(doc.createTextNode(String.valueOf(p.getId())));
                    prof.appendChild(profId);

                    Element profNome = doc.createElement("nome");
                    profNome.appendChild(doc.createTextNode(p.getNome()));
                    prof.appendChild(profNome);

                    professoresElem.appendChild(prof);
                }

                Element alunosElem = doc.createElement("alunosMatriculados");
                for (Aluno a : d.getAlunosMatriculados()) {
                    Element aluno = doc.createElement("aluno");

                    Element alunoId = doc.createElement("id");
                    alunoId.appendChild(doc.createTextNode(String.valueOf(a.getId())));
                    aluno.appendChild(alunoId);

                    Element alunoNome = doc.createElement("nome");
                    alunoNome.appendChild(doc.createTextNode(a.getNome()));
                    aluno.appendChild(alunoNome);

                    alunosElem.appendChild(aluno);
                }

                elem.appendChild(id);
                elem.appendChild(nome);
                elem.appendChild(ofertada);
                elem.appendChild(capacidade);
                elem.appendChild(alunosElem);
                elem.appendChild(professoresElem);

                root.appendChild(elem);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_PATH)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregar() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList lista = doc.getElementsByTagName("disciplina");

            for (int i = 0; i < lista.getLength(); i++) {
                Element e = (Element) lista.item(i);
                int id = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent());
                String nome = e.getElementsByTagName("nome").item(0).getTextContent();
                boolean ofertada = Boolean.parseBoolean(e.getElementsByTagName("ofertada").item(0).getTextContent());
                Disciplina d = new Disciplina(id, nome, ofertada);
                d.setCapacidadeMaxima(
                        Integer.parseInt(e.getElementsByTagName("capacidadeMaxima").item(0).getTextContent()));
                banco.put(id, d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}