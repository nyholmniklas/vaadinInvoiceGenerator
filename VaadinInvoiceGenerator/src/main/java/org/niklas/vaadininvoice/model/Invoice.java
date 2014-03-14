package org.niklas.vaadininvoice.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	private String logoImageFilePath;
	
	public Invoice() {
		priceFormat = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.US));
		customer = new Address();
		company = new Address();
		referenceNumber = "";
		invoiceNumber = "";
		description = "Write description here";
		rows = new HashMap<Integer, InvoiceRow>();
		Calendar c = Calendar.getInstance();    
		c.setTime(new Date());
		invoiceDate = c.getTime();
		c.add(Calendar.DATE, 14);
		dueDate = c.getTime();
	}
	
	public BigDecimal getTotal() {
		BigDecimal total = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		for (InvoiceRow row:rows.values()) {
			total = total.add(row.getTotal());
		}
		return total;
	}
	
	public BigDecimal getSubTotal() {
		BigDecimal subTotal = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		for (InvoiceRow row:rows.values()) {
			subTotal = subTotal.add(row.getSubTotal());
		}
		return subTotal;
	}
	
	public BigDecimal getVatTotal() {
		BigDecimal vatTotal = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		for (InvoiceRow row:rows.values()) {
			vatTotal = vatTotal.add(row.getVatTotal());
		}
		return vatTotal;
	}
	
	public void removeRowById(Integer id) {
		rows.remove(id);
	}

	public void addRow(int id, InvoiceRow row) {
		rows.put(id, row);
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

	public String getLogoImageFilePath() {
		return logoImageFilePath;
	}

	public void setLogoImageFilePath(String logoImageFilePath) {
		this.logoImageFilePath = logoImageFilePath;
	}

}
