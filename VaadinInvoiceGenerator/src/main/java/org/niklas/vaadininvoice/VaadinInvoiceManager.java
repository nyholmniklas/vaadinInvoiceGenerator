package org.niklas.vaadininvoice;

import org.niklas.vaadininvoice.invoice.Invoice;
import org.niklas.vaadininvoice.invoice2pdf.Invoice2Pdf;
import org.niklas.vaadininvoice.invoice2pdf.Invoice2PdfBoxImpl;

public class VaadinInvoiceManager {
	private Invoice2Pdf invoice2Pdf;
	
	public VaadinInvoiceManager(){
		invoice2Pdf = new Invoice2PdfBoxImpl();
	}

	public void createPdfTest(Invoice invoice){
		invoice2Pdf.getPdfFromInvoice(invoice);
	}
}
