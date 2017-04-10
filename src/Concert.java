import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Concert extends Event{

	private final int duration = 180;
	
	public Concert(String [] command) throws ParseException{
		
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getName()+" ON " + getStartDate());
		return sb.toString();
	}
	
	public String write(User user){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append(this.getName() + " ON " + df.format(this.getStartDate()));
		return sb.toString();
	}
}
