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

	/**
	 * Default constructor
	 */
	public GenreDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * full constructor
	 * @param id(int): genre id
	 * @param name(String): genre name
	 */
	public GenreDAO(int id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor without id
	 * @param name(String): genre name
	 */
	public GenreDAO(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor that receives a genre
	 * @param g(Genre)
	 */
	public GenreDAO(Genre g){
		this.setId(g.getId());
		this.setName(g.getName());
	}

	/**
	 * Select a genre from the database based on the parameter id
	 * @param id(int): genre int
	 */
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

	/**
	 * According the genre id, update or insert: if id is -1, insert genre
	 * if id is greater than 0, update genre
	 * @return int: 1 if has been updated or inserted, 0 if didn't work, -1 if error
	 */
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

	/**
	 * According the genre id, delete that artist from the data base
	 * @return int: 1 if the genre has been deleted, 0 if didn't work, -1 if error
	 */
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

	/**
	 * Search all genre
	 * @return Set<Genre>: all genres
	 */
	public static Set<Genre> SelectALL(){
		return Search(SentenciasGenre.SELECTALL, -1, "");
	}

	/**
	 * Search genres depending the statement and the parameter
	 * @param sql(SentenciasGenre): statement by want to search (name)
	 * @param id(int): genre id
	 * @param argument(String): genre name
	 * @return
	 */
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

	/**
	 * Check if a genre already exists on the database
	 * @param name(String): genre name
	 * @return true if exist
	 */
	public static boolean Exist(String  name){
		PreparedStatement ps=null;
		ResultSet rs=null;
		int result=-1;
		 try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasGenre.EXIST.getSQL());
			ps.setString(1, name);
			rs=ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					result=rs.getInt("id");
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

		 return result>0;
	}
}
