package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import models.Channel;

public class ConnectionBD {
	private  static Channel c = XMLManager.GetChannel();
	private  static Connection conec = null;

	/**
	 * Do the connection to the database
	 */
	private static void Conect() {
		try {
			Class.forName(c.getDriver().getDriver());
			try {
				conec = DriverManager.getConnection(c.getTypebdd().getType() + "//" + c.getIp() + ":" + c.getPort()
						+ "/" + c.getNameBD(),c.getUser(), c.getPassword());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * if the connection is null, call Conect();
	 * @return Connection
	 */
	public static Connection getConnection(){
		if(conec==null){
			Conect();
		}
		return conec;
	}

	/**
	 * if the connection isn't null, close it
	 * @return Boolean: if close the connection: true
	 */
	public static boolean CloseConnection(){
		boolean closed=false;
		if(conec!=null){
			try {
				conec.close();
				closed=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return closed;
	}

}
