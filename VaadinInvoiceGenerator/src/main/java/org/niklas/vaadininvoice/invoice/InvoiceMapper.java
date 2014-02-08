package org.niklas.vaadininvoice.invoice;

import org.niklas.vaadininvoice.gui.VaadinInvoiceAppGui;

public interface InvoiceMapper {
	public Invoice getInvoiceFromForm(VaadinInvoiceAppGui gui);
}
