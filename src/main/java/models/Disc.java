package models;

import java.time.LocalDate;
import java.util.List;

public class Disc {
	int id;
	String name;
	Artist artist;
	String photo;
	LocalDate date;
	List<Song> songlist;
}
