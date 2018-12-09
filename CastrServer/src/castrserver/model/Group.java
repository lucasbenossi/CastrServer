package castrserver.model;

public class Group {
	private int id;
	private String name;
	private int creatorId;
	
	public Group(int id, String name, int creatorId) {
		this.id = id;
		this.name = name;
		this.creatorId = creatorId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCreatorId() {
		return this.creatorId;
	}
}
