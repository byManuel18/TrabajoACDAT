package models;

import java.util.List;

public class Artist {
	int id;
	String name;
	String nationality;
	String photo;
	List<Disc> disclist;

	public Artist(int id, String name, String nationality, String photo, List<Disc> disclist) {
		this.id = id;
		this.name = name;
		this.nationality = nationality;
		this.photo = photo;
		this.disclist = disclist;
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


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public List<Disc> getDisclist() {
		return disclist;
	}


	public void setDisclist(List<Disc> disclist) {
		this.disclist = disclist;
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
		Artist other = (Artist) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", nationality=" + nationality + ", photo=" + photo
				+ ", disclist=" + disclist + "]";
	}


}
