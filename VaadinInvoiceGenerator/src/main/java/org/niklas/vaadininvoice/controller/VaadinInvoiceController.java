package org.niklas.vaadininvoice.controller;

import java.io.File;
import org.niklas.vaadininvoice.invoice2pdf.Invoice2Pdf;
import org.niklas.vaadininvoice.model.Invoice;

public class VaadinInvoiceController {
	private Invoice2Pdf invoice2Pdf;
	private String sessionId;

	public void setInvoice2Pdf(Invoice2Pdf invoice2Pdf) {
		this.invoice2Pdf = invoice2Pdf;
	}

	public void createPdf(Invoice invoice){
		
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
