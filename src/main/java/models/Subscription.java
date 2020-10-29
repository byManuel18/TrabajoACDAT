package models;

public class Subscription {
	User sub;
	Playlist playlist;

	public Subscription(User sub, Playlist playlist) {
		this.sub = sub;
		this.playlist = playlist;
	}

	public User getSub() {
		return sub;
	}

	public void setSub(User sub) {
		this.sub = sub;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playlist == null) ? 0 : playlist.hashCode());
		result = prime * result + ((sub == null) ? 0 : sub.hashCode());
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
		Subscription other = (Subscription) obj;
		if (playlist == null) {
			if (other.playlist != null)
				return false;
		} else if (!playlist.equals(other.playlist))
			return false;
		if (sub == null) {
			if (other.sub != null)
				return false;
		} else if (!sub.equals(other.sub))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subscription [sub=" + sub + ", playlist=" + playlist + "]";
	}


}
