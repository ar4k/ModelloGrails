import org.codehaus.groovy.grails.commons.GrailsApplication;

import grails.util.Environment

import org.ar4k.UtenteRuolo
import org.ar4k.Utente
import org.ar4k.Ruolo
import org.ar4k.secure.*

class BootStrap {

	GrailsApplication grailsApplication
	def bootStrapService

	def init = { servletContext ->
		switch (Environment.current) {
			case Environment.DEVELOPMENT:
				log.info("Sistema in configurazione di sviluppo... ")
				if ( provaConfigurazioni() ) log.info("Sistema configurato e pronto...")
				break;
			case Environment.PRODUCTION:
				if ( provaConfigurazioni() ) log.info("Sistema configurato e pronto...")
				break;
		}

		log.debug("\nIn BootStrap.groovy:\n***  ")
		for (String property: System.getProperty("java.class.path").split(";")) {
			log.debug(property + "\n")
		}
		log.debug("***\n")
	}

	def destroy = {
	}
	
	Boolean provaConfigurazioni() {
		// aggiunge un utente di test
		aggiungiUtente('rossonet','westing')
		return true
	}
	
	/** Aggiunge un utente al contesto */
	Boolean aggiungiUtente(String nome,String password) {
		Boolean risultato = true
		log.debug("Creo l'utente "+nome)
		def adminRole = Ruolo.create()
		adminRole.authority='ROLE_ADMIN'
		def testUser = Utente.create()
		testUser.username=nome
		testUser.password=password
		UtenteRuolo utenteRuolo = UtenteRuolo.create()
		utenteRuolo.utente=testUser
		utenteRuolo.ruolo=adminRole
		try {
			testUser.save(flush:true)
			adminRole.save(flush:true)
			utenteRuolo.save(flush:true)
		} catch (Exception e){
			log.warn("Impossibile salvare l'utente. Errore: "+e.toString())
			risultato = false
		}
		return risultato
	}
}
