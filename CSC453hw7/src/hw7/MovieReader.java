package hw7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MovieReader {

	public static List<Movie> getMovies(String fileName, String separator, String genreSeparator) {
		String line = "";
		InputStream is = MovieReader.class.getResourceAsStream("/res/" + fileName);
		List<Movie> movieList = null;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			movieList = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				String[] movie = line.split(separator);
				String[] genres = movie[2].split(genreSeparator);
				movieList.add(new Movie(Integer.parseInt(movie[0]), movie[1], genres));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return movieList;
	}
}
