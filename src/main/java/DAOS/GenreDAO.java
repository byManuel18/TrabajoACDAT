package DAOS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import enums.SentenciasGenre;
import models.Genre;
import utilities.ConnectionBD;

public class GenreDAO extends Genre{

	public GenreDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GenreDAO(int id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}

	public GenreDAO(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public GenreDAO(Genre g){
		this.setId(g.getId());
		this.setName(g.getName());
	}

	public GenreDAO(int id){
		super();
		PreparedStatement ps=null;
		ResultSet rs=null;
		 try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasGenre.SELECTBYID.getSQL());
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					this.setId(id);
					this.setName(rs.getString("nombre"));
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
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasGenre.UPDATE.getSQL());
				ps.setString(1, this.getName());
				ps.setInt(2, this.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasGenre.INSERT.getSQL());
				ps.setString(1, this.getName());
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
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasGenre.DELETE.getSQL());
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

	public static Set<Genre> SelectALL(){
		return Search(SentenciasGenre.SELECTALL, -1, "");
	}

	private static Set<Genre> Search(SentenciasGenre sql,int id,String argument){
		Set<Genre> listGenre=new HashSet<Genre>();
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(sql.getSQL());

			rs=ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					Genre n=new Genre(rs.getInt("id"), rs.getString("nombre"));
					listGenre.add(n);
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


		return listGenre;
	}
}
