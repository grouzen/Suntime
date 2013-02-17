package ua.com.suntime;

import java.io.Serializable;

public class SuntimeSightCategory implements Serializable {
	
    private static final long serialVersionUID = 2038467079181831218L;
    
    private int id;
	private String title;
	
	public SuntimeSightCategory(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
}
