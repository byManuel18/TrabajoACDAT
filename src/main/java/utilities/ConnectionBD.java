package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import models.Channel;

public class ConnectionBD {
	private  static Channel c = XMLManager.GetChannel();
	private  static Connection conec = null;

	private static void Conect() {
		try {
			Class.forName(c.getDriver().getDriver());
			try {
				conec = DriverManager.getConnection(c.getTypebdd().getType() + "//" + c.getIp() + ":" + c.getPort()
						+ "/" + c.getNameBD() + c.getTimezone(), c.getUser(), c.getPassword());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnection(){
		if(conec==null){
			Conect();
		}
		return conec;
	}

	public static boolean CloseConnection(){
		boolean closed=false;
		if(conec!=null){
			try {
				conec.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return closed;
	}

}
