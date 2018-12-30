package Parser.jsonpapackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class showCitationParser {

	static String UpstreamFileName=null;
	static FileWriter upstreamFW;

	
	public static void main(String[] args) throws Exception 
	{ 
		String line;
		String filePath ="F:\\Thesislogs\\Survey logs\\showReferenceQ1.txt";

		
		try{
			BufferedReader srcReader= new BufferedReader(new FileReader(filePath));
			while((line=srcReader.readLine())!=null){
				JSONParser parser1 = new JSONParser(); 
				Object jsnobj = parser1.parse(line);
				if(((JSONObject) jsnobj).get("PaperId")!=null){
					String paperId = ((JSONObject) jsnobj).get("PaperId").toString();
					String[] querysplit = ((JSONObject) jsnobj).get("Query").toString().split("&");
					String limit= querysplit[2].substring(5, querysplit[2].length());
					//System.out.println(Query);
					//String Query="MATCH (n:paper)-[:CITES]->(m:paper) WHERE n.PAPER_ID=\""+ paperId+"\" RETURN count(m)";
					String Query="MATCH (n:paper)-[:CITES]->(m:paper) WHERE n.PAPER_ID=\""+ paperId+"\" RETURN m LIMIT "+limit;
					writeToFile(Query);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		upstreamFW.close();
	}
	
	
	 static void writeToFile(String query){
		 StringBuilder sb;

			sb = new StringBuilder();			
			if (UpstreamFileName == null) {
				UpstreamFileName = "F:\\Thesislogs\\ParsedQueryFiles\\parsedneo4jQueryForShowReferenceSeen.csv"; //dblp+citation_0
				try {
					upstreamFW = new FileWriter(new File(UpstreamFileName), false);
					sb.append("Query");				
					upstreamFW.write(sb.toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
				sb= new StringBuilder();
			sb.append(query);		
			try {
				upstreamFW.write(System.getProperty("line.separator"));
				//System.out.println(sb.toString());
				upstreamFW.write(sb.toString());	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	
} 
