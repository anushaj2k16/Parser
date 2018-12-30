
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

public class venueRelation {
	public static final String VENUELIST="venueList";
	public static JedisPool pool;
	public static Jedis jedis;
	public static Map<String,String> venueMap;
	static long idCnt=0;	
	static String fileNameVertex;
	static String fileNameEdge;
	static FileWriter venueVertexFW;
	static FileWriter venueEdgeFW;
	
	public static void main(String[] args) {
		pool = new JedisPool(new JedisPoolConfig(),"localhost",6379);	
		jedis =null;
		
		String line;
		String filePath ="E:\\DBLP+Ref+newdelimiter\\dblp+ref-newdelimiter-all.csv";
		// TODO Auto-generated method stub
		try{
			jedis = pool.getResource();   
			System.out.println("Connection to server sucessfully"); 
			System.out.println("Server is running: "+jedis.ping());
			
			BufferedReader srcReader= new BufferedReader(new FileReader(filePath));
			while((line=srcReader.readLine())!=null){
				String[] data = line.split("\\$\\:\\$"); //total of 15 indexes
				
				if (!data[4].replaceAll("\"", "").equals("")){
					venueMap=new HashMap<String, String>();
					String venueName= data[4].replaceAll("\"", "");
					
						if(jedis.hget(VENUELIST, venueName) != null){		
							String existingVenueId=jedis.hget(VENUELIST, venueName);
							writeVenueEdge(data[0],existingVenueId);
						}else{
							idCnt++;
							String venueID="ven_"+idCnt;
							venueMap.put(venueName, venueID);
							jedis.hmset(VENUELIST,venueMap );
							writeVenueEdge(data[0],venueID);
							writeVenueVertex(venueID, venueName);
						}
				}else{
					continue;
				}
				
			}
			venueVertexFW.close();
			venueEdgeFW.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static void writeVenueEdge(String paperid, String venueid){
		StringBuilder sb;
		if(fileNameEdge==null){
			fileNameEdge="venue_edge.dsv";
			try {
				venueEdgeFW= new FileWriter(new File(fileNameEdge),true);
				sb=new StringBuilder();
				sb.append("PAPER_ID");
				sb.append("$:$");
				sb.append("VENUE_ID");
				venueEdgeFW.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
				sb=new StringBuilder();
				sb.append(paperid);
				sb.append("$:$");
				sb.append("\""+venueid+"\"");
				try {
					venueEdgeFW.write(System.getProperty("line.separator"));
					venueEdgeFW.write(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	
	static void writeVenueVertex(String venueid, String venueName){
		StringBuilder sb;
		if(fileNameVertex==null){
			fileNameVertex="venue_vertex.dsv";
			try {
				venueVertexFW= new FileWriter(new File(fileNameVertex),true);
				sb=new StringBuilder();
				sb.append("VENUE_ID");
				sb.append("$:$");
				sb.append("VENUE_NAME");
				venueVertexFW.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
				sb=new StringBuilder();
				sb.append("\""+venueid+"\"");
				sb.append("$:$");
				sb.append("\""+venueName+"\"");
				try {
					venueVertexFW.write(System.getProperty("line.separator"));
					venueVertexFW.write(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	
	}


