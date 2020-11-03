package DAOS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import enums.SentenciasPlayList;
import models.Playlist;
import models.User;
import utilities.ConnectionBD;

public class PlayListDAO extends Playlist{


	public PlayListDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayListDAO(int id, String name, String description, User creator) {
		super(id, name, description, creator);
		// TODO Auto-generated constructor stub
	}

	public PlayListDAO(String name, String descripcion, User creator) {
		super(name, descripcion, creator);
		// TODO Auto-generated constructor stub
	}

	public PlayListDAO(Playlist pl){
		this.setCreator(pl.getCreator());
		this.setDescription(pl.getDescription());
		this.setId(pl.getId());
		this.setName(pl.getName());
	}

	public PlayListDAO(int id){
		super();
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasPlayList.SELECTBYID.getSQL());
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					this.setId(rs.getInt("id"));
					this.setDescription(rs.getString("descripcion"));
					this.setName(rs.getString("nombre"));
					User creator=new UsersDAO(rs.getInt("id_usuario"));
					this.setCreator(creator);
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
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasPlayList.UPDATE.getSQL());
				ps.setString(1, this.getName());
				ps.setString(2, this.getDescription());
				ps.setInt(3, this.getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				ps=ConnectionBD.getConnection().prepareStatement(SentenciasPlayList.INSERT.getSQL());
				ps.setString(1, this.getName());
				ps.setString(2, this.getDescription());
				ps.setInt(3, this.getCreator().getId());
				result=ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public int delete(){
		int result=-1;
		PreparedStatement ps=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasPlayList.DELETE.getSQL());
			ps.setInt(1,this.getId());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static Set<Playlist> SearchID_Creator(int id){
		return Search(SentenciasPlayList.SELECTFORCREATOR, "", id);
	}
	public static Set<Playlist> SelectAll(){
		return Search(SentenciasPlayList.SELECTALL, "", -1);
	}
	public static Set<Playlist> SearchbyUserSubscriber(int id){
		return  Search(SentenciasPlayList.SELECTFORUSERSUBSCRIBER, "", id);
	}


	private static Set<Playlist> Search(SentenciasPlayList sql,String argument, int id){
		Set<Playlist> playlist=new HashSet<Playlist>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=ConnectionBD.getConnection().prepareStatement(sql.getSQL());
			if(sql==SentenciasPlayList.SELECTFORCREATOR){
				ps.setInt(1, id);
			}else if(sql==SentenciasPlayList.SELECTFORUSERSUBSCRIBER){
				ps.setInt(1, id);
			}
			rs=ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					User creator=new UsersDAO(rs.getInt("id_usuario"));
					Playlist add=new Playlist(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), creator);
					playlist.add(add);
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

		return playlist;
	}

}
