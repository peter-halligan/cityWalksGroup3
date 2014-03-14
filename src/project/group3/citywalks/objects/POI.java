package project.group3.citywalks.objects;


public class POI {
	private int poiId;
	private Coordinate coordinate;
	private String name;
	private String description;
	
	public POI(int poiId, Coordinate coordinate, String name, String description) {
		super();
		this.poiId = poiId;
		this.coordinate = coordinate;
		this.name = name;
		this.description = description;
	}
		
	public int getPoiId() {
		return poiId;
	}
	public void setPoiId(int poiId) {
		this.poiId = poiId;
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
