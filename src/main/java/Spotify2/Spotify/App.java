package Spotify2.Spotify;


import DAOS.PlayListDAO;
import DAOS.UsersDAO;
import models.Playlist;
import models.User;
import utilities.ConnectionBD;


/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {



    	Playlist pl=new PlayListDAO(1);



    	System.out.println(pl);
    	for(User p:pl.getSubscribers()){
    		System.out.print(p);
    	}

        	ConnectionBD.CloseConnection();


    }
}
