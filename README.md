<div align="center">
  <img src="https://github.com/igorjuancc/BiblioDoc/blob/main/sijoga-sosifod/img/logoSijoga.PNG" width="160vw" height="160vh" />
</div>

# Sistema Jurídico On-line Para Grandes Administrações
O Sistema Jurídico On-line Para Grandes Administrações - SIJOGA, é uma aplicação WEB desenvolvida para automatizar e protocolar processos jurídicos. A plataforma disponibiliza o cadastro dos perfis de advogados, juizes e das partes envolvidas em um processo (Promovente e Promovido); o cadastro de processos e suas fases, o anexo de documentos referêntes a cada fase quando necessário, a seleção automática de juizes conforme a demanda, a solicitação de intimações judiciais e a indicação do vencedor e o parecer final indicado pelo juiz conforme encerramento do processo.    
O SIJOGA possuí integração com a aplicação [Sistema Online Sobre Informações Factíveis de
Oficiais de Justiça Do Paraná - SOSIFOD](https://github.com/igorjuancc/sosifod) via web service para a solcitação de intimações judiciais, obtenção da lista de oficiais de justiça disponíveis e o cadastro de novas fases do processo referêntes as intimações requisitadas.

<!--ts-->
   * [Resumo](#Sistema-Jurídico-On-line-Para-Grandes-Administrações)
   * [Índice](#Índice)
   * [Começando](#Começando)
       * [Pré-requisitos](#Pré-requisitos)
         * [Obrigatórios](#Obrigatórios)
           * [Softwares](#Softwares)
           * [Bibliotecas](#Bibliotecas)
         * [Opcionais](#Opcionais)
   * [Configuração](#Configuração)
   * [Utilização do SIJOGA](#Utilização-do-SIJOGA)
   * [Guias](#Guias)
   * [Sobre o Projeto](#Sobre-o-Projeto)
       * [Documentação](#Documentação)
       * [Técnologias](#Técnologias)
       * [Ferramentas](#Ferramentas)
   * [Autores](#Autores) 
      
<!--te-->

# Começando

## Pré-requisitos

Informações sobre servidor ou ferramentas para execução do projeto podem ser encontradas na seção [Guias](#Guias) desse documento.

### Obrigatórios

#### Softwares

* [JRE - Java Runtime Environment 8 (Ou superior)](https://www.java.com/pt-BR/download/manual.jsp)    
* [JDK - Java Development Kit 8 (Ou superior)](https://www.oracle.com/br/java/technologies/javase/javase8-archive-downloads.html)    
* [PostgreSQL 10 (Ou superior)](https://www.postgresql.org/download/)  
* [GlassFish 4.1](https://download.oracle.com/glassfish/4.1/release/index.html) 
* [Netbeans 8.2 (Ou superior)](https://netbeans.apache.org/download/archive/index.html) 
    * Ou outra IDE de sua preferência

#### Bibliotecas

* [JSF 2.2](https://mvnrepository.com/artifact/com.sun.faces/jsf-api/2.2.20) 
* [Hibernate 4.3.1](https://mvnrepository.com/artifact/org.hibernate/hibernate-core/4.3.1.Final)
* [JasperReports 6.13](https://community.jaspersoft.com/project/jasperreports-library/releases)
    * [Commons BeanUtils 1.9.4](https://mvnrepository.com/artifact/commons-beanutils/commons-beanutils/1.9.4)
    * [Commons Collections 4.4.4](https://mvnrepository.com/artifact/org.apache.commons/commons-collections4/4.4)
    * [Commons Digester 2.1](https://mvnrepository.com/artifact/commons-digester/commons-digester/2.1)
    * [Commons Logging 1.2](https://mvnrepository.com/artifact/commons-logging/commons-logging/1.2)
    * [IText 2.1.7](https://mvnrepository.com/artifact/com.lowagie/itext/2.1.7)
    * [JasperReports 6.13](https://mvnrepository.com/artifact/net.sf.jasperreports/jasperreports/6.13.0)
    * [Joda Time 2.4](https://mvnrepository.com/artifact/joda-time/joda-time/2.4)
* [PrimeFaces 7.0](https://www.primefaces.org/downloads/) 
* [Commons FileUpload 1.4](https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload/1.4) 
* [Commons IO 2.6](https://mvnrepository.com/artifact/commons-io/commons-io/2.6) 
* [JavaMail API 1.6.0](https://mvnrepository.com/artifact/javax.mail/javax.mail-api/1.6.0) 
* [JDBC Driver Postgresql 9.4.1209](https://repo1.maven.org/maven2/org/postgresql/postgresql/9.4.1209/) 
* [OmniFaces 2.7.2](https://mvnrepository.com/artifact/org.omnifaces/omnifaces/2.7.2) 

### Opcionais

* [Git 2.33 (Ou superior)](https://git-scm.com/downloads)
* [Apache Ant 1.10.7 (Ou superior)](https://ant.apache.org/easyant/download.cgi)

## Configuração

1. Para executar o projeto, efetue o download ou o colone desse repositório.
```
git clone https://github.com/igorjuancc/sijoga
```
2. Acesse o PostgreSQL via psql ou pgAdmin e crie uma nova base de dados com o nome **SIJOGA**.
    
    2.1 Utlize o arquivo **BDSijoga.sql** localizado na pasta raiz desse projeto para criar as tabelas e os inserts necessários para utilizar a aplicação.
     
3. Abra o projeto clonado com o NetBeans (ou a IDE de sua preferência) e efetue a importação de **todas** as bibliotecas indicadas na seção [Obrigatórios](#Obrigatórios) desse repositório.
4. Abra o arquivo hibernate.cfg.xml localizado no pacote default do projeto e modifique as propriedades de conexão com as informações pertinentes ao seu computador ou ao servidor utilizado para o deploy da aplicação.
5. Ainda na IDE utilizada, modifique as seguintes linhas dos arquivos:  
 * RelatorioFacade.java do pacote br.com.sijoga.facade:
     * Linha 21 com endereço de host onde essa aplicação estará funcionando
 * IntimacaoFacade.java do pacote br.com.sijoga.facade:
     * Linhas 57 e 79 com endereço de host onde a aplicação [SOSIFOD](https://github.com/igorjuancc/sosifod) estará funcionando
     
6. Com o auxilio do Netbeans (ou da IDE utilizada) ou do Apache Ant, crie o arquivo .war da aplicação.

    6.1  Com o Apache Ant dentro da pasta do projeto.
    ```
     $ ant
    ```
    
7. Copie o arquivo SGR.war da pasta "dist" para a pasta autodeploy do servidor glassfish ou insira via interface gráfica do console do servidor.
```
cd ~/sijoga/dist
cp SIJOGA.war ~/glassfish4/glassfish/domains/domain1/autodeploy
```
8. Inicie (ou reinicie) o servidor da aplicação e acesse o projeto no navegador de acordo com as configurações do glassfish, normalmente http://localhost:8080/sijoga/index.jsf.

## Utilização do SIJOGA

1. A aplicação SIJOGA inicia-se na tela de index do projeto, na qual existem três botões para as seguintes funcionalidades da aplicação: Cadastros de novos perfis: "Novo Advogado" e "Novo Juiz". E "Login", esse redireciona para a tela onde o usuário irá inserir suas informações para acessar a plataforma.

<div align="center">
  <h3>Index</h3>
  <img height="300vh" width="600vw" src="https://github.com/igorjuancc/BiblioDoc/blob/main/sijoga-sosifod/img/indexSijoga.PNG" />   
</div>

2. O SIJOGA possuí três tipos de perfis:

    2.1 Advogado: Que tem acesso aos processos em que está envolvido, registra fases e processos, emite relatórios e cadastra novas partes(clientes).
    
    2.2 Juiz: Que tem acesso a uma lista dos processos aos quais está alocado, interage com as fases dos processos, solicita intimações e encerra os processos.
    
    2.3 Parte: Que tem acesso aos seus processos e pode também emitir relatórios com informações e resutados dos processos.
   
    <table>
      <tr align="center">
        <th>Login</th>
        <th>Home Advogado</th>
      </tr>
      <tr>
        <td>
          <img height="250vh" width="600vw" src="https://github.com/igorjuancc/BiblioDoc/blob/main/sijoga-sosifod/img/loginSijoga.PNG" />
        </td>
        <td>
          <img height="250vh" width="600vw" src="https://github.com/igorjuancc/BiblioDoc/blob/main/sijoga-sosifod/img/homeAdvSijoga.PNG" />
        </td>
      </tr>  
    </table> 

3. E necessário cadastrar ao menos dois advogados, duas partes e um juiz para utilização do sistema.
4. Para utilizar o recurso de intimações é necessário que a aplicação [SOSIFOD](https://github.com/igorjuancc/sosifod) esteja em correto funcionamento.
5. A pasta "jasper" desse projeto possuí os arquivos fontes dos relatórios dessa plataforma, podendo ser modificados com o [Jaspersoft® Studio](https://community.jaspersoft.com/project/jaspersoft-studio).

## Guias
>[Guia GlassFish 4.1](https://github.com/igorjuancc/guia/blob/main/Servidores/GlassFish/4.1/glassfish-4.1.md) 

>[Guia Apache Ant 1.10.7](https://github.com/igorjuancc/guia/blob/main/Automacao(build)/ApacheAnt/1.10.7/apacheant-1.10.7.md) 

## Sobre o Projeto
O projeto SIJOGA foi desenvolvido como requisito de avaliação parcial da disciplina DS150 - Desenvolvimento de Aplicações Corporativas, do curso de Tecnólogia em Análise e Desenvolvimento de Sistemas, do Setor de Educação Profissional e Tecnológica, da Universidade Federal do Paraná, sob orientação do Prof. Dr. Razer Anthom Nizer Rojas Montaño.

### Documentação 

A descrição e os detalhes da atividade podem ser encontrados no seguinte [repositório](https://github.com/igorjuancc/BiblioDoc/blob/main/sijoga-sosifod/Trabalho%202020a%20-%20Sistemas%20Jur%C3%ADdicos.pdf).

### Técnologias  
 * [Java EE](https://www.oracle.com/br/java/technologies/java-ee-glance.html)
 * [JSF](https://netbeans.apache.org/kb/docs/web/jsf20-intro_pt_BR.html#:~:text=O%20JavaServer%20Faces%20(JSF)%20%C3%A9,a%20um%20cliente%20de%20destino.)
 * [Hibernate](https://hibernate.org/)
 * [PrimeFaces](https://www.primefaces.org/)
 * [Start Bootstrap](https://github.com/StartBootstrap/startbootstrap-sb-admin-2)
 
### Ferramentas
 * [Netbeans](https://netbeans.apache.org/)
 * [PgAdmin](https://www.pgadmin.org/)
 * [PostgreSQL](https://www.postgresql.org/)
 * [Jaspersoft Studio](https://community.jaspersoft.com/project/jaspersoft-studio)
 * [Git](https://git-scm.com/) e [GitHub](https://github.com/)
 * [Apache Ant](https://ant.apache.org/) 
 * [GlassFish](https://javaee.github.io/glassfish/)
    
## Autores

### Front-end 

<table>  
  <tr align="center">    
    <td>      
      <img src="https://avatars.githubusercontent.com/u/29365473?v=4" width="100px;" alt="Fernanda" />
    </td>
    <td>      
       <img src="https://avatars.githubusercontent.com/u/21203047?v=4" width="100px;" alt="Gisele" />
    </td>
  </tr>  
  <tr align="center">    
    <td>
      <a href="https://github.com/fernandaCarvalhoSilva" target="_blank">Fernanda Silva</a>           
    </td>  
    <td>
      <a href="https://github.com/giselegomes" target="_blank">Gisele Gomes</a>                   
    </td>
  </tr> 
</table>

### Back-end

<table>  
  <tr align="center">    
    <td>      
      <img src="https://avatars.githubusercontent.com/u/50890812?s=400&u=566e615dd1691c75eabd1dcb4ba749be82d1e86c&v=4" width="100px;" alt="Igor Juan" />      
    </td>
    <td>     
      <img src="https://avatars.githubusercontent.com/u/12835252?v=4" width="100px;" alt="Matheus" />      
    </td>
  </tr>  
  <tr align="center">    
    <td>
      <a href="https://github.com/igorjuancc" target="_blank">Igor Juan</a>                  
    </td>  
    <td>
      <a href="https://github.com/mattewar" target="_blank">Matheus Warkentin</a>                        
    </td>
  </tr> 
</table>

Em caso de dúvidas, sugestões e informações, entre em contato: <br /> 
<a href="https://br.linkedin.com/in/igor-juan-cordeiro-da-costa-2b4a77101" target="_blank"> <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"> </a>
<a href="https://www.facebook.com/igorjuan.cordeirodacosta" target="_blank"> <img src="https://img.shields.io/badge/Facebook-1877F2?style=for-the-badge&logo=facebook&logoColor=white" target="_blank"> </a>
<a href="https://twitter.com/zig_cwb" target="_blank"> <img src="https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white" target="_blank"> </a>
<a href="https://github.com/igorjuancc" target="_blank"> <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" target="_blank"> </a>
