package models;


import enums.Driver;
import enums.TypeBDD;

public class Channel {
	private TypeBDD typebdd;
	private Driver driver;
	private String nameBD = "";
	private String port = "";
	private String timezone = "";
	private String user = "";
	private String password = "";
	private String ip = "";


	public Channel() {
		this.typebdd=TypeBDD.MySQL;
		this.driver=Driver.MySQL;
		this.nameBD = "spotify2.0";
		this.port = "3306";
		this.timezone = "?useUnicode=true&amp;useJDBCCompliantTimezoneShift=true&amp;useLegacyDatetimeCode=false&amp;serverTimezone=UTC";
		this.user = "root";
		this.password = "";
		this.ip = "localhost";
	}


	public Channel(TypeBDD typebdd, Driver driver, String nameBD, String port, String timezone, String user,
			String password, String ip) {
		super();
		this.typebdd = typebdd;
		this.driver = driver;
		this.nameBD = nameBD;
		this.port = port;
		this.timezone = timezone;
		this.user = user;
		this.password = password;
		this.ip = ip;
	}


	public TypeBDD getTypebdd() {
		return typebdd;
	}


	public void setTypebdd(TypeBDD typebdd) {
		this.typebdd = typebdd;
	}


	public Driver getDriver() {
		return driver;
	}


	public void setDriver(Driver driver) {
		this.driver = driver;
	}


	public String getNameBD() {
		return nameBD;
	}


	public void setNameBD(String nameBD) {
		this.nameBD = nameBD;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	public String getTimezone() {
		return timezone;
	}


	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((nameBD == null) ? 0 : nameBD.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((timezone == null) ? 0 : timezone.hashCode());
		result = prime * result + ((typebdd == null) ? 0 : typebdd.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Channel other = (Channel) obj;
		if (driver != other.driver)
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (nameBD == null) {
			if (other.nameBD != null)
				return false;
		} else if (!nameBD.equals(other.nameBD))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (timezone == null) {
			if (other.timezone != null)
				return false;
		} else if (!timezone.equals(other.timezone))
			return false;
		if (typebdd != other.typebdd)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Channel [typebdd=" + typebdd + ", driver=" + driver + ", nameBD=" + nameBD + ", port=" + port
				+ ", timezone=" + timezone + ", user=" + user + ", password=" + password + ", ip=" + ip + "]";
	}







}
