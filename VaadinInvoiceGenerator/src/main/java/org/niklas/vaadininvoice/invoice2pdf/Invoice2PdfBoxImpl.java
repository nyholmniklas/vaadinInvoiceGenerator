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

	public File getPdfFromInvoice(Invoice invoice) {
		PDDocument doc = null;
		try {
			doc = new PDDocument();
			doc.addPage(createPage(doc, invoice));
			doc.save(new File("C:\\temp\\firstout.pdf"));
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (COSVisitorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private PDPage createPage(PDDocument doc, Invoice invoice) throws IOException {
		PDPage page = new PDPage();
		PDType1Font font = PDType1Font.HELVETICA;
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);
		contentStream.setFont(font, 12);
		contentStream.beginText();
		contentStream.moveTextPositionByAmount(100, 400);
		contentStream.drawString(invoice.getName());
		contentStream.endText();
		contentStream.close();
		return page;
	}

}
