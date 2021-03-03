package paket;

import java.util.ArrayList;
import java.util.Comparator;

public class SortMoguceSale implements Comparator<ArrayList<Sala>> {

	@Override
	public int compare(ArrayList<Sala> s1, ArrayList<Sala> s2) {

        int prio1 = s1.size();
        int prio2 = s2.size();
        
        int razlika = prio1 - prio2;
		
		return razlika;
	}

	
}
