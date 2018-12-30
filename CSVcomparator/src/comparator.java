import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class comparator {
	static int recordMatched=30;
	static String xmlFileName;
	static FileWriter xmlFW;
	static boolean matched=false;
	public static void main(String [] args)
	{
		BufferedReader reader_aminer;
		String titleYear_aminer="";
		String titleYear_dblp="";
		String line_aminer;
		String line_dblp;
		
	    try {
	    	String filePath="\\E:\\DBLP+Ref+newdelimiter\\dblp+ref-newdelimiter-all.csv"; //ID,Title,Authors,Year,Venue,CitationCount,References,Abstract
	    
	    	String dblpFilePath="\\E:\\DBLP+Ref+newdelimiter\\dblp+xml+newdelimiter+1.csv"; //Title,Year,Authors,Institution,URL,Pages,vType,ISBN,Volume,Journal,Number,BookTitle
	    	
			reader_aminer = new BufferedReader(new FileReader(filePath));
			
			RandomAccessFile  reader_dblp = new RandomAccessFile(dblpFilePath, "rw");
			
			while ((line_aminer = reader_aminer.readLine()) != null)  
			{	
				try{
					if(!line_aminer.isEmpty()){
						String[] data_dblp=new String[12];
						String[] data= line_aminer.split("\\$\\:\\$");
						try{
							data[1]=data[1].replace(".", "");
							 titleYear_aminer=data[1].replace("\"", "").toLowerCase()+data[2];
							 //System.out.println(titleYear_aminer);
						}catch(Exception e){
							System.out.println(data);
						}
						
						while((line_dblp = reader_dblp.readLine()) != null){
								try{
									data_dblp=new String[12];
									 data_dblp=line_dblp.split("\\$\\:\\$");
									try{
										 titleYear_dblp=data_dblp[0].toLowerCase()+data_dblp[1];
									}catch(Exception e){
										System.out.println(data_dblp);
									}
									
									if(titleYear_aminer.equals(titleYear_dblp)){
										recordMatched++;
										System.out.println("lines matched - "+recordMatched+titleYear_aminer);
										writeToFile(data, data_dblp);
										//reader_dblp.seek(0);
										matched=true;
										break;
									}
								}catch(Exception e){
									System.out.println(line_aminer);
									System.out.println(line_dblp);
									e.printStackTrace();
								}
							
							}	
						if(!matched){
							data_dblp=new String[12];
							writeToFile(data,data_dblp);					
						}	
						matched=false;
						reader_dblp.seek(0);
						}
					}catch(Exception e){
						
						System.out.println(line_aminer);
						e.printStackTrace();
					}
				}
			
			reader_aminer.close();
			reader_dblp.close();
			xmlFW.close();
	        } catch (IOException e) {
	        	System.out.println("here");
	        e.printStackTrace();
	        return;
	    }
	}
	
	static void writeToFile(String[] aminer,String[] dblp){
		 StringBuilder sb;		
			sb = new StringBuilder();			
			if (xmlFileName == null) {
				xmlFileName = "integrated_data+2.csv";
				try {
					xmlFW = new FileWriter(new File(xmlFileName), true);
					sb.append("ID");
					sb.append("$:$");
					sb.append("Title");
					sb.append("$:$");
					sb.append("Year");
					sb.append("$:$");
					sb.append("Authors");
					sb.append("$:$");
					sb.append("Venue");
					sb.append("$:$");
					sb.append("CitationCOunt");
					sb.append("$:$");
					sb.append("Institution");
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
					sb.append("$:$");
					sb.append("URL");
					sb.append("$:$");
					sb.append("Abstract");
					sb.append("$:$");
					sb.append("References");
					xmlFW.write(System.getProperty("line.separator"));
					xmlFW.write(sb.toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}	
			
			for(int i=0;i<dblp.length;i++){
				if(dblp[i]==null){
					dblp[i]="";
				}
			}
			
			sb= new StringBuilder();
			
			sb.append(aminer[0]); //id
			sb.append("$:$");
			sb.append(aminer[1]); //title
			sb.append("$:$");
			sb.append(aminer[2]); //year
			sb.append("$:$");
			sb.append(aminer[3]); //authors
			sb.append("$:$");
			sb.append(aminer[4]); //venue
			sb.append("$:$");
			sb.append(aminer[5]); //citationcount
			sb.append("$:$");
			sb.append(dblp[3]); //Institution
			sb.append("$:$");
			sb.append(dblp[5]); //pages
			sb.append("$:$");
			sb.append(dblp[6]); //vtype
			sb.append("$:$");
			sb.append(dblp[7]); //ISBN
			sb.append("$:$");
			if(dblp.length<9){
				sb.append("");
			}else{				
				sb.append(dblp[8]); //volume
			}
			sb.append("$:$");
			if(dblp.length<10){
				sb.append("");
			}else{				
				sb.append(dblp[9]); //journal
			}
			sb.append("$:$");
			if(dblp.length<11){
				sb.append("");
			}else{
				sb.append(dblp[10]); //number		
			}
			sb.append("$:$");
			if(dblp.length<12){	
				sb.append("");
			}else{
				sb.append(dblp[11]); //booktitle		
			}
			sb.append("$:$");
			if(dblp.length<5){
				sb.append("");
			}else{
				sb.append(dblp[4]); //url			
			}
			sb.append("$:$");
			sb.append(aminer[7]); //abstract
			sb.append("$:$");
			sb.append(aminer[6]); //references
			
			
			try {
				xmlFW.write(System.getProperty("line.separator"));
				xmlFW.write(sb.toString());	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	
}
