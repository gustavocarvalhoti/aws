<h1>Treinamento AWS:.</h1>

<h2>Pilares</h2>
<p>
Segurança. <br>
Eficiência de performance.<br>
Confiabilidade.<br>
Excelência operacional.<br>
Otimização de custos.
</p>

<h2>É indicado fazer dupla autenticação</h2>

![img_5.png](imagens/005.png)<br>

<h2>URLS</h2>
<p>
https://aws.amazon.com/pt/console/<br>
https://console.aws.amazon.com<br>
https://github.com/cassianobrexbit/dio-live-coding-aws<br>
https://calculator.aws <- Faz as estimativas de preço<br>
https://www.freenom.com/pt/index.html<br>
https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html<br>
</p>

<h2>Significados</h2>
<p>
RDS: Banco de dados relacional.<br>
DynamoDB: NoSQL.<br>
EC2: Elastic computer - Maquina virtual - Nuvem de Computação Elastica<br>
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

<h2>Dicas</h2>
<p>
namecheap - https://www.namecheap.com <- Dominios com preço bom
</p>

<h2>Security Group: A maquina está na AWS, ele é o firewall</h2>
<p>

![img_1.png](imagens/001.png)<br>
Key pair: Da acesso a maquina virtual, não perder o arquivo aws-gus.pem<br>
0.0.0.0/0 é o acesso geral, qualquer computador pode acessar <br>
0.0.0.0/0, ::/0**** > Esse funciona para ipv4 e ipv6 <br>
Add a rule HTTP > 0.0.0.0/0, ::/0<br>
![img_11.png](imagens/011.png)<br>
![img_12.png](imagens/012.png)<br>

Depois precisa add o sec group a instância <br>
EC2 - Intâncias - Ações - Segurança - Alterar Grupo de Segurança - Add<br>
![img_7.png](imagens/149.png)<br>
</p>

<h2>Lambda Functions</h2>
<p>
- Cria pequenas funções com node.<br>
- Pode ser container e codigo zip.<br>
- Selecionei - Basic Info - Scratch - Node.js - Create function - Vai gravar no DynamoDB, verificar o arquivo index.js.<br> 
</p>

<h2>EC2 - Elastic Computer - Nuvem de Computação Elastica (Maquina Virtual)</h2>
<p>
t1.micro, t2.micro e t3.micro geração das máquinas<br>
EC2 - Instâncias - Selecionar a imagem Amazon Linux - t2.micro - gerar o par de chaves .pem <br>

![img.png](imagens/000.png)<br>
A maquina virtual e disco são cobrados separados.<br>
Selecionar ela no console e depois conectar e selecionar Cliente SSH<br>
![img_2.png](imagens/002.png)<br>
Copiar a chave para esse diretorio e rodar o comando abaixo<br>

````
chmod 400 aws-gus.pem <- Mudar o acesso de leitura, dar acesso somente para o meu usuário
ssh -i "aws-gus.pem" ec2-user@ec2-34-229-142-62.compute-1.amazonaws.com
````

![img_3.png](imagens/003.png)<br>
<b>É indicado proteger contra encerramento.</b>
![img_4.png](imagens/004.png)<br>
<b>Criar script de inicialização EC2.</b><br>
Advanced Details:<br>
![img_10.png](imagens/010.png)<br>

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

<br>Verificando se deu certo (Ele abre a pagina inicial do Apache) > Public IPv4 address -> 35.175.186.61 <br>

![img_13.png](imagens/013.png) <br>
Criando uma imagem a partir dessa instancia: image and templates - create image - create image<br>
O ideal é interromper antes de criar<br>
Verificar em: Images - AMIs<br>

