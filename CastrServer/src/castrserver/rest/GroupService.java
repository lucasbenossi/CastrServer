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
import com.google.gson.JsonPrimitive;

import castrserver.dao.DAOFactory;
import castrserver.dao.GroupDAO;
import castrserver.model.Group;
import castrserver.model.User;
import castrserver.rest.json.ExceptionHandler;
import castrserver.rest.json.GsonUtils;

@Path("/Group")
public class GroupService {
	
	@POST
	@Path("/create")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(JsonElement json) {
		Group group = GsonUtils.getInstance().fromJson(json, Group.class);
		
		try (DAOFactory daoFact = new DAOFactory();) {
			GroupDAO dao = daoFact.createGroupDAO();
			dao.create(group);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(new JsonPrimitive("ok")).build();
	}
	
	@GET
	@Path("/read")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@QueryParam("id") int id) {
		Group group = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			GroupDAO dao = daoFact.createGroupDAO();
			group = dao.read(id);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(GsonUtils.getInstance().toJsonTree(group, Group.class)).build();
	}
	
	@POST
	@Path("/update")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(JsonElement json) {
		Group group = GsonUtils.getInstance().fromJson(json, Group.class);
		
		try (DAOFactory daoFact = new DAOFactory();) {
			GroupDAO dao = daoFact.createGroupDAO();
			dao.update(group);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(new JsonPrimitive("ok")).build(); 
	}
	
	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam("id") int id) {
		try (DAOFactory daoFact = new DAOFactory();) {
			GroupDAO dao = daoFact.createGroupDAO();
			dao.delete(id);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(new JsonPrimitive("ok")).build(); 
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response all() {
		JsonElement json = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			GroupDAO dao = daoFact.createGroupDAO();
			
			JsonArray jarray = new JsonArray();
			for(Group group : dao.all()) {
				jarray.add(GsonUtils.getInstance().toJsonTree(group));
			}
			
			json = jarray;
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(json).build();
	}
	
	@GET
	@Path("getOwnerFromGroup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOwnerFromGroup(@QueryParam("id") int id) {
		User user = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			GroupDAO dao = daoFact.createGroupDAO();
			user = dao.getOwnerFromGroup(id);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(GsonUtils.getInstance().toJsonTree(user, User.class)).build();
	}
	
	@GET
	@Path("getGroupsFromOwner")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroupsFromOwner(@QueryParam("id") int id) {
		JsonElement json = null;
		
		try (DAOFactory daoFact = new DAOFactory();) {
			GroupDAO dao = daoFact.createGroupDAO();
			
			JsonArray jarray = new JsonArray();
			for(Group group : dao.getGroupsFromOwner(id)) {
				jarray.add(GsonUtils.getInstance().toJsonTree(group));
			}
			
			json = jarray;
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			return ExceptionHandler.toResponse(e);
		}
		
		return Response.ok(json).build();
	}
}
