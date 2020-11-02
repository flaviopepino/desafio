A classe Desafio.scala incluí um novo pedido de um novo produto em automationpractice.com
Rodar rodar o teste é necessário o Gatling Open Source.
Para obter o Gatling Open Source na página www.gatling.io
Baixar o Gatling Open Source e colocar o arquivo Desafio.scala dentro da pasta do gatling user-files/simulations e user-files/resources para emails.csv 
que são as pastas padrões para configurar os testes em Gatling
Entrar no modo de linha de comando na pasta bin do gatling
e digitar o comando gatling.bat no Windows ou ./gatling.sh no Linux
Escolher a opção que representar o Desafio.scala
Ao rodar o teste é necessário descartar o email utilizado na execução anterior e criar um novo em emails.csv seguindo a 
numeração flavioNN@flavio.com onde NN deve ser incrementado (pois o site valida o email ao criar um novo cadastro).
Para rodar um teste de performance um cenário com M usuário é necessário criar os M e-mails no arquivo emails.csv e trocar o número de usuários 
virtuais no arquivo Desafio.scala na seção

	setUp(scn.inject(atOnceUsers(M))).protocols(httpProtocol) onde M é o número de usuário virtuais desejados.
	
atOnceUsers é uma das formas de iniciar os usuários no teste, em que todos usuários iniciam simultaneamente, há outras formas de iniciar os usuários virtuais 
também. 
Atenção, Se for rodado um teste em que o número de usuários virtuais for maior que o número de entradas (emails) no arquivo emails.csv 
ocorrerá um erro em tempo de execução e o teste for abortado, existe formas de contornas essa situação utilizando o método circular de leitura do arquivo,
entretanto a aplicação não permitiria reutilizar a entrada (email).


DESAFIO 2 - TESTE DA API
O teste foi criado em Gatling pois é uma ferramenta apropriada a gerar elevadas cargas que as APIs sofrem.
O número de usuários pode ser elevado gradualmente de acordo com a seção

  setUp(scn.inject(rampUsers(5) during (10 seconds)).protocols(httpProtocol))
  
  Esta configuração insere 5 usuários durante 10 segundos, ou seja, 1 usuário a cada 2 segundos. O Gatling permite outras formas de submeter a quantidade de
  usuários virtuais. Um número maior de usuários significa um maior consumo de recursos do computador que está gerando a carga, portanto é necessário 
  atenção a essa configuração. É possível ainda controlar a quantidade de iterações que cada usuários realiza e também a quantidade de requisições por segundo.
  Após algumas execuções do teste Desafio2.scala , a aplicação parece não responder mais que 7 requisições por segundos para um único cliente 
  conforme o relatório em anexo.desafio2-20201102203900271
  Poderia ser investigado a causa desse gargalo, quem sabe alguma limitação ativa do ambiente.
  
  Os resultados das execuções dos testes se encontram por padrão na pasta results do Gatling, é gerada uma pasta com prefixo do nome do cenário que foi executado
  seguido da data e horário da execução.
  




	
	
	


