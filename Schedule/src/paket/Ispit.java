package paket;

import java.util.ArrayList;

public class Ispit {

    String sifra;
	int prijavljeni;
	int racunari;
	ArrayList<String> odseci;
	int godina;
	ArrayList<ArrayList<Sala>> moguceSale;   // moguce kombinacije sala
	ArrayList<Sala> najboljeSale;            // kombinacija sala koju smo izabrali
	int termin;
	int dan;
	ArrayList<Uticanje> listaUticanja;       // lista kolizija koje ne smeju da se dese
	
	public Ispit(String sifra, int prijavljeni, int racunari, ArrayList<String> odseci) {
		super();
		this.sifra = sifra;
		this.prijavljeni = prijavljeni;
		this.racunari = racunari;
		
		this.odseci = new ArrayList<String>();
		this.odseci = odseci;
		
	    char c = sifra.charAt(5);
	    godina=Character.getNumericValue(c);  
	    
	  
	    moguceSale = new ArrayList<ArrayList<Sala>>();
	    najboljeSale = new ArrayList<Sala>();
	    listaUticanja = new ArrayList<>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + godina;
		result = prime * result + ((odseci == null) ? 0 : odseci.hashCode());
		result = prime * result + prijavljeni;
		result = prime * result + racunari;
		result = prime * result + ((sifra == null) ? 0 : sifra.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ispit other = (Ispit) obj;
		if (godina != other.godina)
			return false;
		if (odseci == null) {
			if (other.odseci != null)
				return false;
		} else if (!odseci.equals(other.odseci))
			return false;
		if (prijavljeni != other.prijavljeni)
			return false;
		if (racunari != other.racunari)
			return false;
		if (sifra == null) {
			if (other.sifra != null)
				return false;
		} else if (!sifra.equals(other.sifra))
			return false;
		return true;
	}

   
	
	
	
	
	
}
