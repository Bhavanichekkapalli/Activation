package demo.hackathon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Siva Adabala
 * Debraj Nath
 * Bhavani Chekkapalli
 */
public class GenericLogFileTailer implements Runnable {
	/**
	 * How frequently to check for file changes; defaults to 5 seconds
	 */
	private long sampleInterval = 5000;

	/**
	 * The log file to tail
	 */
	private File logfile;

	/**
	 * Log Patterns
	 */
	private List<String> errorPatterns = null;

	public List<String> getErrorPatterns() {
		return errorPatterns;
	}

	public void setErrorPatterns(List<String> errorPatterns) {
		this.errorPatterns = errorPatterns;
	}

	/**
	 * Notification Type
	 */
	
	String notificationType = null;
	
	
	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * Defines whether the log file tailer should include the entire contents of
	 * the exising log file or tail from the end of the file when the tailer
	 * starts
	 */
	private boolean startAtBeginning = false;

	/**
	 * Is the tailer currently tailing?
	 */
	private boolean tailing = false;

	/**
	 * Set of listeners
	 */
	private Set listeners = new HashSet();

	/**
	 * Creates a new log file tailer that tails an existing file and checks the
	 * file for updates every 5000ms
	 */
	public GenericLogFileTailer(File file) {
		this.logfile = file;
	}

	/**
	 * Creates a new log file tailer
	 * 
	 * @param file
	 *            The file to tail
	 * @param sampleInterval
	 *            How often to check for updates to the log file (default =
	 *            5000ms)
	 * @param startAtBeginning
	 *            Should the tailer simply tail or should it process the entire
	 *            file and continue tailing (true) or simply start tailing from
	 *            the end of the file
	 * @param errorPatterns
	 *            Should be provided by client/user
	 */
	public GenericLogFileTailer(File file, long sampleInterval,
			boolean startAtBeginning, List<String> errorPatterns,String notificationType) {
		this.logfile = file;
		this.sampleInterval = sampleInterval;
		this.errorPatterns = errorPatterns;
		this.notificationType = notificationType;
	}

	public void addLogFileTailerListener(GenericLogFileTailerListener listener) {
		this.listeners.add(listener);
	}

	public void removeLogFileTailerListener(
			GenericLogFileTailerListener listener) {
		this.listeners.remove(listener);
	}

	protected void fireNewLogFileLine(String line) {
		for (Iterator i = this.listeners.iterator(); i.hasNext();) {
			GenericLogFileTailerListener listener = (GenericLogFileTailerListener) i
					.next();
			listener.newLogFileLine(line);
		}
	}

	public void stopTailing() {
		this.tailing = false;
	}

	public void run() {
		
		// The file pointer keeps track of where we are in the file
		long filePointer = 0;

		// Determine start point
		if (this.startAtBeginning) {
			filePointer = 0;
		} else {
			filePointer = this.logfile.length();
		}

		try {
			// Start tailing
			this.tailing = true;
			RandomAccessFile file = new RandomAccessFile(logfile, "r");
			while (this.tailing) {
				try {
					// Compare the length of the file to the file pointer
					long fileLength = this.logfile.length();
					if (fileLength < filePointer) {
						// Log file must have been rotated or deleted;
						// reopen the file and reset the file pointer
						file = new RandomAccessFile(logfile, "r");
						filePointer = 0;
					}

					if (fileLength > filePointer) {
						// There is data to read
						file.seek(filePointer);
						String line = file.readLine();
						while (line != null) {
							this.fireNewLogFileLine(line);

							line = file.readLine();

							//Check if Pattern matches.If so , notify based on the notify type
							if(isPatternMatching(line, this.errorPatterns)){
								//Code to Notify
								if(notificationType == NotificationType.EMAIL){
									//Line for Body, File name for Subject, Signature name
									
								}else if(notificationType == NotificationType.SAVE_TO_DISK){
									
								}else{
									System.out.println("No notification type specified by client");
								}
							}
							//System.out.println(logfile.getName() + " -> "	+ line);
						}
						filePointer = file.getFilePointer();
					}
					// Sleep for the specified interval
					Thread.sleep(this.sampleInterval);
				} catch (Exception e) {
				}
			}

			// Close the file that we are tailing
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean isPatternMatching(String line, List<String> patterns) {
		boolean patternMatched = false;

		if (patterns.size() <= 0) {
			return false;
		}
		StringBuilder builder = new StringBuilder("");
		for (String pattern : patterns) {
			builder.append(pattern).append("|");
		}
		String patternsString = "";
		if (builder.toString().indexOf("|") >= 0) {
			patternsString = builder.toString().substring(0,
					builder.toString().lastIndexOf("|"));
		}

		System.out.println("Pattern String :" + patternsString);
		Pattern p = Pattern.compile("(" + patternsString + ")");
		Matcher m = p.matcher(line);

		// List<String> animals = new ArrayList<String>();
		while (m.find()) {
			System.out.println("Found a " + m.group() + ".");
			patternMatched = true;
			// animals.add(m.group());
		}

		return patternMatched;
	}
	
	public static synchronized void appendContents(String sFileName, String sContent) {
        try {
            File oFile = new File(sFileName);
            if (!oFile.exists()) {
                oFile.createNewFile();
            }
            if (oFile.canWrite()) {
                BufferedWriter oWriter = new BufferedWriter(new FileWriter(sFileName, true));
                oWriter.write (sContent);
                oWriter.close();
            }
        }
        catch (IOException oException) {
            throw new IllegalArgumentException("Error appending/File cannot be written: \n" + sFileName);
        }
    }


}