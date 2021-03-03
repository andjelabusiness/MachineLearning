package paket;

import java.io.InputStream;

public class HandlerFile {

	public static InputStream inputStreamFile( String putanja) {
		try {
			InputStream is = HandlerFile.class.getResourceAsStream(putanja);
			return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	};
}