![img_15.png](imagens/015.png) <br>
Agora podemos criar um EC2 dessa imagem.<br>
![img_16.png](imagens/016.png)<br>
<b>Liberar comunicação entre duas EC2</b><br>
Change security groups - Verificar que só tem o de acesso remoto por enquanto. <br>
![img_6.png](imagens/006.png)<br>
O grupo default é criado com o EC2 para comunicaçao interna. <br>
Adicionar o default nos dois EC2. <br>
![img_7.png](imagens/007.png)<br>
Agora eles conseguem se comunicar.<br>
![img_8.png](imagens/008.png)<br>
<b>Atribuir IP Fixo EC2</b><br>
Network & Security - Elastic IPs <br>
Pode associar a 1 instancia sem cobrar, SE A INSTANCIA ESTIVER STOP SERÁ COBRADO.<br>
![img_17.png](imagens/017.png)<br>
Elastic IP Address: Agora precisa associar so EC2 que deseja.<br>
![img_18.png](imagens/018.png)<br>
<b>RDS - Banco de dados</b><br>
Create database - Standard create - MySQL - MySQL 5.7.22 - Free tier - Create database <br>
Acessei o EC2 web-dev2 e dele eu consigo acessar o MySQL - g*****2 <br>

````
mysql -u admin -h database-1.cgsaefhkgnsu.us-east-1.rds.amazonaws.com -p
show databases;
create database cadastro;
OBS: Para o EC2 enchergar o banco precisamos adicionar o grupo de segurança criado para o RDS
EC2 - Instâncias - Segurança - Alterar grupos de segurança 
````

<b>EC2 apontando para o RDS</b><br>

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

![img_19.png](imagens/019.png)

<b>Escalando</b><br>
O usuário chega na AWS pela 80 ou 443 e o load balance distribui. <br>
Você pode gerenciar pela porta 22. <br>
![img_20.png](imagens/020.png)<br>
Load Balancing - Load Balancers - Create Load Balancer - Http/Https<br>
Selecionar no mínimo duas:<br>
![img_21.png](imagens/021.png)<br>
![img_22.png](imagens/022.png)<br>
Deixar a imagem sem preencher por enquanto, ela será preenchida pelo serviço que escala.
![img_23.png](imagens/023.png)<br>
Auto Scaling - Launch Configurations - Create - Selecionar a AMIs<br>
![img_24.png](imagens/024.png)<br>
![img_25.png](imagens/025.png)<br>
![img_26.png](imagens/026.png)<br>
![img_27.png](imagens/027.png)<br>
![img_28.png](imagens/028.png)<br>
![img_29.png](imagens/029.png)<br>
![img_31.png](imagens/031.png)<br>
![img_32.png](imagens/032.png)<br>
![img_33.png](imagens/033.png)<br>
![img_34.png](imagens/034.png)<br>
Se eu terminar um EC2 ele cria outro automaticamente. <br>
</p>

<h2>Gerenciar ambiente com AWS CLI</h2>

````
https://www.youtube.com/watch?v=JWYaaAPAL8c&t=0s
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
aws --version
#Verificar no site da AWS para descobrir o Access key e Secret access key 
#IAM - Security credentials - Create access key - Retrieve access key

aws configure
IAM - Add Usuário - Colocar o nome de Usuário - Habilitar Chave de acesso - Colocar as permissões - Criar usuário
add AWS Access Key Id       = ****                  <- Agora tem o ID
add Secret Access Key Id    = ****                  <- Agora tem a Secret
add Default region name     = sa-east-1
add Default output format   = json 

aws help
https://awscli.amazonaws.com/v2/documentation/api/latest/reference/index.html

aws ec2 help
aws ec2 describe-instances | more                   <- Lista as instancias EC2
aws ec2 describe-instances --instance-id id-da-instancia <- Filtar os campos
aws ec2 describe-instances --filters="Name=instance-state-name,Values=running" <- Lista as ezecutando
aws ec2 describe-instances --filters="Name=instance-state-name,Values=running" --query="Reservations[*].[InstanceId, State] <- Retorna penas InstanceId, State

aws ec2 start-instances --instance-ids i-idddddadas <- Start
aws ec2 stop-instances --instance-ids i-idddddadas  <- Stop 

aws rds describe-db-instances                       <- Mostras as intancias de RDS

sudo rm -rf /usr/local/bin/aws                      <- Desinstalar AWS
sudo rm -rf /usr/local/aws-cli
````

<p>
Criar usuário para o terminal.<br>

![img_35.png](imagens/035.png)<br>
![img_36.png](imagens/036.png)<br>
![img_37.png](imagens/037.png)<br>
![img_38.png](imagens/038.png)<br>
![img_39.png](imagens/039.png)<br>
![img_40.png](imagens/040.png)<br>
</p>

