package org.niklas.vaadininvoice.invoice;

import org.niklas.vaadininvoice.gui.VaadinInvoiceAppGui;

public class InvoiceMapperImpl implements InvoiceMapper {
	
	public Invoice getInvoiceFromForm(VaadinInvoiceAppGui gui) {
		Invoice invoice = new Invoice();
		System.out.println(gui.getNameTextFieldValue());
		invoice.setName(gui.getNameTextFieldValue());
		return invoice;
	}

}
