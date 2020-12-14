package id.nci.absensi.model;

public class MJsonResult {
	
	private String result,message;
	private Object data;
	
//	public MJsonResult(String result, String message, Object data) {
//		super();
//		this.result = result;
//		this.message = message;
//		this.data = data;
//	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
