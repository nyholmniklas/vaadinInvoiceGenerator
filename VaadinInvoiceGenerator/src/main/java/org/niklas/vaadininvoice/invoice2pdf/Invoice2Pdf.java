package org.niklas.vaadininvoice.invoice2pdf;

import java.io.File;

import org.niklas.vaadininvoice.invoice.Invoice;

public interface Invoice2Pdf {
	public File getPdfFromInvoice(Invoice invoice, String sessionId);
}
