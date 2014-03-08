package org.niklas.vaadininvoice.mapping;

import org.niklas.vaadininvoice.gui.VaadinInvoiceGui;
import org.niklas.vaadininvoice.model.Invoice;

public interface InvoiceMapper {
	public Invoice getInvoiceFromForm(VaadinInvoiceGui gui);
}
