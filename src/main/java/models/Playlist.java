package models;

import java.util.HashSet;
import java.util.Set;
import DAOS.UsersDAO;

public class Playlist{
	private int id;
	private String name;
	private String description;
	private User creator;
	private Set<User> subscribers=new HashSet<User>();
	boolean synchro=false;

	public Playlist(){
		this(-1,"","",null);
	}

	public Playlist(String name, String descripcion,User creator){
		this(-1,name,descripcion,creator);
	}

	public Playlist(int id, String name, String description, User creator) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.creator = creator;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}


	public Set<User> getSubscribers() {
		if(!synchro){
			subscribers=UsersDAO.SelectSubscribers(this.id);
			synchro=true;
		}
		return subscribers;
	}

	public void setSubscribers(Set<User> subscribers) {
		this.subscribers = subscribers;
	}

	public boolean isSynchro() {
		return synchro;
	}

	public void setSynchro(boolean synchro) {
		this.synchro = synchro;
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
		Playlist other = (Playlist) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Playlist [id=" + id + ", name=" + name + ", description=" + description + ", creator=" + creator + "]";
	}


}
