package hw7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Movie {
	private int movieId;
	private String title;
	private int year;
	private String[] genres;
	private Matcher m;

	public Movie(int movieId, String title, String[] genres) {
		super();
		this.movieId = movieId;
		m = Pattern.compile("\\((\\d{4}?)\\)").matcher(title);
		while (m.find()) {
			this.year = Integer.parseInt(m.group(1));
		}
		this.title = title.replace("(" + year + ")", "").trim();
		this.genres = genres;
	}

	public int getMovieId() {
		return movieId;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
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
