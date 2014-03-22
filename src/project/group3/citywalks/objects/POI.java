package project.group3.citywalks.objects;

import java.io.Serializable;


public class POI  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2187528287036708868L;
	private int poiid;
	private int cid;
	private String name;
	private String description;
	private int type;
	private int walkId;
	
	public int getWalkId() {
		return walkId;
	}

	public void setWalkId(int walkId) {
		this.walkId = walkId;
	}
	private Coordinate coordinate;
	
	public int getPoiid() {
		return poiid;
	}

	public void setPoiid(int poiid) {
		this.poiid = poiid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public POI() {
		super();
		this.poiid = 0;
		this.cid = 0;
		this.name = "";
		this.description = "";
		this.type = 0;
		this.coordinate = null;
	}
		
	
	public Coordinate getCoordinate() {
		return coordinate;
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
