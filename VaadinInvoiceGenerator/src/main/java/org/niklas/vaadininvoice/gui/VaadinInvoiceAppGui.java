package org.niklas.vaadininvoice.gui;

import org.niklas.vaadininvoice.VaadinInvoiceManager;
import org.niklas.vaadininvoice.invoice.InvoiceMapperImpl;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class VaadinInvoiceAppGui extends Window {
	private final TextField customerNameTextField = new TextField("Customer Name");
	private final TextField customerStreetTextField = new TextField("Street");
	private final TextField customerCityTextField = new TextField("City");
	private final TextField customerPostcodeTextField = new TextField("Postcode");
	private final Button createButton = new Button("Create pdf");
	
	private final VaadinInvoiceManager manager;
	
	public VaadinInvoiceAppGui(VaadinInvoiceManager manager) {
		super("VaadinInvoiceApp");
		this.manager = manager;
		initComponents();
	}

	private void initComponents() {
		addComponent(customerNameTextField);
		addComponent(customerStreetTextField);
		addComponent(customerCityTextField);
		addComponent(customerPostcodeTextField);
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
		return customerNameTextField.getValue().toString();
	}

	public String getCustomerStreet() {
		return customerStreetTextField.getValue().toString();
	}
	
	public String getCustomerCity() {
		return customerCityTextField.getValue().toString();
	}
	
	public String getCustomerPostcode() {
		return customerPostcodeTextField.getValue().toString();
	}
	
}
