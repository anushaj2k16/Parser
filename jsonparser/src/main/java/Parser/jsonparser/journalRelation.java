
package Parser.jsonparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class journalRelation {
	public static final String JOURNALLIST="journalList";
	public static JedisPool pool;
	public static Jedis jedis;
	public static Map<String,String> journalMap;
	static long idCnt=0;	
	static String fileNameVertex;
	static String fileNameEdge;
	static FileWriter journalVertexFW;
	static FileWriter journalEdgeFW;
	
	public static void main(String[] args) {
		pool = new JedisPool(new JedisPoolConfig(),"localhost",6379);	
		jedis =null;
		
		String line;
		String filePath ="E:\\Neo4jDatafiles\\leftjoindistinct+refexceeded_final.csv";
		// TODO Auto-generated method stub
		try{
			jedis = pool.getResource();   
			System.out.println("Connection to server sucessfully"); 
			System.out.println("Server is running: "+jedis.ping());
			
			BufferedReader srcReader= new BufferedReader(new FileReader(filePath));
			while((line=srcReader.readLine())!=null){
				String[] data = line.split("\\$\\:\\$"); //total of 15 indexes
				
				if (!data[11].replaceAll("\"", "").equals("")){
					journalMap=new HashMap<String, String>();
					String journalName= data[11].replaceAll("\"", "");
					
						if(jedis.hget(JOURNALLIST, journalName) != null){		
							String existingAUId=jedis.hget(JOURNALLIST, journalName);
							writeJournalEdge(data[0],existingAUId);
						}else{
							idCnt++;
							String journalID="jour_"+idCnt;
							journalMap.put(journalName, journalID);
							jedis.hmset(JOURNALLIST,journalMap );
							writeJournalEdge(data[0],journalID);
							writeJournalVertex(journalID, journalName);
						}
				}else{
					continue;
				}
				
			}
			journalVertexFW.close();
			journalEdgeFW.close();
			srcReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static void writeJournalEdge(String paperid, String journalid){
		StringBuilder sb;
		if(fileNameEdge==null){
			fileNameEdge="paperJournalRelationship.dsv";
			try {
				journalEdgeFW= new FileWriter(new File(fileNameEdge),true);
				sb=new StringBuilder();
				sb.append("PAPER_ID");
				sb.append("$:$");
				sb.append("JOURNAL_ID");
				journalEdgeFW.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
				sb=new StringBuilder();
				sb.append(paperid);
				sb.append("$:$");
				sb.append("\""+journalid+"\"");
				try {
					journalEdgeFW.write(System.getProperty("line.separator"));
					journalEdgeFW.write(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	
	static void writeJournalVertex(String journalid, String journalName){
		StringBuilder sb;
		if(fileNameVertex==null){
			fileNameVertex="journalVertex.dsv";
			try {
				journalVertexFW= new FileWriter(new File(fileNameVertex),true);
				sb=new StringBuilder();
				sb.append("JOURNAL_ID");
				sb.append("$:$");
				sb.append("JOURNAL_NAME");
				journalVertexFW.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
				sb=new StringBuilder();
				sb.append("\""+journalid+"\"");
				sb.append("$:$");
				sb.append("\""+journalName+"\"");
				try {
					journalVertexFW.write(System.getProperty("line.separator"));
					journalVertexFW.write(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	
	}

