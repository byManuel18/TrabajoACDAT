package enums;

public enum Escenas {
	INICIO("/views/Start"),
	REGISTRY("/views/Registry"),

	;
	private String url;

    Escenas(String fxmlFile) {
        this.url = fxmlFile;
    }

    public String getUrl() {
        return url;
    }

}
