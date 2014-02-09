package org.niklas.vaadininvoice.invoice;

import java.util.List;

public class Invoice {
	private Address customer;
	private Address company;
	private int reference;
	private String description;
	private float totalsum;
	private List<InvoiceRow> rows;
	private String dueDate;
	
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
	public float getTotalsum() {
		return totalsum;
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
	public void setTotalsum(float totalsum) {
		this.totalsum = totalsum;
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
	
	

}
