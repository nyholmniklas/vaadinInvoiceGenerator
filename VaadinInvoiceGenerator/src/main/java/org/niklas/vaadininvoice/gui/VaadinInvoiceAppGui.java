package org.niklas.vaadininvoice.gui;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.niklas.vaadininvoice.controller.VaadinInvoiceController;
import org.niklas.vaadininvoice.model.InvoiceMapperImpl;
import org.niklas.vaadininvoice.model.InvoiceRow;

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
	private VerticalLayout layout;
	private Button createButton;
	private TitlePanel titlePanel;
	private AddressPanel addressPanel;
	private InvoiceRowPanel invoiceRowPanel;
	private VaadinInvoiceController controller;

	public VaadinInvoiceAppGui(VaadinInvoiceController controller) {
		super();
		this.controller = controller;
		createButton = new Button("Generate PDF");
		titlePanel = new TitlePanel(createButton);
		addressPanel = new AddressPanel();
		invoiceRowPanel = new InvoiceRowPanel();
		setActionListeners();
		setLayout();
	}

	private void setLayout() {
		layout = new VerticalLayout();
		layout.addComponent(titlePanel);
		layout.addComponent(addressPanel);
		layout.addComponent(invoiceRowPanel);
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
	}

	private void setActionListeners() {
		createButton.addClickListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				createPdf();
				titlePanel.setLink(controller.getPdfFile());
			}
		});
	}

	private void createPdf() {
		controller.createPdf(new InvoiceMapperImpl().getInvoiceFromForm(this));
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
