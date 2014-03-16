package org.niklas.vaadininvoice.invoice2pdf;

import java.io.File;

import org.niklas.vaadininvoice.model.Invoice;

public interface Invoice2Pdf {
	public File getPdfFromInvoice(Invoice invoice, File file);
}
