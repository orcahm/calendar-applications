import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class WriteFile {

	private BufferedWriter bw;
	
	public WriteFile(String args) throws IOException{
		bw = new BufferedWriter(new FileWriter(args));
	}
	
	public void write(String str) throws IOException{
		bw.append(str + "\n");
	}
	
	public void close() throws IOException{
		bw.close();
	}

	public BufferedWriter getBw() {
		return bw;
	}

	public void setBw(BufferedWriter bw) {
		this.bw = bw;
	}
	
}
