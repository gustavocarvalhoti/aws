<h1>Treinamento AWS:.</h1>

<h2>Pilares:</h2>
<p>
Segurança. <br>
Eficiência de performance.<br>
Confiabilidade.<br>
Excelência operacional.<br>
Otimização de custos.
</p>

<h2>É indicado fazer dupla autenticação.</h2>

![img_5.png](imagens/img_5.png)<br>

<h2>URLS:</h2>
<p>
https://aws.amazon.com/pt/console/<br>
https://console.aws.amazon.com<br>
https://github.com/cassianobrexbit/dio-live-coding-aws<br>
https://calculator.aws <- Faz as estimativas de preço<br>
https://www.freenom.com/pt/index.html<br>
https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html<br>
</p>

<h2>Significados:</h2>
<p>
RDS: Banco de dados relacional.<br>
DynamoDB: NoSQL.<br>
EC2: Elastic computer - maquina virtual.<br>
S3: Armazenamento de arquivos.<br>
Cognito: Poll de usuários, garantindo as permissões, rules, validação de emails entre outras coisas.<br>
API Gateway: HTTP Api (Mais simples) - Criar as rotas - Ele cria o endpoint para acessar a Lambda Function.<br>
Route: API_GATWEWAY/items (post/get) - Cadastrar os 2 lá e integrar com a Lambda Function.<br>
EBS - Disco rígido na AWS.<br>
VPC - Rede virtual da AWS.<br>
ECR - Elastic Container Registry (Repositório Privado)<br>
CloudShell - Maquina virtual linux para interagir com o ambiente AWS<br>
# aws s3 ls <br>
</p>

<h2>Security Group: A maquina está na AWS, ele é o firewall.</h2>

<p>

![img_1.png](imagens/img_1.png)<br>
Key pair: Da acesso a maquina virtual, não perder o arquivo aws-gus.pem<br>
Add a rule HTTP -> 0.0.0.0/0, ::/0<br>

![img_11.png](imagens/img_11.png)
![img_12.png](imagens/img_12.png)

</p>

<h2>Lambda Functions:</h2>
<p>
- Cria pequenas funções com node.<br>
- Pode ser container e codigo zip.<br>
- Selecionei - Basic Info - Scratch - Node.js - Create function - Vai gravar no DynamoDB, verificar o arquivo index.js.<br> 
</p>

<h2>Mão na massa:</h2>
<p>
Criarei um EC2 (img.png)<br>

![img.png](imagens/img_0.png)<br>
A maquina virtual e disco são cobrados separados.<br>
</p>

x<h2>Acessar a EC2:</h2>
<p>
Selecionar ela no console e depois conectar e selecionar Cliente SSH<br>

![img_2.png](imagens/img_2.png)<br>

Copiar a chave para esse diretorio e rodar o comando abaixo<br>
`chmod 400 aws-gus.pem` (Mudar o acesso de leitura)<br>
`ssh -i "aws-gus.pem" ec2-user@ec2-34-229-142-62.compute-1.amazonaws.com`<br>
</p>

![img_3.png](imagens/img_3.png)<br>

<h2>É indicado proteger contra encerramento.</h2>

![img_4.png](imagens/img_4.png)<br>

<h2>Criar script de inicialização EC2.</h2>

<p>
Advanced Details:<br>

![img_10.png](imagens/img_10.png)<br>

````
#!/bin/bash                                                   <- Provisionar o ambiente
yum update -y                                                 <- Faz o update e responde y para continuar
amazon-linux-extras install -y lamp-mariadb10.2-php7.2 php7.2 <- Add esses repositórios
yum install -y httpd mariadb-server                           <- Install apache e MySQL
yum install -y php7.2
systemctl start httpd                                         <- Start apache
systemctl enable httpd                                        <- Inicia automatico quando reiniciar
systemctl start mariadb                                       <- Start apache
systemctl enable mariadb                                      <- Inicia automatico quando reiniciar
usermod -a -G apache ec2-user                                 <- Alterar permissões Apache
chown -R ec2-user:apache /var/www
````

`netstat -ltun <- Verificar se o que está rodando (Entrar via ssh)` <br><br>
![img_9.png](imagens/img_9.png)

Verificando se deu certo (Ele abre a pagina inicial do Apache) -> Public IPv4 address -> 35.175.186.61 <br>
![img_13.png](imagens/img_13.png) <br>

Criando uma imagem a partir dessa instancia: image and templates - create image - create image<br>
Verificar em: Images - AMIs<br>
![img_15.png](imagens/img_15.png) <br>
Agora podemos criar um EC2 dessa imagem.<br>
![img_16.png](imagens/img_16.png)<br>
</p>

<h2>Liberar comunicação entre duas EC2.</h2>
<p>
Change security groups - Verificar que só tem o de acesso remoto por enquanto. <br>

