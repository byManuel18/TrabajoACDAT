package enums;

public enum SentenciasArtista {
	SELECTBYID("SELECT * FROM artista WHERE id=?"),
	INSERT("INSERT INTO artista(nombre,nacionalidad,foto) VALUES(?,?,?)"),
	UPDATE("UPDATE artista SET nombre=?,nacionalidad=?,foto=? WHERE id=?"),
	DELETE("DELETE FROM artista WHERE id=?"),
	SELECTALL("SELECT * FROM artista WHERE id>0"),
	;

	private String sql="";

	private SentenciasArtista(String sql){
		this.sql=sql;
	}

	public String getSQL(){
		return this.sql;
	}

}
