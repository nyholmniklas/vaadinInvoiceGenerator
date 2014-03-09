package org.niklas.vaadininvoice.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Invoice {
	private Address customer;
	private Address company;
	private String referenceNumber;
	private String invoiceNumber;
	private String description;
	private List<InvoiceRow> rows;
	private Date dueDate;
	private Date invoiceDate;
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
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getTotalFormatted() {
		return priceFormat.format(getTotal());
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
	public void setInvoiceDate(Date date) {
		this.invoiceDate = date;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

}