<h2>Lightsail (Console da AWS), configura mais fácil os serviços</h2>
<p>
Ele monta e sobe muito rápido o ambiente<br>
A porta 22 é do acesso ssh<br>

![img_41.png](imagens/041.png)<br>
Tem muitas imagens prontas<br>
![img_42.png](imagens/042.png)<br>
![img_43.png](imagens/043.png)<br>
Acessando pelo terminal<br>
![img_44.png](imagens/044.png)<br>
chmod u+x wp1.sh <- Permissão para executar<br>
![img.png](imagens/059.png)<br>
![img_1.png](imagens/046.png)<br>
É possível criar uma nova instância a partir de um snapshot.<br>
Através deste recurso você tem um processo de recover bem simples, bastando apenas criar uma nova instância baseada em
seu snapshot.<br>
snapshot é uma cópia do ambiente<br/>
Eu escalo ela utilizando o load balancer
</p>

<h2>Mais sobre Lightsail</h2>

````
ssh -i lightsail.rmerces.pem ubuntu@44.205.17.187 <- Conecta utilizando a chave pem
sudo apt-get update -y <- "-y" para já aprovar tudo
sudo apt-get install nginx
python3 -m http.server      <- Sobe 1 servidor na porta 8000 para teste, precisa abilitar a rule depois
sudo systemctl anable nginx <- Deixa sempre ativo
sudo service nginx start    <- Inicia
````

<h2>Lightsail: Load Balancer</h2>
<p>
Faz o balancemanto da carga, se a maquina cair ele para de enviar para ela<br>

![img.png](imagens/145.png)<br>
Target são as instâncias que eu vou apontar<br>
![img_1.png](imagens/146.png)<br>
Verifica se a instância possui o arquivo server.txt, para verificar se ela está de pé<br>
![img_2.png](imagens/147.png)<br>
</p>

<h2>Lightsail: Metrics, Alarmes</h2>
<p>
Caso tenha apenas 1 instância ele envia email ou sms<br>

![img_3.png](imagens/148.png)
</p>

<h2>Lightsail: Storage</h2>
<p>
<b>Bucket:</b> Google drive, one drive - repositório de objetos, arquivos, backups - mais lento. O nome precisa ser unico<br>
<b>Disk:</b> Salva as informações para não perder ao reiniciar

![img_4.png](imagens/150.png)<br>

````
Para utilizar o disk precisamos formatar
sudo sfdisc -l          <- lista os dispositivos reconhecidos
df -h                   <- lista os dispositivos montados
sudo fdisk /dev/xvdf    <- Aponta para o disco criado acima
p                       <- Print do que tem no disco
m                       <- lista as opções
n                       <- Cria a nova particao
p                       <- Particao primaria
1                       <- Numero da particao
enter
enter
p                       <- Print
w                       <- Gravar na tabela de particao
sudo sfdisc -l          <- Verificar se deu certo
sudo mkfs.ext4 /dev/xvdf1   <- Instala o file system para ubuntu
cd /mnt
sudo mkdir data             <- Cria a pasta
sudo mount /dev/xvdf1 /mnt/data/  <- Monta o disco
sudo vi /etc/fstab      <- Editar para montar ao reiniciar
/dev/xvdf1  /mnt/data   ext4    defaults    0 1 <- Adicionar essa linha abaixo da linha existente (Seguir o padrão de tab)
sudo mount -a           <- Lê o fstab e monta os arquivos para testar
cd mnt
sudo chown -R ubuntu:ubuntu data    <- Dar permissão na pasta data
````

</p>

<h2>Lightsail: Containers</h2>
<p>
Aponta a imagem do docker hub que vai utilizar <br>
Essa imagem sobe apenas um http na porta 80 <br>
Tem a partir de 7$ por mês <br>
Se mudar a imagem vc pode fazer o redeploy para baixar a nova imagem<br>
Uma coisa legal que ele guarda as versões que subiram, podendo voltar a versão facilmente<br>

![img_5.png](imagens/151.png) <br>
![img_6.png](imagens/152.png) <br>
</p>

<h2>Cloud Watch: Monitorar EC2 e aplicações</h2>
<p>
Concentra eventos em lote<br>
Podemos criar gráficos e ações por ele<br>
Mostra quanto vai pagar abaixo:<br>

