package org.niklas.vaadininvoice.controller;

import java.io.File;

import org.niklas.vaadininvoice.gui.VaadinInvoiceGui;
import org.niklas.vaadininvoice.invoice2pdf.Invoice2Pdf;
import org.niklas.vaadininvoice.invoice2pdf.Invoice2PdfBoxImpl;
import org.niklas.vaadininvoice.mapping.InvoiceMapper;
import org.niklas.vaadininvoice.mapping.InvoiceMapperImpl;
import org.niklas.vaadininvoice.model.Invoice;

public class VaadinInvoiceController {
	private Invoice2Pdf invoice2Pdf;
	private InvoiceMapper invoiceMapper;
	private String sessionId;
	
	public VaadinInvoiceController(){
		invoice2Pdf = new Invoice2PdfBoxImpl();
		invoiceMapper = new InvoiceMapperImpl();
	}

	public void createPdf(VaadinInvoiceGui vaadinInvoiceGui){
		invoice2Pdf.getPdfFromInvoice(invoiceMapper.getInvoiceFromForm(vaadinInvoiceGui), sessionId);
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
