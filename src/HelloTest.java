import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloTest{
	
	public static void main(String[] args){
		String line = "Hello this is test log.Test exception in file1";
		
		List<String> errorPatterns = new ArrayList<String>();
		errorPatterns.add("Test exception in file1");
		errorPatterns.add("Test exception in file2");
		errorPatterns.add("Hello");
		
		HelloTest helloTest = new HelloTest();
		boolean isPatternMatched = helloTest.isPatternMatching(line,errorPatterns);
		
		System.out.println("Pattern Matched :"+isPatternMatched);
		
		
	}
	boolean isPatternMatching(String line, List<String> patterns) {
		  boolean patternMatched = false;
		  
		  
		  if(patterns.size() <= 0){
			  return false;
		  }
		  StringBuilder builder = new StringBuilder("");
		  for(String pattern : patterns){
			  builder.append(pattern).append("|");
		  }
		  String patternsString = "";
		  if(builder.toString().indexOf("|") >= 0){
			  patternsString = builder.toString().substring(0, builder.toString().lastIndexOf("|"));
		  }
			
		  System.out.println("Pattern String :"+patternsString);
			Pattern p = Pattern.compile("("+patternsString+")");
			Matcher m = p.matcher(line);

			//List<String> animals = new ArrayList<String>();
			while (m.find()) {
				System.out.println("Found a " + m.group() + ".");
				patternMatched = true;
				//animals.add(m.group());
			}
			
			return patternMatched;
		}
	
}