package vo;

import java.io.Serializable;

public class Book implements Serializable {
	
	private int ISBN;
	private String name;
	private int dailyProd;
	private int qtd;
	private String author;
	private float price;

	public Book(){
		
	}
	
	public Book(int iSBN, String name, int dailyProd, String author,
			float price) {
		super();
		ISBN = iSBN;
		this.name = name;
		this.dailyProd = dailyProd;
		this.author = author;
		this.price = price;
	}

	public int getISBN() {
		return ISBN;
	}

	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDailyProd() {
		return dailyProd;
	}

	public void setDailyProd(int dailyProd) {
		this.dailyProd = dailyProd;
		this.setQtd(dailyProd);
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
