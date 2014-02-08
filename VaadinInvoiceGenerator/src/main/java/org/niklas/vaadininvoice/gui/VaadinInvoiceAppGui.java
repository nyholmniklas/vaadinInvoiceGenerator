package org.niklas.vaadininvoice.gui;

import org.niklas.vaadininvoice.VaadinInvoiceManager;
import org.niklas.vaadininvoice.invoice.InvoiceMapperImpl;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class VaadinInvoiceAppGui extends Window {

	private final Button createButton = new Button("Create pdf");
	private final AddressPanel addressPanel = new AddressPanel();
	private final VaadinInvoiceManager manager;
	
	public VaadinInvoiceAppGui(VaadinInvoiceManager manager) {
		super("VaadinInvoiceApp");
		this.manager = manager;
		initComponents();
	}

	private void initComponents() {
		addComponent(addressPanel);
		addComponent(createButton);
		createButton.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				createPdf();
			}
		});
	}
	
	private void createPdf() {
		manager.createPdfTest(new InvoiceMapperImpl().getInvoiceFromForm(this));
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
		return addressPanel.getCustomerPostcodeTextField().getValue().toString();
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
	


	
}
