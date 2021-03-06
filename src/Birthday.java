import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Birthday extends Diary {

	public Birthday(String [] command) throws ParseException{
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		super.setStartDate(df.parse(command[2]));
	
		super.setId(Integer.parseInt(command[4]));
		
		this.nameCombination(command);
		super.setParticipant();
	}
	
	public Birthday(Diary Birthday){
		
		super.setStartDate(Birthday.getStartDate());
		super.setId(Birthday.getId());
		super.setName(Birthday.getName());
		super.setParticipant();
		super.getParticipant().addAll(Birthday.getParticipant());
	}
	
	private void nameCombination(String [] command){
		
		StringBuilder sb = new StringBuilder();
		for (int i = 5; i < command.length; i++) {
			if(i!=5)
				sb.append(" ");
			sb.append(command[i]);
		}
		super.setName(sb.toString());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getName() + " ADDED TO " + this.getParticipant().get(0) + "'S AGENDA");
		return sb.toString();
	}
	
	public String write(User user){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder sb = new StringBuilder();
		sb.append("BIRTHDAY: " + this.getName() + df.format(this.getStartDate()));
		return sb.toString();
	}
	

}
