package id.nci.absensi.model;

public class MTags {
	String id,tag_id,status;

	public MTags(String id, String tag_id, String status) {
		super();
		this.id = id;
		this.tag_id = tag_id;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
