package app.controller;

import app.model.Coordinate;
import app.model.CoordinateManager;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class MapController {

	private final Result result;
	private final CoordinateManager manager;

	public MapController(Result result, CoordinateManager coordinates) {
		this.result = result;
		this.manager = coordinates;
	}

	@Path("/map/add/{latitude}/{longitude}")
	public void addNewCoordinate(String latitude, String longitude) {
		Coordinate coordinate = new Coordinate(Double.valueOf(latitude), Double.valueOf(longitude));
		manager.add(coordinate);
	}

	@Path("/map.json")
	public void getJSON() {
		result.use(Results.json()).from(manager.getCoordinates(), "coordenadas").serialize();
	}
	
	@Path("/map")
	public void show() {}
	
	@Path("/map/clear")
	public void clearJSON() {
		manager.clear();
	}
}