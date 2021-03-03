package paket;

import java.util.ArrayList;
import java.util.Map;

public class Uticanje {

	Ispit i1;
	Ispit i2;
	int vrsta; // tip uticanja
	
	public Uticanje(Ispit i1, Ispit i2, int vrsta) {
		super();
		this.i1 = i1;
		this.i2 = i2;
		this.vrsta = vrsta; // 0 kada ne moze u isto vreme ista sala dva ispita, 1 oba na istoj godini i isti odsek makar jedan, ne smeju u isti dan
	}
	
	public boolean provera(Map<Ispit, ArrayList<Sala>> raspored) {
	
		if (!raspored.containsKey(i1) || !raspored.containsKey(i2) ) return false;
		else {
			if (i1.dan == i2.dan && i1.termin == i2.termin && vrsta == 0) {
				for (int i =0; i <i1.najboljeSale.size(); i++) {
					if (i2.najboljeSale.contains(i1.najboljeSale.get(i))) {
						return true;} // imamo koliziju
				}
			}
			else if (i1.dan == i2.dan && vrsta == 1) {
				return true;
			}
			else if (i1.termin == i2.termin && vrsta == 2) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i1 == null) ? 0 : i1.hashCode());
		result = prime * result + ((i2 == null) ? 0 : i2.hashCode());
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
		Uticanje other = (Uticanje) obj;
		if (i1 == null) {
			if (other.i1 != null)
				return false;
		} else if (!i1.equals(other.i1))
			return false;
		if (i2 == null) {
			if (other.i2 != null)
				return false;
		} else if (!i2.equals(other.i2))
			return false;
		return true;
	}
	
	
}
