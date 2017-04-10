import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Course extends Diary {

	private final int duration = 120;
	
	public Course(String [] command) throws ParseException{
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append(command[3] + " ");
		sb.append(command[4]);
		
		super.setStartDate(df.parse(sb.toString()));
		this.calculationFinishTime(this.duration);
		
		super.setQuota(Integer.parseInt(command[2]));
		super.setId(Integer.parseInt(command[5]));
		this.nameCombination(command);
		super.setParticipant();
	
	}
	
	public Course(Diary temp){
		
		super.setStartDate(temp.getStartDate());
		super.setFinishDate(temp.getFinishDate());
		super.setName(temp.getName());
		super.setQuota(temp.getQuota());
		super.setId(getId());
		super.setParticipant();
		super.getParticipant().addAll(temp.getParticipant());
	}
	
	

	private void nameCombination(String [] command){
		
		StringBuilder sb = new StringBuilder();
		for (int i = 6; i < command.length; i++) {
			if(i!=6)
				sb.append(" ");
			sb.append(command[i]);
		}
		super.setName(sb.toString());
	}
	
	private void calculationFinishTime(int timeMinute){
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(super.getStartDate());
		cal.set(Calendar.MINUTE,cal.get(Calendar.MINUTE)+timeMinute);
		super.setFinishDate(cal.getTime());
		
	}
	
	
	public String toString(){
		DateFormat df = new SimpleDateFormat("EEEEE HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append(this.getName() + " ARRANGED ");
		for (int i = 0; i < this.getParticipant().size(); i++) {
			if(i!=0) sb.append(",");
			sb.append(this.getParticipant().get(i).getId());
		}
		
		sb.append(" ON " + df.format(this.getStartDate()).toUpperCase(Locale.ENGLISH));
		return sb.toString();
	}
	
	public String write(User user){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append(this.getName() + " ON " + df.format(this.getStartDate()));
		return sb.toString();
	}
}
