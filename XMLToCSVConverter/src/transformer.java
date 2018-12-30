
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
 
class Item{
	private String firstText = null;
 
	public void setFirstText(String str){
		firstText =  str;
	}
 
	public String getFirstText(){		
		if(firstText == null){
			return null;
		}else{
			return firstText;
		}
	}
}
 
public class transformer {
	static boolean tagOpened=false;
	static boolean titleTagOpened=false;
	static boolean authorTagOpened=false;
	static boolean yearTagOpened=false;
	static boolean institutionTagOpened=false;
	static boolean urlTagOpened=false;
	static boolean pagesTagOpened=false;
	static boolean isbnTagOpened=false;
	static boolean volumeTagOpened=false;
	static boolean journalTagOpened=false;
	static boolean numberTagOpened=false;
	static boolean doiTagOpened=false;
	static boolean booktitleTagOpened=false;
	static String xmlFileName;
	static FileWriter xmlFW;
	static String vertexType;
	static xmldatastructure objxmldatastructure= new xmldatastructure();
	public static void main(String[] args) throws XMLStreamException, FactoryConfigurationError, IOException {
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		InputStream in = new FileInputStream("\\ThesisMaterials\\DBLPDataset\\dblp_dataset\\dblp.xml");
		XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
 
		Item item = null;
 
		while (eventReader.hasNext()) {
			try{
				XMLEvent event = eventReader.nextEvent();
				
			//reach the start of an item
			if (event.isStartElement()) {
 
				StartElement startElement = event.asStartElement();
 
				if ((startElement.getName().getLocalPart().equals("phdthesis"))||(startElement.getName().getLocalPart().equals("incollection"))
						||(startElement.getName().getLocalPart().equals("article"))||(startElement.getName().getLocalPart().equals("inproceedings"))
						||(startElement.getName().getLocalPart().equals("www"))||(startElement.getName().getLocalPart().equals("book"))
						||(startElement.getName().getLocalPart().equals("mastersthesis"))||(startElement.getName().getLocalPart().equals("proceedings"))) {
					tagOpened=true;
					objxmldatastructure.vType=startElement.getName().toString();
				}
				
				if(tagOpened){
					if (event.asStartElement().getName().getLocalPart().equals("title")) {
						titleTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("author")||event.asStartElement().getName().getLocalPart().equals("editor")) {
						authorTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("year")) {
						yearTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("school")) {
						institutionTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("url")||event.asStartElement().getName().getLocalPart().equals("ee")) {
						urlTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("pages")) {
						pagesTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("isbn")) {
						isbnTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("volume")) {
						volumeTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("journal")) {
						journalTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("number")) {
						numberTagOpened=true;
					}else if (event.asStartElement().getName().getLocalPart().equals("booktitle")) {
						booktitleTagOpened=true;
					}
				}		
			}else if(!event.isEndElement()){
				if(titleTagOpened){
					objxmldatastructure.Title=event.toString().replace(".", "");
					titleTagOpened=false;
				}else if(authorTagOpened){
					if(objxmldatastructure.Author.isEmpty()){
						objxmldatastructure.Author=event.toString();
					}else{
						objxmldatastructure.Author+=";"+event.toString();
					}
					authorTagOpened=false;
				}else if(yearTagOpened){
					objxmldatastructure.Year=event.toString();
					yearTagOpened=false;
				}else if(institutionTagOpened){
					if(objxmldatastructure.Institution.isEmpty()){
						objxmldatastructure.Institution=event.toString();
					}else{
						objxmldatastructure.Institution+=";"+event.toString();
					}
					institutionTagOpened=false;
				}else if(urlTagOpened){
					if(objxmldatastructure.URL.isEmpty()){
						objxmldatastructure.URL=event.toString();
					}else{
						objxmldatastructure.URL+=";"+event.toString();
					}
					urlTagOpened=false;
				}else if(pagesTagOpened){
					objxmldatastructure.pages=event.toString();
					pagesTagOpened=false;
				}else if(isbnTagOpened){
					objxmldatastructure.ISBN=event.toString();
					isbnTagOpened=false;
				}else if(volumeTagOpened){
					objxmldatastructure.Volume=event.toString();
					volumeTagOpened=false;
				}else if(journalTagOpened){
					objxmldatastructure.Journal=event.toString();
					journalTagOpened=false;
				}else if(journalTagOpened){
					objxmldatastructure.Journal=event.toString();
					journalTagOpened=false;
				}else if(booktitleTagOpened){
					objxmldatastructure.BookTitle=event.toString();
					booktitleTagOpened=false;
				}
			}
			
 
			//reach the end of an item
			if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				if (endElement.getName().getLocalPart().equals("phdthesis")||endElement.getName().getLocalPart().equals("incollection")
						||endElement.getName().getLocalPart().equals("article")||endElement.getName().getLocalPart().equals("inproceedings")
						||endElement.getName().getLocalPart().equals("www")||endElement.getName().getLocalPart().equals("book")
						||endElement.getName().getLocalPart().equals("mastersthesis")||endElement.getName().getLocalPart().equals("proceedings")) {
					writeToFile(objxmldatastructure);
					tagOpened=false;
					item = null;
					objxmldatastructure=new xmldatastructure();
				}
				
			}
			}catch(Exception e){
				 e.printStackTrace();
			}
			
		}
		xmlFW.close();
	}
	
	
	static void writeToFile(xmldatastructure itemObj){
		 StringBuilder sb;
			
			sb = new StringBuilder();			
			if (xmlFileName == null) {
				xmlFileName = "dblp+xml+newdelimiter+1.dsv";
				try {
					xmlFW = new FileWriter(new File(xmlFileName), true);
					sb.append("Title");
					sb.append("$:$");
					sb.append("Year");
					sb.append("$:$");
					sb.append("Authors");
					sb.append("$:$");
					sb.append("Institution");
					sb.append("$:$");
					sb.append("URL");
					sb.append("$:$");
					sb.append("Pages");
					sb.append("$:$");
					sb.append("vType");
					sb.append("$:$");
					sb.append("ISBN");
					sb.append("$:$");
					sb.append("Volume");
					sb.append("$:$");
					sb.append("Journal");
					sb.append("$:$");
					sb.append("Number");
					sb.append("$:$");
					sb.append("BookTitle");
					xmlFW.write(System.getProperty("line.separator"));
					xmlFW.write(sb.toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}	
			sb= new StringBuilder();
			sb.append(itemObj.Title);
			sb.append("$:$");
			sb.append(itemObj.Year);
			sb.append("$:$");
			sb.append(itemObj.Author);
			sb.append("$:$");
			sb.append(itemObj.Institution);
			sb.append("$:$");
			sb.append(itemObj.URL);
			sb.append("$:$");
			sb.append(itemObj.pages);
			sb.append("$:$");
			sb.append(itemObj.vType);
			sb.append("$:$");
			sb.append(itemObj.ISBN);
			sb.append("$:$");
			sb.append(itemObj.Volume);
			sb.append("$:$");
			sb.append(itemObj.Journal);
			sb.append("$:$");
			sb.append(itemObj.Number);
			sb.append("$:$");
			sb.append(itemObj.BookTitle);
	
			try {
				xmlFW.write(System.getProperty("line.separator"));
				xmlFW.write(sb.toString());	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
}

