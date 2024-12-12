# Technical Case

<div align="center">
  <a href="https://www.java.com/pt-BR/" target="_blank" rel="noreferrer" rel="noopener">
    <img src="https://img.shields.io/badge/Java 17-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java"/>
  </a>
  <a href="https://spring.io/" target="_blank" rel="noreferrer" rel="noopener">
    <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring"/>
  </a>
  <a href="https://git-scm.com/" target="_blank" rel="noreferrer" rel="noopener">
    <img src="https://img.shields.io/badge/Git-E34F26?style=for-the-badge&logo=git&logoColor=white" alt="Git"/>
  </a>
  <a href="https://www.java.com/pt-BR/" target="_blank" rel="noreferrer" rel="noopener">
    <img src="https://img.shields.io/badge/JPA-ED9B00?style=for-the-badge&logo=java&logoColor=white" alt="Java"/>
  </a>
  <a href="https://www.java.com/pt-BR/" target="_blank" rel="noreferrer" rel="noopener">
    <img src="https://img.shields.io/badge/Docker-ED9B0?style=for-the-badge&logo=java&logoColor=white" alt="Java"/>
  </a>
</div></br>



> Trata-se de um case tecnico para demonstração de organização de commits no github, modelagem das entidades, funcionalidades do Framework e dos recursos mais novos da linguagem Java 17, testabilidade, manutenibilidade, etc.    

## ⚙️ Funcionalidades

- [x] Endpoint para cadastrar um cliente, com as seguintes informações: id (único), nome,
  número da conta (único) e saldo em conta;
- [x] Endpoint para listartodos os clientes cadastrados;
- [x] Endpoint para buscar um cliente pelo número da conta;
- [x] Endpoint para realizar transferência entre 2 contas. A conta origem precisa ter
  saldo suficiente para a realização da transferência e a transferência deve ser de no
  máximo R$ 100,00 reais;
- [x] Endpoint para buscar as transferências relacionadas à uma conta, por ordem de
  data decrescente. Lembre-se que transferências sem sucesso também devem ser
  armazenadas e retornadas.

## ⚙️ Outros requisitos
1. Solução desenvolvida em Java 11 ou superior;
2. Maven ou Gradle como gerenciador de dependências;
3. Banco de dados in memory;
4. Controle de concorrência na operação de transferência;
5. Utilize corretamente os padrões de códigos de resposta HTTP (status code) para as
   APIs;
6. Error Handling;
7. Controle de versão das APIs;
8. Testes unitários;
9. Testes integrados;
10. Documentação no código;
11. Readme.md com a documentação de como utilizar a aplicação.

## 🚀 Como executar e testar

Passo-a-passo para execução e teste local na IDE

- Clonar o projeto no github
- Abrir o projeto como projeto maven na sua IDE de preferência
- Executar o progama localmente na sua IDE

Passo-a-passo para execução e teste local no docker

- Baixe a imagem do DockerHub: 
  - docker pull atiladocker/case-itau:latest
- Baixe a imagem do DockerHub:
    - docker run -p 8080:8080 atiladocker/case-itau:latest
- Acesse a aplicação em `http://localhost:8080`.
