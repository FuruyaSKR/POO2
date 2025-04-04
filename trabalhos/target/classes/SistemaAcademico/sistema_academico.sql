DROP DATABASE IF EXISTS sistema_academico;
CREATE DATABASE sistema_academico;
USE sistema_academico;

-- Enum para situação do aluno
CREATE TABLE SituacaoAlunoEnum (
    situacao ENUM('APROVADO', 'REPROVADO', 'MATRICULADO') PRIMARY KEY
);

-- Tabela de Cursos
CREATE TABLE Curso (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

-- Tabela de Alunos (sem vínculo direto com Curso)
CREATE TABLE Aluno (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

-- Tabela de Fases
CREATE TABLE Fase (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL,
    nome VARCHAR(100) NOT NULL
);

-- Associação Curso-Fase
CREATE TABLE Curso_Fase (
    curso_id INT NOT NULL,
    fase_id INT NOT NULL,
    PRIMARY KEY (curso_id, fase_id),
    FOREIGN KEY (curso_id) REFERENCES Curso(id),
    FOREIGN KEY (fase_id) REFERENCES Fase(id)
);

-- Tabela de Disciplinas
CREATE TABLE Disciplina (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    ofertada BOOLEAN NOT NULL,
    capacidadeMaxima INT NOT NULL
);

-- Tabela associativa entre Fase e Disciplina
CREATE TABLE Fase_Disciplina (
    fase_id INT NOT NULL,
    disciplina_id INT NOT NULL,
    PRIMARY KEY (fase_id, disciplina_id),
    FOREIGN KEY (fase_id) REFERENCES Fase(id),
    FOREIGN KEY (disciplina_id) REFERENCES Disciplina(id)
);

-- Tabela de Professores
CREATE TABLE Professor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

-- Relacionamento N:N entre Disciplina e Aluno
CREATE TABLE Disciplina_Aluno (
    disciplina_id INT,
    aluno_id INT,
    PRIMARY KEY (disciplina_id, aluno_id),
    FOREIGN KEY (disciplina_id) REFERENCES Disciplina(id),
    FOREIGN KEY (aluno_id) REFERENCES Aluno(id)
);

-- Relacionamento N:N entre Disciplina e Professor
CREATE TABLE Disciplina_Professor (
    disciplina_id INT,
    professor_id INT,
    PRIMARY KEY (disciplina_id, professor_id),
    FOREIGN KEY (disciplina_id) REFERENCES Disciplina(id),
    FOREIGN KEY (professor_id) REFERENCES Professor(id)
);

-- Tabela de Matrículas
CREATE TABLE Matricula (
    id INT PRIMARY KEY AUTO_INCREMENT,
    aluno_id INT NOT NULL,
    disciplina_id INT NOT NULL,
    curso_id INT NOT NULL,
    situacaoFinal ENUM('APROVADO', 'REPROVADO', 'MATRICULADO'),
    FOREIGN KEY (aluno_id) REFERENCES Aluno(id),
    FOREIGN KEY (disciplina_id) REFERENCES Disciplina(id),
    FOREIGN KEY (curso_id) REFERENCES Curso(id)
);

-- Tabela de Frequência
CREATE TABLE Frequencia (
    id INT PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
    presente BOOLEAN NOT NULL,
    professor_id INT NOT NULL,
    matricula_id INT NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES Professor(id),
    FOREIGN KEY (matricula_id) REFERENCES Matricula(id)
);

-- Tabela de Avaliação
CREATE TABLE Avaliacao (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nota DOUBLE NOT NULL,
    professor_id INT NOT NULL,
    matricula_id INT NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES Professor(id),
    FOREIGN KEY (matricula_id) REFERENCES Matricula(id)
);
