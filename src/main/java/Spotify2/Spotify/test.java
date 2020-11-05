package Spotify2.Spotify;

import DAOS.ArtistDAO;
import DAOS.DiscDAO;
import models.Artist;
import models.Disc;
import utilities.ConnectionBD;

public class test {
	public static void main(String[] args) {

		Artist n = new ArtistDAO(1);
		System.out.println(n);

		//Disc d = new DiscDAO(1);
		//System.out.println(d);
		System.out.println(n);
		for(Disc d: n.getDisclist()){
			System.out.println(d);
		}

		ConnectionBD.CloseConnection();
	}
}
