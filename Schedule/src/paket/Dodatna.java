package paket;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Dodatna {

	public static Map<Ispit,ArrayList<Sala>> raspored = new LinkedHashMap<Ispit, ArrayList<Sala>>();
	
	public static void rasporediIspite() {  // obavlja sve funkcije rasporedjivanja ispita
		
		for(int i =0; i < Main.ispiti.size(); i++) {
			
			Ispit ispit = Main.ispiti.get(i);
			int stud = ispit.prijavljeni;
			ArrayList<Sala> grupaSala = new ArrayList<>();
			
			Main.koraci .append("Pocetak izracunavanja mogucih sala za ispit " + ispit.sifra + "." + "\n");
			
			izrMoguceSale(ispit, ispit.moguceSale, grupaSala, stud, 0, Main.sale.size() ); // pravimo sve kombinacije sala za ovaj ispit
			
			Main.koraci .append("Kraj izracunavanja mogucih sala za ispit " + ispit.sifra + "." + "\n");
			 ispit.moguceSale = JSONalati.sortirajMoguceSale(ispit.moguceSale, ispit);   // sortiramo moguce sale
			//Collections.sort(Main.ispiti.get(i).moguceSale, new SortMoguceSale());
			
		}
		Main.koraci.append("\n");
		
		Collections.sort(Main.ispiti, new SortIspita());    // sortiramo ispite
		
		izrUticanje();                                      // za sve ispite izracunamo kako uticu jedni na druge, tj. kolizije
		
		raspored = dodajIspit(new LinkedHashMap<Ispit, ArrayList<Sala>>() , 0, 0);  // pravi se raspored
		
		//ispisi();
		Csv();
		koraci();
		
	}
		
	
	
	

	
	public static void izrMoguceSale(Ispit ispit,  ArrayList<ArrayList<Sala>> moguceSale , ArrayList<Sala> jedanIzbor,int brLjudi, int pocetak, int kraj) {
		
		ArrayList<Sala> kopijaIzbora = new ArrayList<>();
		
		if(jedanIzbor.size() > 0 && brLjudi <=0) {
			moguceSale.add(jedanIzbor);
		}
		
		else if (pocetak < kraj) {
			
			for ( int j = 0; j< jedanIzbor.size(); j++) {
				kopijaIzbora.add(jedanIzbor.get(j));
			}
			int novibrljudi = brLjudi - Main.sale.get(pocetak).kapacitet; // preostali broj ljudi koje treba smestiti
			
			if( ispit.racunari == 1 && Main.sale.get(pocetak).racunari == 1) jedanIzbor.add(Main.sale.get(pocetak));
			else if (ispit.racunari == 0) jedanIzbor.add(Main.sale.get(pocetak));
			
			izrMoguceSale (ispit, moguceSale, jedanIzbor, novibrljudi,pocetak+1, kraj );
			izrMoguceSale (ispit, moguceSale, kopijaIzbora, brLjudi ,pocetak+1, kraj );
		}
		
	}
	
	
	public static Map<Ispit,ArrayList<Sala>> dodajIspit(Map<Ispit,ArrayList<Sala>> raspored, int dan, int termin) {
			
		if (raspored.size() == Main.ispiti.size()) {
			Main.koraci .append("\n" + "RASPORED ISPITA JE NAPRAVLJEN " + "\n");
			return raspored;
		}
		else {
			Ispit ispit = nerasporedjeniIspiti(raspored).get(0); // uzmem ispit
			
			for (int i = 0; i <ispit.moguceSale.size(); i++) {
				
				for (int j =0; j<Main.rok.trajanje; j++) {
					for (int t=0; t<Main.termini.length; t++) {
						
						 Map<Ispit,ArrayList<Sala>> kopijaRasporeda = new LinkedHashMap<Ispit, ArrayList<Sala>>(raspored);
						 
						 ispit.dan = j;
						 ispit.termin = t;
						 
						 ispit.najboljeSale = ispit.moguceSale.get(i); // izabrali smo kombinaciju sala u koje cemo pokusati da smestimo studente
						 kopijaRasporeda.put(ispit,ispit.moguceSale.get(i));
						 
						 Main.koraci .append("Ispit "+ ispit.sifra+ " pokusavamo da smestimo u dan " + (ispit.dan+1) +" i termin " +Main.termini[t]+ " u sale {");
						 for (int str =0; str<ispit.najboljeSale.size(); str++) {
							 Main.koraci.append(" "+ ispit.najboljeSale.get(str).naziv);
							 if ( str == ispit.najboljeSale.size()-1) {
								 Main.koraci.append(" }" + "\n");
							 }
						 }
						 
						 if (validanRaspored(kopijaRasporeda)== true) { //moze da se smesti
							 
							 Main.koraci .append("Ispit "+ ispit.sifra+ " smo smestili u dan " + (ispit.dan+1) +" i termin " +Main.termini[t]+ " u sale {");
							 for (int str =0; str<ispit.najboljeSale.size(); str++) {
								 Main.koraci.append(" "+ ispit.najboljeSale.get(str).naziv);
								 if ( str == ispit.najboljeSale.size()-1) {
									 Main.koraci.append(" }" + "\n\n");
								 }
							 }
							 
							 Map<Ispit,ArrayList<Sala>> buduciRaspored = dodajIspit(kopijaRasporeda, dan, termin);
							
							 
							 if (buduciRaspored != null) {
								 return buduciRaspored;
							 }
						 }
						
						 
					}
				}
			}
			
			return null;
		}
			
	}
	
	public static ArrayList<Ispit> nerasporedjeniIspiti( Map<Ispit,ArrayList<Sala>> raspored){
		
		ArrayList<Ispit> povratna = new ArrayList<>(); // fja pravi listu jos uvek nerasporedjenih ispita
		for (int i =0; i<Main.ispiti.size(); i++) {
			if (!raspored.containsKey(Main.ispiti.get(i))) povratna.add(Main.ispiti.get(i));
		}
		
		return povratna;
	}
	
	public static int getIndeksSale(Sala s) { // dohvata indeks sale iz niza "sale"
		
	    for ( int i =0; i < Main.sale.size(); i++) {
	    	if ( s == Main.sale.get(i)) return i;
	    }
		return 0;
	}

	
	public static void izrUticanje() {  // pravi Uticanja za sve ispite
		
		for (int i =0; i< Main.ispiti.size(); i++) {
			
			for (int j = 0; j< Main.ispiti.size(); j++) {
				boolean preskoci = false;
				if (i == j) continue;
				
				for (int k = 0; k< Main.ispiti.get(i).moguceSale.size(); k++) {
					if (preskoci) break;
					for ( int c= 0; c<Main.ispiti.get(j).moguceSale.size(); c++) {
						if (preskoci) break;
						
						for ( int v = 0; v<Main.ispiti.get(i).moguceSale.get(k).size(); v++) {
							if (Main.ispiti.get(j).moguceSale.get(c).contains(Main.ispiti.get(i).moguceSale.get(k).get(v))) {
								Uticanje utic = new Uticanje(Main.ispiti.get(i), Main.ispiti.get(j), 0); // ukoliko ima preklapanja u salama
								
								Main.ispiti.get(i).listaUticanja.add(utic);
								preskoci = true;
								break;
							}
						}
					}
				}
				
				for(int k =0; k<Main.ispiti.get(j).odseci.size(); k++) { // ukoliko su ista godina i isti odsek
					if (Main.ispiti.get(i).odseci.contains(Main.ispiti.get(j).odseci.get(k)) && Main.ispiti.get(i).godina==Main.ispiti.get(j).godina ) {
						
						Uticanje utic = new Uticanje(Main.ispiti.get(i), Main.ispiti.get(j), 1);
						Main.ispiti.get(i).listaUticanja.add(utic);
						break;
					}
					
				}
				
				//novo
				for(int k =0; k<Main.ispiti.get(j).odseci.size(); k++) { // ukoliko su isti odsek i susedne godine
					if (Main.ispiti.get(i).odseci.contains(Main.ispiti.get(j).odseci.get(k))) {
						if (( Main.ispiti.get(i).godina==1 && Main.ispiti.get(j).godina==2) || ( Main.ispiti.get(i).godina==2 && Main.ispiti.get(j).godina==1) || ( Main.ispiti.get(i).godina==2 && Main.ispiti.get(j).godina==3) || ( Main.ispiti.get(i).godina==3 && Main.ispiti.get(j).godina==2) || ( Main.ispiti.get(i).godina==3 && Main.ispiti.get(j).godina==4) || ( Main.ispiti.get(i).godina==4 && Main.ispiti.get(j).godina==3)) {
							
							Uticanje utic = new Uticanje(Main.ispiti.get(i), Main.ispiti.get(j), 2);
							Main.ispiti.get(i).listaUticanja.add(utic);
							break;
						}
						
					}
					
				}
				
			   
			}
		}
	}
	
	
	public static boolean validanRaspored(Map<Ispit,ArrayList<Sala>> raspored) {
		
		for (int i =0; i< Main.ispiti.size(); i++) { // proveramo da nema neka kolizija
			for (int j = 0; j< Main.ispiti.get(i).listaUticanja.size(); j++) {
				if (Main.ispiti.get(i).listaUticanja.get(j).provera(raspored)) { 
					return false;  // ako imamo koliziju vracamo false ovde
				}
			}
		}
		return true;
	}
	
	public static void ispisi() {
		 for (int i =0; i<Main.ispiti.size(); i++) {
	
			 System.out.println("Ispit je " + Main.ispiti.get(i).sifra + " : za dan roka " + Main.ispiti.get(i).dan + " za termin " + Main.ispiti.get(i).termin);
			 for(int j = 0; j<Main.ispiti.get(i).najboljeSale.size(); j++) {
				 System.out.print(Main.ispiti.get(i).najboljeSale.get(j).naziv + " ");
			 }
			 System.out.println();
			 System.out.println();
		 }
	}
	
	
	public static void Csv() { // pravljenje scv fajla
		
		try {
			File file= new File("./Izlaz.csv");
			
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			
			for(int i = 0; i<Main.rok.trajanje; i++) {
				fw.write("Dan" + Integer.toString(i+1)+ "," );
				for( int j = 0; j<Main.sale.size(); j++) {
					fw.write(Main.sale.get(j).naziv);
					if (j != Main.sale.size()-1) fw.write(",");
				}
				fw.write("\n");
				
				for (int j=0; j<Main.termini.length; j++) {
					fw.write(Main.termini[j] + ",");
					for (int s=0; s<Main.sale.size(); s++) {
						boolean upisan = false;
						for (int k = 0; k< Main.ispiti.size(); k++) {
							if (Main.ispiti.get(k).dan == i && Main.ispiti.get(k).termin == j) {
								if (Main.ispiti.get(k).najboljeSale.contains(Main.sale.get(s))) {
									fw.write(Main.ispiti.get(k).sifra);
									upisan = true;
								}
							}
						}
						if (upisan == false) fw.write("X");
						if ( s != Main.sale.size()-1) fw.write(",");
					}	
					
					fw.write("\n");
				}
				
			}
			
			fw.close();
		
		}
		catch (Exception e) {
			
		}
	}
	
	
	public static void koraci() {  // pravljenje fajla koraci
		
		try{
            File file= new File("./Koraci.txt");
			
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			
			fw.write(Main.koraci.toString());
			
			fw.close();
		}
		catch (Exception e) {
			
		}
	} 
}
	
	
