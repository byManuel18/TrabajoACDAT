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

	public UsersDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsersDAO(int id, String mail, String name, String photo,boolean active) {
		super(id, mail, name, photo,active);
		// TODO Auto-generated constructor stub
	}

	public UsersDAO(String mail, String name, String photo,boolean active) {
		super(mail, name, photo,active);
		// TODO Auto-generated constructor stub
	}

	public UsersDAO(User u){
		this.setId(u.getId());
		this.setMail(u.getMail());
		this.setName(u.getName());
		this.setPhoto(u.getPhoto());
		this.setActive(u.isActive());
		//this.setSubscriptions(u.getSubscriptions());
	}

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

	public static Set<User> SelectSubscribers(int idlista){
		return Search(SentenciasUsers.SUBSCRIBERS, idlista, "");
	}

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
