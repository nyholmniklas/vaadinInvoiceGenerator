package org.niklas.vaadininvoice.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class Invoice {
	private Address customer;
	private Address company;
	private int referenceNumber;
	private int invoiceNumber;
	private String description;
	private List<InvoiceRow> rows;
	private String dueDate;
	private DecimalFormat priceFormat;
	
	public Invoice() {
		priceFormat = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.US));
	}
	
	public Address getCustomer() {
		return customer;
	}
	public Address getCompany() {
		return company;
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

	public int getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(int referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
	

}
