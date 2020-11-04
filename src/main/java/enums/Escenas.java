package enums;

public enum Escenas {
	INICIO("/views/Start"),

	;
	private String url;

    Escenas(String fxmlFile) {
        this.url = fxmlFile;
    }

    public String getUrl() {
        return url;
    }

}
