#JavaAr4k
Progetto bootstrap applicativi Grails

![alt text](http://www.rossonet.org/wp-content/uploads/2015/01/logoRossonet4.png "Rossonet")

[http://www.rossonet.org](http://www.rossonet.org)

Licenza: [LGPL 3.0](https://www.gnu.org/licenses/lgpl.html)
Per maggiori dettagli sulla licenza rimando a [questa voce](http://it.wikipedia.org/wiki/GNU_Lesser_General_Public_License) di Wikipedia

![alt text](https://www.gnu.org/graphics/gplv3-88x31.png "LGPL Logo")


<a href="http://www.youtube.com/watch?feature=player_embedded&v=9uQAiXJah4U
" target="_blank"><img src="http://img.youtube.com/vi/9uQAiXJah4U/0.jpg" 
alt="Rossonet" width="640" height="360" border="10" /></a>


###Guida rapida per il deploy

La [spin Rossonet](http://www.rossonet.org/archives/94) di [Fedora 21](http://it.wikipedia.org/wiki/Fedora_%28informatica%29) è predisposta per contenere tutti gli strumenti utili per lo sviluppo sulla piattaforma Ar4k.


Un'installazione Ar4k funzionante è composta da due elementi: un ambiente JVM in cui eseguire agenteAr4k e un account ssh su una macchina Linux che ospita il broker [ActiveMQ](http://activemq.apache.org/) e i servizi per la gestione dell'infrastruttura cloud gestiti con [Consul](https://www.consul.io/).

####Procedure ambiente di sviluppo agenteAr4k

La procedura illustrata vale per macchine CentOS/RedHat/Fedora (il codice è per una sessione di [Bash](https://it.wikipedia.org/wiki/Bash)) 

#####Installazione automatica

Per installare in automatico tutto il sistema compreso le dipendenza,
utilizzare il seguente comando.
Se non eseguito con privilegi di root, verrà chiesta l'autenticazione 
per installare Java e git.

```bash
sh <(curl -L -s http://<url verso installa.sh>) 
```

Se "curl" non fosse presente nel sistema, installarlo con:
```bash
yum install curl
```

#####Installazione manuale

Per scaricare l'intero sistema:
```bash
git clone https://github.com/rossonet/agentear4k.git
```

Per lavorare con git in bash:
```bash
git config --global push.default matching
git config credential.helper store
```

Per creare un'applicazione in un unico file .jar con tutte le librerie incluse e Tomcat 7 integrato:
```bash
./compila.sh
```
esecuzione:
```bash
./ar4k.sh
```

Per aggiornare tutto il progetto e eseguirlo in ambiente di sviluppo (Ctrl-C per interrompere l'esecuzione):
```bash
./rigenera.sh && ./grailsw run-app
```

Per eseguirlo in ambiente di sviluppo (Ctrl-C per interrompere l'esecuzione):
```bash
./grailsw run-app
```

Per eseguire i test:
```bash
./grailsw test-app
```

Per creare un war installabile su Tomcat >= 7
```bash
./grailsw war
```