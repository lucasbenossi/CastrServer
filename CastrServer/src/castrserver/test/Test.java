package castrserver.test;

import java.sql.Date;

import castrserver.dao.DAOFactory;
import castrserver.dao.UserDAO;
import castrserver.model.User;

public class Test {
	public static void main(String[] args) throws Exception {
		try (DAOFactory daoFact = new DAOFactory();){
			UserDAO dao = daoFact.createUserDAO();
			dao.create(new User(0, "lucasbenossi2", "123", "lucas m. benossi", Date.valueOf("1997-06-25")));
		}
	}
}
