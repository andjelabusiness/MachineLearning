package paket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.json.*;

public class Main {
	
	public static ArrayList<Sala> sale = new ArrayList<Sala>();
	public static Rok rok;
	public static ArrayList<Ispit> ispiti = new ArrayList<Ispit>();
	public static String[] termini = {"08:00", "11:30" , "15:00" , "18:00"};
	
	public static StringBuilder koraci = new StringBuilder();
	
	
	public static void main(String[] args) {
		
		JSONObject o = JSONalati.getJSONObjectFile("rok7.json");
		rok = JSONalati.napraviRok(o);
		ispiti = rok.ispiti;
		ispiti = JSONalati.sortirajIspite(ispiti);
		
		/*for (int i = 0; i< ispiti.size(); i++) {
			System.out.println(i);
			System.out.println(ispiti.get(i).sifra + " br.ljudi je " + ispiti.get(i).prijavljeni);
		}*/
		
		JSONArray s = JSONalati.getJSONnizFile("sale7.json");
		sale = JSONalati.napraviSale(s);
		//sale = JSONalati.sortirajSale(sale);
		
		
		Dodatna.rasporediIspite();
		
		/*for(int i =0; i< ispiti.size(); i++) {
			System.out.println();
			System.out.println();
			System.out.println("Ispit je " + ispiti.get(i).sifra + " :");
			for (int j = 0; j< ispiti.get(i).moguceSale.size(); j++) {
				System.out.print (" {");
				for (int k = 0; k<ispiti.get(i).moguceSale.get(j).size(); k++) {
					System.out.print(" " + ispiti.get(i).moguceSale.get(j).get(k).naziv);
					
				}
				System.out.print (" }");
			}
			System.out.println();
			System.out.println();
			
		}*/
		
		/*for (int i = 0; i< s.length(); i++) {
		System.out.println(sale.get(i).naziv + " kapac" + sale.get(i).kapacitet);
	    }*/
		
		
	}

	
}
