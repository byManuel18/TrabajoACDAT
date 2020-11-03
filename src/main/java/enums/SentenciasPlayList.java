package enums;

public enum SentenciasPlayList {
	SELECTBYID("SELECT * FROM lista WHERE id=? "),
	SELECTALL("SELECT * FROM lista"),
	SELECTFORCREATOR("SELECT * FROM lista WHERE id_usuario=?"),
	SELECTFORUSERSUBSCRIBER("SELECT list.id, list.nombre, list.id_usuario, list.descripcion FROM "
			+ "lista as list JOIN suscripcion as sub ON sub.id_lista=list.id JOIN usuario as user ON"
			+ " user.id=sub.id_usuario where user.id=?"),
	DELETE("DELETE  FROM lista WHERE id=?"),
	INSERT("INSERT INTO lista(nombre,descripcion,id_usuario) VALUES(?,?,?)"),
	UPDATE("UPDATE lista SET nombre=?,descripcion=?, WHERE id=?"),
	;

	private String sql = "";

	private SentenciasPlayList(String sql) {
		this.sql = sql;
	}

	public String getSQL() {
		return this.sql;
	}
}
