package loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;  
class dblploader{  
	static String FileName;
	static FileWriter FW;
	static String FileName1;
	static FileWriter FW1;
public static void main(String args[]){  
try{  
	BufferedReader reader_aminer;
	String line_aminer;
//step1 load the driver class  
//Class.forName("oracle.jdbc.driver.OracleDriver");  
  
//step2 create  the connection object  
//Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","SYSTEM","SYSTEM");  
  
//step3 create the statement object  
//Statement stmt=con.createStatement();  
  

String filePath="\\E:\\DBLP+Ref+newdelimiter\\dblp+ref-newdelimiter-all.csv"; //ID,Title,Authors,Year,Venue,CitationCount,References,Abstract
reader_aminer = new BufferedReader(new FileReader(filePath));

while ((line_aminer = reader_aminer.readLine()) != null)  
{	
	String id=null;
	String title=null;
	String year=null;
	String authors=null;	
	String venue=null;
	String citationCnt=null;
	String references= null;
	String paperAbstract=null;
	try{
		if(!line_aminer.isEmpty()){
			
			String[] data= line_aminer.split("\\$\\:\\$");
			if(data[0]!=""){
				 id=data[0].replace("\"", "");
			}
			
			if(data[1]!=""){
				title=data[1].replace(".", "").replace("\"", "").replace("'", "''");	
			}
			/* if(data[2]!=""){
				 year=data[2];
			 }
			 
			 if(data[3]!=""){
				 authors=data[3].replace("\"", "").replace("'", "''");	
			 }
			 
			 if(data[4]!=""){
				 venue=data[4].replace("\"", "").replace("'", "''");
			 }
			 
			 if(data[5]!=""){
				 citationCnt=data[5];
			 }*/
			 
			 if(data[6]!=""){
				 references= data[6].replace("\"", "");
				 if(references.length()>4000){
					// references=references.substring(0, 3998);
					 writeReferenceToFile(id, title, references);
				 }
				 
			 }
			 
			 if(data[7]!=""){
				 paperAbstract=data[7].replace("\"", "").replace("'", "''");
				 if(paperAbstract.length()>4000){
					 //paperAbstract=paperAbstract.substring(0, 3998);
					 writeAbstractToFile(id, title, paperAbstract);
				 }
			 }
		}
		
	//	String insertStmt="insert into dblp_dataset values('"+id+"','"+title+"','"+year+"','"+authors+"','"+venue+"','"+citationCnt+"','"+references+"','"+paperAbstract+"')";
		//System.out.println(insertStmt);
		//step4 execute query  

		//stmt.executeQuery(insertStmt);  
	}catch(Exception e1){
		System.out.println(line_aminer);
		e1.printStackTrace();
	}
}

//con.commit();

//con.close(); 
FW.close();
FW1.close();
  
}catch(Exception e){
	e.printStackTrace();
	System.out.println(e);}  
  
}  

static void writeAbstractToFile(String id, String title, String paperabstract){
	 StringBuilder sb;
		
		sb = new StringBuilder();			
		if (FileName == null) {
			FileName = "abstractLenEcxeeded_final.csv";
			try {
				FW = new FileWriter(new File(FileName), true);
					
			}catch(Exception e){
				e.printStackTrace();
			}
			
}
		sb= new StringBuilder();
		sb.append(id);
		sb.append("$:$");
		sb.append(title);
		sb.append("$:$");
		sb.append(paperabstract);
		try {
			FW.write(System.getProperty("line.separator"));
			FW.write(sb.toString());	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

static void writeReferenceToFile(String id, String title, String reference){
	 StringBuilder sb;
		
		sb = new StringBuilder();			
		if (FileName1 == null) {
			FileName1 = "refLenEcxeeded_final.csv";
			try {
				FW1 = new FileWriter(new File(FileName1), true);
					
			}catch(Exception e){
				e.printStackTrace();
			}
			
}
		sb= new StringBuilder();
		sb.append(id);
		sb.append("$:$");
		sb.append(title);
		sb.append("$:$");
		sb.append(reference);
		try {
			FW1.write(System.getProperty("line.separator"));
			FW1.write(sb.toString());	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}  