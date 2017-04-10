import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {

	private ArrayList<String> line;
	
	public ReadFile(String filename) throws IOException{
		this.line = new ArrayList<String>();
		String str = null;
		
		try{
		
			FileReader file = new FileReader(filename);
			BufferedReader bf = new BufferedReader(file);
			
			while((str=bf.readLine()) != null ){
				if(str.length()!=0)
				this.line.add(str);
			}
		}catch(Exception e){
			System.err.println("ERROR : " + e.getMessage());
		}
	}

	public String getLine(int index) {
		return line.get(index);
	}
	
	public int lineListSize(){
		return line.size();
	}
	
}
