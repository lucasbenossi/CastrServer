package castrserver.test;

import castrserver.dao.DAOFactory;
import castrserver.dao.GameDAO;
import castrserver.model.Game;

public class Test {
	public static void main(String[] args) throws Exception {
		try (DAOFactory daoFact = new DAOFactory();){
			GameDAO dao = daoFact.createGameDAO();
			for(Game game : dao.getGamesFromOwner(23)) {
				System.out.println(game.getName());
			}
		}
	}
}
