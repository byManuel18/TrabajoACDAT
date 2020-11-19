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


	/**
	 * Default constructor
	 */
	public PlayListDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Full constructor
	 * @param id(int): playlist id
	 * @param name(String): playlist name
	 * @param description(String): playlist description
	 * @param creator(User): playlist creator
	 */
	public PlayListDAO(int id, String name, String description, User creator) {
		super(id, name, description, creator);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor without id
	 * @param name(String): playlist name
	 * @param description(String): playlist description
	 * @param creator(User): playlist creator
	 */
	public PlayListDAO(String name, String descripcion, User creator) {
		super(name, descripcion, creator);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor that receives a playlist
	 * @param pl(PlayList)
	 */
	public PlayListDAO(Playlist pl){
		this.setCreator(pl.getCreator());
		this.setDescription(pl.getDescription());
		this.setId(pl.getId());
		this.setName(pl.getName());
	}

	/**
	 * Select a playlist form the database based on the parameter id
	 * @param id(int): playlist id
	 */
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

	/**
	 * According the playlist id, update or insert: if id is -1, insert playlist
	 * if id is greater than 0, update playlist
	 * @return int: 1 if has been updated or inserted, 0 if didn't work, -1 if error
	 */
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

	/**
	 * According the playlist id, delete that playlist from the database
	 * @return int: 1 if the playlist has been deleted, 0 if didn't work, -1 if error
	 */
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

	/**
	 * Search playlists by creator
	 * @param id(int): creator id
	 * @return Set<Playlist>: playlist selected by author
	 */
	public static Set<Playlist> SearchID_Creator(int id){
		return Search(SentenciasPlayList.SELECTFORCREATOR, "", id);
	}

	/**
	 * Search all playlist
	 * @return Set<Playlist>: all playlists
	 */
	public static Set<Playlist> SelectAll(){
		return Search(SentenciasPlayList.SELECTALL, "", -1);
	}

	/**
	 * Search playlists by a subscriber
	 * @param id(int): subscriber id
	 * @return Set<Playlist>: playlists selected by a subscriber
	 */
	public static Set<Playlist> SearchbyUserSubscriber(int id){
		return  Search(SentenciasPlayList.SELECTFORUSERSUBSCRIBER, "", id);
	}

	/**
	 * Search playlists where the user isn't the creator
	 * @param id(int): user id
	 * @return Set<playlist>: playlists selected where user isn't the creator
	 */
	public static Set<Playlist> SearchAllExceptUSer(int id){
		return  Search(SentenciasPlayList.SLECTALLEXCEPTUSER, "", id);
	}

	/**
	 * Search playlists by name where the user isn't the creator
	 * @param id(int): user id
	 * @param name(name): playlist name
	 * @return Set<Playlist>: playlists selected by name where user isn't the creator
	 */
	public static Set<Playlist> SearchAllExceptUSerAndNameList(int id,String name){
		return  Search(SentenciasPlayList.SLECTALLEXCEPTUSERANDNAME, name, id);
	}

	/**
	 * Search playlists by creator name where the user isn't the creator
	 * @param id(int): user id
	 * @param name(string): creator name
	 * @return Set<Playlist>: playlists selected by creator where user isn't the creator
	 */
	public static Set<Playlist> SearchAllExceptUSerAndNameCreator(int id,String name){
		return  Search(SentenciasPlayList.SLECTALLEXCEPTUSERANDNAMEFROMCREATOR, name, id);
	}


	/**
	 * Search playlists depending the statement and parameters
	 * @param sql(SentenciasPlayList): statement by want to search
	 * @param argument(String): parameter by want to search
	 * @param id(int): user id
	 * @return Set<Playlist>: playlists selected based on the statement
	 */
	private static Set<Playlist> Search(SentenciasPlayList sql,String argument, int id){
		Set<Playlist> playlist=new HashSet<Playlist>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=ConnectionBD.getConnection().prepareStatement(sql.getSQL());
			if(sql==SentenciasPlayList.SELECTFORCREATOR||sql==SentenciasPlayList.SLECTALLEXCEPTUSER){
				ps.setInt(1, id);
			}else if(sql==SentenciasPlayList.SELECTFORUSERSUBSCRIBER){
				ps.setInt(1, id);
			}else if(sql==SentenciasPlayList.SLECTALLEXCEPTUSERANDNAME||sql==SentenciasPlayList.SLECTALLEXCEPTUSERANDNAMEFROMCREATOR){
				ps.setInt(1, id);
				ps.setString(2, argument+"%");
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

	/**
	 * Add a song to a playlist
	 * @param id_song(int): song id
	 * @param id_list(int): playlist id
	 * @return int: 1 if has been added, 0 if didn't work, -1 if error
	 */
	public static int addSongToPlayList(int id_song,int id_list){
		int result=-1;
		PreparedStatement ps=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasPlayList.ADDSONGTOPLAYLIST.getSQL());
			ps.setInt(1, id_list);
			ps.setInt(2, id_song);
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
	 * Delete a song from a playlist
	 * @param id_song(int): song id
	 * @param id_list(int): playlist id
	 * @return int: 1 if the song has been deleted from the playlist, 0 if didn't work, -1 if error
	 */
	public static int DeleteSongToPlayList(int id_song,int id_list){
		int result=-1;
		PreparedStatement ps=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasPlayList.DELETESONGFROMPLAYLIST.getSQL());
			ps.setInt(1, id_list);
			ps.setInt(2, id_song);
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
	 * unsubscribe a user from a playlist
	 * @param id_user(int): user id
	 * @param id_lista(int): playlist id
	 * @return int: 1 if the user has been unsubscribed from the playlist, 0 if didn't work, -1 if error
	 */
	public static int Desuscribe(int id_user,int id_lista){
		int result=-1;
		PreparedStatement ps=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasPlayList.DESUSBSCRIBE.getSQL());
			ps.setInt(1, id_user);
			ps.setInt(2, id_lista);
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
	 * Subscribe a user into a playlist
	 * @param id_user(int): user id
	 * @param id_lista(int): playlist id
	 * @return int: 1 if the user has been subscribed into the playlist, 0 if didn't work, -1 if error
	 */
	public static int Subscribe(int id_user,int id_lista){
		int result=-1;
		PreparedStatement ps=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasPlayList.SUBSCRIBE.getSQL());
			ps.setInt(1, id_user);
			ps.setInt(2, id_lista);
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

	public static boolean ExistSameName(int id, String n){
		boolean result=false;
		PreparedStatement ps = null;
		ResultSet rs=null;

		try {
			ps=ConnectionBD.getConnection().prepareStatement(SentenciasPlayList.EXIST.getSQL());
			ps.setInt(1, id);
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
