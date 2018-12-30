import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class changeDelimiter {
	static String xmlFileName;
	static FileWriter xmlFW;
	public static void main(String[] args) {
		BufferedReader reader_aminer;
		String line_aminer;
		 try {
		String filePath="\\E:\\Neo4jDatafiles\\paperVertex.csv";
		reader_aminer = new BufferedReader(new FileReader(filePath));
		while ((line_aminer = reader_aminer.readLine()) != null)  
		{	
			String[] data= line_aminer.split("\\$\\:\\$");
			writeToFile(data);
			
		}
		xmlFW.close();
		reader_aminer.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	static void writeToFile(String[] aminer){
		 StringBuilder sb;		
			sb = new StringBuilder();			
			if (xmlFileName == null) {
				xmlFileName = "paperVertexMod.csv";
				try {
					xmlFW = new FileWriter(new File(xmlFileName), true);
					/*sb.append("ID");
					sb.append(",");
					sb.append("Title");
					sb.append(",");
					sb.append("Year");
					sb.append(",");
					sb.append("Authors");
					sb.append(",");
					sb.append("Venue");
					sb.append(",");
					sb.append("CitationCOunt");
					sb.append(",");
					sb.append("Institution");
					sb.append(",");
					sb.append("Pages");
					sb.append(",");
					sb.append("vType");
					sb.append(",");
					sb.append("ISBN");
					sb.append(",");
					sb.append("Volume");
					sb.append(",");
					sb.append("Journal");
					sb.append(",");
					sb.append("Number");
					sb.append(",");
					sb.append("BookTitle");
					sb.append(",");
					sb.append("URL");
					sb.append(",");
					sb.append("Abstract");
					xmlFW.write(System.getProperty("line.separator"));
					xmlFW.write(sb.toString());*/
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}	
			
						
			sb= new StringBuilder();
			
			sb.append(aminer[0]); //id
			sb.append(",");
			if(!aminer[1].replaceAll("\"", "").equals("")){
			aminer[1]=aminer[1].substring(1, aminer[1].length()-1);
			
			String replaced=aminer[1].replace("\"", "'").replace("''", "'");
			
			sb.append("\""+replaced+"\""); //title
			}else{
				sb.append(aminer[1]);
			}
		    sb.append(",");
			sb.append(aminer[2]); //year
			sb.append(",");
			sb.append(aminer[3]); //authors
			sb.append(",");
			sb.append(aminer[4]); //venue
			sb.append(",");
			sb.append(aminer[5]); //citationcount
			sb.append(",");
			sb.append(aminer[6]); //Institution
			sb.append(",");
			sb.append(aminer[7]); //pages
			sb.append(",");
			sb.append(aminer[8]); //vtype
			sb.append(",");
			sb.append(aminer[9]); //ISBN
			sb.append(",");
					
				sb.append(aminer[10]); //volume
			
			sb.append(",");
						
				sb.append(aminer[11]); //journal
			
			sb.append(",");
			
				sb.append(aminer[12]); //number		
			
			sb.append(",");
			
				sb.append(aminer[13]); //booktitle		
			
			sb.append(",");
			
				sb.append(aminer[14]); //url			
			
			sb.append(",");
			//System.out.println(aminer[15]);
			if(!aminer[15].replaceAll("\"", "").equals("")){
			aminer[15]=aminer[15].substring(1, aminer[15].length()-1);
			String abst=aminer[15].replace("\"", "'").replace("''", "'");
			sb.append("\""+abst+"\""); //abstract
			}else{
				sb.append(aminer[15]);
			}
					
			
			try {
				xmlFW.write(System.getProperty("line.separator"));
				xmlFW.write(sb.toString());	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}

}
