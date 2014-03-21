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
import org.niklas.vaadininvoice.model.Address;
import org.niklas.vaadininvoice.model.Invoice;
import org.niklas.vaadininvoice.model.InvoiceRow;

public class Invoice2PdfBoxImpl implements Invoice2Pdf{
	private final PDType1Font normalFont = PDType1Font.HELVETICA;
	private final PDType1Font boldFont = PDType1Font.HELVETICA_BOLD;
	private final PDType1Font italicFont = PDType1Font.TIMES_ITALIC;

	public File getPdfFromInvoice(Invoice invoice, File file) {
		PDDocument doc = null;
		try {
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
		float mediaBoxWidth = page.getMediaBox().getWidth();
		float mediaBoxHeight = page.getMediaBox().getHeight();
		System.out.println("MediaBox dimensions are " + mediaBoxWidth + "x"
				+ mediaBoxHeight);
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
			contentStream.drawXObject(img, 50f, 692f, (float)logoDimensions.getWidth(), (float) logoDimensions.getHeight());
		}
		else {
			contentStream = new PDPageContentStream(doc, page);
		}

		writeBoldText(175, 662, "BILL TO: ", contentStream);
		writeAddress(175, 642, invoice.getCustomer(), page, contentStream);
//		writeItalicText(50, 662, "From: ", contentStream);
		writeAddressItalic(50, 642, invoice.getCompany(), page, contentStream);
		writeInvoiceInfo(350, 662, invoice, page, contentStream);
		writeInvoiceRows(50, 450, invoice, page, contentStream);
		writeDescription(50, 525, invoice, page, contentStream);
		contentStream.close();
		return page;
	}
	
	
	
	private PDPage writeAddress(int x, int y, Address address, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeText(x, y, address.getName(), contentStream);
		writeText(x, y -= 17, address.getAddress(), contentStream);
		writeText(x, y -= 17, address.getPostcode(), contentStream);
		writeText(x, y -= 17, address.getCity(), contentStream);
		return page;
	}
	
	private PDPage writeAddressItalic(int x, int y, Address address, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeItalicText(x, y, address.getName(), contentStream);
		writeItalicText(x, y -= 17, address.getAddress(), contentStream);
		writeItalicText(x, y -= 17, address.getPostcode(), contentStream);
		writeItalicText(x, y -= 17, address.getCity(), contentStream);
		return page;
	}
	
	private void writeItalicText(int x, int y, String value,
			PDPageContentStream contentStream) throws IOException {
		writeTextWithFont(x, y, value, contentStream, italicFont);
		
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
	
	private PDPage writeInvoiceInfo(int x, int y, Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		writeBoldText(x, y, "Invoice Date", contentStream);
		writeText(x, y - 14, df.format(invoice.getInvoiceDate()), contentStream);
		writeBoldText(x, y - 50, "Due Date", contentStream);
		writeText(x, y - 64, df.format(invoice.getDueDate()), contentStream);
		writeBoldText(x + 100, y, "Invoice Number", contentStream);
		writeText(x+100, y - 14, ""+invoice.getInvoiceNumber(), contentStream);
		writeBoldText(x+100, y -50, "Reference", contentStream);
		writeText(x+100, y - 64, ""+ invoice.getReferenceNumber(), contentStream);
		return page;
	}
	
	private PDPage writeInvoiceRows(int x, int y, Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
		writeBoldText(x, y, "Quantity", contentStream);
		writeBoldText(x+100, y, "Description", contentStream);
		writeBoldText(x+200, y, "Price", contentStream);
		writeBoldText(x+300, y, "Tax Rate %", contentStream);
		writeBoldText(x+400, y, "Total", contentStream);
		
		int i = 0;
		for (; i < invoice.getRows().size() ; i++) {
			InvoiceRow row = invoice.getRows().get(i);
			int tempY = y - 25 - (14*i);
			writeText(x, tempY, row.getDescription(), contentStream);
			writeText(x+100, tempY, Integer.toString(row.getQuantity()), contentStream);
			writeText(x+200, tempY, row.getPrice().toString(), contentStream);
			writeText(x+300, tempY, row.getTaxRate().toString(), contentStream);
			writeText(x+400, tempY, row.getTotal().toString()+" EUR", contentStream);
		}
		i++;
		writeText(x+300, y - 50 - (20*i), "Sub-Total: ", contentStream);
		writeText(x+400, y - 50 - (20*i), invoice.getSubTotal().toString()+" EUR", contentStream);
		i++;
		writeText(x+300, y - 50 - (20*i), "VAT Total: ", contentStream);
		writeText(x+400, y - 50 - (20*i), invoice.getVatTotal().toString()+" EUR", contentStream);
		i++;
		writeBoldText(x+300, y - 50 - (20*i), "TOTAL: ", contentStream);
		writeBoldText(x+400, y - 50 - (20*i), invoice.getTotal().toString()+" EUR", contentStream);
		
		return page;
	}
	
	
	private PDPage writeDescription(int x, int y, Invoice invoice, PDPage page, PDPageContentStream contentStream) throws IOException {
	    float fontSize = 12;
	    float leading = 1.5f * fontSize;
	    int marginX = x;
	    int marginY = y;
	    
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
		float maxWidth = 200;
		float maxHeight = 80;
		float width = img.getWidth();
		float height = img.getHeight();
		System.out.println("Image of size "+width+"x"+height+"px was resized to:");
		while (width > maxWidth) {
			height = (maxWidth / width) * height;
			width = maxWidth;
		}
		while (height > maxHeight) {
			width = (maxHeight / height) * width;
			height = maxHeight;
		}
		System.out.println(width+"x"+height+"px.");
		
		return new Dimension((int) width,(int) height);
	}

}
