package DAOS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import enums.SentenciasSong;
import models.Disc;
import models.Genre;
import models.Song;
import utilities.ConnectionBD;

public class SongDAO extends Song{

	/**
	 * Default constructor
	 */
	public SongDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * full constructor
	 * @param id(int): song id
	 * @param name(String): song name
	 * @param duration(int): song duration
	 * @param genre(Genre): song genre
	 * @param disc(Disc): song disc
	 */
	public SongDAO(int id, String name, int duration, Genre genre, Disc disc) {
		super(id, name, duration, genre, disc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor without id
	 * @param name(String): song name
	 * @param duration(int): song duration
	 * @param genre(Genre): song genre
	 * @param disc(Disc): song disc
	 */
	public SongDAO(String name, int duration, Genre genre, Disc disc) {
		super(name, duration, genre, disc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor that receives a song
	 * @param s(Song)
	 */
	public SongDAO(Song s){
		this.setId(s.getId());
		this.setName(s.getName());
		this.setDuration(s.getDuration());
		this.setGenre(s.getGenre());
		this.setDisc(s.getDisc());
	}

	/**
	 * Select a song from the database based on the parameter id
	 * @param id(int): song id
	 */
	public SongDAO(int id){
		super();
		PreparedStatement ps= null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasSong.SELECTBYID.getSQL());
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					this.setId(id);
					this.setName(rs.getString("nombre"));
					this.setDuration(rs.getInt("duracion"));
					this.setGenre(new GenreDAO(rs.getInt("id_genero")));
					this.setDisc(new DiscDAO(rs.getInt("id_disco")));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * According the song id, update or insert: if id is -1, insert song
	 * if id is greater than 0, update song
	 * @return int: 1 if has been updated or inserted, 0 if didn't work, -1 if error
	 */
	public int update(){
		int result=-1;

		PreparedStatement ps=null;

		if(this.getId()>0){
			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasSong.UPDATE.getSQL());
				ps.setString(1, this.getName());
				ps.setInt(2, this.getDuration());
				ps.setInt(3, this.getGenre().getId());
				ps.setInt(4, this.getDisc().getId());
				ps.setInt(5, this.getId());

				result=ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasSong.INSERT.getSQL());
				ps.setString(1, this.getName());
				ps.setInt(2, this.getDuration());
				ps.setInt(3, this.getGenre().getId());
				ps.setInt(4, this.getDisc().getId());

				result =ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * According the song id, delete that song form the database
	 * @return int: 1 if the song has been deleted, 0 if didn't work, -1 if error
	 */
	public int delete(){
		int result=-1;

		PreparedStatement ps=null;
		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasSong.DELETE.getSQL());
			ps.setInt(1, this.getId());

			result=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Search all songs
	 * @return Set<Song>: all songs
	 */
	public static Set<Song> SelectAll(){
		return Search(SentenciasSong.SELECTALL,"",null,null,-1);
	}

	/**
	 * Search songs from a playlist
	 * @param id(int): playlist id
	 * @return Set<Song>: songs selected from a playlist
	 */
	public static Set<Song> SelectForPlaylist(int id){
		return Search(SentenciasSong.SELECTFORPLAYLIST,"",null,null,id);
	}

	/**
	 * Search songs from a disc
	 * @param d(Disc)
	 * @return Set<Song>: songs selected from a disc
	 */
	public static Set<Song> SelectForDisc(Disc d){
		return Search(SentenciasSong.SELECTFORDISC,"",d,null,-1);
	}

	/**
	 * Search songs by name
	 * @param name(String): song name
	 * @return Set<Song>: songs selected by name
	 */
	public static Set<Song> SelectForName(String name){
		return Search(SentenciasSong.SELECTBYNAME,name,null,null,-1);
	}

	/**
	 * Search songs depending the statement and parameters
	 * @param sql(SentenciasSong): statement by want to search
	 * @param name(String): song name
	 * @param disc(Disc): song disc
	 * @param genre(Genre): song genre
	 * @param n(int): playlist id
	 * @return Set<Song>: songs selected based on the statement
	 */
	private static Set<Song> Search(SentenciasSong sql, String name, Disc disc, Genre genre, int n){
		Set<Song> listSong=new HashSet<Song>();

		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(sql.getSQL());
			if(sql==SentenciasSong.SELECTFORPLAYLIST){
				ps.setInt(1, n);
			}else if(sql==SentenciasSong.SELECTFORDISC){
				ps.setInt(1, disc.getId());
			}else if(sql==SentenciasSong.SELECTBYNAME){
				ps.setString(1, name+"%");
			}
			rs=ps.executeQuery();

			if(rs!=null){
				while(rs.next()){
					Song s=new Song(rs.getInt("id"),rs.getString("nombre"),rs.getInt("duracion"),new GenreDAO(rs.getInt("id_genero")),new DiscDAO(rs.getInt("id_disco")));
					listSong.add(s);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listSong;
	}

	public static boolean ExistSong(int id_disc, String n){
		boolean result=false;

		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasSong.EXIST.getSQL());
			ps.setInt(1, id_disc);
			ps.setString(2, n);
			rs=ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					result=true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}
