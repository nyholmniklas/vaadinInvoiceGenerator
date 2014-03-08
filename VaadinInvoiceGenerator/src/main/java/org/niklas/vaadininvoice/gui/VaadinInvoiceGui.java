package org.niklas.vaadininvoice.gui;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.niklas.vaadininvoice.controller.VaadinInvoiceController;
import org.niklas.vaadininvoice.mapping.InvoiceMapperImpl;
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

public class VaadinInvoiceGui extends Panel {
	private VerticalLayout layout;
	private Button createButton;
	private TitlePanel titlePanel;
	private InfoPanel infoPanel;
	private InvoiceRowPanel invoiceRowPanel;
	private VaadinInvoiceController controller;

	public VaadinInvoiceGui(VaadinInvoiceController controller) {
		super();
		this.controller = controller;
		createButton = new Button("Generate PDF");
		titlePanel = new TitlePanel(createButton);
		infoPanel = new InfoPanel();
		invoiceRowPanel = new InvoiceRowPanel();
		setActionListeners();
		setLayout();
	}

	private void setLayout() {
		layout = new VerticalLayout();
		layout.addComponent(titlePanel);
		layout.addComponent(infoPanel);
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
		controller.createPdf(this);
	}

	public String getCustomerName() {
		return infoPanel.getCustomerNameTextField().getValue().toString();
	}

	public String getCustomerStreet() {
		return infoPanel.getCustomerStreetTextField().getValue().toString();
	}

	public String getCustomerCity() {
		return infoPanel.getCustomerCityTextField().getValue().toString();
	}

	public String getCustomerPostcode() {
		return infoPanel.getCustomerPostcodeTextField().getValue()
				.toString();
	}

	public String getCompanyName() {
		return infoPanel.getCompanyNameTextField().getValue().toString();
	}

	public String getCompanyStreet() {
		return infoPanel.getCompanyStreetTextField().getValue().toString();
	}

	public String getCompanyCity() {
		return infoPanel.getCompanyCityTextField().getValue().toString();
	}

	public String getCompanyPostcode() {
		return infoPanel.getCompanyPostcodeTextField().getValue().toString();
	}

	public String getDueDate() {
		return infoPanel.getDueDateField().getValue().toString();
	}

	public HashMap<Integer, InvoiceRow> getInvoiceRows() {
		return invoiceRowPanel.getInvoiceRows();
	}

	public String getReferenceNumber() {
		return infoPanel.getReferenceNumberField().getValue();
	}

	public String getInvoiceNumber() {
		return infoPanel.getInvoiceNumberField().getValue();
	}

}
