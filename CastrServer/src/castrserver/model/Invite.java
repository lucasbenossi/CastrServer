package castrserver.model;

public class Invite {
	private User user;
	private Game game;
	private boolean joined;
	
	public Invite(User user, Game game, boolean joined) {
		super();
		this.user = user;
		this.game = game;
		this.joined = joined;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	
}
