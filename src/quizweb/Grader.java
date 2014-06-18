
package quizweb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Grader {
	/* instance variable */
	private Statement stmt;
	
	public Grader(Statement stmt) {
		this.stmt = stmt;
	}
	
	public boolean grade(int id, String answ) throws SQLException {
		// get the question type
		ResultSet rs = stmt.executeQuery("SELECT * FROM questions WHERE  question_id="+id);
		rs.next();
		int questionType = rs.getInt("type");
		String corrAnswer = rs.getString("answer").toLowerCase();
		String answer = answ.toLowerCase();
		String order = rs.getString("sequence");
		switch (questionType) {
			case 1: 			
				if (gradeResponse(answer, corrAnswer)) return true;
				break;
			case 2: 
				if (gradeResponse(answer, corrAnswer)) return true;
				break;
			case 3: 
				if (gradeMultiChoice(answer, corrAnswer)) return true;
				break;
			case 4: 
				if (gradeResponse(answer, corrAnswer)) return true;
				break;
			case 5: 			
				if (gradeMultiAnswer(answer, corrAnswer, order)) return true;
				break;
			case 6: 			
				if (gradeFillMultiBlank(answer, corrAnswer)) return true;
				break;
			default: break;
		}
		return false;
	}
	
	public boolean gradeResponse (String answ, String corrAnsw) {
		String answer = deleteSpace(answ);
		String corrAnswer = deleteSpace(corrAnsw);
		
		String[] parts = corrAnswer.split(";");
		for (int i=0; i<parts.length; i++) {
			if (parts[i].equals(answer)) return true;
		}
		return false;
	}
	
	public boolean gradeMultiChoice (String answ, String corrAnsw) {
		String answer = deleteSpace(answ);
		String corrAnswer = deleteSpace(corrAnsw);
		
		if (corrAnswer.equals(answer)) return true;
		return false;
	}
	
	public boolean gradeFillMultiBlank (String answ, String corrAnsw) {
		String answer = deleteSpace(answ);
		String corrAnswer = deleteSpace(corrAnsw);
		
		String[] answerParts = answer.split(";");
		String[] corrAnswerClusters = corrAnswer.split(";");
		
		// check if the number of answers match
		int answerLength = answerParts.length;
		int corrAnswerLength = corrAnswerClusters.length;
		if (answerLength != corrAnswerLength) {
			return false;
		}
		
		// for each answer, check if it's one of the correct alternatives
		int corr = 0;
		for (int i=0; i<answerLength; i++) {
			String[] alternatives = corrAnswerClusters[i].split(",");
			for (int j=0; j<alternatives.length; j++) {
				if (alternatives[j].equals(answerParts[i])) {
					corr++;
					break;
				}
			}
		}
		
		// check if all answers are correct
		if (corr==answerLength) return true;
		return false;
	}
	
	public boolean gradeMultiAnswer (String answ, String corrAnsw, String order) {
		Set<Integer> set = new HashSet<Integer>();
		String answer = deleteSpace(answ);
		String corrAnswer = deleteSpace(corrAnsw);
		
		String[] answerParts = answer.split(";");
		String[] corrAnswerClusters = corrAnswer.split(";");
		
		// check if the number of answers match
		int answerLength = answerParts.length;
		int corrAnswerLength = corrAnswerClusters.length;
		if (answerLength != corrAnswerLength) {
			return false;
		}
		
		// for each answer, check if it's one of the correct alternatives
		int corr = 0;
		if (order.equals("on")) {
			for (int i=0; i<answerLength; i++) {
				String[] alternatives = corrAnswerClusters[i].split(",");
				for (int j=0; j<alternatives.length; j++) {
					if (alternatives[j].equals(answerParts[i])) {
						corr++;
						break;
					}
				}
			}
		} else {
			for (int i=0; i<answerLength; i++) {
				for (int j=0; j<corrAnswerClusters.length; j++) {
					if (!set.contains(j)) {
						String[] alternatives = corrAnswerClusters[j].split(",");
						for (int k=0; k<alternatives.length; k++) {
							if (alternatives[k].equals(answerParts[i])) {
								corr++;
								set.add(j);
								break;
							}
						}	
					}				
				}
			}
		}		
		
		// check if all answers are correct
		if (corr==answerLength) return true;
		return false;
	}
	
	public String deleteSpace(String string) {
		String result = "";
		String restStr = string;
		
		while(true) {
			// find the index of the first space	
			int index = restStr.indexOf(' ');
			if (index == -1) break;
			
			// store parts before and after the blank as substrings
			String firstStr = restStr.substring(0,index);
			restStr = restStr.substring(index+1);
			
			// add the first part and a blank to the display text
			result += firstStr;		
		}
		
		result += restStr;
		return result;
	}
}
