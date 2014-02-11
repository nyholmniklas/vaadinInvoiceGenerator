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
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.label.ContentMode;

public class VaadinInvoiceAppGui extends Panel {

	private final Button createButton = new Button("Generate PDF");
	private final TitlePanel titlePanel = new TitlePanel(createButton);
	private final AddressPanel addressPanel = new AddressPanel();
	private final InvoiceRowPanel invoiceRowPanel = new InvoiceRowPanel();
	private final VaadinInvoiceManager manager;
	private final VerticalLayout layout = new VerticalLayout();

	public VaadinInvoiceAppGui(VaadinInvoiceManager manager) {
		super();
		this.manager = manager;
		initComponents();
	}

	private void initComponents() {
		layout.addComponent(titlePanel);
		layout.addComponent(addressPanel);
		layout.addComponent(invoiceRowPanel);
		layout.setMargin(true);
		layout.setSpacing(true);
		

		setContent(layout);
		
		createButton.addClickListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				createPdf();
				downloadPdf();
				titlePanel.setLink(manager.getPdfFile());
			}
		});
	}

	private void createPdf() {
		manager.createPdfTest(new InvoiceMapperImpl().getInvoiceFromForm(this));
	}
	
	private void downloadPdf() {
//		File file = new File("C:\\temp\\firstout.pdf");
//		Resource fileResource = new FileResource(file);
//		Window pdfWindow = new Window();
//		pdfWindow.addComponent(f);
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
