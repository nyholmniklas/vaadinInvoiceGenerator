package org.niklas.vaadininvoice.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class InvoiceRow {
	private Integer id;
	private Integer quantity;
	private String description;
	private BigDecimal price;
	private BigDecimal taxRate;
	private DecimalFormat priceFormat;
	
	public InvoiceRow(Integer id) {
		super();
		this.id = id;
		this.quantity = 1;
		this.description = "Product";
		this.price = new BigDecimal(1);
		this.taxRate = new BigDecimal(23);
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

	public BigDecimal getPrice() {
		return price.setScale(2, RoundingMode.HALF_EVEN);
	}
	
	public String getTotalToString(){
		return priceFormat.format(getTotal());
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(BigDecimal price) {
		this.price = price.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getTaxRate() {
		return taxRate.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getTotal() {
		return getPrice().multiply(new BigDecimal(getQuantity())).setScale(2, RoundingMode.HALF_EVEN);
	}
	
	public BigDecimal getSubTotal(){
		BigDecimal subTotal = getTotal().subtract(getTotal().multiply(getTaxRate().divide(new BigDecimal(100))));
		return subTotal.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getVatTotal() {
		return getTotal().subtract(getSubTotal()).setScale(2, RoundingMode.HALF_EVEN);
	}
}
