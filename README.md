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

![img_5.png](imgs/img_5.png)<br>

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
</p>

<h2>Security Group: A maquina está na AWS, ele é o firewall.</h2>

<p>

![img_1.png](imgs/img_1.png)<br>
Key pair: Da acesso a maquina virtual, não perder o arquivo aws-gus.pem<br>
Add a rule HTTP -> 0.0.0.0/0, ::/0<br>

![img_11.png](imgs/img_11.png)
![img_12.png](imgs/img_12.png)

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

![img.png](imgs/img.png)<br>
A maquina virtual e disco são cobrados separados.<br>
</p>

<h2>Acessar a EC2:</h2>
<p>
Selecionar ela no console e depois conectar e selecionar Cliente SSH<br>

![img_2.png](imgs/img_2.png)<br>

Copiar a chave para esse diretorio e rodar o comando abaixo<br>
`chmod 400 aws-gus.pem` (Mudar o acesso de leitura)<br>
`ssh -i "aws-gus.pem" ec2-user@ec2-34-229-142-62.compute-1.amazonaws.com`<br>
</p>

![img_3.png](imgs/img_3.png)<br>

<h2>É indicado proteger contra encerramento.</h2>

![img_4.png](imgs/img_4.png)<br>

<h2>Criar script de inicialização EC2.</h2>

<p>
Advanced Details:<br>

![img_10.png](imgs/img_10.png)<br>

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
![img_9.png](imgs/img_9.png)

Verificando se deu certo (Ele abre a pagina inicial do Apache) -> Public IPv4 address -> 35.175.186.61 <br>
![img_13.png](imgs/img_13.png) <br>

Criando uma imagem a partir dessa instancia: image and templates - create image - create image<br>
Verificar em: Images - AMIs<br>
![img_15.png](imgs/img_15.png) <br>
Agora podemos criar um EC2 dessa imagem.<br>
![img_16.png](imgs/img_16.png)<br>
</p>

<h2>Liberar comunicação entre duas EC2.</h2>
<p>
Change security groups - Verificar que só tem o de acesso remoto por enquanto. <br>

![img_6.png](imgs/img_6.png)<br>
O grupo default é criado com o EC2 para comunicaçao interna. <br>
Adicionar o default nos dois EC2. <br>
![img_7.png](imgs/img_7.png)<br>
Agora eles conseguem se comunicar.<br>
![img_8.png](imgs/img_8.png)<br>
</p>

<h2>Atribuir IP Fixo EC2.</h2>
<p>
Network & Security - Elastic IPs <br>
Pode associar a 1 instancia sem cobrar, SE A INSTANCIA ESTIVER STOP SERÁ COBRADO.<br>

![img_17.png](imgs/img_17.png)<br>
Agora precisa associar so EC2 que deseja.<br>
![img_18.png](imgs/img_18.png)
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

![img_19.png](imgs/img_19.png)

<h2>Escalando</h2>
<p>
O usuário chega na AWS pela 80 ou 443 e o load balance distribui. <br>
Você pode gerenciar pela porta 22. <br>

![img_20.png](imgs/img_20.png)<br>

Load Balancing - Load Balancers - Create Load Balancer - Http/Https<br>
Selecionar no mínimo duas -><br>
![img_21.png](imgs/img_21.png)<br>
![img_22.png](imgs/img_22.png)<br>
Deixar a imagem sem preencher por enquanto, ela será preenchida pelo serviço que escala.
![img_23.png](imgs/img_23.png)<br>
Auto Scaling - Launch Configurations - Create - Selecionar a AMIs<br>
![img_24.png](imgs/img_24.png)<br>
![img_25.png](imgs/img_25.png)<br>
![img_26.png](imgs/img_26.png)<br>
![img_27.png](imgs/img_27.png)<br>
![img_28.png](imgs/img_28.png)<br>
![img_29.png](imgs/img_29.png)<br>
![img_31.png](imgs/img_31.png)<br>
![img_32.png](imgs/img_32.png)<br>
![img_33.png](imgs/img_33.png)<br>
![img_34.png](imgs/img_34.png)<br>
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

![img_35.png](imgs/img_35.png)<br>
![img_36.png](imgs/img_36.png)<br>
![img_37.png](imgs/img_37.png)<br>
![img_38.png](imgs/img_38.png)<br>
![img_39.png](imgs/img_39.png)<br>
![img_40.png](imgs/img_40.png)<br>
</p>

<h2>Amazon Lightsail (Digital Ocean é parecido), configura mais fácil os serviços</h2>
<p>
Ele monta e sobe muito rápido o ambiente<br>
A porta 22 é do acesso ssh<br>

![img_41.png](imgs/img_41.png)<br>
Tem muitas imagens prontas<br>
![img_42.png](imgs/img_42.png)<br>
![img_43.png](imgs/img_43.png)<br>
Acessando pelo terminal<br>
![img_44.png](imgs/img_44.png)<br>
chmod u+x wp1.sh <- Permissão para executar<br>
![img.png](imgs/img_59.png)<br>
![img_1.png](imgs/img_46.png)<br>
É possível criar uma nova instância a partir de um snapshot.<br>
Através deste recurso você tem um processo de recover bem simples, bastando apenas criar uma nova instância baseada em
seu snapshot.<br>
</p>

<h2>Cloud Watch: Monitorar EC2 e aplicações</h2>
<p>
Concentra eventos em lote<br>
Podemos criar gráficos e ações por ele<br>
Mostra quanto vai pagar abaixo:<br>

![img_2.png](imgs/img_47.png)<br>
Criar alarme de custo<br>
![img_4.png](imgs/img_49.png)<br>
![img_5.png](imgs/img_50.png)<br>
Ciar um S3 para o exemplo:<br>
![img_6.png](imgs/img_51.png)<br>
Subi as imagens da pasta cloudwatch-site/imagens<br>
![img_7.png](imgs/img_52.png)<br>
Criei o EC2 agora<br>
![img_8.png](imgs/img_53.png)<br>
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
![img_9.png](imgs/img_54.png)<br>

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
![img_10.png](imgs/img_55.png)<br>
![img_11.png](imgs/img_56.png)<br>
Agora o EC2 aponta para as imagens que estão no S3.<br>
![img_12.png](imgs/img_57.png)<br>
Testando<br>

````
sudo apt-get install stress-ng
````

Rodando o teste durante 5 minutos dentro do EC2<br>
![img_13.png](imgs/img_58.png)<br>

</p>