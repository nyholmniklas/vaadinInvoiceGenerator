package org.niklas.vaadininvoice;

import java.io.File;

import org.niklas.vaadininvoice.invoice.Invoice;
import org.niklas.vaadininvoice.invoice2pdf.Invoice2Pdf;
import org.niklas.vaadininvoice.invoice2pdf.Invoice2PdfBoxImpl;

public class VaadinInvoiceManager {
	private Invoice2Pdf invoice2Pdf;
	private String sessionId;
	
	public VaadinInvoiceManager(){
		invoice2Pdf = new Invoice2PdfBoxImpl();
	}

	public void createPdfTest(Invoice invoice){
		invoice2Pdf.getPdfFromInvoice(invoice, sessionId);
	}

	public void setSessionId(String id) {
		this.sessionId = id;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public File getPdfFile() {
		File file = new File("C:\\temp\\"+sessionId+".pdf");
		return file;
	}
}
