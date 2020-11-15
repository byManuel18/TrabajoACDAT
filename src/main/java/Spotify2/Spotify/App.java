package Spotify2.Spotify;


import DAOS.ArtistDAO;
import DAOS.GenreDAO;
import DAOS.PlayListDAO;
import DAOS.UsersDAO;
import models.Artist;
import models.Genre;
import models.Playlist;
import models.User;
import utilities.ConnectionBD;

public class App
{
	private static User user=null;
    public static void main( String[] args )
    {

    	Ejecutable.main(args);

        ConnectionBD.CloseConnection();


    }
    public static void setUser(User s){
    	user=s;
    }
    public static User getUser(){
    	return user;
    }
}
