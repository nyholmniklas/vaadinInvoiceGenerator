package org.niklas.vaadininvoice.invoice2pdf;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.niklas.vaadininvoice.model.Invoice;
import org.niklas.vaadininvoice.model.InvoiceRow;

public class Invoice2PdfBoxImpl implements Invoice2Pdf{
	private final PDType1Font normalFont = PDType1Font.HELVETICA;
	private final PDType1Font boldFont = PDType1Font.HELVETICA_BOLD;

	public File getPdfFromInvoice(Invoice invoice, String sessionId) {
		PDDocument doc = null;
		try {
			File file = new File("C:\\temp\\"+sessionId+".pdf");
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
		
		//Draw logo here because of contentstream and imager creation order bug!! Look into generalized method of drawing images on pdf
		PDPageContentStream contentStream;
		if (invoice.getLogoImageFilePath() != null) {
			InputStream in = new FileInputStream(new File(invoice.getLogoImageFilePath()));
			PDJpeg img = new PDJpeg(doc, in);
			//Have to create new content stream to avoid bug with pdfbox
			contentStream = new PDPageContentStream(doc, page);
			Dimension logoDimensions = getImageDimensions(img);
//			img.setWidth((int) Math.round(logoDimensions.getWidth()));
//			img.setHeight((int) Math.round(logoDimensions.getHeight()));
//			contentStream.drawImage(img, 50, 50);
			contentStream.drawXObject(img, 50f, 50f, (float)logoDimensions.getWidth(), (float) logoDimensions.getHeight());
		}
		else {
			contentStream = new PDPageContentStream(doc, page);
		}

		
		writeCustomerAddress(invoice, page, contentStream);
		writeCompanyAddress(invoice, page, contentStream);
		writeInvoiceInfo(invoice, page, contentStream);
		writeInvoiceRows(invoice, page, contentStream);
		writeDescription(invoice, page, contentStream);
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
	
//	private PDPage drawLogo(Invoice invoice, PDPage page, PDDocument doc) throws IOException {
//		InputStream in = new FileInputStream(new File(invoice.getLogoImageFilePath()));
//		PDJpeg img = new PDJpeg(doc, in);
//		//Have to create new content stream to avoid bug with pdfbox
//		PDPageContentStream contentStream = new PDPageContentStream(doc, page);
//		img.setWidth(200);
//		contentStream.drawImage(img, 100, 700);
//		contentStream.close();
//		return page;
//	}
	
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
	
	private PDPage writeInvoiceInfo(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		DateFormat df = new SimpleDateFormat("dd.MM.YYYY");
		writeBoldText(370, 720, "Invoice Date", contentStream);
		writeText(370, 700, df.format(invoice.getInvoiceDate()), contentStream);
		writeBoldText(490, 720, "Due Date", contentStream);
		writeText(490, 700, df.format(invoice.getDueDate()), contentStream);
		writeBoldText(370, 680, "Invoice Number", contentStream);
		writeText(370, 665, ""+invoice.getInvoiceNumber(), contentStream);
		writeBoldText(490, 680, "Reference", contentStream);
		writeText(490, 665, ""+ invoice.getReferenceNumber(), contentStream);
		return page;
	}
	
	private PDPage writeInvoiceRows(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeBoldText(100, 500, "Quantity", contentStream);
		writeBoldText(200, 500, "Description", contentStream);
		writeBoldText(300, 500, "Price", contentStream);
		writeBoldText(400, 500, "Tax Rate %", contentStream);
		writeBoldText(500, 500, "Total", contentStream);
		
		int i = 0;
		for (; i < invoice.getRows().size() ; i++) {
			InvoiceRow row = invoice.getRows().get(i);
			int y = 460 - (14*i);
			writeText(100, y, row.getDescription(), contentStream);
			writeText(200, y, Integer.toString(row.getQuantity()), contentStream);
			writeText(300, y, row.getPrice().toString(), contentStream);
			writeText(400, y, row.getTaxRate().toString(), contentStream);
			writeText(500, y, row.getTotalToString(), contentStream);
		}
		
		writeBoldText(400, 440 - (14*i), "TOTAL: ", contentStream);
		writeBoldText(500, 440 - (14*i), invoice.getTotal().toString(), contentStream);
		
		return page;
	}
	
	
	private PDPage writeDescription(Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
	    float fontSize = 12;
	    float leading = 1.5f * fontSize;
	    int marginX = 100;
	    int marginY = 600;
	    
	    PDRectangle mediabox = page.findMediaBox();
	    float width = mediabox.getWidth() - 2*marginX;
	    float startX = mediabox.getLowerLeftX() + marginX;
	    float startY = mediabox.getLowerLeftY() +  marginY;

	    List<String> lines = new ArrayList<String>();
	    int lastSpace = -1;
	    String text = invoice.getDescription();
	    while (text.length() > 0)
	    {
	        int spaceIndex = text.indexOf(' ', lastSpace + 1);
	        if (spaceIndex < 0)
	        {
	            lines.add(text);
	            text = "";
	        }
	        else
	        {
	            String subString = text.substring(0, spaceIndex);
	            float size = fontSize * normalFont.getStringWidth(subString) / 1000;
	            if (size > width)
	            {
	                if (lastSpace < 0)
	                    lastSpace = spaceIndex;
	                subString = text.substring(0, lastSpace);
	                lines.add(subString);
	                text = text.substring(lastSpace).trim();
	                lastSpace = -1;
	            }
	            else
	            {
	                lastSpace = spaceIndex;
	            }
	        }
	    }

	    contentStream.beginText();
	    contentStream.setFont(normalFont, fontSize);
	    contentStream.moveTextPositionByAmount(startX, startY);            
	    for (String line: lines)
	    {
	        contentStream.drawString(line);
	        contentStream.moveTextPositionByAmount(0, -leading);
	    }
	    contentStream.endText();
	    
	    return page;
	}
	
	private Dimension getImageDimensions(PDJpeg img) {
		float maxWidth = 100;
		float maxHeight = 50;
		float width = img.getWidth();
		float height = img.getHeight();
		System.out.println("Image of size "+width+"x"+height+"px was resized to:");
		if (width > maxWidth) {
			height = (maxWidth / width) * height;
			width = maxWidth;
		}
		if (height > maxHeight) {
			width = (maxHeight / height) * width;
			height = maxHeight;
		}
		System.out.println(width+"x"+height+"px.");
		
		return new Dimension((int) width,(int) height);
	}

}
