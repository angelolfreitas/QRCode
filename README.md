# Gerador de QRCode
Esse projeto foi feito para exercitar conceitos de REST a partir do framework SpringBoot.
O projeto utiliza do recurso AWS para armazenar os posts da aplicação e permitir o acesso dos objetos gerados pelas requisições HTTP.
Utilizei inicialmente o JDK 25, mas existiam algumas dependências mais antigas que impediam a execução do programa (então, rebaixei para 22).

Utilizei docker para garantir a execução do programa em qualquer outra máquina e maven para gestão de dependências, e testei as requisições com o
insomnia.

O código fonte está totalmente documentado. Anotei outras coisas sobre conceitos de REST e RESTfull e outras técnicas em uma página do notion (acesse por esse
link: https://www.notion.so/SpringBoot-HTTP-290aafd3bbb78062a29bd25c797e8582?source=copy_link).

# 1: Como executar a aplicação
Para compilação e execução da aplicação, recorri ao Docker para garantir a execução bem sucedida em qualquer máquina. Possíveis problemas na compilação
devem ser associados às definições relacionadas ao AWS.
Você precisa do docker instalado para fazer os passos seguintes, e de um bucket no AWS.
Abaixo, os passos que você deve seguir para executar ou modificar a aplicação:
## 1.1: Você só quer executar
Você pode executar essa aplicação pelo hub do docker sem precisar deste repositório.
Basta criar um bucket no aws, das suas credenciais e de um arquivo .env com essas variáveis de ambiente:
```bash
AWS_ACCESS_KEY_ID=123...
AWS_SECRET_ACCESS_KEY=456...
ENV AWS_REGION=tua-regiao
ENV AWS_BUCKET_NAME=nome-do-teu-bucket
```
Depois, chame a docker image do meu perfil no dockerhub:
```bash
docker run --env-file .env -p 8080:8080 angelofr/qrcode-gen:3.1 
```
Isso vai executar a docker image no meu perfil do docker hub, passando como argumento o .env que você criou e associa a porta local do container à sua.
Pronto, o container foi criado a partir da docker image e estará em execução até que você interrompa.
Você pode testar com o insomnia ou alguma aplicação semelhante. Basta mandar uma requisição de post nesse formato:
<img width="729" height="206" alt="image" src="https://github.com/user-attachments/assets/a26a6f9d-a2d6-4e7a-a455-d0a28fd8ed3e" />

O link vai te redirecionar para o valor associado à chave gerada pela aplicação, que é guardado no AWS e está em formato PNG. Você não é obrigado a encriptar links, pode ser qualquer tipo de texto mesmo.


## 1.2: Você quer mexer no código fonte
Instale o repositório, manipule o que quiser. Para executar, prossiga dessa forma:

### 1.2.1: Passos
Crie um arquivo .env com suas credenciais do AWS:
```bash
AWS_ACCESS_KEY_ID=123...
AWS_SECRET_ACCESS_KEY=456...
AWS_REGION=tua-regiao
AWS_BUCKET_NAME=nome-do-teu-bucket
```
Por fim, compile a dockerimage:
```bash
docker build -t nome-da-docker-image:versão .
```
E depois execute por um container:
```bash
docker run --env-file .env -p 8080:8080 nome-da-docker-image:versão
```
### 1.2.2: Explicação
Você precisa passar como argumento as suas credenciais do amazon AWS. Para isso, você só precisa criar um arquivo .env com o seguinte formato:
```bash
AWS_ACCESS_KEY_ID=123...
AWS_SECRET_ACCESS_KEY=456...
AWS_REGION=tua-regiao
AWS_BUCKET_NAME=nome-do-teu-bucket
```
Troque esses valores pela access key e pela secret do seu usuário do AWS (você deve achar na seção IAM do console do AWS).
Eles vão ser requeridos pelo sistema do AWS para postar e recuperar os objetos (não são variáveis da aplicação).

Por fim, você precisa ter criado um bucket no client S3 do AWS. Esse bucket precisa ter uma política que permita o post e o get de objetos no servidor.
Crie esse bucket e troque aqueles campos de region e bucket name no arquivo .env pelos seus.
ELes atualmente estão preenchidos com a região e o nome do meu bucket.

Eles vão ser necessários na aplicação na implementação do (`StoragePort.java`) para o AWS (em questão, (`StorageAdapter.java`)). São usados pelo método (`uploadFIle()`)para publicar 
arquivos (em forma de arrays de bytes) e associá-los a um nome de acesso (em questão, o parâmetro fileName dess método).
Pronto, a configuração das variáveis de ambiente está feita.

Agora, você precisa fazer com que o jar seja executado com esses parâmetros (então, você precisa buildar a imagem do docker, usada para gerar os containers qe executam a aplicação em si).
Rode o comando:
```bash
docker build -t nome-da-docker-image:versão .
```
Isso vai gerar uma docker image com o nome à esquerda dos ":", e com a versão à direita. (o ponto é importante, pois ele vai compilar todos os arquivos vistos no script do docker).
Espere a docker image ser gerada e, por fim, execute o container a partir dela:
```bash
docker run --env-file .env -p 8080:8080 nome-da-docker-image:versão
```
Pronto. Para confirmar que o container está rodando, você pode abrir outro terminal e digitar:
```bash
docker ps
```
Deve aparecer as informações da docker image que você gerou.
Agora, você pode testar as requisições com alguma aplicação como o insomnia.

## 2: Avisos
Caso você coloque isso em um repositório, adicione o .env ao .gitignore para que suas credenciais do AWS não sejam expostas.

