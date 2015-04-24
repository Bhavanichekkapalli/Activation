package demo.hackathon.generate;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
/**
 * @author Debraj Nath
 * 
 */
 
public class GenerateLog {
	private static final int MYTHREADS = 30;
 
	public static void main(String args[]) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		String[] logFiles = { "file1.log", "file2.log" };
 
		for (int i = 0; i < logFiles.length; i++) {
 
			String logFile = logFiles[i];
			Runnable worker = new MyRunnable(logFile);
			executor.execute(worker);
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
 
		}
		System.out.println("\nFinished all threads");
	}
 
	public static class MyRunnable implements Runnable {
		private final String logFile;
 
		MyRunnable(String logFile) {
			this.logFile = logFile;
		}
 
		@Override
		public void run() {
 			try {
				//Code for writing into logs
				System.out.println("\nHERE");
		        Thread t = Thread.currentThread();
		        long id = t.getId();
		        String name = t.getName();
		        long prioirty = t.getPriority();
		        String gname = t.getThreadGroup().getName();
									 
				String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
				String content = "Adding Logs in file : " + logFile + " for Timestamp -> " + timeStamp ;
				content = content + "\n Thread Details \nName :" + name +"\nGroup :" +  gname + "\nPrioirty :" + prioirty + "\nId :" + id;

				System.out.println("\nHERE1");
				
				String line;
				Process p = Runtime.getRuntime().exec
				        (System.getenv("windir") +"\\system32\\"+"tasklist.exe");
		        BufferedReader input =
		                new BufferedReader(new InputStreamReader(p.getInputStream()));
		        while ((line = input.readLine()) != null) {
		            content = content  +"\n" + line; 
		        }
		        input.close();
		        content = content  +"\n" ;
				File file = new File(logFile);
	 
				if (!file.exists()) {
					System.out.println("Hello");
					file.createNewFile();
				}
					 
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
	 				
			} catch (Exception e) {
				
			}
		}
	}
}