![img_2.png](imagens/047.png)<br>
Criar alarme de custo<br>
![img_4.png](imagens/049.png)<br>
![img_5.png](imagens/050.png)<br>
Ciar um S3 para o exemplo:<br>
![img_6.png](imagens/051.png)<br>
Subi as imagens da pasta cloudwatch-site/imagens<br>
![img_7.png](imagens/052.png)<br>
Criei o EC2 agora<br>
![img_8.png](imagens/053.png)<br>
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
![img_9.png](imagens/054.png)<br>

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
![img_10.png](imagens/055.png)<br>
![img_11.png](imagens/056.png)<br>
Agora o EC2 aponta para as imagens que estão no S3.<br>
![img_12.png](imagens/057.png)<br>
Testando<br>

````
sudo apt-get install stress-ng
````

Rodando o teste durante 5 minutos dentro do EC2<br>
![img_13.png](imagens/058.png)<br>
Criar alarme EC2 CPU Utilization:<br>
![img.png](imagens/061.png)<br>
![img_1.png](imagens/060.png)<br>
![img_2.png](imagens/062.png)<br>
Tem como receber por SMS<br>
![img_3.png](imagens/063.png)<br>
![img_4.png](imagens/064.png)<br>
Alarme quando a imagem parar<br>
![img_5.png](imagens/065.png)<br>
![img_6.png](imagens/066.png)<br>
![img_7.png](imagens/067.png)<br>
Logs:<br>
![img_8.png](imagens/068.png)<br>
Criar um usuário para enviar os logs para o Cloud Watch<br>
![img_9.png](imagens/069.png)<br>

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

![img_10.png](imagens/070.png)<br>
![img_11.png](imagens/071.png)<br>
![img_12.png](imagens/072.png)<br>
Verificar caso precise no futuro<br>
![img_13.png](imagens/073.png)<br>
![img_14.png](imagens/074.png)<br>
AWS Lambda, roda o script de tempos em tempos.<br>
![img_15.png](imagens/075.png)<br>
![img_16.png](imagens/076.png)<br>
![img_17.png](imagens/077.png)<br>
![img_19.png](imagens/079.png)<br>
Enviando email no caso de erro para o tópico<br>
![img_20.png](imagens/080.png)<br>
Add police para enviar SNS<br>
![img_21.png](imagens/081.png)<br>
![img_22.png](imagens/082.png)<br>
![img_23.png](imagens/083.png)<br>

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

![img_24.png](imagens/084.png)<br>
</p>

<h2>ECS - Elastic Container Service - Nossos containers Docker</h2>
<p>
4 camadas: <br>
- Container <br>
- Task: Parâmetros para a imagem rodar <br> 
- Service: Administra as tarefas <br>
- Cluster: Agrupamento de instancias<br>

![img_25.png](imagens/085.png)<br>
Ele configura/gerencia automaticamente a infra<br>
Fargate - Paga por tarefa, no EC2 tem mais flexibilidade<br>
![img_26.png](imagens/086.png)<br>
![img_27.png](imagens/087.png)<br>
![img_28.png](imagens/088.png)<br>
![img_29.png](imagens/089.png)<br>
Para terminar o serviço não pode ter task<br>
![img_45.png](imagens/045.png)<br>
![img.png](imagens/145.png)<br>
</p>

<h2>Criar cluster EC2</h2>
<p>

![img_90.png](imagens/090.png)<br>
![img_91.png](imagens/091.png)<br>
![img_92.png](imagens/092.png)<br>
![img_93.png](imagens/093.png)<br>
![img_94.png](imagens/094.png)<br>
![img_95.png](imagens/095.png)<br>
Ele criou as 2 instancias EC2<br>
![img_96.png](imagens/096.png)<br>
Se uma terminar ele já cria outra<br>
![img_97.png](imagens/097.png)<br>
</p>

<h2>Subindo aplicação monolitica EC2</h2>
<p>

