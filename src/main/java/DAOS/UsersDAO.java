package DAOS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import enums.SentenciasUsers;
import models.User;
import utilities.ConnectionBD;

public class UsersDAO extends User{

	/**
	 * Default constructor
	 */
	public UsersDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Full constructor
	 * @param id(int): user id
	 * @param mail(String): user mail
	 * @param name(String): user name
	 * @param photo(String): user photo
	 * @param active(Boolean): if user account is activated or not("deleted")
	 */
	public UsersDAO(int id, String mail, String name, String photo,boolean active) {
		super(id, mail, name, photo,active);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor without id
	 * @param mail(String): user mail
	 * @param name(String): user name
	 * @param photo(String): user photo
	 * @param active(Boolean): if user account is activated or not("deleted")
	 */
	public UsersDAO(String mail, String name, String photo,boolean active) {
		super(mail, name, photo,active);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor that receives a user
	 * @param u(User)
	 */
	public UsersDAO(User u){
		this.setId(u.getId());
		this.setMail(u.getMail());
		this.setName(u.getName());
		this.setPhoto(u.getPhoto());
		this.setActive(u.isActive());
		//this.setSubscriptions(u.getSubscriptions());
	}

	/**
	 * Select a user form the database based on the parameter id
	 * @param id(int): user id
	 */
	public UsersDAO(int id){
		super();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasUsers.SELECTBYID.getSQL());
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					this.setId(id);
					this.setMail(rs.getString("correo"));
					this.setName(rs.getString("nombre"));
					this.setPhoto(rs.getString("foto"));
					this.setActive(rs.getBoolean("activo"));
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
	 * Select a user form the database based on the parameter mail
	 * @param email(String): user mail
	 */
	public UsersDAO(String email){
		super();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasUsers.SELECTBYEMAIL.getSQL());
			ps.setString(1, email);
			rs=ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					this.setId(rs.getInt("id"));
					this.setMail(email);
					this.setName(rs.getString("nombre"));
					this.setPhoto(rs.getString("foto"));
					this.setActive(rs.getBoolean("activo"));
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
	 * According the user id, update or insert: if id is -1, insert user
	 * if id is greater than 0, update user
	 * @return int: 1 if has been updated or inserted, 0 if didn't work, -1 if error
	 */
	public int update(){
		int result=0;
		PreparedStatement ps=null;

		if(this.getId()>0){
			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasUsers.UPDATEUSER.getSQL());
				ps.setString(1, this.getName());
				ps.setString(2, this.getMail());
				ps.setString(3, this.getPhoto());
				ps.setBoolean(4, this.isActive());
				ps.setInt(5,this.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasUsers.INSERTUSERS.getSQL());
				ps.setString(1, this.getName());
				ps.setString(2, this.getMail());
				ps.setString(3, this.getPhoto());
				ps.setBoolean(4, this.isActive());
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
	 * According the user id, deactivate that user (cant use that user)
	 * @return int: 1 if the artist has been deactivated, 0 if didn't work, -1 if error
	 */
	public int defuse(){
		int resultado=0;
		PreparedStatement ps=null;
		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasUsers.DEFUSE.getSQL());
			ps.setInt(1, this.getId());
			resultado=ps.executeUpdate();
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

		return resultado;
	}

	/**
	 * Show subscribers from a playlist
	 * @param idlista(int): playlist id
	 * @return Set<User>: subscribers from the playlist
	 */
	public static Set<User> SelectSubscribers(int idlista){
		return Search(SentenciasUsers.SUBSCRIBERS, idlista, "");
	}

	/**
	 * Search users depending the statement and parameters
	 * @param sql(SentenciasUsers): statement by want to search
	 * @param n(int): playlist id
	 * @param argument(String): parameters
	 * @return Set<User>: users selected based on the statement
	 */
	private static Set<User> Search(SentenciasUsers sql,int n,String argument){
		Set<User> userlist=new HashSet<User>();
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(sql.getSQL());
			if(sql==SentenciasUsers.SUBSCRIBERS){
				ps.setInt(1, n);
			}
			rs=ps.executeQuery();

			if(rs!=null){
				while(rs.next()){
					User toadd=new User(rs.getInt("id"), rs.getString("correo"), rs.getString("nombre"), rs.getString("foto"),rs.getBoolean("activo"));
					userlist.add(toadd);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return userlist;
	}



}
