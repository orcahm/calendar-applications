
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private int id;
	
	public UserNotFoundException(int id){
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
