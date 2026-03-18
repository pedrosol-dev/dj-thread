# Dj Thread

Projeto acadêmico desenvolvido em **Java com Spring Boot** para simular uma mesa de DJ, onde diferentes instrumentos executam simultaneamente em **threads independentes**.

## Descrição

O sistema representa uma mesa de DJ em que cada instrumento funciona como uma faixa musical separada.  
Cada faixa roda em sua própria thread, permitindo execução simultânea e controle individual por comandos de texto no terminal.

O usuário pode interagir com a aplicação pausando, retomando, consultando o status e encerrando os instrumentos sem afetar a execução dos demais.

## Objetivo

Aplicar conceitos de **programação concorrente** em Java, utilizando threads e mecanismos de sincronização para garantir o controle seguro dos estados de execução de cada instrumento.

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Maven
- Threads
- Sincronização com `synchronized`, `wait()` e `notifyAll()`

## Conceitos aplicados

- Programação concorrente
- Criação e gerenciamento de threads
- Execução simultânea de tarefas
- Controle de estado compartilhado
- Sincronização entre threads
- Encerramento seguro de processos
- Interação por linha de comando

## Funcionamento do sistema

Cada instrumento da mesa de DJ é executado em uma thread própria, simulando uma faixa musical em reprodução contínua.

A aplicação principal recebe comandos digitados pelo usuário no terminal e permite controlar individualmente cada instrumento.

### Exemplos de instrumentos
- Bateria
- Baixo
- Synth

### Exemplos de comandos
- `pausar bateria`
- `retomar baixo`
- `status`
- `encerrar`

## Estrutura do projeto

```bash
src/main/java/com/projetodj/
├── DjApplication.java
├── model/
│   └── Instrumento.java
├── service/
│   └── MesaDJService.java
└── runner/
    └── ConsoleRunner.java

##🧑‍💻Equipe

Projeto desenvolvido para fins acadêmicos.

- Pedro Sol
- Marcello Augusto
- Paulo Nery
- Heitor Didier
