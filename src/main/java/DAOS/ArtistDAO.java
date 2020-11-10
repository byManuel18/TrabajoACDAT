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

	public ArtistDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArtistDAO(int id, String name, String nationality, String photo) {
		super(id, name, nationality, photo);
		// TODO Auto-generated constructor stub
	}

	public ArtistDAO(String name, String nationality, String photo) {
		super(name, nationality, photo);
		// TODO Auto-generated constructor stub
	}

	public ArtistDAO(Artist a){
		this.setId(a.getId());
		this.setName(a.getName());
		this.setNationality(a.getNationality());
		this.setPhoto(a.getPhoto());
		//this.setDisclist(a.getDisclist());
		this.setSynchro(false);
	}

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
	public  static Set<Artist> SelectbyName(String name){
		return Search(SentenciasArtista.SELECTBYNAME, name);
	}
	public  static Set<Artist> SelectbyNacionalidad(String name){
		return Search(SentenciasArtista.SELECTBYNACIONALIDAD, name);
	}
	public  static Set<Artist> SelectAll(){
		return Search(SentenciasArtista.SELECTALL, "");
	}
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

}
