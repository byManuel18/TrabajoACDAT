package Spotify2.Spotify;

import models.Channel;
import utilities.XMLManager;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Channel c=new Channel();
        System.out.println( XMLManager.SetChanel(c));
    }
}
