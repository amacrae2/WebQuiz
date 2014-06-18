package quizweb;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SummaryStatistics {

	private String user;
	private double mean;
	private double median;
	private int highScore;
	private int timesTaken;
	
	public SummaryStatistics(String user, double mean, double median, int highScore, int timesTaken) {
		super();
		this.user = user;
		this.mean = mean;
		this.median = median;
		this.highScore = highScore;
		this.timesTaken = timesTaken;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user; 
	}
	
	public double getMean() {
		return round(mean,2);
	}

	public void setMean(double mean) {
		this.mean = mean; 
	}

	public double getMedian() {
		return round(median,2);
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public int getTimesTaken() {
		return timesTaken;
	}

	public void setTimesTaken(int timesTaken) {
		this.timesTaken = timesTaken;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
