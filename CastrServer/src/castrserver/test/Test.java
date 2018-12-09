package castrserver.test;

import java.sql.Date;

import castrserver.dao.DAOFactory;
import castrserver.dao.GameDAO;
import castrserver.model.Game;

public class Test {
	public static void main(String[] args) throws Exception {
		try (DAOFactory daoFact = new DAOFactory();){
			GameDAO dao = daoFact.createGameDAO();
			dao.create(new Game(0, "jogo", "local", Date.valueOf("2015-10-11"), 8));
			for(Game game : dao.all()) {
				System.out.println(game.getName());
			}
		}
	}
}
