package demo.hackathon;

/**
 * Provides listener notification methods when a tailed log file is updated
 */
public interface GenericLogFileTailerListener extends Runnable
{
  /**
   * A new line has been added to the tailed log file
   * 
   * @param line   The new line that has been added to the tailed log file
 * @return 
   */
  public String newLogFileLine( String line );
}