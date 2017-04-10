import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Appointment extends Diary{
	
	public Appointment(String [] command) throws ParseException{
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

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append("AN APPOINTMENT ARRANGED FOR ");
		for (int i = 0; i < this.getParticipant().size(); i++) {
			if(i!=0) sb.append(",");
			sb.append(this.getParticipant().get(i).getId());
		}
		sb.append(" ON" + df.format(this.getStartDate()));
		return sb.toString();
	}
	
	public String write(User user){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append("APPOINTMENT WITH ");
		for (int i = 0; i < this.getParticipant().size(); i++) {
			if(i!=0) sb.append(",");
			if(this.getParticipant().get(i).getId()!=user.getId())
				sb.append(this.getParticipant().get(i).getId());
		}
		sb.append(" ON " + df.format(this.getStartDate()));
		return sb.toString();
	}

	
}