![img_98.png](imagens/098.png)<br>
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
![img_99.png](imagens/99.png)<br>
![img_100.png](imagens/100.png)<br>
![img_101.png](imagens/101.png)<br>
![img_102.png](imagens/102.png)<br>
Manter as 2 ultimas imagens<br>
![img_103.png](imagens/103.png)<br>
![img_104.png](imagens/104.png)<br>
Vamos criar a task para a nossa imagem<br>
![img_105.png](imagens/105.png)<br>
![img_106.png](imagens/106.png)<br>
![img_107.png](imagens/107.png)<br>
![img_108.png](imagens/108.png)<br>
![img_109.png](imagens/109.png)<br>
![img_110.png](imagens/110.png)<br>
![img_111.png](imagens/111.png)<br>
Dar acesso ao container<br>
![img_112.png](imagens/112.png)<br>
Criando o cluster<br>
![img_113.png](imagens/113.png)<br>
![img_114.png](imagens/114.png)<br>
![img_115.png](imagens/115.png)<br>
Criar load balance<br>
![img_116.png](imagens/116.png)<br>
![img_117.png](imagens/117.png)<br>
![img_118.png](imagens/118.png)<br>
![img_119.png](imagens/119.png)<br>
![img_120.png](imagens/120.png)<br>
![img_121.png](imagens/121.png)<br>
Acessar o container (acesso interno)
![img_122.png](imagens/122.png)<br>
Deixando mais especifico<br>
![img_123.png](imagens/123.png)<br>
![img_124.png](imagens/124.png)<br>
</p>

<h2>Subindo aplicação em Microserviços</h2>
<p>
De acordo com o path ele muda o target Group<br>

![img_125.png](imagens/125.png)<br>
![img_126.png](imagens/126.png)<br>
![img_127.png](imagens/127.png)<br>
Cria paras as 3, igual o exemplo anterior.
![img_106.png](imagens/106.png)<br>
![img_128.png](imagens/128.png)<br>
Criar load balancer<br>
![img_129.png](imagens/129.png)<br>
![img_130.png](imagens/130.png)<br>
![img_131.png](imagens/131.png)<br>
![img_132.png](imagens/132.png)<br>
Criar os 3 target groups<br>
![img_133.png](imagens/133.png)<br>
Editar resgras LB<br>
![img_134.png](imagens/134.png)<br>
![img_135.png](imagens/135.png)<br>
![img_136.png](imagens/136.png)<br>
![img_137.png](imagens/137.png)<br>
![img_138.png](imagens/138.png)<br>
Agora é só criar o serviço<br>
![img_139.png](imagens/139.png)<br>
![img_140.png](imagens/140.png)<br>
![img_141.png](imagens/141.png)<br>
![img_142.png](imagens/142.png)<br>
![img_143.png](imagens/143.png)<br>
Pegar o Ip Publico<br>
![img_144.png](imagens/144.png)<br>
</p>

<h2>Lambda em Java</h2>
<p>
https://finalexception.blogspot.com/2020/01/aws-api-gateway-e-lambda-parte-1.html<br>
https://www.baeldung.com/aws-lambda-dynamodb-java<br>
</p>

````
aws lambda create-function --function-name Funcao01 --zip-file fileb://LambdaApiGatewayP01-1.0.jar --handler service.lambda.Funcao01::handleRequest --runtime java8 --role arn:aws:iam::974780188439:role/lambda

#Testa a lambda e grava a resposta ma pasta tmp
aws lambda invoke --function-name Funcao01 --invocation-type RequestResponse /tmp/resposta.txt

#Criar dynamo local
docker pull instructure/dynamo-local-admin
docker run -p 8000:8000 -it --rm instructure/dynamo-local-admin
````

<h2>VPC - Rede virtual privada da AWS </h2>
<p>
Configurações de rede<br>
Subnets = Subredes - Cada uma tem um range de ips<br>
VPC - Listar VPC Criadas<br>
Ajustar o Grupo de Segurança - Segurança - Criar Grupo <br>

![img_153.png](imagens/153.png)<br>

<b>ACLs da rede - Lista de controles de acesso</b><br>
Nela podemos adicionar várias regras e adicionar a varias subredes<br>
![img_154.png](imagens/154.png)<br>
![img_155.png](imagens/155.png)<br>
![img_156.png](imagens/156.png)<br>
Editar - Add regras de entrada - Aceita somente entrada da rede 172.31.0.0/20 que é a sub-rede<br>
![img_157.png](imagens/157.png)<br>
Agora falta associar - ACL - Associações de sub-rede - Editar - Marcar a sub-rede<br>
![img_158.png](imagens/158.png)<br>
Precisa configurar a saída tb<br>
![img_159.png](imagens/159.png)<br>

