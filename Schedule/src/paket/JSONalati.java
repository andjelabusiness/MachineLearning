package paket;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONalati {

	public static String getJSONstringFile(String putanja) {
		Scanner skener;
		InputStream is = HandlerFile.inputStreamFile(putanja);
		skener = new Scanner(is);
		String json = skener.useDelimiter("\\Z").next();
		skener.close();
		return json;
	}
	
	public static JSONObject getJSONObjectFile (String putanja) {
		return new JSONObject(getJSONstringFile(putanja));
	}
	
	public static JSONArray getJSONnizFile (String putanja) {
		return new JSONArray(getJSONstringFile(putanja));
	}
	
	//sale
	public static Sala napraviSalu (JSONObject o) {
		Sala s = new Sala(o.getString("naziv"), o.getInt("kapacitet"), o.getInt("racunari"), o.getInt("dezurni"), o.getInt("etf"));
		return s;
	}
	
	// niz sala
	public static ArrayList<Sala> napraviSale (JSONArray json) {
		ArrayList<Sala> lista = new ArrayList<>();
		
		if (json != null) { 
			   int len = json.length();
			   for (int i=0;i<len;i++){ 
			   Sala s= napraviSalu((JSONObject) json.get(i));
			   lista.add(s);
		       } 
		}
			   
		return lista;
	}
	
	//sortiranje sala
	public static ArrayList<Sala> sortirajSale (ArrayList<Sala> sale){
		
		Collections.sort(sale, new Comparator<Sala>() {

			@Override
			public int compare(Sala s1, Sala s2) {
				
				return Integer.valueOf(s1.kapacitet).compareTo(s2.kapacitet);
			}
			
		});
		
		return sale;
	}
	
	//sortiranje grupa sala
	public static ArrayList<ArrayList<Sala>> sortirajMoguceSale ( ArrayList<ArrayList<Sala>> moguceSale, Ispit ispit){
		
		Collections.sort(moguceSale, new Comparator<ArrayList<Sala>>() {

			@Override
			public int compare(ArrayList<Sala> o1, ArrayList<Sala> o2) {
				int brdez1 = 0;
				boolean svietf1 = true;
				int visakmesta1 = 0;
				int brdez2 = 0;
				boolean svietf2 = true;
				int visakmesta2 = 0;
				
				for(int i = 0; i<o1.size(); i++) {
					brdez1 += o1.get(i).dezurni;
					if (o1.get(i).etf != 1) svietf1=false;
					visakmesta1 += o1.get(i).kapacitet;
				}	
				for(int i = 0; i<o2.size(); i++) {
					brdez2 += o2.get(i).dezurni;
					if (o2.get(i).etf != 1) svietf2=false;
					visakmesta2 += o2.get(i).kapacitet;
				}	
				visakmesta1 -= ispit.prijavljeni;
				visakmesta2 -= ispit.prijavljeni;
				
			
				if (brdez1<brdez2) return -1;
				else if (brdez1== brdez2 && svietf1 == true ) return -1;
				//else if (brdez1== brdez2 && svietf1 == false && svietf2 == true ) return 1;
				
				return 0;
			}
			
		});
		
		return moguceSale;
	}
	
	
	//rokovi i ispiti
	public static Ispit napraviIspit (JSONObject o) {
		
		JSONArray odsecij = o.getJSONArray("odseci");
		
        ArrayList<String> listaodseka = new ArrayList<>();
		if (odsecij != null) { 
			   int len = odsecij.length();
			   for (int i=0;i<len;i++){ 
			   listaodseka.add(odsecij.getString(i));
		       } 
		}
		
		Ispit i = new Ispit(o.getString("sifra"), o.getInt("prijavljeni"), o.getInt("racunari"), listaodseka);
		return i;
	}
	
	public static Rok napraviRok (JSONObject o) {
		
		JSONArray jsonispiti = o.getJSONArray("ispiti");
		
        ArrayList<Ispit> ispiti  = new ArrayList<Ispit>();
		if (jsonispiti != null) { 
			   int len = jsonispiti.length();
			   for (int i=0;i<len;i++){ 
				  Ispit ispit = napraviIspit((JSONObject) jsonispiti.get(i)); 
			      ispiti.add(ispit);
		       } 
		}		
		
		Rok r = new Rok(o.getInt("trajanje_u_danima"), ispiti);
		
		return r;
		
	}
	
	//sortiraj ispite
	public static ArrayList<Ispit> sortirajIspite (ArrayList<Ispit> ispiti){
		
		Collections.sort(ispiti, new Comparator<Ispit>() {

			@Override
			public int compare(Ispit i1, Ispit i2) {
				
				return Integer.valueOf(i2.prijavljeni).compareTo(i1.prijavljeni);
			}
			
		});
		
		return ispiti;
	}	
	
	
	public static boolean objectExist (JSONObject jsonObjekat , String kljuc) {
		Object obj;
		try {
			obj = jsonObjekat.get(kljuc);
		} catch (Exception e) {
			return false;
		}
		return obj != null;
	}
}
