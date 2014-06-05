package vo;

public class Book {
	private int ISBN;
	private String name;
	private int dailyProd;
	private String author;
	private double price;

	
	public Book(int iSBN, String name, int dailyProd, String author,
			double price) {
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
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
