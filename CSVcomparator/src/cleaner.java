import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class cleaner {
	static int lineNumber=0;
	static String xmlFileName;
	static FileWriter xmlFW;
	public static void main(String[] args) {
		//output files of jsonparser are input to this prgram
		String filePath="D:\\FileReadAndWrite\\CSVcomparator\\dblp+ref-newdelimiter-0.csv"; //start with 0 and ends with dblp+ref-newdelimiter-3.dsv
		BufferedReader reader_aminer;
		try {
			reader_aminer = new BufferedReader(new FileReader(filePath));
			String line_aminer;
			// TODO Auto-generated method stub
			while ((line_aminer = reader_aminer.readLine()) != null) {
				lineNumber++;
				if(!line_aminer.isEmpty()){
									
					try{
						String[] data= line_aminer.split("\\$\\:\\$");
						//lineNumber++;
						System.out.println(data.length+" "+lineNumber);
						//System.out.println("len");
						if(data[0].substring(9, 10).equals("-")){
							//do nothing
						}else{
							System.out.println(lineNumber+" "+line_aminer);
							writeToFile(lineNumber,data);
							continue;
						}
						String titleYear_aminer=data[1]+data[3];
						writeToFile(lineNumber,data);
					}catch(Exception e){
					System.out.println(lineNumber+" "+line_aminer);
						continue;
					}
				}
			}
			reader_aminer.close();
		//	xmlFW.close();
			
		} catch ( IOException e) {
			System.out.println("here");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//writes the dirty records identified into check.csv which can be cleaned manually using this as base file.	
	static void writeToFile(int lines,String[] expdata){
		 StringBuilder sb;
			
			sb = new StringBuilder();			
			if (xmlFileName == null) {
				xmlFileName = "check.csv";
				try {
					xmlFW = new FileWriter(new File(xmlFileName), true);
						
				}catch(Exception e){
					
				}
				
	}
			sb= new StringBuilder();
			sb.append(expdata[1]);
			sb.append(",");
			sb.append(expdata[2]);
			try {
				xmlFW.write(System.getProperty("line.separator"));
				xmlFW.write(sb.toString());	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 }
}
