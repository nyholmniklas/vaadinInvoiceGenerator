package org.niklas.vaadininvoice.invoice;

import java.util.ArrayList;

import org.niklas.vaadininvoice.gui.VaadinInvoiceAppGui;

public class InvoiceMapperImpl implements InvoiceMapper {
	
	public Invoice getInvoiceFromForm(VaadinInvoiceAppGui gui) {
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
		
		ArrayList<InvoiceRow> invoiceRows = new ArrayList<InvoiceRow>(gui.getInvoiceRows().values());
		invoice.setRows(invoiceRows);
		
		return invoice;
	}

}
