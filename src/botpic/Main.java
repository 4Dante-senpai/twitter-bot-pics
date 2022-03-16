package botpic;

import botpic.utilities.*;
import twitter.client.Twitter;


import java.io.StringReader;

import org.json.simple.JSONArray;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;



public class Main {
	public static void main(String[] args) throws Exception {

		//final String urlKonachan = "http://danbooru.donmai.us/posts.json?tags=order%3Arandom&limit=1"; //Limit 1 significa que solo trae la info de 1 imagnes
		//Dejo de andar de la nada la API de danbooru
		
		final String urlKonachan = "https://danbooru.donmai.us/posts/random";
		
		
		
		
		UrlData data = new UrlData(urlKonachan); 
		System.out.println(urlKonachan);
		Gson gson = new Gson();
		Twitter twitter = new Twitter();
		Environment environment = new Environment();
		while (true) {
			while (data.checkInternet()) {
				JSONArray picsData = data.getJson();	
				if (picsData != null) {
					String json = picsData.toString().replace("\\", "").replace("[", "").replace("]", "");		
					JsonReader reader = new JsonReader(new StringReader(json));
					reader.setLenient(true);
					Pic picture = gson.fromJson(reader, Pic.class);
					picture.setAbsolutPath(environment.getPath());
			        boolean picInDisk = picture.download();
			        environment.insert(picture);
			        if ((picInDisk == true) && (picture.getRating().contains("s"))) {

				        try {	        	
				        	Tweet tweet = new Tweet (picture, twitter.UploadFile(picture.getPath()));
				        	twitter.sendTweet(tweet.getTexto(),tweet.getImagenId());
				        	System.out.println("Tweet realizado con exito. Proximo en 3 horas");
					        int time = 60000 * 180 ; //3 horas, 60000 = 1 minuto
					        Thread.sleep(time);
				        } catch (Exception exception) {
				        	System.out.println("Error al mandar el tweet, se intentara con otra imagen");				        	
				        }
			        }
				}
			}
			System.out.println("No hay internet, voy a esperar 15 minutos");
			Thread.sleep(900000);
		}
	}

}
