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

public class authorRelation {

	public static final String AUTHORSLIST="authorsList";
	public static JedisPool pool;
	public static Jedis jedis;
	public static Map<String,String> authorMap;
	static long idCnt=0;			
	static String fileNameVertex;
	static String fileNameEdge;
	static String fileNamecoAuthorEdge;
	static FileWriter authorVertexFW;
	static FileWriter authorEdgeFW;
	static FileWriter coauthorshipEdgeFW;
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
				int firstLoopindex=0;
				if (!data[3].replaceAll("\"", "").equals("")){
					authorMap=new HashMap<String, String>();
					String[] authors= data[3].replaceAll("\"", "").split(";");
					/*if(authors.length>1){
						firstLoopindex=1;
					}*/
					for(int i=firstLoopindex;i<authors.length;i++){
						//System.out.println(jedis.hget(AUTHORSLIST, authors[i]));
						if(jedis.hget(AUTHORSLIST, authors[i]) != null){		
							String existingAUId=jedis.hget(AUTHORSLIST, authors[i]);
							writeAuthorEdge(data[0],existingAUId);
						}else{
							idCnt++;
							String authorID="au_"+idCnt;
							authorMap.put(authors[i], authorID);
							jedis.hmset(AUTHORSLIST,authorMap );
							//System.out.println(jedis.hget(AUTHORSLIST, authors[i]));
							writeAuthorEdge(data[0],authorID);
							writeAuthorVertex(authorID, authors[i]);
						}
					}
					
					if(authors.length>1){
					String[] coAuthorship= data[3].replaceAll("\"", "").split(";");
					for(int m=0;m<coAuthorship.length;m++){
						for(int n=m+1;n<coAuthorship.length;n++){
							String authorid=jedis.hget(AUTHORSLIST, coAuthorship[m]);
							String coAuthorid=jedis.hget(AUTHORSLIST, coAuthorship[n]);
							writeCoAuthorshipEdge(authorid,coAuthorid);
						}
					}
				}
				}else{
					continue;
				}
				
			}
			authorVertexFW.close();
			authorEdgeFW.close();
			coauthorshipEdgeFW.close();
			srcReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static void writeAuthorEdge(String paperid, String authorid){
		StringBuilder sb;
		if(fileNameEdge==null){
			fileNameEdge="E:\\Neo4jDatafiles\\paperAuthorRelationship.dsv";
			try {
				authorEdgeFW= new FileWriter(new File(fileNameEdge),true);
				sb=new StringBuilder();
				sb.append("PAPER_ID");
				sb.append("$:$");
				sb.append("AUTHOR_ID");
				authorEdgeFW.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
				sb=new StringBuilder();
				sb.append(paperid);
				sb.append("$:$");
				sb.append("\""+authorid+"\"");
				try {
					authorEdgeFW.write(System.getProperty("line.separator"));
					authorEdgeFW.write(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	
	static void writeAuthorVertex(String authorid, String authorName){
		StringBuilder sb;
		if(fileNameVertex==null){
			fileNameVertex="E:\\Neo4jDatafiles\\authorVertex.dsv";
			try {
				authorVertexFW= new FileWriter(new File(fileNameVertex),true);
				sb=new StringBuilder();
				sb.append("AUTHOR_ID");
				sb.append("$:$");
				sb.append("AUTHOR_NAME");
				authorVertexFW.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
				sb=new StringBuilder();
				sb.append("\""+authorid+"\"");
				sb.append("$:$");
				sb.append("\""+authorName+"\"");
				try {
					authorVertexFW.write(System.getProperty("line.separator"));
					authorVertexFW.write(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	
	static void writeCoAuthorshipEdge(String authorid, String coauthorid){
		StringBuilder sb;
		if(fileNamecoAuthorEdge==null){
			fileNamecoAuthorEdge="E:\\Neo4jDatafiles\\coauthorshipRelationship.dsv";
			try {
				coauthorshipEdgeFW= new FileWriter(new File(fileNamecoAuthorEdge),true);
				sb=new StringBuilder();
				sb.append("AUTHOR_ID");
				sb.append("$:$");
				sb.append("COAUTHOR_ID");
				coauthorshipEdgeFW.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
				sb=new StringBuilder();
				sb.append("\""+authorid+"\"");
				sb.append("$:$");
				sb.append("\""+coauthorid+"\"");
				try {
					coauthorshipEdgeFW.write(System.getProperty("line.separator"));
					coauthorshipEdgeFW.write(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			
		}
	}

