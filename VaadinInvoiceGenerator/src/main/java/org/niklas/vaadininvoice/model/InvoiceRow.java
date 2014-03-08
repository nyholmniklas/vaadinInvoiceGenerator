package org.niklas.vaadininvoice.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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
		priceFormat = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.US));
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
		return Double.parseDouble(priceFormat.format(price));
	}
	
	public String getTotalFormatted(){
		return priceFormat.format(getTotal());
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
		return ((double) quantity) * price;
	}
	
	public Double getSubTotal(){
		double total = getTotal();
		double subTotal = total - (total*(taxRate/100));
		return Double.parseDouble(priceFormat.format(subTotal));
	}
}
