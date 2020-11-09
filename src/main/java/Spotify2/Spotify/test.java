package Spotify2.Spotify;

import DAOS.ArtistDAO;
import DAOS.DiscDAO;
import DAOS.GenreDAO;
import DAOS.PlayListDAO;
import DAOS.SongDAO;
import models.Artist;
import models.Disc;
import models.Playlist;
import models.Song;
import utilities.ConnectionBD;
import utilities.GeneralUtilities;

public class test {
	public static void main(String[] args) {

		/*Artist n = new ArtistDAO(1);
		System.out.println(n);

		//Disc d = new DiscDAO(1);
		//System.out.println(d);
		System.out.println(n);
		for(Disc d: n.getDisclist()){
			System.out.println(d);
		}

		ConnectionBD.CloseConnection();*/
		//System.out.print(GeneralUtilities.ValidateURL("https://i.blogs.es/594843/chrome/450_1000.jpg"));
/*
		SongDAO nuevo=new SongDAO(3);
		System.out.println(nuevo.delete());
*/
		Playlist nueva = new PlayListDAO(1);
		for(Song s:nueva.getSongs()){
			System.out.println(s);
		}
		System.out.println(nueva.getCreator());
		ConnectionBD.CloseConnection();
	}
}
