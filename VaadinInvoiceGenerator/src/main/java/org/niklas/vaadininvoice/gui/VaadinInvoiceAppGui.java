package org.niklas.vaadininvoice.gui;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.niklas.vaadininvoice.VaadinInvoiceManager;
import org.niklas.vaadininvoice.invoice.InvoiceMapperImpl;
import org.niklas.vaadininvoice.invoice.InvoiceRow;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;

public class VaadinInvoiceAppGui extends Window {

	private final Button createButton = new Button("Create pdf");
	private final AddressPanel addressPanel = new AddressPanel();
	private final InvoiceRowPanel invoiceRowPanel = new InvoiceRowPanel();
	private final VaadinInvoiceManager manager;

	public VaadinInvoiceAppGui(VaadinInvoiceManager manager) {
		super("VaadinInvoiceApp");
		this.manager = manager;
		initComponents();
	}

	private void initComponents() {
		addComponent(addressPanel);
		addComponent(invoiceRowPanel);
		addComponent(createButton);
		createButton.addClickListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				createPdf();
				addDownloadButton();
			}
		});
	}

	private void createPdf() {
		manager.createPdfTest(new InvoiceMapperImpl().getInvoiceFromForm(this));
	}

	private void addDownloadButton(){
		Button downloadButton = new Button("Download Pdf");
		File file = new File("C:\\temp\\firstout.pdf");
		Resource fileResource = new FileResource(file);
		FileDownloader fileDowloader = new FileDownloader(fileResource);
		fileDowloader.extend(downloadButton);
		addComponent(downloadButton);
	}


	public String getCustomerName() {
		return addressPanel.getCustomerNameTextField().getValue().toString();
	}

	public String getCustomerStreet() {
		return addressPanel.getCustomerStreetTextField().getValue().toString();
	}

	public String getCustomerCity() {
		return addressPanel.getCustomerCityTextField().getValue().toString();
	}

	public String getCustomerPostcode() {
		return addressPanel.getCustomerPostcodeTextField().getValue()
				.toString();
	}

	public String getCompanyName() {
		return addressPanel.getCompanyNameTextField().getValue().toString();
	}

	public String getCompanyStreet() {
		return addressPanel.getCompanyStreetTextField().getValue().toString();
	}

	public String getCompanyCity() {
		return addressPanel.getCompanyCityTextField().getValue().toString();
	}

	public String getCompanyPostcode() {
		return addressPanel.getCompanyPostcodeTextField().getValue().toString();
	}

	public String getDueDate() {
		return addressPanel.getDueDateField().getValue().toString();
	}

	public HashMap<Integer, InvoiceRow> getInvoiceRows() {
		return invoiceRowPanel.getInvoiceRows();
	}

}
