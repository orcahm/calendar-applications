import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class User {

	private int Id;
	private String name;
	private ArrayList<Diary> diary;
	
	public User(String [] command) {
		this.Id = Integer.parseInt(command[2]);
		this.diary = new ArrayList<Diary>();
		StringBuilder sb = new StringBuilder();
		for (int i = 3; i < command.length; i++) {
			if(i!=3)
				sb.append(" ");
			sb.append(command[i]);
		}
		this.name = sb.toString();
	
		
	}
	
	public void available(String [] command) throws ParseException, UserUnavailableException{
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append(command[2] + " ");
		sb.append(command[3]);
		Date tempStart = df.parse(sb.toString()); 
		
		for (int i = 0; i < this.diary.size(); i++) {
			if(!this.diary.get(i).getClass().toString().equals("class Birthday") && !this.diary.get(i).getClass().toString().equals("class Anniversary")){
				if((this.diary.get(i).getStartDate().before(tempStart) && this.diary.get(i).getFinishDate().after(tempStart)) ||
						(this.diary.get(i).getStartDate().equals(tempStart))){
					throw new UserUnavailableException(this.Id);
				}
			}
		}	
	}
	
	public void available(String [] command,Diary diary) throws UserUnavailableException{
		
		for (int i = 0; i < this.diary.size(); i++) {
			boolean sameDay = true;
			if(!this.diary.get(i).getClass().toString().equals("class Birthday") && !this.diary.get(i).getClass().toString().equals("class Anniversary")){
				if(diary.getClass().toString().equals("class Course") || this.diary.get(i).getClass().toString().equals("class Course")){
					sameDay = sameDay(this.diary.get(i),diary);
				}
				if(sameDay){
					if((afterHour(this.diary.get(i).getStartDate(), diary.getStartDate()) && afterHour(diary.getStartDate(), this.diary.get(i).getFinishDate()))){
						throw new UserUnavailableException(this.Id);
					}
					if((afterHour(diary.getStartDate(),this.diary.get(i).getStartDate()) && afterHour(this.diary.get(i).getStartDate() , diary.getFinishDate()))){
						throw new UserUnavailableException(this.Id);
					}
					if(sameHour(this.diary.get(i).getStartDate(), diary.getStartDate())){
						throw new UserUnavailableException(this.Id);
					}
				}
			}
		}	
	}
	
	public boolean sameDay(Diary course,Diary addDiary){
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(course.getStartDate());
		cal2.setTime(addDiary.getStartDate());
		
		if((cal1.get(Calendar.DAY_OF_MONTH)-cal2.get(Calendar.DAY_OF_MONTH))%7==0) return true;
		else return false;
		
	}
	
	public boolean sameHour(Date date1,Date date2){
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		
		if((cal1.get(Calendar.HOUR_OF_DAY)-cal2.get(Calendar.HOUR_OF_DAY))==0) return true;
		else return false;
		
	}
	
	public boolean afterHour(Date date1,Date date2){
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		
		if((cal1.get(Calendar.HOUR_OF_DAY)-cal2.get(Calendar.HOUR_OF_DAY))<0) return true;
		else return false;
		
	}
	
	public void deleteDiary(int id){
		
		for (int i = 0; i < this.diary.size(); i++) {
			if(this.diary.get(i).getId()==id){
				this.diary.remove(i);
				break;
			}
		}	
	}
	
	public boolean sameDay(Date course,Date startDate){
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(course);
		cal2.setTime(startDate);
		
		if((cal1.get(Calendar.DAY_OF_MONTH)-cal2.get(Calendar.DAY_OF_MONTH))%7==0) return true;
		else return false;
	}
	
	public ArrayList<Diary> listWrite(Date startDate,Date finishDate){
		
		ArrayList<Diary> templist = new ArrayList<Diary>();
		Calendar cal = Calendar.getInstance();
		
		for (int i = 0; i < this.diary.size(); i++) {
			if(this.diary.get(i).getClass().toString().equals("class Course") && this.diary.get(i).getStartDate().before(startDate)){
				Diary temp = new Course(this.diary.get(i));
					while(finishDate.after(temp.getStartDate())){
						if(temp.getStartDate().after(startDate)){
							templist.add(temp);
						}
						cal.setTime(temp.getStartDate());
						cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+7);
						Diary temp1 = new Course(temp);
						temp1.setStartDate(cal.getTime());
						temp = new Course(temp1);
					}
					continue;
			}
			if((this.diary.get(i).getClass().toString().equals("class Birthday") || this.diary.get(i).getClass().toString().equals("class Anniversary"))
					&& this.diary.get(i).getStartDate().before(startDate)){
				Diary temp;
				if(this.diary.get(i).getClass().toString().equals("class Birthday")){
					temp = new Birthday(this.diary.get(i));
				}else  temp = new Anniversary(this.diary.get(i));
					while(finishDate.after(temp.getStartDate())){
						if(temp.getStartDate().after(startDate)){
							templist.add(temp);
						}
						cal.setTime(temp.getStartDate());
						cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+7);
						Diary temp1; 
						if(this.diary.get(i).getClass().toString().equals("class Birthday")){
							temp1 = new Birthday(this.diary.get(i));
						}else temp1 = new Anniversary(this.diary.get(i));
						temp1.setStartDate(cal.getTime());
						temp = new Course(temp1);
					}
					continue;
			}
			if(startDate.before(this.diary.get(i).getStartDate()) &&  finishDate.after(this.diary.get(i).getStartDate())){
				templist.add(this.diary.get(i));
			}
		}
		
		return templist;
		
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Diary> getDiary() {
		return diary;
	}
	public void setDiary(Diary diary) {
		this.diary.add(diary);
	}
}
