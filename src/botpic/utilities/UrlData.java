package botpic.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.net.URLConnection;


public class UrlData {
	private  String data;

	public UrlData(String str) {
		this.data = str;
	}

	public String getStr() {
		return data;
	}


	public void setStr(String str) {
		this.data = str;
	}	


	@SuppressWarnings("unchecked")
	public  JSONArray getJson() throws IOException {
		/*A partir de la url se obtiene el json de donde van a estar las pics, 
		 * devuelve un array.
		*/
		try {
			//Se conecta con la url.
		    URL PreUrl = new URL(this.data);
		    HttpURLConnection PreRequest = (HttpURLConnection) PreUrl.openConnection();
		    PreRequest.setRequestMethod("GET");
		    String PreRedirect = PreRequest.getHeaderField("Location");
		    if (PreRedirect != null){
		        PreRequest = (HttpURLConnection) new URL(PreRedirect).openConnection();
		    }
		    PreRequest.connect();
//		    if (request.getResponseCode() == 200) {
//		    	System.out.println("Code 200: Se establecio conexion correctamente");
//		    	System.out.println(request.getURL());
//		    } else {
//		    	System.out.println("Code " + request.getResponseCode() + ": No se pudo establecer conexcion");
//		    }
		    String urljson = PreRequest.getURL().toString()+".json";
		    System.out.println(urljson);
		    URL url = new URL(urljson);
		    HttpURLConnection request = (HttpURLConnection) url.openConnection();
		    request.setRequestMethod("GET");
		    String redirect = request.getHeaderField("Location");
		    if (redirect != null){
		        request = (HttpURLConnection) new URL(redirect).openConnection();
		    }
		    request.connect();
	    
		    
		    //carga el buffer desde la url para pasarlo al json
		    BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) request.getContent()));
		    String input = in.readLine();
		    
		    
		    //se conviernte en json
		    JSONParser parse = new JSONParser();
		    try {
		    	Object obj = parse.parse(input);
				JSONArray jsonp = new JSONArray();
				jsonp.add(obj);
				return jsonp;
				
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			} 
		} catch (MalformedURLException error) {
            // Output expected MalformedURLExceptions.
            System.out.println("Hubo un error en la URL de la imagen.");
            return null;
        } catch (Exception exception) {
            // Output unexpected Exceptions.
        	System.out.println(exception.toString());
          //  System.out.println("Hubo un error al procesar la imagen. Con el parseo");
            return null;
        }
	}
	
	public boolean checkInternet() {
	    try {
	        URL url = new URL("http://www.google.com");
	        URLConnection connection = url.openConnection();
	        connection.connect();
	        return true;
	     } catch (MalformedURLException e) {
	        System.out.println("Internet is not connected");
	        return false;
	     } catch (IOException e) {
	        System.out.println("Internet is not connected");
	        return false;
	     }
	}
}
