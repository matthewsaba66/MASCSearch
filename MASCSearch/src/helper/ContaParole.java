package helper;
import java.util.StringTokenizer;


public class ContaParole {

	public static long wordCount(String string) {
		StringTokenizer s = new StringTokenizer(string," ");
		long i = 0;
		while(s.hasMoreTokens()){
			s.nextToken();
			i++;
		}
		return i;	}

}