![img_6.png](imagens/img_6.png)<br>
O grupo default é criado com o EC2 para comunicaçao interna. <br>
Adicionar o default nos dois EC2. <br>
![img_7.png](imagens/img_7.png)<br>
Agora eles conseguem se comunicar.<br>
![img_8.png](imagens/img_8.png)<br>
</p>

<h2>Atribuir IP Fixo EC2.</h2>
<p>
Network & Security - Elastic IPs <br>
Pode associar a 1 instancia sem cobrar, SE A INSTANCIA ESTIVER STOP SERÁ COBRADO.<br>

![img_17.png](imagens/img_17.png)<br>
Agora precisa associar so EC2 que deseja.<br>
![img_18.png](imagens/img_18.png)
</p>

<h2>RDS.</h2>
<p>
Create database - Standard create - MySQL - MySQL 5.7.22 - Free tier - Create database <br>
Acessei o EC2 web-dev2 e dele eu consigo acessar o MySQL - g*****2 <br>
</p>

````
mysql -u admin -h database-1.cgsaefhkgnsu.us-east-1.rds.amazonaws.com -p
show databases;
create database cadastro;
````

<h2>EC2 apontando para o RDS:</h2>

````
sudo systemctl stop mariadb                                   <- Parar
sudo systemctl disable mariadb                                <- Remover o auto start
cd /var/www/
mkdir cadastro
cd cadastro/
vi dbinfo.cadastro      <- Verificar o arquivo cadastro/dbinfo.cadastro
cd ../html/
vi index.php            <- Verificar o arquivo cadastro/index.php
Vai subir o projeto abaixo já apontando para o RDS com ajuda do PHP.
sudo shutdown -h now    <- Parar linux
````

![img_19.png](imagens/img_19.png)

<h2>Escalando</h2>
<p>
O usuário chega na AWS pela 80 ou 443 e o load balance distribui. <br>
Você pode gerenciar pela porta 22. <br>

![img_20.png](imagens/img_20.png)<br>

Load Balancing - Load Balancers - Create Load Balancer - Http/Https<br>
Selecionar no mínimo duas -><br>
![img_21.png](imagens/img_21.png)<br>
![img_22.png](imagens/img_22.png)<br>
Deixar a imagem sem preencher por enquanto, ela será preenchida pelo serviço que escala.
![img_23.png](imagens/img_23.png)<br>
Auto Scaling - Launch Configurations - Create - Selecionar a AMIs<br>
![img_24.png](imagens/img_24.png)<br>
![img_25.png](imagens/img_25.png)<br>
![img_26.png](imagens/img_26.png)<br>
![img_27.png](imagens/img_27.png)<br>
![img_28.png](imagens/img_28.png)<br>
![img_29.png](imagens/img_29.png)<br>
![img_31.png](imagens/img_31.png)<br>
![img_32.png](imagens/img_32.png)<br>
![img_33.png](imagens/img_33.png)<br>
![img_34.png](imagens/img_34.png)<br>
Se eu terminar um EC2 ele cria outro automaticamente. <br>
</p>

<h2>Gerenciar ambiente</h2>

````
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
aws --version
aws configure
aws ec2 help
aws ec2 describe-instances | more
aws ec2 describe-instances --instance-id id-da-instancia
aws ec2 start-instances --instance-ids i-idddddadas
aws ec2 stop-instances --instance-ids i-idddddadas
````

<p>
Criar usuário para o terminal.<br>

![img_35.png](imagens/img_35.png)<br>
![img_36.png](imagens/img_36.png)<br>
![img_37.png](imagens/img_37.png)<br>
![img_38.png](imagens/img_38.png)<br>
![img_39.png](imagens/img_39.png)<br>
![img_40.png](imagens/img_40.png)<br>
</p>

<h2>Amazon Lightsail (Digital Ocean é parecido), configura mais fácil os serviços</h2>
<p>
Ele monta e sobe muito rápido o ambiente<br>
A porta 22 é do acesso ssh<br>

![img_41.png](imagens/img_41.png)<br>
Tem muitas imagens prontas<br>
![img_42.png](imagens/img_42.png)<br>
![img_43.png](imagens/img_43.png)<br>
Acessando pelo terminal<br>
![img_44.png](imagens/img_44.png)<br>
chmod u+x wp1.sh <- Permissão para executar<br>
![img.png](imagens/img_59.png)<br>
![img_1.png](imagens/img_46.png)<br>
É possível criar uma nova instância a partir de um snapshot.<br>
Através deste recurso você tem um processo de recover bem simples, bastando apenas criar uma nova instância baseada em
seu snapshot.<br>
</p>

<h2>Cloud Watch: Monitorar EC2 e aplicações</h2>
<p>
Concentra eventos em lote<br>
Podemos criar gráficos e ações por ele<br>
Mostra quanto vai pagar abaixo:<br>

