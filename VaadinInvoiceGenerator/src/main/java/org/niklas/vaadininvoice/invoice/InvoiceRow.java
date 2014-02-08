package org.niklas.vaadininvoice.invoice;

public class InvoiceRow {
	private Integer id;
	private Integer quantity;
	private String description;
	private Double price;
	
	public InvoiceRow(Integer id, Integer quantity, String description, Double price) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.description = description;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}
	
	public Double getTotal(){
		return  quantity * price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
