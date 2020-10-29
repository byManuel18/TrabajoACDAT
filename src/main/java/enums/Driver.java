package enums;

public enum Driver {
	H2("org.h2.Driver"),
	MySQL("com.mysql.cj.jdbc.Driver");

	private String driver="";

	private Driver(String driver) {
		this.driver=driver;
	}

	public String getDriver(){
		return driver;
	}

}
