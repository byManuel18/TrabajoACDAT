package DAOS;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import enums.SentenciasDisc;
import models.Artist;
import models.Disc;
import utilities.ConnectionBD;

public class DiscDAO extends Disc{

	/**
	 * Default Constructor
	 */
	public DiscDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Full constructor
	 * @param id(int): disc id
	 * @param name(String): disc name
	 * @param artist(Artist): disc artist
	 * @param photo(String): disc photo
	 * @param date(LocalDate): disc date release
	 */
	public DiscDAO(int id, String name, Artist artist, String photo, LocalDate date) {
		super(id, name, artist, photo, date);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor without id
	 * @param name(String): disc name
	 * @param artist(Artist): disc artist
	 * @param photo(String): disc photo
	 * @param date(LocalDate): disc date release
	 */
	public DiscDAO(String name, Artist artist, String photo, LocalDate date) {
		super(name, artist, photo, date);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor that receives a disc
	 * @param disc(Disc)
	 */
	public DiscDAO(Disc disc){
		this.setDate(disc.getDate());
		this.setName(disc.getName());
		this.setArtist(disc.getArtist());
		this.setPhoto(disc.getPhoto());
		this.setId(disc.getId());
	}

	/**
	 * Select a disc from the database based on the parameter id
	 * @param id(int): disc id
	 */
	public DiscDAO(int id){
		super();
		PreparedStatement ps=null;
		ResultSet rs = null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasDisc.SELECTBYID.getSQL());
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					this.setId(id);
					this.setName(rs.getString("nombre"));
					Artist a=new ArtistDAO(rs.getInt("id_artista"));
					this.setArtist(a);
					this.setPhoto(rs.getString("foto"));
					if(rs.getDate("fecha_produccion")!=null){
						this.setDate(rs.getDate("fecha_produccion").toLocalDate());
					}else{
						this.setDate(null);
					}
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
	 * According the disc id, update or insert: if id is -1, insert disc
	 * if id is greater than 0, update disc
	 * @return int: 1 if has been updated or inserted, 0 if didn't work, -1 if error
	 */
	public int update(){
		int result=-1;

		PreparedStatement ps=null;
		if(this.getId()>0){
			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasDisc.UPDATE.getSQL());
				ps.setString(1, this.getName());
				ps.setString(2, this.getPhoto());
				if(this.getDate()!=null){
					ps.setDate(3, Date.valueOf(this.getDate()));
				}else{
					ps.setDate(3, null);
				}
				ps.setInt(4, this.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{

			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasDisc.INSERT.getSQL());
				ps.setString(1, this.getName());
				ps.setString(2, this.getPhoto());
				ps.setInt(3, this.getArtist().getId());
				if(this.getDate()!=null){
					ps.setDate(4, Date.valueOf(this.getDate()));
				}else{
					ps.setDate(4, null);
				}
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
	 * According the disc id, delete that disc from the data base
	 * @return int: 1 if the disc has been deleted, 0 if didn't work, -1 if error
	 */
	public int delete(){
		int result=-1;

		PreparedStatement ps=null;
		try {
			ps = ConnectionBD.getConnection().prepareStatement(SentenciasDisc.DELETE.getSQL());
			ps.setInt(1, this.getId());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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
	 * Search discs by author
	 * @param artist(Artist): disc author
	 * @return Set<Disc>: discs selected by author
	 */
	public static Set<Disc> SearchByAuthor(Artist artist){

		return Search(SentenciasDisc.SELECTBYAUTHOR, -1, artist, "", null);
	}

	/**
	 * Search discs by name
	 * @param name(String): disc name
	 * @return Set<Disc>: discs selected by name
	 */
	public static Set<Disc> SearchByName(String name){

		return Search(SentenciasDisc.SELECTBYNAME, -1, null, name, null);
	}

	/**
	 * Search discs depending the statement and parameters
	 * @param sent(SentenciasDisc): statement by want to search (Author or name)
	 * @param id(int): disc id
	 * @param artista(Artista): disc author
	 * @param argumentos(String): disc name
	 * @param date(LocalDate): disc date release
	 * @return Set<Disc>: discs selected based on the statement
	 */
	private static Set<Disc> Search(SentenciasDisc sent, int id, Artist artista, String argumentos, LocalDate date){
		Set<Disc> listadisc=new HashSet<Disc>();

		PreparedStatement ps=null;
		ResultSet rs=null;


			try {
				ps=ConnectionBD.getConnection().prepareStatement(sent.getSQL());
				if(sent==SentenciasDisc.SELECTBYAUTHOR){
					ps.setInt(1, artista.getId());
				}else if(sent==SentenciasDisc.SELECTBYNAME){
					ps.setString(1, argumentos+"%");
				}
				rs=ps.executeQuery();

				if(rs!=null){
					while(rs.next()){
						LocalDate fecha = null;
						if(rs.getDate("fecha_produccion")!=null){
							fecha=rs.getDate("fecha_produccion").toLocalDate();
						}
						Artist a=new ArtistDAO(rs.getInt("id_artista"));
						Disc d= new Disc(rs.getInt("id"), rs.getString("nombre"), a, rs.getString("foto"), fecha);
						listadisc.add(d);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return listadisc;
	}

	public static boolean ExistDisc(String n, int id_artist){
		boolean result=false;

		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasDisc.EXIST.getSQL());
			ps.setInt(1, id_artist);
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
