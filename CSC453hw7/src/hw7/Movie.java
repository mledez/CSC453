package hw7;

public class Movie {
	private int movieId;
	private String title;
	private String[] genres;

	public Movie(int movieId, String title, String[] genres) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.genres = genres;
	}

	public int getMovieId() {
		return movieId;
	}

	public String getTitle() {
		return title;
	}

	public String[] getGenres() {
		return genres;
	}

	@Override
	public String toString() {
		String report = "";
		for (String genre : genres)
			report += genre + "; ";
		return String.format("Movie ID: %d\nTitle: %s\nGenres: %s\n\n", movieId, title, report);
	}
}
