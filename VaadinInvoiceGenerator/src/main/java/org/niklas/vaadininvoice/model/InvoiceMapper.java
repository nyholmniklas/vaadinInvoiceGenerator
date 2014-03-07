package org.niklas.vaadininvoice.model;

import org.niklas.vaadininvoice.gui.VaadinInvoiceAppGui;

public interface InvoiceMapper {
	public Invoice getInvoiceFromForm(VaadinInvoiceAppGui gui);
}
