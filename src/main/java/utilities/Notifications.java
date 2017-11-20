package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.Price;
import repositories.UserRepository;
import schemas.Mutation;
import models.Price;

public class Notifications {
	private static JDBC jdbc = new JDBC();
	private static UserRepository userRepository  = new UserRepository();
	private static Mutation mutation = new Mutation(userRepository);
	
	public static void main(String[] args) {

		//jdbc.saveUser(5, "mouse5", "mousepassword5", 8001.00);
		//System.out.println("Mydouble: " + jdbc.findById(5));
		while (true) {
			int id = -1;
			//if (!mutation.getLoggedInUserId().equals("-1")) {
				//id = Integer.parseInt(mutation.getLoggedInUserId());
			
			//}
			id = 5;
			getPrice(jdbc.findById(id));
		}
		
	}

	
	public static void getPrice(double notifyPrice) {
			URL url;
		    HttpURLConnection conn;
		    BufferedReader br;
		    String line;
		    String result = "";
		try {
				url = new URL("https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC&tsyms=USD");
				
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
			
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				result = "";
				while ((line = br.readLine()) != null) {
					result += line;
				}
				System.out.println("result " + result);
				
				JsonParser parser = new JsonParser();
				JsonElement rootNode = parser.parse(result);
				
				if (rootNode.isJsonObject()) {
					JsonObject display = rootNode.getAsJsonObject();
					JsonObject raw = display.getAsJsonObject("RAW");
					JsonObject bitcoin = raw.getAsJsonObject("BTC");
					JsonObject usd = bitcoin.getAsJsonObject("USD");
					JsonElement price = usd.get("PRICE");
					JsonElement low = usd.get("LOW24HOUR");
					JsonElement high = usd.get("HIGH24HOUR");

					double priceD = Double.parseDouble(price.toString());
					System.out.println("Current Price: " + priceD);
					double priceL = Double.parseDouble(low.toString());
					System.out.println("Lowest Price: " + priceL);
					double priceH = Double.parseDouble(high.toString());
					System.out.println("Highest Price: " + priceH);
					
					//create an instance of price 
					Price priceClass = new Price(priceD, priceL, priceH);
					
					jdbc.savePrice(priceD, priceH, priceL);
				}
				// delay function on 10000ms
				Thread.sleep(10000);
				br.close();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	    
	}
}
