package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtilities {

	private static final String URL_EXPRESION="(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)";
	private static final String MAIL_EXPRESION="^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'"
			+ "*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";

	public static boolean ValidateURL(String url){

		Pattern pat = Pattern.compile(URL_EXPRESION);
        Matcher mat = pat.matcher(url);

		return mat.matches();
	}
	public static boolean ValidateMAIL(String mail){
		Pattern pat = Pattern.compile(MAIL_EXPRESION);
        Matcher mat = pat.matcher(mail);

		return mat.matches();

	}

}
