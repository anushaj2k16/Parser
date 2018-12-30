package Parser.jsonpapackage;


import java.io.BufferedReader;
import java.io.File;

// Java program to read JSON from a file 

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator; 
import java.util.Map; 

import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 

public class venueParser {
	static String UpstreamFileName=null;
	static FileWriter upstreamFW;

	
	public static void main(String[] args) throws Exception 
	{ 
		String line;
		String filePath ="F:\\Thesislogs\\Survey logs\\ShowVenueQ1.txt";


		
		try{
			BufferedReader srcReader= new BufferedReader(new FileReader(filePath));
			while((line=srcReader.readLine())!=null){
				JSONParser parser1 = new JSONParser(); 
				Object jsnobj = parser1.parse(line);
				if(((JSONObject) jsnobj).get("PaperId")!=null){
					String paperId = ((JSONObject) jsnobj).get("PaperId").toString();
					//System.out.println(Query);
					String Query="MATCH (n:paper)-[:VENUE_OF]->(m:venue) WHERE n.PAPER_ID=\""+ paperId+"\" RETURN m";
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
				UpstreamFileName = "F:\\Thesislogs\\ParsedQueryFiles\\parsedneo4jQueryForShowVenue.csv"; //dblp+citation_0
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

