package org.niklas.vaadininvoice.invoice;

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
		
		return invoice;
	}

}
