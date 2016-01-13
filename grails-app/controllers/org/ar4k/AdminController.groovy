/**
 * Controller interfaccia web principale della console amministrativa
 * 
 * 
 * @author Andrea Ambrosini (Rossonet s.c.a r.l)
 */

package org.ar4k
import java.util.List;

import javax.swing.text.html.HTML

import com.ecwid.consul.v1.QueryParams
import com.jcraft.jsch.JSch

import grails.converters.JSON
import groovy.json.*


class AdminController {

	/**
	 * 
	 * @return Interfaccia principale
	 */
	def index() {
		//[messaggioOlark: "Benvenuto nel template di sviluppo applicativo AR4K!"]
	}

	/**
	 * In generale le funzionalità legate allo scope session e il menù dell'utente.
	 * @return Pannello in alto a destra
	 */
	def headerNotification() {
		render(template: "headerNotification")
	}

	/**
	 * 
	 * @return Testata
	 */
	def headerHtml() {
		render(template: "headerHtml")
	}

	/**
	 * 
	 * @return Definizione applicazione AngularJS 
	 */
	def appjs() {
		render(template: "appjs",contentType: 'text/javascript')
	}

	/**
	 * 
	 * @return Barra laterale sinistra
	 */
	def sidebar() {
		render(template: "sidebar")
	}

	def utenti() {
		render(template: "utenti")
	}

	def utentiCtrl() {
		render(template: "utentiCtrl",contentType: 'text/javascript')
	}

	def dashrossonet() {
		render(template: "dashRossonet")
	}

	def dashrossonetCtrl() {
		render(template: "dashRossonetCtrl",contentType: 'text/javascript')
	}

	def apiAr4k() {
		render(template: "apiAr4k")
	}

	def apiAr4kCtrl() {
		render(template: "apiAr4kCtrl")
	}

	def console() {
		render(template: "console")
	}

	def consoleCtrl() {
		render(template: "consoleCtrl")
	}

	/**
	 *
	 * @return Foglio di stile AngularJS. Tramite la configurazione grafica dell'interfaccia è possibile variare lo stile
	 */
	def sbadmin2() {
		def colori = [
			bordo: '#152039',
			fondoBody: '#717A8F',
			footerColor: '#152039',
			menuAttivo: '#565519',
			menuFocus: '#69682F',
			menuColor: '#D5D5A4',
			menuSfondo: '#3A445B',
			rigaDispariColor: 'black',
			rigaDispariSfondo: '#717A8F',
			rigaPariColor: 'black',
			rigaPariSfondo: '#D5D5A4',
			primary: 'grey',
			success: 'green',
			info: 'white',
			warning: 'orange',
			danger: 'red'
		]
		def grafica = [colori:colori]
		render(template: "sbadmin2",contentType:"text/css",model:[grafica: grafica])
	}

	/**
	 *
	 * @return Foglio di stile AngularJS. Tramite la configurazione grafica dell'interfaccia è possibile variare lo stile
	 */
	def main() {
		def colori = [
			bordo: '#152039',
			fondoBody: '#717A8F',
			footerColor: '#152039',
			menuAttivo: '#565519',
			menuFocus: '#69682F',
			menuColor: '#D5D5A4',
			menuSfondo: '#3A445B',
			rigaDispariColor: 'black',
			rigaDispariSfondo: '#717A8F',
			rigaPariColor: 'black',
			rigaPariSfondo: '#D5D5A4',
			primary: 'grey',
			success: 'green',
			info: 'white',
			warning: 'orange',
			danger: 'red'
		]
		def grafica = [colori:colori]
		render(template: "main",contentType:"text/css",model:[grafica: grafica])
	}

	/**
	 *
	 * @return Processi e oggetti collegati in JSON
	 */
	def listaProcessi() {
		def risultato = []
		risultato.add(processo:"Processo 1",stato:"aperto",id:1,descrizione:'processo di prova 1')
		risultato.add(processo:"Processo 2",stato:"chiuso",id:2,descrizione:'secondo test...')
		def incapsulato = [processi:risultato,conto:risultato.size()]
		render incapsulato as JSON
	}
}

