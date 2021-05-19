import java.io.File;
import java.io.IOException;
import java.io.FileWriter; 
import java.io.FileNotFoundException;  
import java.util.Scanner; 
public class NewFile {
	
	
	//Lager en ny fil
	public void createFile() {
		try {
		      File testFile = new File("test.txt");
		      if (testFile.createNewFile()) {
		        System.out.println("File created: " + testFile.getName());
		      } else {
		    	 System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		    	System.out.println("An error occurred.");
		    	e.printStackTrace();
		    }
	}
	
	
	//Skriver til fil, skriver over hvis det er noe i filen.
	public void writeFile(String text) {
		try {
		      FileWriter myWriter = new FileWriter("test.txt");
		      myWriter.write(text);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	
	//Leser fra fil
	public void readFile() {
		try {
		      File myObj = new File("test.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        System.out.println(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
}
