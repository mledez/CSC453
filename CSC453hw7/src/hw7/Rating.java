package hw7;

public class Rating {
	private int userId;
	private int movieId;
	private int rating;
	private int timeStamp;

	public Rating(int userId, int movieId, int rating, int timeStamp) {
		super();
		this.userId = userId;
		this.movieId = movieId;
		this.rating = rating;
		this.timeStamp = timeStamp;
	}

	public int getUserId() {
		return userId;
	}

	public int getMovieId() {
		return movieId;
	}

	public int getRating() {
		return rating;
	}

	public int getTimeStamp() {
		return timeStamp;
	}
}
