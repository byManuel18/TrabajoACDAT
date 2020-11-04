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


/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {

    	Ejecutable.main(args);

        ConnectionBD.CloseConnection();


    }
}
