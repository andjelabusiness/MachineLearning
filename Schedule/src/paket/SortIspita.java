package paket;

import java.util.Comparator;

public class SortIspita implements Comparator<Ispit> {

	@Override
	public int compare(Ispit i1, Ispit i2) {
		
		int prio1= i1.moguceSale.size(); // veci prioritet ispiti koji imaju manje kombinacija sala
		int prio2= i2.moguceSale.size();
		
		if ( i1.racunari == 1) prio1--; // veci prioritet ispiti koji zahtevaju racunare
		if ( i2.racunari == 1) prio2--;
		
		int razlika = prio1 - prio2;
		return razlika;
	}

}
