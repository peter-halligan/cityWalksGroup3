package project.group3.citywalks.objects;

public class Coordinate {
	
	private double latitude;
	private double longitude;
	private int coordinateId;
	private int walkId;
	private boolean visible;
	
	
	public Coordinate(int id,double latitude, double longitude) {
		super();
		this.coordinateId = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.walkId = 0;
		this.visible = false;
	}
	
	public int getWalkId() {
		return walkId;
	}

	public void setWalkId(int walkId) {
		this.walkId = walkId;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		if(visible == 0)
		{
			this.visible = false;
		}
		this.visible = true;
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
