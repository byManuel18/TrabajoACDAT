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

	public SongDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SongDAO(int id, String name, int duration, Genre genre, Disc disc) {
		super(id, name, duration, genre, disc);
		// TODO Auto-generated constructor stub
	}

	public SongDAO(String name, int duration, Genre genre, Disc disc) {
		super(name, duration, genre, disc);
		// TODO Auto-generated constructor stub
	}

	public SongDAO(Song s){
		this.setId(s.getId());
		this.setName(s.getName());
		this.setDuration(s.getDuration());
		this.setGenre(s.getGenre());
		this.setDisc(s.getDisc());
	}

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

	public static Set<Song> SelectAll(){
		return Search(SentenciasSong.SELECTALL,"",null,null,-1);
	}

	public static Set<Song> SelectForPlaylist(int id){
		return Search(SentenciasSong.SELECTFORPLAYLIST,"",null,null,id);
	}

	private static Set<Song> Search(SentenciasSong sql, String name, Disc disc, Genre genre, int n){
		Set<Song> listSong=new HashSet<Song>();

		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(sql.getSQL());
			if(sql==SentenciasSong.SELECTFORPLAYLIST){
				ps.setInt(1, n);
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
}
