package DAOS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import enums.SentenciasArtista;
import models.Artist;
import utilities.ConnectionBD;

public class ArtistDAO extends Artist {

	/**
	 * Default constructor
	 */
	public ArtistDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * full constructor
	 * @param id(int): artist id
	 * @param name(String): artist name
	 * @param nationality(String): artist nationality
	 * @param photo(String): artist photo
	 */
	public ArtistDAO(int id, String name, String nationality, String photo) {
		super(id, name, nationality, photo);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor without id
	 * @param name(String): artist name
	 * @param nationality(String): artist nationality
	 * @param photo(String): artist photo
	 */
	public ArtistDAO(String name, String nationality, String photo) {
		super(name, nationality, photo);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor that receives an artist
	 * @param a(Artist)
	 */
	public ArtistDAO(Artist a){
		this.setId(a.getId());
		this.setName(a.getName());
		this.setNationality(a.getNationality());
		this.setPhoto(a.getPhoto());
		//this.setDisclist(a.getDisclist());
		this.setSynchro(false);
	}

	/**
	 * Select an artist from the database based on the parameter ID
	 * @param id(int): Artist id
	 */
	public ArtistDAO(int id){
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasArtista.SELECTBYID.getSQL());
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					this.setId(id);
					this.setName(rs.getString("nombre"));
					this.setNationality(rs.getString("nacionalidad"));
					this.setPhoto(rs.getString("foto"));
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
	 * According the artist id, update or insert: if id is -1, insert artist
	 * if id is greater than 0, update artist
	 * @return int: 1 if has been updated or inserted, 0 if didn't work, -1 if error
	 */
	public int update(){
		int result=-1;
		PreparedStatement ps=null;

		if(this.getId()>0){
			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasArtista.UPDATE.getSQL());
				ps.setString(1, this.getName());
				ps.setString(2, this.getNationality());
				ps.setString(3, this.getPhoto());
				ps.setInt(4, this.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasArtista.INSERT.getSQL());
				ps.setString(1, this.getName());
				ps.setString(2, this.getNationality());
				ps.setString(3, this.getPhoto());
				result=ps.executeUpdate();
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
	 * According the artist id, delete that artist from the data base
	 * @return int: 1 if the artist has been deleted, 0 if didn't work, -1 if error
	 */
	public int delete(){
		int result=-1;
		PreparedStatement ps=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasArtista.DELETE.getSQL());
			ps.setInt(1, this.getId());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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

	/**
	 * Search artists by name
	 * @param name(String): Artist name
	 * @return Set<Artist>: artist selected by name
	 */
	public  static Set<Artist> SelectbyName(String name){
		return Search(SentenciasArtista.SELECTBYNAME, name);
	}

	/**
	 * Search artists by nationality
	 * @param name(String): Artist nationality
	 * @return Set<Artist>: artist selected by nationality
	 */
	public  static Set<Artist> SelectbyNacionalidad(String name){
		return Search(SentenciasArtista.SELECTBYNACIONALIDAD, name);
	}

	/**
	 * Search all artists
	 * @return Set<Artist>: all artists
	 */
	public  static Set<Artist> SelectAll(){
		return Search(SentenciasArtista.SELECTALL, "");
	}

	/**
	 * Search artists depending the statement and the parameter
	 * @param sql(SentenciasArtista): statement by want to search (name or nationality)
	 * @param parametro(String): parameter by want to search
	 * @return Set<Artist>: artists selected based on the statement
	 */
	private static Set<Artist> Search(SentenciasArtista sql,String parametro){
		Set<Artist> listArtist=new HashSet<Artist>();
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(sql.getSQL());
			if(sql==SentenciasArtista.SELECTBYNAME||sql==SentenciasArtista.SELECTBYNACIONALIDAD){
				ps.setString(1, parametro+"%");
			}

			rs=ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					Artist n=new Artist(rs.getInt("id"),rs.getString("nombre"),rs.getString("nacionalidad"),rs.getString("foto"));
					listArtist.add(n);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listArtist;
	}

	public static boolean existArtist(String s){
		boolean result=false;

		PreparedStatement ps=null;
		ResultSet rs=null;

		try{
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasArtista.EXIST.getSQL());
			ps.setString(1, s);
			rs=ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					result=true;
				}
			}
		}catch (SQLException e) {
			// TODO: handle exception
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
