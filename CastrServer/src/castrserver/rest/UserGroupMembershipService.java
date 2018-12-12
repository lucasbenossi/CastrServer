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
import com.google.gson.JsonPrimitive;

import castrserver.dao.DAOFactory;
import castrserver.dao.UserGroupMembershipDAO;
import castrserver.model.Group;
import castrserver.model.User;
import castrserver.rest.json.ExceptionHandler;
import castrserver.rest.json.GsonUtils;

@Path("UserGroupMembership")
public class UserGroupMembershipService {

	@POST
	@Path("insertUserGroup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertUserGroup(@QueryParam("groupId") int groupId, @QueryParam("userId") int userId) {
		try (DAOFactory daoFact = new DAOFactory();) {
			UserGroupMembershipDAO dao = daoFact.createUserGroupMembershipDAO();
			dao.insertUserGroup(groupId, userId);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(new JsonPrimitive("ok")).build();
	}
	
	@POST
	@Path("deleteUserGroup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUserGroup(@QueryParam("groupId") int groupId, @QueryParam("userId") int userId) {
		try (DAOFactory daoFact = new DAOFactory();) {
			UserGroupMembershipDAO dao = daoFact.createUserGroupMembershipDAO();
			dao.deleteUserGroup(groupId, userId);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(new JsonPrimitive("ok")).build();
	}
	
	@GET
	@Path("groupsUserIsIn")
	@Produces(MediaType.APPLICATION_JSON)
	public Response groupsUserIsIn(@QueryParam("userId") int userId) {
		JsonElement json = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			UserGroupMembershipDAO dao = daoFact.createUserGroupMembershipDAO();
			
			JsonArray array = new JsonArray();
			for(Group group : dao.groupsUserIsIn(userId)) {
				array.add(GsonUtils.getInstance().toJsonTree(group, Group.class));
			}
			json = array;
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(json).build();
	}
	
	@GET
	@Path("usersFromGroup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response usersFromGroup(@QueryParam("groupId") int groupId) {
		JsonElement json = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			UserGroupMembershipDAO dao = daoFact.createUserGroupMembershipDAO();
			
			JsonArray array = new JsonArray();
			for(User user : dao.usersFromGroup(groupId)) {
				array.add(GsonUtils.getInstance().toJsonTree(user, User.class));
			}
			json = array;
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(json).build();
	}
}
