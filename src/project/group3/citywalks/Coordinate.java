package project.group3.citywalks;

public class Coordinate {
	
	private double latitude;
	private double longitude;
	private int coordinateId;
	
	
	public Coordinate(int id,double latitude, double longitude) {
		super();
		this.coordinateId = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int getCoordinateId() {
		return coordinateId;
	}
	public void setCoordinateId(int coordinateId) {
		this.coordinateId = coordinateId;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
