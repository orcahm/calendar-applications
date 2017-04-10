
public class UserUnavailableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private int id;

	public UserUnavailableException(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
