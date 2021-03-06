package Parser.jsonparser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/***
 * DBLP+citation network contains:
	-3,079,007 papers
	-25,166,994 citation relationship
 * @author Anusha Janardhana
 * Json to CSV parser
 */
public class dblp_parser {
	static int returnCount=0;
	static int lineCount=0;
	static boolean multipleReference=false;
	static String UpstreamFileName;
	static FileWriter upstreamFW;
    public static void main(String[] args) throws IOException {
    	//dataStructure objdataStructure= new dataStructure();
		BufferedReader reader;
		String line;
		
        JSONParser parser = new JSONParser(); 
        String filePath="E:\\dblp.v10\\dblp-ref\\dblp-ref-3.json";
        datastruct objdatastruct= new datastruct();
		reader = new BufferedReader(new FileReader(filePath));
		while ((line = reader.readLine()) != null)  
		{
			lineCount++;
			try{
				if(!line.isEmpty()){

					line="["+line+"]";		
					JSONParser parser1 = new JSONParser(); 
					JSONArray jsonArray = (JSONArray) parser.parse(line);
									
					//for(Object jsnobj: jsonArray){	    	
					   						
						Object jsnobj =jsonArray;
						if(((JSONObject) jsnobj).get("id")!=null){
							objdatastruct.id=((JSONObject) jsnobj).get("id").toString();
						}else{
							objdatastruct.id="";
						}
						
						//abstract
						if(((JSONObject) jsnobj).get("abstract")!=null){
							objdatastruct.Abstract=((JSONObject) jsnobj).get("abstract").toString();
						}else{
							objdatastruct.Abstract="";
						}
 	
					    	//citation count
						if(((JSONObject) jsnobj).get("n_citation")!=null){
					    	objdatastruct.CitationCount=((JSONObject) jsnobj).get("n_citation").toString();
						}else{
							objdatastruct.CitationCount="0";
						}
					    	//title
						if(((JSONObject) jsnobj).get("title")!=null){
					    	objdatastruct.Title=((JSONObject) jsnobj).get("title").toString();
						}else{
							objdatastruct.Title="";
						}
						
					    	//year
						if(((JSONObject) jsnobj).get("year")!=null){
					    	objdatastruct.Year=((JSONObject) jsnobj).get("year").toString();
						}else{
							objdatastruct.Year="";
						}
					    	//venue
						if(((JSONObject) jsnobj).get("venue")!=null){
					    	objdatastruct.Venue=((JSONObject) jsnobj).get("venue").toString();
						}else{
							objdatastruct.Venue="";
						}
					    	
					    	//Authors
						if(((JSONObject) jsnobj).get("authors")!=null) {
						if (!((JSONObject) jsnobj).get("authors").toString().contains("[]")){
							Gson gson = new Gson();
					    	String authors=((JSONObject) jsnobj).get("authors").toString();
					    	String[] authorArray = gson.fromJson(authors, String[].class);
					    	 if(authorArray.length==1){
					    		 objdatastruct.Authors=authorArray[0];
					            }else{
					            	 for (String author : authorArray) {
					            		 if(objdatastruct.Authors==null){
					            			 objdatastruct.Authors=author+";";
					            		 }else{
					            			 objdatastruct.Authors+= author+";";
					            		 }		 
								        }
					            }
					    	 if(authorArray.length>1){
					    		 objdatastruct.Authors=objdatastruct.Authors.substring(0,objdatastruct.Authors.length()-1);
					    	 }
						}		
					}else{
						objdatastruct.Authors="";
					}
					    	 
					    	 //references
						try{
							if ((((JSONObject) jsnobj).get("references")!=null)){	
								if (!((JSONObject) jsnobj).get("references").toString().contains("[]")){
									Gson gson = new Gson();
									String references=((JSONObject) jsnobj).get("references").toString();
							    	String[] referencesArray = gson.fromJson(references, String[].class);
							    	 if(referencesArray.length==1){
							    		 objdatastruct.References=referencesArray[0];
							            }else{
							            	 for (String reference : referencesArray) {
							            		 if(objdatastruct.References==null){
							            			 objdatastruct.References=reference+";";
							            		 }else{
							            			 
							            			 objdatastruct.References+= reference+";";
							            		 }
							            		
										        }
							            }
							    	 if(referencesArray.length>1){
							    		 objdatastruct.References=objdatastruct.References.substring(0,objdatastruct.References.length()-1);
							    		// System.out.println(objdatastruct.References);
							    	 }
								}else{
									objdatastruct.References="";
								}
						    	 	    	
							}else{
								objdatastruct.References="";
							}
						}catch(Exception e){
							System.out.println(((JSONObject) jsnobj).get("references").toString());
							e.printStackTrace();
						}
						
					  //  }
					writeToFile(objdatastruct);
					objdatastruct= new datastruct();
					}					
				//}
			}catch(Exception e){
				System.out.println(line);
				e.printStackTrace();
			
			}
			
		}
		System.out.println("Lines read from file = "+returnCount);
		System.out.println("Wrote to file = "+returnCount);
		upstreamFW.close();
    }
    
    static void writeToFile(datastruct itemObj){
		 StringBuilder sb;
		 returnCount++;
			sb = new StringBuilder();			
			if (UpstreamFileName == null) {
				UpstreamFileName = "dblp+ref-newdelimiter-3.dsv"; //dblp+citation_0
				try {
					upstreamFW = new FileWriter(new File(UpstreamFileName), true);
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
					sb.append("CitationCount");
					sb.append("$:$");
					sb.append("References");
					sb.append("$:$");
					sb.append("Abstract");					
					upstreamFW.write(sb.toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}	
			
			String idPaper="\""+itemObj.id+"\"";
			String title="\""+itemObj.Title+"\"";
			String authors="\""+itemObj.Authors+"\"";
			String venue="\""+itemObj.Venue+"\"";
			String references="\""+itemObj.References+"\"";
			String paperAbstract="\""+itemObj.Abstract+"\"";
			
			sb= new StringBuilder();
			sb.append(idPaper);
			sb.append("$:$");
			sb.append(title);
			sb.append("$:$");
			sb.append(itemObj.Year);
			sb.append("$:$");
			sb.append(authors);
			sb.append("$:$");
			sb.append(venue);
			sb.append("$:$");
			sb.append(itemObj.CitationCount);
			sb.append("$:$");
			sb.append(references);
			sb.append("$:$");
			sb.append(paperAbstract);
			
			try {
				upstreamFW.write(System.getProperty("line.separator"));
				upstreamFW.write(sb.toString());	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
}