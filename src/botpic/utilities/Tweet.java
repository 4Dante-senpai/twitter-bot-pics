package botpic.utilities;

import java.io.UnsupportedEncodingException;


public class Tweet {

	private String texto;
	private String imagenId;
	

	public Tweet (Pic imagen, String imagenId) throws UnsupportedEncodingException {
		this.texto = parse(imagen.getTag_string_artist(),imagen.getTag_string_character(),imagen.getSource());
		this.imagenId = imagenId;
	}
	
	
	private String parse(String artista, String personajes, String fuente) throws UnsupportedEncodingException {

		
		String acumulador;
		artista = "\u270F Artist: " + artista;
		String personajeSeries = "\u2668 Character/Series:";
		fuente = "\u2615 Source: " + fuente;
		String pie = "\u2B50 Content taken periodically from Konachan, please support the original artist. \u2B50";
		

		if (personajes.length() > 0) {
			String[] personajesArray = personajes.split(" ");
			for (int i=0; i < personajesArray.length; i++) {
				String[] nombresArray = personajesArray[i].split("_");
				String personajeIndv = "#";
				for (int x=0; x < nombresArray.length; x++) {
					personajeIndv = personajeIndv + capitalize(nombresArray[x]);
				}
				personajeIndv = personajeIndv.replace("("," ( #").replace(")", " )").replace("'", "");	
				personajeSeries = personajeSeries + " " + personajeIndv;
			} 
		}	else {
			personajeSeries = personajeSeries + " Original";
		}

		acumulador = artista + System.lineSeparator() + System.lineSeparator() + personajeSeries + System.lineSeparator() + System.lineSeparator() + fuente + System.lineSeparator() + System.lineSeparator() + pie;
		
		return acumulador;
	}


	private static String capitalize(String str)
	{
	    if(str == null) return str;
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public String getImagenId() {
		return imagenId;
	}


	public void setImagenId(String imagenId) {
		this.imagenId = imagenId;
	}

	
	
	}