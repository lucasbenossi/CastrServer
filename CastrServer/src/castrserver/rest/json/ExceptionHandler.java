package castrserver.rest.json;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.Response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ExceptionHandler {
	public static JsonElement toJson(Exception e) {
		JsonObject json = new JsonObject();
		
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		e.printStackTrace(pw);
		
		json.addProperty("exeption", e.toString());
		json.addProperty("stackTrace", writer.toString());
		
		return json;
	}

	public static Response toResponse(Exception e) {
		return Response.serverError().entity(ExceptionHandler.toJson(e)).build();
	}
}
