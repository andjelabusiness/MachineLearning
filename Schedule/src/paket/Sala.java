package paket;

public class Sala {

	String naziv;
	int kapacitet;
	int racunari;
	int dezurni;
	int etf;
	
	
	public Sala(String naziv, int kapacitet, int racunari, int dezurni, int etf) {
		super();
		this.naziv = naziv;
		this.kapacitet = kapacitet;
		this.racunari = racunari;
		this.dezurni = dezurni;
		this.etf = etf;
	}


	


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sala other = (Sala) obj;
		if (dezurni != other.dezurni)
			return false;
		if (etf != other.etf)
			return false;
		if (kapacitet != other.kapacitet)
			return false;
		if (naziv != other.naziv)
			return false;
		if (racunari != other.racunari)
			return false;
		return true;
	}

	
	
	
}
