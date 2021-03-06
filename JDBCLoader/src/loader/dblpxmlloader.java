package loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;  

class dblpxmlloader{  
	
public static void main(String args[]){  
try{  
	BufferedReader reader_aminer;
	String line_aminer;
	String insertStmt="";
//step1 load the driver class  
Class.forName("oracle.jdbc.driver.OracleDriver");  
  
//step2 create  the connection object  
Connection con=DriverManager.getConnection(  
"jdbc:oracle:thin:@localhost:1521:orcl","SYSTEM","SYSTEM");  
  
//step3 create the statement object  
Statement stmt=con.createStatement();  
  

String filePath="\\E:\\DBLP+Ref+newdelimiter\\dblp+xml+newdelimiter+1.csv"; //ID,Title,Authors,Year,Venue,CitationCount,References,Abstract
reader_aminer = new BufferedReader(new FileReader(filePath));

while ((line_aminer = reader_aminer.readLine()) != null)  
{	
	String id=null;
	String title=null;
	String year=null;
	String authors=null;	
	String institution=null;
	String URL=null;
	String Pages=null;
	String vType=null;
	String ISBN=null;
	String volume=null;
	String Journal=null;
	String number=null;
	String BookTitle=null;
	
	
	try{
		if(!line_aminer.isEmpty()){
			//Title,Year,Authors,Institution,URL,Pages,vType,ISBN,Volume,Journal,Number,BookTitle
			String[] data= line_aminer.split("\\$\\:\\$");
						
			if(data[0]!=""){
				title=data[0].replace(".", "").replace("\"", "").replace("'", "''");	
			}
			 if(data[1]!=""){
				 year=data[1];
			 }
			 
			  if(data.length>2){
			 if(data[2]!=""){
				 authors=data[2].replace("\"", "").replace("'", "''");	
			 }
			 }
			  if(data.length>3){
			 if(data[3]!=""){
				 institution=data[3].replace("\"", "").replace("'", "''");
			 }
			 }
			  if(data.length>4){
			 if(data[4]!=""){
				 URL=data[4].replace("'", "''");
			 }
			 }
			 	 if(data.length>5){		 
			  if(data[5]!=""){
				 Pages=data[5].replace("'", "''");
			 }
			 }
			 
			  if(data.length>6){
			  if(data[6]!=""){
				 vType=data[6].replace("'", "''");
			 }
			 }
			 
			 if(data.length>7){
			  if(data[7]!=""){
				 ISBN=data[7].replace("'", "''");
			 }
			}
			
			 if(data.length>8){
			 if(data[8]!=""){
				 volume=data[8].replace("'", "''");
			 }
			}
			
			 if(data.length>9){
			 if(data[9]!=""){
				 Journal=data[9].replace("'", "''");
			 }
			 }
			 
			  if(data.length>10){
			 if(data[10]!=""){
				 number=data[10].replace("'", "''");
			 }
			 }
			 
			  if(data.length>11){
			 if(data[11]!=""){
				 BookTitle=data[11].replace("'", "''");
			 }
			}
		}
		
		 insertStmt="insert into dblp_withouReference values('"+title+"','"+year+"','"+authors+"','"+institution+"','"+URL+"','"+
		Pages+"','"+vType+"','"+ISBN+"','"+volume+"','"+Journal+"','"+number+"','"+BookTitle+"')";
		
		//step4 execute query  
		stmt.executeQuery(insertStmt);  
	}catch(Exception e1){
		System.out.println(insertStmt);
		System.out.println(line_aminer);
		e1.printStackTrace();
	}
}

con.commit();

con.close(); 
  
}catch(Exception e){
	e.printStackTrace();
	System.out.println(e);}  
  
}  



}  