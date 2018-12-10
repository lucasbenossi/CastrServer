package castrserver.rest.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
	private static Gson gson = null;
	
	public static Gson getInstance() {
		if(gson == null) {
			gson = new GsonBuilder().setPrettyPrinting().create();
		}
		return gson;
	}
}
