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

	public Disc(int id, String name, Artist artist, String photo, LocalDate date, List<Song> songlist) {
		this.id = id;
		this.name = name;
		this.artist = artist;
		this.photo = photo;
		this.date = date;
		this.songlist = songlist;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Song> getSonglist() {
		return songlist;
	}

	public void setSonglist(List<Song> songlist) {
		this.songlist = songlist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disc other = (Disc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Disc [id=" + id + ", name=" + name + ", artist=" + artist + ", photo=" + photo + ", date=" + date
				+ ", songlist=" + songlist + "]";
	}


}
