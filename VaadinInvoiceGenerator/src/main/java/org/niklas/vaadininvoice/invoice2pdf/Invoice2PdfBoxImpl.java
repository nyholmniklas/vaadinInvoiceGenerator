package org.niklas.vaadininvoice.invoice2pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.niklas.vaadininvoice.invoice.Invoice;

public class Invoice2PdfBoxImpl implements Invoice2Pdf{
	private final PDType1Font normalFont = PDType1Font.HELVETICA;
	private final PDType1Font boldFont = PDType1Font.HELVETICA_BOLD;

	public File getPdfFromInvoice(Invoice invoice) {
		PDDocument doc = null;
		try {
			File file = new File("C:\\temp\\firstout.pdf");
			doc = new PDDocument();
			doc.addPage(createPage(doc, invoice));
			doc.save(file);
			doc.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (COSVisitorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private PDPage createPage(PDDocument doc, Invoice invoice) throws IOException {
		PDPage page = new PDPage();
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);
		writeCustomerAddress(invoice, page, contentStream);
		writeCompanyAddress(invoice, page, contentStream);
		writeDueDate(invoice, page, contentStream);
		contentStream.close();
		return page;
	}
	
	private PDPage writeCustomerAddress(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeBoldText(50, 720, "Billable", contentStream);
		writeText(50, 700, invoice.getCustomer().getName(), contentStream);
		writeText(50, 685, invoice.getCustomer().getAddress(), contentStream);
		writeText(50, 670, invoice.getCustomer().getPostcode(), contentStream);
		writeText(50, 655, invoice.getCustomer().getCity(), contentStream);
		return page;
	}
	
	private PDPage writeCompanyAddress(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeBoldText(200, 720, "Payable to", contentStream);
		writeText(200, 700, invoice.getCompany().getName(), contentStream);
		writeText(200, 685, invoice.getCompany().getAddress(), contentStream);
		writeText(200, 670, invoice.getCompany().getPostcode(), contentStream);
		writeText(200, 655, invoice.getCompany().getCity(), contentStream);
		return page;
	}
	
	private void writeText(int x, int y, String value, PDPageContentStream contentStream) throws IOException {		
		writeTextWithFont(x, y, value, contentStream, normalFont);
	}
	
	private void writeBoldText(int x, int y, String value, PDPageContentStream contentStream) throws IOException {		
		writeTextWithFont(x, y, value, contentStream, boldFont);
	}
	
	private void writeTextWithFont(int x, int y, String value, PDPageContentStream contentStream, PDType1Font font) throws IOException {
		contentStream.moveTo(0, 0);
		contentStream.beginText();
		contentStream.moveTextPositionByAmount(x, y);
		contentStream.setFont(font, 12);
		contentStream.drawString(value);
		contentStream.endText();
	}
	
	
	private PDPage writeDueDate(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeBoldText(400, 720, "Due Date", contentStream);
		writeText(400, 700, invoice.getDueDate(), contentStream);
		return page;
	}

}
