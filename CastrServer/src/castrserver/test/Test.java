package castrserver.test;

import java.sql.Date;

import castrserver.dao.DAOFactory;
import castrserver.dao.GameDAO;
import castrserver.dao.UserGroupMembershipDAO;
import castrserver.model.Game;

public class Test {
	public static void main(String[] args) throws Exception {
		try (DAOFactory daoFact = new DAOFactory();){
			UserGroupMembershipDAO dao = daoFact.createUserGroupMembershipDAO();
			dao.deleteUserGroup(21, 21);
		}
	}
}
