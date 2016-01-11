#JavaAr4k
Progetto bootstrap applicativi Java/Groovy/Grails

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

La procedura illustrata vale per macchine CentOS/RedHat/Fedora (il codice è per una sessione di [Bash](https://it.wikipedia.org/wiki/Bash)) 

#####Installazione manuale

Per scaricare l'intero sistema:
```bash
git clone https://github.com/ar4k/ModelloGrails.git
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

### Note per gli sviluppatori

La documentazione delle classi Groovy è disponibile a questo indirizzo: [http://ar4k.github.io/ModelloGrails/web-app/docs/gapi/index.html](http://ar4k.github.io/ModelloGrails/web-app/docs/gapi/index.html);
La documentazione delle classi Java è raggiungibile in questo: [http://ar4k.github.io/ModelloGrails/web-app/docs/api/index.html](http://ar4k.github.io/ModelloGrails/web-app/docs/api/index.html) 

L'ambiente è testato per l'esecuzione con la JVM Oracle in versione 1.7.x. 
