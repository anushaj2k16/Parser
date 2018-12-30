package Parser.jsonparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class replaceVenJourAuthorIDs {
	public static JedisPool pool;
	public static Jedis jedis;			
	static String filepaperVertexIdReplaced;
	static FileWriter paperVertexFW;
	public static void main(String[] args) {
		
		pool = new JedisPool(new JedisPoolConfig(),"localhost",6379);	
		jedis =null;
		String line;
		String filePath ="E:\\DBLP+Ref+newdelimiter\\dblp+ref-newdelimiter-all.csv"; 
		
		
		try{
			jedis = pool.getResource();   
			System.out.println("Connection to server sucessfully"); 
			System.out.println("Server is running: "+jedis.ping());
			
			BufferedReader srcReader= new BufferedReader(new FileReader(filePath));
			while((line=srcReader.readLine())!=null){
				String[] data = line.split("\\$\\:\\$"); //total of 15 indexes
				int firstLoopindex=0;
				
			}
		}catch(Exception e){
				e.printStackTrace();
			}
	}

}
