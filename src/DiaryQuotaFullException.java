
public class DiaryQuotaFullException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	private int id;
	
	public DiaryQuotaFullException(int id){
		this.id=id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
