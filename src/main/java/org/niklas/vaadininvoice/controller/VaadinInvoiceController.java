package org.niklas.vaadininvoice.controller;

import java.io.File;
import org.niklas.vaadininvoice.invoice2pdf.Invoice2Pdf;
import org.niklas.vaadininvoice.model.Invoice;

public class VaadinInvoiceController {
	private Invoice2Pdf invoice2Pdf;
	private String sessionId;
	private File file;
	public static final String OPENSHIFT_PATH = System
			.getenv("OPENSHIFT_DATA_DIR");
	public static final String LOCAL_PATH = "C:\\temp\\";
	private String folderPath;

	public VaadinInvoiceController() {
		if (OPENSHIFT_PATH == null || OPENSHIFT_PATH.isEmpty()) {
			System.out.println("Using local file path");
			folderPath = LOCAL_PATH;
		}
		else {
			System.out.println("Using openshift file path");
			folderPath = OPENSHIFT_PATH;
		}
		if (!new File(folderPath).exists())
			new File(folderPath).mkdir();
	}

	public void setInvoice2Pdf(Invoice2Pdf invoice2Pdf) {
		this.invoice2Pdf = invoice2Pdf;
	}

	public void createPdf(Invoice invoice){
		if (!new File(folderPath).exists()) System.out.println("ahdfgosfhguioösdf");
		String filePath = folderPath+"/"+sessionId+".pdf";
		file = new File(filePath);
		invoice2Pdf.getPdfFromInvoice(invoice, file);
	}

	public void setSessionId(String id) {
		this.sessionId = id;
	}
	
	public String getFolderPath(){
		return folderPath;
	}

	public String getSessionId() {
		return sessionId;
	}

	public File getPdfFile() {
		return file;
	}
}
