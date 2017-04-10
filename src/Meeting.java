import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Meeting extends Diary {

	
	public Meeting(String [] command) throws ParseException{
	
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append(command[2] + " ");
		sb.append(command[3]);
		
		super.setStartDate(df.parse(sb.toString()));
		this.calculationFinishTime(Integer.parseInt(command[4]));
			
		super.setId(Integer.parseInt(command[6]));
		super.setParticipant();
	
	}
	
	private void calculationFinishTime(int timeMinute){
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(super.getStartDate());
		cal.set(Calendar.MINUTE,cal.get(Calendar.MINUTE)+timeMinute);
		super.setFinishDate(cal.getTime());
		
	}
	
	public String write(User user){
		StringBuilder sb = new StringBuilder();
		sb.append("MEETING WITH ");
		for (int i = 0; i < this.getParticipant().size(); i++) {
			if(i!=0) sb.append(",");
			if(this.getParticipant().get(i).getId()!=user.getId())
				sb.append(this.getParticipant().get(i).getId());
		}
		sb.append(" ON" + this.getStartDate());
		return sb.toString();
	}
	
	public String toString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append("A MEETING ARRANGED FOR ");
		for (int i = 0; i < this.getParticipant().size(); i++) {
			if(i!=0) sb.append(",");
			sb.append(this.getParticipant().get(i).getId());
		}
		
		sb.append(" ON " + df.format(this.getStartDate()));
		return sb.toString();
	}
	
	

}
