package Spotify2.Spotify;

import models.Channel;
import utilities.ConnectionBD;
import utilities.XMLManager;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        if(ConnectionBD.getConnection()!=null){
        	ConnectionBD.CloseConnection();
        }
    }
}
