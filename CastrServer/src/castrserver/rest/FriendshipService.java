package castrserver.rest;

import java.io.IOException;
import java.sql.SQLException;

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
import castrserver.dao.FriendshipDAO;
import castrserver.model.User;
import castrserver.rest.json.ExceptionHandler;
import castrserver.rest.json.GsonUtils;

@Path("Friendship")
public class FriendshipService {
	
	@POST
	@Path("addFriend")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addFriend(@QueryParam("userId") int userId, @QueryParam("friendId") int friendId) {
		try (DAOFactory daoFact = new DAOFactory();) {
			FriendshipDAO dao = daoFact.createFriendshipDAO();
			dao.addFriend(userId, friendId);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok("ok").build();
	}
	
	@POST
	@Path("removeFriend")
	@Produces(MediaType.TEXT_PLAIN)
	public Response removeFriend(@QueryParam("userId") int userId, @QueryParam("friendId") int friendId) {
		try (DAOFactory daoFact = new DAOFactory();) {
			FriendshipDAO dao = daoFact.createFriendshipDAO();
			dao.removeFriend(userId, friendId);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok("ok").build();
	}
	
	@GET
	@Path("getFriends")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFriends(@QueryParam("userId") int userId) {
		JsonElement json = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			FriendshipDAO dao = daoFact.createFriendshipDAO();
			
			JsonArray array = new JsonArray();
			for(User user : dao.getFriends(userId)) {
				array.add(GsonUtils.getInstance().toJsonTree(user, User.class));
			}
			json = array;
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(json).build();
	}
	
	@GET
	@Path("checkFriendship")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkFriendship(@QueryParam("userId") int userId, @QueryParam("friendId") int friendId) {
		JsonElement json = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			FriendshipDAO dao = daoFact.createFriendshipDAO();
			
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("friends", dao.checkFriendship(userId, friendId));
			
			json = jsonObj;
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(json).build();
	}
}
