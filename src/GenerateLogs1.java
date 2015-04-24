import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
* Simple Java program to create File and Directory in Java, without using
* any third-party library.* @author http://java67.blogspot.com
*
*/
public class GenerateLogs1 {

    public static void main(String args[]) throws IOException {

       
        boolean success = false;

       
        String dir = "C:\\Siva\\DemoLogs";

        // Creating new directory in Java, if it doesn't exists
        File directory = new File(dir);
        if (directory.exists()) {
            System.out.println("Directory already exists ...");

        } else {
            System.out.println("Directory not exists, creating now");

            success = directory.mkdir();
            if (success) {
                System.out.printf("Successfully created new directory : %s%n", dir);
            } else {
                System.out.printf("Failed to create new directory: %s%n", dir);
            }
        }

       String[] fileNames = new String[10];
       for(int i=0;i<fileNames.length;i++){
    	   fileNames[i] ="demoLog"+i+".log"; 
       }
       
       generateLogs();

        /*File f = new File(filename);
        if (f.exists()) {
            System.out.println("File already exists");

        } else {
            System.out.println("No such file exists, creating now");
            success = f.createNewFile();
            if (success) {
                System.out.printf("Successfully created new file: %s%n", f);
            } else {
                System.out.printf("Failed to create new file: %s%n", f);
            }
        }*/


    }
    
    public static void generateLogs()  {
    	File file1 = new File("C:\\Siva\\DemoLogs\\test1.log");
    	File file2 = new File("C:\\Siva\\DemoLogs\\test2.log");
    	
    	FileWriter fw1 = null;
    	FileWriter fw2 = null;
    	
    	BufferedWriter bw1 = null;
    	BufferedWriter bw2 = null;
		try {
			fw1 = new FileWriter(file1.getAbsoluteFile());
			 bw1 = new BufferedWriter(fw1);
		
			 fw2 = new FileWriter(file2.getAbsoluteFile());
			 bw2 = new BufferedWriter(fw2);
			 
			 int counter = 0;
			while(true){
		
				
				bw1.write(Calendar.getInstance().getTime()+"Generate Logs - "+file1.getAbsoluteFile()+"\n");
				bw2.write(Calendar.getInstance().getTime()+"Generate Logs - "+file2.getAbsoluteFile()+"\n");
				
				if(counter %50 == 0){
					bw1.write(Calendar.getInstance().getTime()+"Generate Logs - "+file1.getAbsoluteFile()+" Exception :"+new Exception("Test exception in file1")+"\n");
					bw2.write(Calendar.getInstance().getTime()+"Generate Logs - "+file2.getAbsoluteFile()+" Exception :"+new Exception("Test exception in file2")+"\n");
				}
				
				//Thread.sleep(100);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/finally{
			
				try {
					if(bw1 != null)
						bw1.close();
					
					if(fw1 != null)
						fw1.close();
					
					if(bw2 != null)
						bw2.close();
					
					if(fw2 != null)
						fw2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
    }
}
