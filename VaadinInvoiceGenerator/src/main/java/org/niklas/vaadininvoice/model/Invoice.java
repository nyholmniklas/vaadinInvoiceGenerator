package org.niklas.vaadininvoice.model;

import java.text.DecimalFormat;
import java.util.List;

public class Invoice {
	private Address customer;
	private Address company;
	private int reference;
	private String description;
	private List<InvoiceRow> rows;
	private String dueDate;
	private DecimalFormat priceFormat;
	
	public Invoice() {
		priceFormat = new DecimalFormat("###.##");
	}
	
	public Address getCustomer() {
		return customer;
	}
	public Address getCompany() {
		return company;
	}
	public int getReference() {
		return reference;
	}
	public String getDescription() {
		return description;
	}
	public Double getTotal() {
		Double total = new Double(0);
		for (InvoiceRow row:rows) {
			total += row.getTotal();
		}
		return total;
	}
	public List<InvoiceRow> getRows() {
		return rows;
	}
	public void setCustomer(Address customer) {
		this.customer = customer;
	}
	public void setCompany(Address company) {
		this.company = company;
	}
	public void setReference(int reference) {
		this.reference = reference;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public void setRows(List<InvoiceRow> rows) {
		this.rows = rows;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getTotalFormatted() {
		return priceFormat.format(getTotal());
	}
	
	

}
