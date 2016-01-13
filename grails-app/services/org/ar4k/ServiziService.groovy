package org.ar4k

import grails.transaction.Transactional
import grails.util.Holders
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.DefaultBroadcaster
import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.converters.JSON
import org.pentaho.di.core.KettleEnvironment

@Transactional
class ServiziService {
	// Tramite Camel garantire l'accesso da vari protocolli
	//static expose = ['jmx']

	/** Contesto applicativo Grails */
	GrailsApplication grailsApplication
	// bean atmosphereMeteor
	def atmosphereMeteor //= Holders.applicationContext.getBean("atmosphereMeteor")

	/** Invia a coda messaggi */
	void codaMessaggi(String messaggio,String icona) {
		log.debug "Messaggio sistema: "+messaggio
		try {
			atmosphereMeteor = Holders.applicationContext.getBean("atmosphereMeteor")
			Broadcaster broadcaster = atmosphereMeteor.broadcasterFactory.lookup(DefaultBroadcaster.class, '/wsa/sistema/codamessaggi')
			broadcaster.broadcast([messaggio:messaggio,icona:icona,tipo:'codaMessaggi'] as JSON)
		} catch (Exception ee) {
			log.warn "Problemi con websocket: "+ee.toString()
		}


	}

	/** Invia a coda messaggi con icona default */
	void codaMessaggiInfo(String messaggio) {
		codaMessaggi(messaggio,'fa-info')
	}

	/** attiva il sottosistema Kettle **/
	void initKettle() {
		KettleEnvironment.init()
	}
	
	/** esegue le procedura ogni 5 min. tramite Quartz */
	void battito() {
		try {
			codaMessaggi((new Date()).toString(),'fa-time')
		} catch (Exception ee) {
			log.warn "Errore nel ciclo Quartz: "+ee.toString()
		}
	}
}
