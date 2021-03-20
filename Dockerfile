FROM "debian:sid"

ARG version=1.0.0

RUN apt update &&\
	apt install -y openjdk-15-jdk-headless wget mariadb-server &&\
	rm -rf /var/lib/apt/lists/* &&\
	cd /tmp &&\
	wget https://download.jboss.org/wildfly/23.0.0.Final/wildfly-23.0.0.Final.tar.gz &&\
	tar xf wildfly-23.0.0.Final.tar.gz -C /opt &&\
	mv /opt/wildfly-23.0.0.Final /opt/wildfly &&\
	rm wildfly-23.0.0.Final.tar.gz
	
COPY "target/JavaAdvancedAppSec-${version}.war" "/opt/wildfly/standalone/deployments/JavaAdvancedAppSec.war"

COPY "installation.sql" "/tmp"

RUN service mariadb start &&\
	mysql -h localhost -u root < /tmp/installation.sql &&\
	rm -rf /tmp/installation.sql &&\
	mysql -h localhost -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED BY 'test'; FLUSH PRIVILEGES;"

EXPOSE 8080
EXPOSE 9990
EXPOSE 3306

CMD ["/bin/sh", "-c", "service mariadb start && /opt/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0"]

