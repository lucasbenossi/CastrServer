package castrserver.test;

import castrserver.dao.DAOFactory;
import castrserver.dao.GroupDAO;
import castrserver.model.Group;

public class Test {
	public static void main(String[] args) throws Exception {
		try (DAOFactory daoFact = new DAOFactory();){
			GroupDAO dao = daoFact.createGroupDAO();
			for(Group group : dao.getGroupsFromOwner(20)) {
				System.out.println(group.getName());
			}
		}
	}
}
