package models;

import com.google.protobuf.Timestamp;

public class Comment {
	int id;
	User user;
	Playlist playlist;
	String message;
	Timestamp instant;

	public Comment(int id, User user, Playlist playlist, String message, Timestamp instant) {
		this.id = id;
		this.user = user;
		this.playlist = playlist;
		this.message = message;
		this.instant = instant;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getInstant() {
		return instant;
	}

	public void setInstant(Timestamp instant) {
		this.instant = instant;
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
		Comment other = (Comment) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", user=" + user + ", playlist=" + playlist + ", message=" + message + ", instant="
				+ instant + "]";
	}


}
