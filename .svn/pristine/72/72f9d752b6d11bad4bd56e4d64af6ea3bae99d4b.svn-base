package quizweb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;

public class StatisticsManager {
	
	public static List<SummaryStatistics> getSummaryStatistics(Connection conn, int quizId, int maxListSize) {
		List<SummaryStatistics> summaryStatistics = new ArrayList<SummaryStatistics>();
		Map<String,Object> params = new HashMap<String,Object>(); 
		params.put("quiz_id", quizId);
		List<String> users = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "username", "quizzes_taken", params, true, "username");
		List<Integer> scores = (List<Integer>)(List<?>) MySQLUtil.getQuery(conn, "score", "quizzes_taken", params, true, "username");
		if (users.size() > 0) {
			summaryStatistics = getSummaryStatistics(users,scores);
		}
		if (maxListSize > 0 && summaryStatistics.size() >= maxListSize) {
			summaryStatistics = summaryStatistics.subList(0, maxListSize);
		}
		return summaryStatistics;
	}

	private static List<SummaryStatistics> getSummaryStatistics(List<String> users, List<Integer> scores) {
		List<SummaryStatistics> summaryStatistics = new ArrayList<SummaryStatistics>();
		String prevUser = users.get(0);
		String currUser;
		int countAll = 0;
		int sumAll = 0;
		int highScoreAll = 0;
		List<Integer> scoresAll = new ArrayList<Integer>();
		int count = 0;
		int sum = 0;
		int highScore = 0;
		List<Integer> currUsersScores = new ArrayList<Integer>();
		for (int i = 0; i < users.size(); i++) {
			currUser = users.get(i);
			int currScore = scores.get(i);
			scoresAll.add(currScore);
			countAll += 1;
			sumAll += currScore;
			if (currScore > highScoreAll) {
				highScoreAll = currScore;
			}
			if (currUser.equals(prevUser)) {
				currUsersScores.add(currScore);
				count += 1;
				sum += currScore;
				if (currScore > highScore) {
					highScore = currScore;
				}
			} else {
				addToSummaryStatisticsList(summaryStatistics, prevUser, count, sum, highScore, currUsersScores, -1);
				count = 1;
				sum = currScore;
				highScore = currScore;
				currUsersScores.clear();
				currUsersScores.add(currScore);
			}
			prevUser = currUser;
		}
		addToSummaryStatisticsList(summaryStatistics, prevUser, count, sum, highScore, currUsersScores, -1);
		addToSummaryStatisticsList(summaryStatistics, "All", countAll, sumAll, highScoreAll, scoresAll, 0);
		return summaryStatistics;
	}
	
	private static void addToSummaryStatisticsList(
			List<SummaryStatistics> summaryStatistics, String prevUser,
			int count, int sum, int highScore, List<Integer> currUsersScores, int index) {
		double mean = ((double)sum)/count;
		double median = findMedian(currUsersScores);
		SummaryStatistics currUserSumStats = new SummaryStatistics(prevUser, mean, median, highScore, count);
		if (index >= 0) {
			summaryStatistics.add(index,currUserSumStats);
		} else {
			summaryStatistics.add(currUserSumStats);
		}
	}

	private static double findMedian(List<Integer> currUsersScores) {
		Collections.sort(currUsersScores);
		if (currUsersScores.size() % 2 == 0) {
			return ((double)currUsersScores.get(currUsersScores.size()/2) + (double)currUsersScores.get(currUsersScores.size()/2-1))/2;
		}
		else {
		    return (double)currUsersScores.get(currUsersScores.size()/2);
		}
	}

}