````
ssh -A -i "aws-ohio.pem" ec2-user@ec2-13-58-7-162.us-east-2.compute.amazonaws.com
````

Precisa passar o -A para dar acesso para chamar a segunda máquina<br>

````
ssh ec2-user@172.31.28.109  <- Agora ele consegue conectar na segunda maquina
````

<b>ACL</b> - Camada de redes - Aplicadas na rede - Para impedir o trafego - Precisa configurar entrada e saída<br>
<b>Security Group</b> - Aplicada a servico/instância especifica - Statefull (Permite a entrada e saida automático)<br>
<b>Criando uma sub-rede publica e uma privada para acessar o banco de dados: </b><br>
sub-rede publica - servidor/app <br>
sub-rede privada - banco de dados <br>
VPC - Suas VPCs - Criar VPC - VPC e muito mais - Criar VPC<br>
![img_160.png](imagens/160.png)<br>
<b>Gateway Nat</b> - Permite o acesso da rede privada para a internet (Cobrado a parte), utilizado para baixar pacotes
ou atulizar libs<br>
![img_161.png](imagens/161.png)<br>
![img_162.png](imagens/162.png)<br>
Agora vou criar a instancia web -> sub-rede publica <br>
EC2 - Instâncias - Iniciar uma instância - linux - t2.micro <br>
![img_163.png](imagens/163.png)<br>
Selecionar a sub-rede publica que criei - habilitar o ip publico automático<br>
![img_164.png](imagens/164.png)<br>
Criar um grupo de segurança vazio porque vou definir depois<br>
![img_165.png](imagens/165.png)<br>
Criar a maquina que possui o banco de dados - linux - t2.micro - selecionar grupo de segurança vazio - Executar
Instância<br>
![img_166.png](imagens/166.png)<br>
Agora eu seleciono a sub-rede privada (Somente a aws pode acessar)<br>
![img_167.png](imagens/167.png)<br>
<b>Configurar permissão da maquina-web</b><br>
Rede e segurança - criar - apontar para a minha VPC - criar<br>
![img_168.png](imagens/168.png)<br>
Recebe a entrada ssh de qualquer ip <br>
![img_169.png](imagens/169.png)<br>
EC2 - Instâncias - maquina-web - açoes - segurança - alterar grupos de segurança - salvar<br>
![img_170.png](imagens/170.png)<br>
Agora ele consegue acessar <br>

````
ssh -i "aws-ohio.pem" ec2-user@ec2-18.compute.amazonaws.com <- Conecta na maquina-web OK
ssh-add ./aws-ohio.pem                          <- Adiciona essa chave ao agente ssh, se ele conseguir ele usa essa chave
ssh -i ec2-user@ec2-18.compute.amazonaws.com    <- Agora o comando diminui
ssh -A ec2-user@ec2-18.compute.amazonaws.com    <- Adiciona o agente a conexao
Na parte abaixou já entrou na maquina-web
ssh ec2-user@10.0.130.4                         <- Tenta acessar a maquina do banco de dados e da erro porque não tem o grupo de segurança
````

Criar um grupo ssh-web-bd para resolver esse acesso e remover a regra de saida<br>
![img_171.png](imagens/171.png)<br>
Na regra de entrada, definir o ip da maquina-web (10.0.12.136) <br>
![img_172.png](imagens/172.png)<br>
Add o grupo na maquina-bd<br>
![img_173.png](imagens/173.png)<br>
Agora funciona, agora a maquina-web consegue acessar a maquina-bd<br>
![img_174.png](imagens/174.png)<br>

<b>Transformando a sub-rede privada em publica</b><br>
![img_175.png](imagens/175.png)<br>
Tabela de rotas, lista de regras se um endereço for acessado ele direciona<br>
![img_176.png](imagens/176.png)<br>
Ao acessar qualquer coisa ele redireciona para o gateway da internet<br>
![img_177.png](imagens/177.png)<br>
![img_178.png](imagens/178.png)<br>
Agora precisa associar a sub-rede<br>
![img_179.png](imagens/179.png)<br>
![img_180.png](imagens/180.png)<br>
Agora consegue acessar a internet e baixar pacotes<br>



</p>