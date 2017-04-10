import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.print.attribute.standard.Finishings;

public class Operation {

	ReadFile file ;
	ArrayList<User> userList;
	ArrayList<Diary> diaryList;
	WriteFile output;
	
	public Operation(String[] args) throws IOException, ParseException{
		
		file = new ReadFile(args[0]);
		output = new WriteFile(args[1]);
		userList = new ArrayList<User>();
		diaryList = new ArrayList<Diary>();
		
		for (int i = 0; i < file.lineListSize(); i++) {
			splitLine(file.getLine(i));
		}
		
		output.close();
		
	}
	
	public void splitLine(String line) throws ParseException, IOException{
		
		output.write("COMMAND:"+line);
		String [] temp = line.split(" ");
		
		switch (temp[0]) {
		case "SAVE":
			saveOperation(temp);
			break;
		case "ARRANGE":
			addDairy(temp);
			break;
		case "ATTEND":
			attendOperation(temp);
			break;
		case "LIST":
			listOperation(temp);
			break;
		case "CANCEL":
			cancelOperation(temp);
			break;
		default:
			break;
		}
	}
	
	public void saveOperation(String [] command) throws ParseException, IOException{
		
		switch (command[1]) {
		case "USER":
			addUser(command);
			break;
		default:
			addDairy(command);
			break;
		}
	}
	
	public void addUser(String [] command) throws IOException{
		
		try{
			controlUserID(command[2]);
			userList.add(new User(command));
			output.write(userList.get(userList.size()-1).getName()+" SAVED");
		}
		catch(UserDuplicatedException e){
			output.write("DUPLICATED USER ID: " + command[2] + " ALREADY EXIST");
		}
		
	}
	
	public void addDairy(String [] command) throws ParseException, IOException{
		
		Diary temp;
		
		try{
			switch(command[1]){
				case "ANNIVERSARY":
					controlDairyID(command[4],command[1]);
					temp = new Anniversary(command);
					addDiaryInUser(command[3], temp);
					output.write(temp.toString());
					break;
				case "BIRTHDAY":
					controlDairyID(command[4],command[1]);
					temp = new Birthday(command);
					addDiaryInUser(command[3], temp);
					output.write(temp.toString());
					break;
				case "COURSE":
					controlDairyID(command[5],command[1]);
					temp = new Course(command);
					diaryList.add(temp);
					output.write(temp.toString());
					break;
				case "APPOINTMENT":
					userAvailableAppointmentAndMeeting(command);
					break;
				case "MEETING":
					userAvailableAppointmentAndMeeting(command);
					break;
				case "SPORT":
					temp = new Sport(command);
					diaryList.add(temp);
					break;
				case "THEATER":
					temp = new Theater(command);
					diaryList.add(temp);
					break;
				case "CONCERT":
					temp = new Concert(command);
					diaryList.add(temp);
					break;
			}		
		}catch(DiaryDuplicatedException e){
			output.write("DUPLICATE " + e.getDiary() +" ID: " + e.getId() + " ALREADY EXIST");
		}catch(UserUnavailableException e){
			output.write("INCOMPATIBLE USER: " + e.getId() +"’S AGENDA IS NOT COMPATIBLE");
		}catch(UserNotFoundException e){
			output.write("USER NOT FOUND: " + e.getId());
		}
		
	}
	
