package org.niklas.vaadininvoice.invoice;

import java.text.DecimalFormat;

public class InvoiceRow {
	private Integer id;
	private Integer quantity;
	private String description;
	private Double price;
	private Double taxRate;
	private DecimalFormat priceFormat;
	
	public InvoiceRow(Integer id, Integer quantity, String description, Double price, Double taxRate) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.description = description;
		this.price = price;
		this.taxRate = taxRate;
		priceFormat = new DecimalFormat("###.##");
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
	
	public String getTotalFormatted(){
		double total =  quantity * (price + ( price * (taxRate/100) ) );
		return priceFormat.format(total);
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

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Double getTotal() {
		return quantity * (price + ( price * (taxRate/100) ) );
	}
}
