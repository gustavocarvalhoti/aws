#!/bin/bash
yum update -y
amazon-linux-extras install -y lamp-mariadb10.2-php7.2 php7.2
yum install -y httpd mariadb-server
systemctl start httpd
systemctl enable httpd
systemctl start mariadb
systemctl enable mariadb
usermod -a -G apache ec2-user
chown -R ec2-user:apache /var/www