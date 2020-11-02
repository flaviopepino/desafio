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




	
	
	