	public void controlUserID(String id) throws UserDuplicatedException{
		
		int tempId = Integer.parseInt(id);
		
		for (int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getId()==tempId)
				throw new UserDuplicatedException();
		}
	}
	
	public void controlDairyID(String id,String command) throws DiaryDuplicatedException{
		
		int tempId = Integer.parseInt(id);
		
		for (int i = 0; i < diaryList.size(); i++) {
			if(diaryList.get(i).getId()==tempId)
				throw new DiaryDuplicatedException(id,command);
		}
	}
	
	public void userAvailableAppointmentAndMeeting(String [] command) throws DiaryDuplicatedException, ParseException, UserUnavailableException, UserNotFoundException, IOException{
		
		controlDairyID(command[6],command[1]);
		String [] tempUser = command[5].split(",");
		ArrayList<Integer> idList = new ArrayList<Integer>();
		
		if(command[1].equals("MEETING"))
			meetingHoursControl(command,tempUser[0]);
		
		for (int i = 0; i < tempUser.length; i++) {
			int tempId = Integer.parseInt(tempUser[i]);
			findUser(tempId);
			idList.add(tempId);
			for (int j = 0; j < userList.size(); j++) {
				if(userList.get(j).getId()==tempId){
					userList.get(j).available(command);
				}	
			}
		}
		Diary temp;
		if(command[1].equals("MEETING")){
			temp = new Meeting(command);
		}else
		temp = new Appointment(command);
		
		userAddDiary(idList, temp);
		output.write(temp.toString());
	}
	
	public void addDiaryInUser(String userID,Diary temp){
		
		diaryList.add(temp);
		int tempId = Integer.parseInt(userID);
		
		for (int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getId()==tempId){
				userList.get(i).setDiary(temp);
				temp.setParticipant(userList.get(i));
			}
				
		}
	}
	
	public void userAddDiary(ArrayList<Integer> users,Diary diary){
		
		for (int i = 0; i < users.size(); i++) {
			for (int j = 0; j < userList.size(); j++) {
				if(users.get(i)==userList.get(j).getId()){
					userList.get(j).setDiary(diary);
					diary.setParticipant(userList.get(i));
				}
			}
		}
	}
	
	public void findUser(int id) throws UserNotFoundException{
		
		for (int i = 0; i < userList.size(); i++) {
			if(userList.get(i).getId()==id){
				return;
			}
		}
		throw new UserNotFoundException(id);
	}

	public void meetingHoursControl(String [] command,String userId) throws ParseException, NumberFormatException, UserUnavailableException{
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.ENGLISH);
		Calendar cal = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(command[2] + " ");
		sb.append(command[3]);
		
		Date tempStart = df.parse(sb.toString()); 
		cal.setTime(tempStart);
		
		int day = cal.get(Calendar.DAY_OF_WEEK);
	
		if(day!=1 && day!=7){
			if(cal.get(Calendar.HOUR_OF_DAY)<9 || cal.get(Calendar.HOUR_OF_DAY)>=18){
				throw new UserUnavailableException(Integer.parseInt(userId)); 
			}
		}
		
	}
	
	
	
	public void attendOperation(String [] command) throws ParseException, IOException{
		
		int tempID = Integer.parseInt(command[2]);
		int index = 0;
		
		try{
			for (; index < diaryList.size(); index++) {
				if(diaryList.get(index).getId()==tempID) break;
			}
			if(index==diaryList.size()){
				throw new DiaryNotFound(tempID,command[1]);
			}
			
			String [] tempUserList = command[3].split(",");
			
			for (int i = 0; i < tempUserList.length; i++) {
				int tempUserId = Integer.parseInt(tempUserList[i]); 
				int count = 0;
				try{
					for (int j = 0; j < this.userList.size(); j++) {
						if(tempUserId==this.userList.get(j).getId()){
							this.userList.get(j).available(command,diaryList.get(index));
							if(diaryList.get(index).getQuota()==diaryList.get(index).getParticipant().size()){
								throw new DiaryQuotaFullException(tempUserId);
							}
							this.userList.get(j).setDiary(diaryList.get(index));
							diaryList.get(index).setParticipant(this.userList.get(j));
							count++;
							break;
						}
					}	
					if(count==0) throw new UserNotFoundException(Integer.parseInt(tempUserList[i]));
				}catch(UserUnavailableException e){
					output.write("UNAVAILABLE USER: " + e.getId());
				}catch(DiaryQuotaFullException e){
					output.write("QUOTA FULL: " + e.getId());
				}catch(UserNotFoundException e){
					output.write("USER NOT FOUND: " + e.getId());
				}
			}
			output.write(diaryList.get(index).getParticipant().size() +" USERS ADDED TO ATTENDENCE LIST OF " + diaryList.get(index).getName());
		}catch(DiaryNotFound e){
			output.write(e.getName() + "NOT FOUND: " + e.getId());
		}
	}
	
	public void listOperation(String [] command) throws IOException, ParseException{
		
		try{
			switch (command[1]) {
			case "DAILY":
				findUser(Integer.parseInt(command[3]));
				writeList(command, 1);
				break;
			case "MONTHLY":
				findUser(Integer.parseInt(command[3]));
				writeList(command, 30);
				break;
			case "WEEKLY":
				findUser(Integer.parseInt(command[3]));
				writeList(command, 7);
				break;
			case "ATTENDANCE":
				findDiary(Integer.parseInt(command[2]),command[1]);
				break;
			}	
		}catch(UserNotFoundException e){
			output.write("USER NOT FOUND: " + e.getId());
		}catch(DiaryNotFound e){
			output.write(e.getName() + "NOT FOUND: " + e.getId());
		}
	}
	
	public void cancelOperation(String [] command) throws IOException{
		
		int tempID = Integer.parseInt(command[2]);
		
		try{
			for (int i = 0; i < diaryList.size(); i++) {
				if(diaryList.get(i).getId()==tempID){
					for (int j = 0; j < diaryList.get(i).getParticipant().size(); j++) {
						diaryList.get(i).getParticipant().get(j).deleteDiary(tempID);
					}
					output.write(command[1] + " CANCELLED: " + command[2]);
					diaryList.remove(i);
					return;
				}
			}
			throw new DiaryNotFound(tempID,command[1]);
		}catch(DiaryNotFound e){
			output.write(e.getName() + "NOT FOUND: " + e.getId());
		}
	}
	
	public void writeList(String [] command,int day) throws ParseException, IOException{
		
		Date temp = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		temp = df.parse(command[2]);
		Calendar calstart = Calendar.getInstance();
		Calendar calfinish = Calendar.getInstance();
		calstart.setTime(temp);
		calfinish.setTime(calstart.getTime());
		calfinish.set(Calendar.DAY_OF_MONTH,calfinish.get(Calendar.DAY_OF_MONTH)+day);
		int id = Integer.parseInt(command[3]);
		int index = 0;
		
		for (; index < userList.size(); index++) {
			if(userList.get(index).getId()==id){
				break;
			}
		}
		
		ArrayList<Diary> templist = userList.get(index).listWrite(calstart.getTime(), calfinish.getTime());
		templist.sort(null);
		
		for (int i = 0; i < templist.size(); i++) {
				output.write(templist.get(i).write(userList.get(index)));
		}
		
	}
	
	public void findDiary(int id,String name) throws DiaryNotFound, IOException{
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < diaryList.size(); i++) {
			if(diaryList.get(i).getId()==id) 
				for (int j = 0; j < diaryList.get(i).getParticipant().size(); j++) {
					if(j!=0) sb.append(",");
					sb.append(diaryList.get(i).getParticipant().get(j).getName());
				}
				output.write(sb.toString());
				return;
		}
		throw new DiaryNotFound(id,name);
	}
}
