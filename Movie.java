import java.util.ArrayList;

public class Movie {
    private String title;
    private String director;
    private String genre;
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Movie(){}
    public Movie(String title, String director, ArrayList<Actor> actors) {
        this.title = title;
        this.director = director;
        this.actors = actors;
    }

    private ArrayList<Actor> actors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Movie> movies) {
        this.actors = actors;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Movie Details - ");

        sb.append("ID: " + getId() + ", ");
        sb.append("year: " + getYear() + ", ");
        sb.append("Title: " + getTitle() + ", ");
        sb.append("Director: " + getDirector() + ", ");
        sb.append("Genre: " + getGenre() + ", ");
        return sb.toString();
    }
}