![img_2.png](imagens/img_47.png)<br>
Criar alarme de custo<br>
![img_4.png](imagens/img_49.png)<br>
![img_5.png](imagens/img_50.png)<br>
Ciar um S3 para o exemplo:<br>
![img_6.png](imagens/img_51.png)<br>
Subi as imagens da pasta cloudwatch-site/imagens<br>
![img_7.png](imagens/img_52.png)<br>
Criei o EC2 agora<br>
![img_8.png](imagens/img_53.png)<br>
Configurando o EC2 <br>

````
sudo apt-get update -y
sudo apt-get install apache2 -y
sudo groupadd www                   <- Cria o grupo www
sudo usermod -a -G www ubuntu       <- Coloca o usuario ubuntu no grupo www
sudo chown -R root:www /var/www     <- Muda as permissões do diretorio
sudo chmod -R 777 /var/www/html     <- Dar permissão de escrita e leitura
cd /var/www/html
copiar os arquivos da pasta /cloudwatch-site/html com o filezilla
````

Para acessar as imagens public<br>
![img_9.png](imagens/img_54.png)<br>

````
{
    "Version": "2012-10-17",
    "Statement": [
        {
        "Sid": "AddPerm",
        "Effect": "Allow",
        "Principal": "*",
        "Action": "s3:GetObject",
        "Resource": "arn:aws:s3:::gcdsilv/imagens/*"
        }
    ]
}
````

Testando a URL: https://gcdsilv.s3.amazonaws.com/imagens/1.jpg <br>
Conectando com o Filezilla<br>
![img_10.png](imagens/img_55.png)<br>
![img_11.png](imagens/img_56.png)<br>
Agora o EC2 aponta para as imagens que estão no S3.<br>
![img_12.png](imagens/img_57.png)<br>
Testando<br>

````
sudo apt-get install stress-ng
````

Rodando o teste durante 5 minutos dentro do EC2<br>
![img_13.png](imagens/img_58.png)<br>
Criar alarme EC2 CPU Utilization:<br>
![img.png](imagens/img_61.png)<br>
![img_1.png](imagens/img_60.png)<br>
![img_2.png](imagens/img_62.png)<br>
Tem como receber por SMS<br>
![img_3.png](imagens/img_63.png)<br>
![img_4.png](imagens/img_64.png)<br>
Alarme quando a imagem parar<br>
![img_5.png](imagens/img_65.png)<br>
![img_6.png](imagens/img_66.png)<br>
![img_7.png](imagens/img_67.png)<br>
Logs:<br>
![img_8.png](imagens/img_68.png)<br>
Criar um usuário para enviar os logs para o Cloud Watch<br>
![img_9.png](imagens/img_69.png)<br>

````
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents",
        "logs:DescribeLogStreams"
    ],
      "Resource": [
        "arn:aws:logs:*:*:*"
    ]
  }
 ]
}
````

![img_10.png](imagens/img_70.png)<br>
![img_11.png](imagens/img_71.png)<br>
![img_12.png](imagens/img_72.png)<br>
Verificar caso precise no futuro<br>
![img_13.png](imagens/img_73.png)<br>
![img_14.png](imagens/img_74.png)<br>
AWS Lambda, roda o script de tempos em tempos.<br>
![img_15.png](imagens/img_75.png)<br>
![img_16.png](imagens/img_76.png)<br>
![img_17.png](imagens/img_77.png)<br>
![img_19.png](imagens/img_79.png)<br>
Enviando email no caso de erro para o tópico<br>
![img_20.png](imagens/img_80.png)<br>
Add police para enviar SNS<br>
![img_21.png](imagens/img_81.png)<br>
![img_22.png](imagens/img_82.png)<br>
![img_23.png](imagens/img_83.png)<br>

````
from botocore.vendored import requests
import boto3

client = boto3.client('sns')

site = 'URL DO SITE'
web_hook = 'URL WEB_HOOK'
mensagem = {'text': '=====Houston, we have a problem!====='}

def lambda_handler(event, context):
    try:
       requests.get(site, timeout=5)
       print('Site OK!')
   except Exception:
       print('Houston, we have a problem!')
       envia_notificacao()

def envia_notificacao():
   client.publish(
       TopicArn='ARN DO TÓPICO',
       Message='Houston, we have a problem!',
       Subject='ALERTA')
````

![img_24.png](imagens/img_84.png)<br>
</p>

<h2>Elastic Container Service (Amazon ECS)</h2>
<p>
4 camadas: <br>
- Container <br>
- Task: Parâmetros para a imagem rodar <br> 
- Service: Administra as tarefas <br>
- Cluster: Agrupamento de instancias<br>

