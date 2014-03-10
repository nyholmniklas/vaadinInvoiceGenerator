package org.niklas.vaadininvoice.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Invoice {
	private Address customer;
	private Address company;
	private String referenceNumber;
	private String invoiceNumber;
	private String description;
	private HashMap<Integer, InvoiceRow> rows;
	private Date dueDate;
	private Date invoiceDate;
	private DecimalFormat priceFormat;
	
	public Invoice() {
		priceFormat = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.US));
		customer = new Address();
		company = new Address();
		referenceNumber = "00000";
		invoiceNumber = "00000";
		description = "Write description here";
		rows = new HashMap<Integer, InvoiceRow>();
		Calendar c = Calendar.getInstance();    
		c.setTime(new Date());
		invoiceDate = c.getTime();
		c.add(Calendar.DATE, 14);
		dueDate = c.getTime();
	}
	
	public Double getTotal() {
		Double total = new Double(0);
		for (InvoiceRow row:rows.values()) {
			total += row.getTotal();
		}
		return total;
	}
	
	public String getTotalToString() {
		return priceFormat.format(getTotal());
	}
	
	public String getSubTotalToString() {
		Double total = (double) 0;
		for (InvoiceRow row:rows.values()) {
			total += row.getSubTotal();
		}
		return new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.US)).format(total);
	}

	public String getVatTotalToString() {
		Double total = (double) 0;
		for (InvoiceRow row:rows.values()) {
			total += row.getVatTotal();
		}
		return new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.US)).format(total);
	}

	public Address getCustomer() {
		return customer;
	}

	public void setCustomer(Address customer) {
		this.customer = customer;
	}

	public Address getCompany() {
		return company;
	}

	public void setCompany(Address company) {
		this.company = company;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HashMap<Integer, InvoiceRow> getRows() {
		return rows;
	}

	public void setRows(HashMap<Integer, InvoiceRow> rows) {
		this.rows = rows;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void removeRowById(Integer id) {
		rows.remove(id);
	}

	public void addRow(int id, InvoiceRow row) {
		rows.put(id, row);
	}

}
