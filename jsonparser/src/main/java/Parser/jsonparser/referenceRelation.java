package Parser.jsonparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class referenceRelation {	
	static String fileName;
	static FileWriter refFW;
	public static void main(String[] args) {
		String line;
		
		String filePath ="E:\\DBLP+Ref+newdelimiter\\dblp+ref-newdelimiter-all.csv";
		// TODO Auto-generated method stub
		try{
			BufferedReader srcReader= new BufferedReader(new FileReader(filePath));
			while((line=srcReader.readLine())!=null){
			
				String[] data = line.split("\\$\\:\\$"); //total of 15 indexes
				
				if (!data[6].replaceAll("\"", "").equals("")){
					writeReferences(data[0], data[6]);
				}else{
					continue;
				}
				
			}
			refFW.close();
			srcReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static void writeReferences(String From_id, String To_id){
		StringBuilder sb;
		if(fileName==null){
			fileName= "E:\\Neo4jDatafiles\\referenceRelationship.dsv";
			try {
				refFW= new FileWriter(new File(fileName),true);
				sb=new StringBuilder();
				sb.append("FROM_ID");
				sb.append("$:$");
				sb.append("TO_ID");
				refFW.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
					
			int index=0;
			String[] referenceids=To_id.replace("\"","").split(";");
			if(referenceids.length>1){
				index=1;
			}
			 for (int i=index;i<referenceids.length;i++) {
				sb=new StringBuilder();
				sb.append(From_id);
				sb.append("$:$");
				sb.append("\""+referenceids[i]+"\"");
				try {
					refFW.write(System.getProperty("line.separator"));
					refFW.write(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

	}
		
}
