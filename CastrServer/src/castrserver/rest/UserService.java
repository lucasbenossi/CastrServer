package castrserver.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import castrserver.dao.DAOFactory;
import castrserver.dao.UserDAO;
import castrserver.model.User;
import castrserver.rest.json.ExceptionHandler;
import castrserver.rest.json.GsonUtils;

@Path("/User")
public class UserService {
	
	@POST
	@Path("/create")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(JsonElement json) {
		User user = GsonUtils.getInstance().fromJson(json, User.class);
		
		try (DAOFactory daoFact = new DAOFactory();) {
			UserDAO dao = daoFact.createUserDAO();
			dao.create(user);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok("ok").build();
	}
	
	@GET
	@Path("/read")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@QueryParam("id") int id) {
		User user = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			UserDAO dao = daoFact.createUserDAO();
			user = dao.read(id);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(GsonUtils.getInstance().toJsonTree(user, User.class)).build();
	}
	
	@POST
	@Path("/update")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response update(JsonElement json) {
		User user = GsonUtils.getInstance().fromJson(json, User.class);
		
		try (DAOFactory daoFact = new DAOFactory();) {
			UserDAO dao = daoFact.createUserDAO();
			dao.update(user);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok("ok").build(); 
	}
	
	@POST
	@Path("/delete")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@QueryParam("id") int id) {
		try (DAOFactory daoFact = new DAOFactory();) {
			UserDAO dao = daoFact.createUserDAO();
			dao.delete(id);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok("ok").build(); 
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response all() {
		JsonElement json = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			UserDAO dao = daoFact.createUserDAO();
			
			JsonArray array = new JsonArray();
			for(User user : dao.all()) {
				array.add(GsonUtils.getInstance().toJsonTree(user, User.class));
			}
			json = array;
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(json).build();
	}
	
	@POST
	@Path("authenticate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response authenticate(JsonElement json) {
		JsonElement response = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			UserDAO dao = daoFact.createUserDAO();
			
			JsonObject jsonObj = json.getAsJsonObject();
			String login = jsonObj.get("login").getAsString();
			String password = jsonObj.get("password").getAsString();
			
			JsonObject auth = new JsonObject();
			auth.addProperty("authenticate", dao.authenticate(login, password));
			
			response = auth;
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(response).build();
	}
}
