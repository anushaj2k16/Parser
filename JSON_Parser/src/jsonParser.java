import org.omg.CORBA.portable.InputStream;
import java.util.Properties;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
public class jsonParser {

	    public static void main(String[] args) throws Exception {
	        java.io.InputStream is = 
	                JsonParsing.class.getResourceAsStream( "sample-json.txt");
	        String jsonTxt = IOUtils.toString( is );

	        JSONObject json = (JSONObject) JSONSerializer.toJSON( jsonTxt );        
	        double coolness = json.getDouble( "coolness" );
	        int altitude = json.getInt( "altitude" );
	        JSONObject pilot = json.getJSONObject("pilot");
	        String firstName = pilot.getString("firstName");
	        String lastName = pilot.getString("lastName");

	        System.out.println( "Coolness: " + coolness );
	        System.out.println( "Altitude: " + altitude );
	        System.out.println( "Pilot: " + lastName );
	    }
	}

