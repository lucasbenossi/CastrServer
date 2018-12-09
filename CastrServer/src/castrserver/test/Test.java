package castrserver.test;

import castrserver.dao.DAOFactory;
import castrserver.dao.UserGroupMembershipDAO;
import castrserver.model.User;

public class Test {
	public static void main(String[] args) throws Exception {
		try (DAOFactory daoFact = new DAOFactory();){
			UserGroupMembershipDAO dao = daoFact.createUserGroupMembershipDAO();
			for(User user : dao.usersFromGroup(21)) {
				System.out.println(user.getName());
			}
		}
	}
}
