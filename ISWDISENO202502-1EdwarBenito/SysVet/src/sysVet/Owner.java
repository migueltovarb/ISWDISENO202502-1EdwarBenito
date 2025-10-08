package sysVet;

public class Owner {
	private String completeName;
	private String id;
	private String contact;
	
	public Owner(String completeName, String id, String contact) {
		super();
		this.completeName = completeName;
		this.id = id;
		this.contact = contact;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "Owner [completeName=" + completeName + ", id=" + id + ", contact=" + contact + "]";
	}
	
}
