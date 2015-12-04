package it.tecnosphera.dbms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFileImpl implements ReadFile{

	public List<Book> readFile(String nomeFile) {
		
		List<Book> retval = new ArrayList<Book>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(nomeFile));
			
			String data;
			while ((data = in.readLine()) != null) {
				Book book= dataSplit(data);
				//nBook++;
				retval.add(book);
			}
			
			
			
		} catch (FileNotFoundException err) {
		
		} catch (IOException err) {
			
		} finally {
			try {
				in.close();
			} catch (IOException ignora) {}
		}
		return retval;
	}

	public Book dataSplit(String data){
		
		String dataSplit[]=data.split(";");
		Publisher publisher=new Publisher();
		Book book=new Book();
		
		book.setTitle(dataSplit[0].trim());
		book.setAuthor(dataSplit[1].trim());
		publisher.setNome(dataSplit[2].trim());
		book.setPublisher(publisher);
		book.setEdition(Integer.parseInt(dataSplit[3].trim()));
		
		return book;
		
	}
	
	}
