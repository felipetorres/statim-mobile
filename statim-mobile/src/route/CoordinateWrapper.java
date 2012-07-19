package route;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CoordinateWrapper implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final Double latitude;
	private final Double longitude;
	private final String ultimoEnderecoVisitado;

	public CoordinateWrapper(Double latitude, Double longitude, String ultimoEnderecoVisitado) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.ultimoEnderecoVisitado = ultimoEnderecoVisitado;
	}

	public List<String> getCoordinateArray() {
		ArrayList<String> array = new ArrayList<String>();
		array.add(String.valueOf(latitude));
		array.add(String.valueOf(longitude));
		array.add(ultimoEnderecoVisitado);
		
		return array;
	}
}
