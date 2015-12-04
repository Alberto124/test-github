package it.tecnosphera.dbms;

import java.util.List;

public class Book {
	private int id;				
	private String title;		
	private String author;		
	private Publisher publisher;
	private int edition;
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public int getEdition() {
		return edition;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}	
	
	
}
