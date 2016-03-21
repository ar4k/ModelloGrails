package org.ar4k

import org.docx4j.TraversalUtil
import org.docx4j.XmlUtils
import org.docx4j.convert.out.FOSettings
import org.docx4j.finders.RangeFinder
import org.docx4j.jaxb.Context
import org.docx4j.model.fields.merge.DataFieldName
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage
import org.docx4j.openpackaging.io.SaveToZipFile
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.PartName
import org.docx4j.openpackaging.parts.SpreadsheetML.WorksheetPart
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.Body
import org.docx4j.wml.CTBookmark
import org.docx4j.wml.CTMarkupRange
import org.docx4j.wml.ContentAccessor
import org.docx4j.wml.P
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage
import org.docx4j.openpackaging.parts.PartName
import org.docx4j.openpackaging.parts.SpreadsheetML.WorksheetPart
import org.xlsx4j.sml.CTRst
import org.xlsx4j.sml.CTXstringWhitespace
import org.xlsx4j.sml.Cell
import org.xlsx4j.sml.Row
import org.xlsx4j.sml.STCellType
import org.xlsx4j.sml.SheetData
import org.xlsx4j.sml.Worksheet

import net.sourceforge.tess4j.*

class MaterialController {

	/**
	 * Upload immagine
	 */
	def uploadImmagine() {
		String id = request.getParameter('id')
		def avatarImage = request.getFile('file')
		//log.info("-- "+id)
		try {
			if (!avatarImage.isEmpty()) {
				render "ok - file size: "+(avatarImage.getBytes().size()/1024).toString()+" Kbyte"
			}
			else {
				render "Empty"
			}
		} catch(Exception eee){
			log.error eee.printStackTrace()
		}
	}


	/**
	 * Visualizza immagine
	 */
	def visualizzaImmagine(String id) {
		def oggetto = null
		if(true){
			render (file: oggetto?.immagine, contentType: 'image/png')
		} else {
			redirect controller:'images',action:'75.png'
		}
	}

	/**
	 * Funzione per generare documenti docx da template e variabili.
	 * Sostituisce le chiavi della mappa trovate come bookmarks
	 * nel template docx con i relativi valori e genera un
	 * documento docx
	 * @param mappa elenco variabili in forma chiave valore
	 * @param templateDoc template messaggio in docx
	 * @return file docx
	 */
	private File generaWord(def mappa,java.io.File templateDoc) {
		Map<DataFieldName, String> map = new HashMap<DataFieldName, String>()
		mappa.each{ dato ->
			map.put( new DataFieldName(dato.bookmark), dato.valore.toString())
		}
		//java.io.File templateDoc
		//templateDoc = new java.io.File(System.getProperty("user.dir")+"/web-app/kettle/template/"+fileTemplate+".docx")
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateDoc)
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart()
		org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement()
		Body body = wmlDocumentEl.getBody()
		replaceBookmarkContents(body.getContent(), map)
		File file = File.createTempFile("export-", ".docx")
		wordMLPackage.save(file)
		return file
	}

	/**
	 * Funzione per generare documenti pdf da template e variabili.
	 * Sostituisce le chiavi della mappa trovate come bookmarks
	 * nel template docx con i relativi valori e accoda il
	 * documento pdf allo stream
	 * @param out OutputStream dove indirizzare il pdf generato
	 * @param mappa elenco variabili in forma chiave valore
	 * @param templateDoc template messaggio in docx
	 * @return
	 */
	private void generaPdf(OutputStream out,def mappa,java.io.File templateDoc) {
		Map<DataFieldName, String> map = new HashMap<DataFieldName, String>()
		mappa.each{ dato ->
			map.put( new DataFieldName(dato.bookmark), dato.valore)
		}
		//java.io.File templateDoc
		//templateDoc = new java.io.File(System.getProperty("user.dir")+"/web-app/kettle/template/"+fileTemplate+".docx")
		//if (Environment.current == Environment.PRODUCTION) templateDoc = new java.io.File(grailsApplication.config.repository+fileTemplate+".docx")
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateDoc)
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart()
		org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement()
		Body body = wmlDocumentEl.getBody()
		replaceBookmarkContents(body.getContent(), map)
		FOSettings foSettings = Docx4J.createFOSettings()
		foSettings.setWmlPackage(wordMLPackage)
		OutputStream file = new java.io.FileOutputStream(File.createTempFile("export-", ".pdf"))
		//wordMLPackage.save(file)
		Docx4J.toFO(foSettings, out, Docx4J.FLAG_EXPORT_PREFER_XSL)
	}

	/**
	 * Factory Docx4J
	 */
	private static org.docx4j.wml.ObjectFactory factoryDoc = Context.getWmlObjectFactory()

	/**
	 * Sostituisce i bookmarks con i contenuti nei paragrafi dei documenti docx
	 * @param paragraphs
	 * @param data
	 * @throws Exception
	 */
	private  void replaceBookmarkContents(List<Object> paragraphs,  Map<DataFieldName, String> data) throws Exception {
		RangeFinder rt = new RangeFinder("CTBookmark", "CTMarkupRange")
		new TraversalUtil(paragraphs, rt)
		for (CTBookmark bm : rt.getStarts()) {
			log.debug(bm.dump())
			// do we have data for this one?
			if (bm.getName()==null) continue
				String value = data.get(new DataFieldName(bm.getName()))
			if (value==null) continue
				try {
					// Can't just remove the object from the parent,
					// since in the parent, it may be wrapped in a JAXBElement
					List<Object> paragrafo = null
					if (bm.getParent() instanceof P) {
						paragrafo = ((ContentAccessor)(bm.getParent())).getContent()
						//log.info("il valore "+value+" è da posizionare nel paragrafo "+paragrafo.dump())
					} else {
						//log.info("il valore in else "+value+" non è posizionato")
						continue
					}
					int i = 0
					def risultato = null
					org.docx4j.wml.R run
					org.docx4j.wml.Text t
					int indice = -1
					for (Object ox : paragrafo) {
						Object listEntry = XmlUtils.unwrap(ox)
						//log.info(listEntry.dump())
						if (listEntry.equals(bm)) {
							//log.info("listEntry.equals(bm)"+bm.getName())
							run = factoryDoc.createR()
							t = factoryDoc.createText()
							run.getContent().add(t)
							t.setValue(value)
							log.debug(i+" -> "+value )
							//paragrafo.add(i, run)
							indice = i+1
							//risultato= run
							risultato= run
							continue
						}
						i++
					}
					if (indice) paragrafo.add(indice, risultato)
				} catch (ClassCastException cce) {
					log.error(cce.getMessage(), cce)
				}
		}


	}


	def tesseractExample() {
		File imageFile = new java.io.File(System.getProperty("user.dir")+"/web-app/tessdata/test-ocr.jpg")
		try {
			ITesseract instance = new Tesseract()  // JNA Interface Mapping
			instance.setDatapath(System.getProperty("user.dir")+"/web-app/tessdata")
			// ITesseract instance = new Tesseract1() // JNA Direct Mapping
			String result = instance.doOCR(imageFile)
			render result
		} catch (TesseractException e) {
			render e.getMessage()
		}

	}


}
