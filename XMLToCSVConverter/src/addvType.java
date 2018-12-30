import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class addvType {
	static String fileName;
	static FileWriter refFW;
	static StringBuilder sb;
	public static void main(String[] args) {
		String line;
		
		String filePath ="F:\\neo4j-community-3.4.1-windows\\neo4j-community-3.4.1\\bin\\authorVertex.csv";
		// TODO Auto-generated method stub
		try{
			BufferedReader srcReader= new BufferedReader(new FileReader(filePath));
			while((line=srcReader.readLine())!=null){
				StringBuilder sb;
					writeReferences(line);		
			}
			refFW.close();
			srcReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	static void writeReferences(String line){
		
		if(fileName==null){
			fileName= "authorVertexNew.csv"; //F:\neo4j-community-3.4.1-windows\neo4j-community-3.4.1\bin
			try {
				refFW= new FileWriter(new File(fileName),true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		sb=new StringBuilder();	
		
				try {
					sb.append(line);
					//sb.append(",");
					//sb.append("\""+"author"+"\"");
					refFW.write(sb.toString());
					refFW.write(System.getProperty("line.separator"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	}
}
