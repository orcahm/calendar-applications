
public class DiaryDuplicatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private String diary;
	private String id;
	
	public DiaryDuplicatedException(String id,String name){
		this.diary = name;
		this.id = id;
	}
	public String getDiary() {
		return diary;
	}
	public void setDiary(String diary) {
		this.diary = diary;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
