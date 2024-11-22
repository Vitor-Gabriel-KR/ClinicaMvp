
# Projeto MVP de Sistema de Clínica


Este é um projeto de MVP para um sistema de gerenciamento de clínica, desenvolvido como parte de um teste para entrevista de emprego. O sistema possui funcionalidades como cadastro de médicos, pacientes, agendas e consultas.

## Como Rodar o Projeto

voce deve ter o PostgreSql Instalado e ter a porta localhost:5432 Livre pra isso e depois :

Passo 1: Rodar o Backend (Java)
Clone o repositório para sua máquina local.
Navegue até a pasta do backend.
Execute o comando para rodar o backend:

```bash
mvn spring-boot:run
```
    
 Caso o comando não rode basta rodar diretamente pelo Botão de ação 

Passo 2: Rodar o Frontend (Vue.js)
Entre na pasta ClinicaMvp:

```bash
cd ClinicaMvp
```
instale as dependências do projeto:
```bash
npm install
```
Rode o servidor de desenvolvimento:
```bash
npm run dev
```
## Tecnologias e Ferramentas do MVP

## Front-end

- **Vue.js**: 3.4.0 (última versão estável de 2024)
- **Vue Router**: Compatível com Vue.js 3.4.0
- **Axios**: 1.5.0 (última versão estável)
- **Vuetify**: 3.2.x (compatível com Vue 3.4.0)
- **BootstrapVue**: 2.23.1 (última estável para Bootstrap 4)

## Back-end

- **Java Spring Boot**: 3.2.0
- **Spring Web**: Parte do Spring Boot 3.2.0
- **Spring Data JPA**: Parte do Spring Boot 3.2.0
- **PostgreSQL Driver**: 42.6.0

## Banco de Dados

- **PostgreSQL**: 15.4

## Testes no Back-end

- **JUnit**: 5.10.0 (Jupiter)

## Outras Ferramentas

- **IDE para Desenvolvimento Back-end**:
  - IntelliJ IDEA Community Edition: 2023.2.3
  - Visual Studio Code: 1.85.0
- **Postman**: 10.18.0
- **Git**: 2.42.0

## Estrutura do Projeto MVP

## 1. Front-end (Vue.js)

- Usar o framework **Vue.js** para criar uma interface dinâmica e responsiva.
- Componentes reutilizáveis para cada tela.
- Comunicação com o backend via **API REST**.

## 2. Back-end (Java Spring)

- Implementar a lógica de negócio usando **Spring Boot**.
- Criar endpoints **RESTful** para gerenciar médicos, agendas, pacientes e consultas.
- Utilizar **Spring Data JPA** para interagir com o banco de dados **PostgreSQL**.

## 3. Banco de Dados (PostgreSQL)

- Tabelas para armazenar médicos, agendas, pacientes e consultas.
- Relacionamentos configurados via **ORM**.

## 4. Testes (JUnit)

- Escrever testes unitários para validar os métodos do backend.

## Etapas de Desenvolvimento

### 5. Cadastro de Médicos

- **Campos do Formulário**:
  - Nome do médico
  - Conselho (ex.: CRM)
  - Estado

- **Front-end**:
  - Formulário simples com validação.
  - Botão para salvar e listar médicos já cadastrados.

- **Back-end**:
  - Endpoint `POST /medicos` para salvar médicos.
  - Endpoint `GET /medicos` para listar médicos.

- **Banco de Dados**:
  - Tabela `medicos` com campos `id`, `nome`, `conselho`, `estado`.

### 6. Cadastro de Agendas

- **Campos do Formulário**:
  - Dias da semana
  - Horários disponíveis
  - Médico vinculado

- **Front-end**:
  - Interface para selecionar dias e horários usando checkboxes e time pickers.
  - Dropdown para selecionar o médico.

- **Back-end**:
  - Endpoint `POST /agendas` para salvar a agenda.
  - Endpoint `GET /agendas` para listar agendas.

- **Banco de Dados**:
  - Tabela `agendas` com campos `id`, `medico_id`, `dias_semana`, `horarios_disponiveis`.

### 7. Cadastro de Pacientes

- **Campos do Formulário**:
  - Nome completo
  - Data de nascimento
  - Telefone
  - Endereço

- **Front-end**:
  - Formulário com validação de campos obrigatórios.

- **Back-end**:
  - Endpoint `POST /pacientes` para cadastrar pacientes.
  - Endpoint `GET /pacientes` para listar pacientes.

- **Banco de Dados**:
  - Tabela `pacientes` com campos `id`, `nome`, `data_nascimento`, `telefone`, `endereco`.

### 8. Cadastro de Consultas

- **Campos do Formulário**:
  - Agenda selecionada
  - Paciente
  - Data/hora

- **Front-end**:
  - Dropdowns para selecionar agenda e paciente.
  - Validação para horários disponíveis.

- **Back-end**:
  - Endpoint `POST /consultas` para cadastrar consulta.
  - Endpoint `GET /consultas` para listar consultas futuras.

- **Banco de Dados**:
  - Tabela `consultas` com campos `id`, `agenda_id`, `paciente_id`, `data_hora`.
