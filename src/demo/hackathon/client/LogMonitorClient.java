package demo.hackathon.client;

// Import the Java classes
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import demo.hackathon.GenericLogFileTailer;
import demo.hackathon.GenericLogFileTailerListener;
import demo.hackathon.NotificationType;

/**
 * Implements console-based log file tailing, or more specifically, tail
 * following: it is somewhat equivalent to the unix command "tail -f"
 */
public class LogMonitorClient implements GenericLogFileTailerListener {
	/**
	 * The log file tailer
	 */
	private GenericLogFileTailer genericLogFileTailer;

	private static final int MYTHREADS = 30;

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public List<String> getErrorPatterns() {
		return errorPatterns;
	}

	public void setErrorPatterns(List<String> errorPatterns) {
		this.errorPatterns = errorPatterns;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public List<String> getMailList() {
		return mailList;
	}

	public void setMailList(List<String> mailList) {
		this.mailList = mailList;
	}

	String directoryName;
	List<String> fileList;
	List<String> errorPatterns;
	String notificationType;
	List<String> mailList;

	public LogMonitorClient() {
		this.directoryName = null;
		this.fileList = null;
		this.errorPatterns = null;
		this.notificationType = NotificationType.SAVE_TO_DISK;
		this.mailList = null;
	}

	/**
	 * Creates a new Tail instance to follow the specified file
	 */
	public LogMonitorClient(String directoryName, List<String> fileList,
			List<String> errorPatterns, String notificationType,
			List<String> mailList) {
		this.directoryName = directoryName;
		this.fileList = fileList;
		this.errorPatterns = errorPatterns;
		this.notificationType = notificationType;
		//default to NotificationType.DOWNLOAD_FILE
		if(notificationType == null || "".equalsIgnoreCase(notificationType)){
			this.notificationType = NotificationType.SAVE_TO_DISK;
		}
		this.mailList = mailList;
	}

	/**
	 * A new line has been added to the tailed log file
	 * 
	 * @param line
	 *            The new line that has been added to the tailed log file
	 */
	public String newLogFileLine(String line) {
		System.out.println(line);
		return line;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Properties logMonitorProps = null;
		try {
			logMonitorProps = getLogMonitorProperties();
		} catch (IOException e2) {
			System.out.println("Make sure log_monitor.properties is in CLASSPATH");
			return;
		}
		
		if(directoryName == null && fileList == null && errorPatterns== null && notificationType == null && mailList == null){
			directoryName = logMonitorProps.getProperty("MONITOR_DIR_NAME");
			if(directoryName == null){
				return;
			}
			
			String fileNames = logMonitorProps.getProperty("MONITOR_FILE_LIST");
			if(fileNames != null){
				fileList = new ArrayList<String>();
				String[] files = fileNames.split(",");
				for(int fileCnt = 0;fileCnt < files.length;fileCnt++){
					fileList.add(files[fileCnt]);
				}
			}else{
				return;
			}
			
			
			
		}
			
			this.directoryName = null;
		this.fileList = null;
		this.errorPatterns = null;
		this.notificationType = NotificationType.SAVE_TO_DISK;
		this.mailList = null;

		
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);

		for (String fileName : fileList) {
			try {
				//fork thread for each file
				Runnable worker = new GenericLogFileTailer(new File(fileName),100, false, errorPatterns, notificationType);
				((GenericLogFileTailer) worker).addLogFileTailerListener(this);
				executor.execute(worker);
			} catch (Exception e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				continue;
			}
		}
		executor.shutdown();

		// Wait until all threads are finish
		while (!executor.isTerminated()) {

		}
		System.out.println("\nFinished all threads");

	}
	
	private Properties getLogMonitorProperties() throws IOException {
		InputStream is = null;
		Properties prop = null;
		try {
			prop = new Properties();
			is = this.getClass().getResourceAsStream("/log_monitor.properties");
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return prop;
	}
	
	/**
	 * Command-line launcher
	 */
	public static void main(String[] args) {
		List<String> fileList = new ArrayList<String>();
		fileList.add("C:\\Siva\\DemoLogs\\test1.log");
		fileList.add("C:\\Siva\\DemoLogs\\test2.log");

		List<String> errorPatterns = new ArrayList<String>();
		errorPatterns.add("Test exception in file1");
		errorPatterns.add("Test exception in file2");
		
		String notificationType = NotificationType.EMAIL;

		String directoryName = "C:\\Siva\\DemoLogs\\";
		
		List<String> mailList = new ArrayList<String>();
		mailList.add("siva.kumar.x.adabala@one.verizon.com");
		
		System.out.println("Start Monitoring the logs");
		Runnable thread = new LogMonitorClient(directoryName, fileList,errorPatterns,notificationType,mailList);
		thread.run();
		System.out.println("Fork a thread to monitor the logs and come out");
		
		
		/*ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);

		for (String fileName : fileList) {
			try {
				Runnable worker = new GenericLogFileTailer(new File(fileName), 100, false, errorPatterns,notificationType);
				((GenericLogFileTailer) worker).addLogFileTailerListener(new LogMonitorClient());
				executor.execute(worker);
			} catch (Exception e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				continue;
			}
		}
		executor.shutdown();

		// Wait until all threads are finish
		while (!executor.isTerminated()) {

		}
		System.out.println("\nFinished all threads");*/
	}

}