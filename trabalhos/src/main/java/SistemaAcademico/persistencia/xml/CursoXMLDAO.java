package SistemaAcademico.persistencia.xml;

import SistemaAcademico.classes.Curso;
import SistemaAcademico.persistencia.IPersistencia;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.*;

public class CursoXMLDAO implements IPersistencia<Curso> {
    private static final String FILE_PATH = "Saida/cursos.xml";
    private final Map<Integer, Curso> mapa = new HashMap<>();

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
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("cursos");
            doc.appendChild(root);

            for (Curso c : mapa.values()) {
                Element curso = doc.createElement("curso");
                root.appendChild(curso);

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(c.getId())));
                curso.appendChild(id);

                Element nome = doc.createElement("nome");
                nome.appendChild(doc.createTextNode(c.getNome()));
                curso.appendChild(nome);

                // Fases
                Element fases = doc.createElement("fases");
                for (var f : c.getFases()) {
                    Element fase = doc.createElement("fase");

                    Element numero = doc.createElement("numero");
                    numero.appendChild(doc.createTextNode(String.valueOf(f.getNumero())));
                    fase.appendChild(numero);

                    Element nomeFase = doc.createElement("nome");
                    nomeFase.appendChild(doc.createTextNode(f.getNome()));
                    fase.appendChild(nomeFase);

                    // Disciplinas da fase
                    Element disciplinas = doc.createElement("disciplinas");
                    for (var d : f.listarDisciplinas()) {
                        Element disciplina = doc.createElement("disciplina");

                        Element idDisc = doc.createElement("id");
                        idDisc.appendChild(doc.createTextNode(String.valueOf(d.getId())));
                        disciplina.appendChild(idDisc);

                        Element nomeDisc = doc.createElement("nome");
                        nomeDisc.appendChild(doc.createTextNode(d.getNome()));
                        disciplina.appendChild(nomeDisc);

                        Element ofertada = doc.createElement("ofertada");
                        ofertada.appendChild(doc.createTextNode(String.valueOf(d.isOfertada())));
                        disciplina.appendChild(ofertada);

                        disciplinas.appendChild(disciplina);
                    }

                    fase.appendChild(disciplinas);
                    fases.appendChild(fase);
                }
                curso.appendChild(fases);

                // Alunos
                Element alunos = doc.createElement("alunos");
                for (var a : c.getAlunos()) {
                    Element aluno = doc.createElement("aluno");

                    Element idAluno = doc.createElement("id");
                    idAluno.appendChild(doc.createTextNode(String.valueOf(a.getId())));
                    aluno.appendChild(idAluno);

                    Element nomeAluno = doc.createElement("nome");
                    nomeAluno.appendChild(doc.createTextNode(a.getNome()));
                    aluno.appendChild(nomeAluno);

                    alunos.appendChild(aluno);
                }
                curso.appendChild(alunos);

                // Matrículas
                Element matriculas = doc.createElement("matriculas");
                for (var m : c.getMatriculas()) {
                    Element matricula = doc.createElement("matricula");

                    Element idMatricula = doc.createElement("id");
                    idMatricula.appendChild(doc.createTextNode(String.valueOf(m.getId())));
                    matricula.appendChild(idMatricula);

                    Element alunoMat = doc.createElement("aluno_id");
                    alunoMat.appendChild(doc.createTextNode(String.valueOf(m.getAluno().getId())));
                    matricula.appendChild(alunoMat);

                    Element discMat = doc.createElement("disciplina_id");
                    discMat.appendChild(doc.createTextNode(String.valueOf(m.getDisciplina().getId())));
                    matricula.appendChild(discMat);

                    Element situacao = doc.createElement("situacaoFinal");
                    situacao.appendChild(doc.createTextNode(m.getSituacaoFinal().toString()));
                    matricula.appendChild(situacao);

                    matriculas.appendChild(matricula);
                }
                curso.appendChild(matriculas);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_PATH)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
