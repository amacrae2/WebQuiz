package quizweb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateTimeManager {
	
	public static Date getDateForOneDayAgo() {
		Date now = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(now); 
		c.add(Calendar.DATE, -1);
		Date oneDayAgo = c.getTime();
		return oneDayAgo;
	}
	
	// based on if they are after one day ago or not
	public static List<Integer> findIndicesOfDatesToRemove(Date oneDayAgo, List<String> dates) {
		List <Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < dates.size(); i++) {
			String date = dates.get(i);
			try {
				Date createDate = new SimpleDateFormat(QuizWebConstants.DATE_TIME_FORMAT, Locale.ENGLISH).parse(date);
				if (createDate.before(oneDayAgo)) {
					indices.add(i);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(indices, Collections.reverseOrder());
		return indices;
	}

}
