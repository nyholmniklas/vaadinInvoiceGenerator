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
	private final PDType1Font addressPropertyFont = PDType1Font.HELVETICA_BOLD;
	private final PDType1Font addressValueFont = PDType1Font.HELVETICA;

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
		contentStream.close();
		return page;
	}
	
	private PDPage writeCustomerAddress(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeAddressRow(50, 700, "Name: ", invoice.getCustomer().getName(), contentStream);
		writeAddressRow(50, 685, "Street: ", invoice.getCustomer().getAddress(), contentStream);
		writeAddressRow(50, 670, "Postcode: ", invoice.getCustomer().getPostcode(), contentStream);
		writeAddressRow(50, 655, "City", invoice.getCustomer().getCity(), contentStream);
		return page;
	}
	
	//X and Y coordinates start from BOTTOM left (pdfbox api)
	private void writeAddressRow(int x, int y, String property, String value, PDPageContentStream contentStream) throws IOException {
		contentStream.moveTo(0, 0);
		contentStream.beginText();
		contentStream.setFont(addressPropertyFont, 12);
		contentStream.moveTextPositionByAmount(x, y);
		contentStream.drawString(property);
		contentStream.endText();
		
		contentStream.moveTo(0, 0);
		contentStream.beginText();
		contentStream.moveTextPositionByAmount(x+70, y);
		contentStream.setFont(addressValueFont, 12);
		contentStream.drawString(value);
		contentStream.moveTextPositionByAmount(0, -15);
		contentStream.endText();
	}

}