![img_25.png](imagens/img_85.png)<br>
Ele configura/gerencia automaticamente a infra<br>
Fargate - Paga por tarefa, no EC2 tem mais flexibilidade<br>
![img_26.png](imagens/img_86.png)<br>
![img_27.png](imagens/img_87.png)<br>
![img_28.png](imagens/img_88.png)<br>
![img_29.png](imagens/img_89.png)<br>
Para terminar o serviço não pode ter task<br>
![img_45.png](imagens/img_45.png)<br>
![img.png](img.png)<br>
</p>

<h2>Criar cluster EC2</h2>
<p>

![img_90.png](imagens/img_90.png)<br>
![img_91.png](imagens/img_91.png)<br>
![img_92.png](imagens/img_92.png)<br>
![img_93.png](imagens/img_93.png)<br>
![img_94.png](imagens/img_94.png)<br>
![img_95.png](imagens/img_95.png)<br>
Ele criou as 2 instancias EC2<br>
![img_96.png](imagens/img_96.png)<br>
Se uma terminar ele já cria outra<br>
![img_97.png](imagens/img_97.png)<br>
</p>

<h2>Subindo aplicação monolitica EC2</h2>
<p>

![img_98.png](imagens/img_98.png)<br>
https://hub.docker.com/r/rmerces/api-monolitica <br>

````
docker pull rmerces/api-monolitica
docker run --name api-mono -d -p 3000:3000 rmerces/api-monolitica
docker ps
http://localhost:3000/api/
http://localhost:3000/api/users
http://localhost:3000/api/threads
http://localhost:3000/api/posts
http://localhost:3000/api/users/2
http://localhost:3000/api/threads/3
http://localhost:3000/api/posts/in-thread/1
http://localhost:3000/api/posts/by-user/2
````

ECR - Elastic Container Registry (Repositório Privado)
![img_99.png](imagens/img_99.png)<br>
![img_100.png](imagens/img_100.png)<br>
![img_101.png](imagens/img_101.png)<br>
![img_102.png](imagens/img_102.png)<br>
Manter as 2 ultimas imagens<br>
![img_103.png](imagens/img_103.png)<br>
![img_104.png](imagens/img_104.png)<br>
Vamos criar a task para a nossa imagem<br>
![img_105.png](imagens/img_105.png)<br>
![img_106.png](imagens/img_106.png)<br>
![img_107.png](imagens/img_107.png)<br>
![img_108.png](imagens/img_108.png)<br>
![img_109.png](imagens/img_109.png)<br>
![img_110.png](imagens/img_110.png)<br>
![img_111.png](imagens/img_111.png)<br>
Dar acesso ao container<br>
![img_112.png](imagens/img_112.png)<br>
Criando o cluster<br>
![img_113.png](imagens/img_113.png)<br>
![img_114.png](imagens/img_114.png)<br>
![img_115.png](imagens/img_115.png)<br>
Criar load balance<br>
![img_116.png](imagens/img_116.png)<br>
![img_117.png](imagens/img_117.png)<br>
![img_118.png](imagens/img_118.png)<br>
![img_119.png](imagens/img_119.png)<br>
![img_120.png](imagens/img_120.png)<br>
![img_121.png](imagens/img_121.png)<br>
Acessar o container (acesso interno)
![img_122.png](imagens/img_122.png)<br>
Deixando mais especifico<br>
![img_123.png](imagens/img_123.png)<br>
![img_124.png](imagens/img_124.png)<br>
</p>

<h2>Subindo aplicação em Microserviços</h2>
<p>
De acordo com o path ele muda o target Group<br>

![img_125.png](imagens/img_125.png)<br>
![img_126.png](imagens/img_126.png)<br>
![img_127.png](imagens/img_127.png)<br>
Cria paras as 3, igual o exemplo anterior.
![img_106.png](imagens/img_106.png)<br>
![img_128.png](imagens/img_128.png)<br>
Criar load balancer<br>
![img_129.png](imagens/img_129.png)<br>
![img_130.png](imagens/img_130.png)<br>
![img_131.png](imagens/img_131.png)<br>
![img_132.png](imagens/img_132.png)<br>
Criar os 3 target groups<br>
![img_133.png](imagens/img_133.png)<br>
Editar resgras LB<br>
![img_134.png](imagens/img_134.png)<br>
![img_135.png](imagens/img_135.png)<br>
![img_136.png](imagens/img_136.png)<br>
![img_137.png](imagens/img_137.png)<br>
![img_138.png](imagens/img_138.png)<br>
Agora é só criar o serviço<br>
![img_139.png](imagens/img_139.png)<br>
![img_140.png](imagens/img_140.png)<br>
![img_141.png](imagens/img_141.png)<br>
![img_142.png](imagens/img_142.png)<br>
![img_143.png](imagens/img_143.png)<br>
Pegar o Ip Publico<br>
![img_144.png](imagens/img_144.png)<br>
</p>
