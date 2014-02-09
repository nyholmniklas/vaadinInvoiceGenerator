package org.niklas.vaadininvoice.invoice2pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.niklas.vaadininvoice.invoice.Invoice;
import org.niklas.vaadininvoice.invoice.InvoiceRow;

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
		writeInvoiceRows(invoice, page, contentStream);
		contentStream.close();
		return page;
	}
	
	private PDPage writeCustomerAddress(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeBoldText(100, 720, "Billable", contentStream);
		writeText(100, 700, invoice.getCustomer().getName(), contentStream);
		writeText(100, 685, invoice.getCustomer().getAddress(), contentStream);
		writeText(100, 670, invoice.getCustomer().getPostcode(), contentStream);
		writeText(100, 655, invoice.getCustomer().getCity(), contentStream);
		return page;
	}
	
	private PDPage writeCompanyAddress(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeBoldText(250, 720, "Payable to", contentStream);
		writeText(250, 700, invoice.getCompany().getName(), contentStream);
		writeText(250, 685, invoice.getCompany().getAddress(), contentStream);
		writeText(250, 670, invoice.getCompany().getPostcode(), contentStream);
		writeText(250, 655, invoice.getCompany().getCity(), contentStream);
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
	
	private PDPage writeInvoiceRows(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeBoldText(100, 500, "Quantity", contentStream);
		writeBoldText(200, 500, "Description", contentStream);
		writeBoldText(300, 500, "Price", contentStream);
		writeBoldText(400, 500, "Total", contentStream);
		
		for (int i = 0; i < invoice.getRows().size() ; i++) {
			InvoiceRow row = invoice.getRows().get(i);
			int y = 460 - (14*i);
			writeText(100, y, Integer.toString(row.getQuantity()), contentStream);
			writeText(200, y, row.getDescription(), contentStream);
			writeText(300, y, row.getPrice().toString(), contentStream);
			writeText(400, y, row.getTotal().toString(), contentStream);
		}
		
		return page;
	}

}
