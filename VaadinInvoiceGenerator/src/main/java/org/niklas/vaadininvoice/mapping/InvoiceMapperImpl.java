package org.niklas.vaadininvoice.mapping;

import java.util.ArrayList;

import org.niklas.vaadininvoice.gui.VaadinInvoiceGui;
import org.niklas.vaadininvoice.model.Address;
import org.niklas.vaadininvoice.model.Invoice;
import org.niklas.vaadininvoice.model.InvoiceRow;

public class InvoiceMapperImpl implements InvoiceMapper {
	
	public Invoice getInvoiceFromForm(VaadinInvoiceGui gui) {
		Invoice invoice = new Invoice();
		
		Address customer = new Address();
		customer.setName(gui.getCustomerName());
		customer.setAddress(gui.getCustomerStreet());
		customer.setCity(gui.getCustomerCity());
		customer.setPostcode(gui.getCustomerPostcode());
		invoice.setCustomer(customer);
		
		Address company = new Address();
		company.setName(gui.getCompanyName());
		company.setAddress(gui.getCompanyStreet());
		company.setCity(gui.getCompanyCity());
		company.setPostcode(gui.getCompanyPostcode());
		invoice.setCompany(company);
		
		invoice.setDueDate(gui.getDueDate());
		invoice.setInvoiceDate(gui.getInvoiceDate());
		invoice.setInvoiceNumber(gui.getInvoiceNumber());
		invoice.setReferenceNumber(gui.getReferenceNumber());
		
		invoice.setDescription(gui.getDescription());
		
		ArrayList<InvoiceRow> invoiceRows = new ArrayList<InvoiceRow>(gui.getInvoiceRows().values());
		invoice.setRows(invoiceRows);
		
		return invoice;
	}

}
