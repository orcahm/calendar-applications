import java.util.ArrayList;
import java.util.Date;

public abstract class Diary implements Comparable<Diary>{
	
	private int id;
	private String name;
	private Date startDate;
	private Date finishDate;
	private ArrayList<User> participant;
	private int quota;
	
	@Override
	public int compareTo(Diary o) {
		if(this.startDate.compareTo(o.startDate)<0){
			return -1;
		}else if(this.startDate.compareTo(o.startDate)>0){
			return 1;
		}else return 0;
	}
	
	public abstract String toString();
	public abstract String write(User user);
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<User> getParticipant() {
		return participant;
	}
	public void setParticipant(User participant) {
		this.participant.add(participant);
	}
	public void setParticipant(){
		this.participant = new ArrayList<User>();
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public int getQuota() {
		return quota;
	}
	public void setQuota(int quota) {
		this.quota = quota;
	}

}
