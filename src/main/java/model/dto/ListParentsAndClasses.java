package model.dto;

import java.util.List;

public class ListParentsAndClasses {
	private int nextId;
	private List<NameAndIdResponse> parents;
	private List<NameAndIdResponse> classes;

	public ListParentsAndClasses(int nextId, List<NameAndIdResponse> parents, List<NameAndIdResponse> classes) {
		super();
		this.nextId = nextId;
		this.parents = parents;
		this.classes = classes;
	}
	
	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}
	
	public ListParentsAndClasses() {
		super();
	}

	public List<NameAndIdResponse> getParents() {
		return parents;
	}

	public void setParents(List<NameAndIdResponse> parents) {
		this.parents = parents;
	}

	public List<NameAndIdResponse> getClasses() {
		return classes;
	}

	public void setClasses(List<NameAndIdResponse> classes) {
		this.classes = classes;
	}

}