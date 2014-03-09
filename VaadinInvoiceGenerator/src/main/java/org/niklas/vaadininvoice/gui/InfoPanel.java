package org.niklas.vaadininvoice.gui;

import java.util.Calendar;
import java.util.Date;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class InfoPanel extends Panel {
	private TextField companyNameTextField;
	private TextField companyStreetTextField;
	private TextField companyCityTextField;
	private TextField companyPostcodeTextField;
	private TextField customerNameTextField;
	private TextField customerStreetTextField;
	private TextField customerCityTextField;
	private TextField customerPostcodeTextField;
	private DateField invoiceDateField;
	private DateField dueDateField;
	private TextField referenceNumberField;
	private TextField invoiceNumberField;
	private Label companyLabel;
	private Label customerLabel;
	private Label invoiceInfoLabel;
	
	public InfoPanel() {
		companyNameTextField = new TextField("Name", "Acme Inc.");
		companyStreetTextField = new TextField("Street", "Main Street");
		companyCityTextField = new TextField("City", "El Dorado");
		companyPostcodeTextField = new TextField("Postcode", "31415");
		customerNameTextField = new TextField("Name", "John Doe");
		customerStreetTextField = new TextField("Street", "Elkroad 52");
		customerCityTextField = new TextField("City", "Atlantis");
		customerPostcodeTextField = new TextField("Postcode", "235711");
		invoiceDateField = new DateField("Invoice Date");
		dueDateField = new DateField("Due Date");
		companyLabel = new Label("<b>Company</b>", ContentMode.HTML);
		customerLabel = new Label("<b>Customer</b>", ContentMode.HTML);
		invoiceInfoLabel = new Label("<b>Invoice Information</b>", ContentMode.HTML);
		referenceNumberField = new TextField("Reference Number", "000000");
		invoiceNumberField = new TextField("Invoice Number", "00000");
		setLayout();
	}
	
	private void setLayout() {
		VerticalLayout companyLayout = new VerticalLayout();
		companyLayout.addComponent(companyLabel);
		companyLayout.addComponent(companyNameTextField);
		companyLayout.addComponent(companyStreetTextField);
		companyLayout.addComponent(companyCityTextField);
		companyLayout.addComponent(companyPostcodeTextField);
		companyLayout.setSpacing(true);
		companyLayout.setSizeFull();
		
		VerticalLayout customerLayout = new VerticalLayout();
		customerLayout.addComponent(customerLabel);
		customerLayout.addComponent(customerNameTextField);
		customerLayout.addComponent(customerStreetTextField);
		customerLayout.addComponent(customerCityTextField);
		customerLayout.addComponent(customerPostcodeTextField);
		customerLayout.setSpacing(true);
		customerLayout.setSizeFull();
		
		VerticalLayout invoiceInfoLayout = new VerticalLayout();
		Calendar c = Calendar.getInstance();    
		c.setTime(new Date());
		invoiceDateField.setResolution(Resolution.DAY);
		invoiceDateField.setDateFormat("dd-MM-yyyy");
		invoiceDateField.setValue(c.getTime());
		c.add(Calendar.DATE, 14);
		dueDateField.setResolution(Resolution.DAY);
		dueDateField.setDateFormat("dd-MM-yyyy");
		dueDateField.setValue(c.getTime());
		invoiceInfoLayout.addComponent(invoiceInfoLabel);
		invoiceInfoLayout.addComponent(invoiceDateField);
		invoiceInfoLayout.addComponent(dueDateField);
		invoiceInfoLayout.addComponent(invoiceNumberField);
		invoiceInfoLayout.addComponent(referenceNumberField);
		invoiceInfoLayout.setSpacing(true);
		invoiceInfoLayout.setSizeFull();
		
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.addComponent(companyLayout);
		horizontalLayout.addComponent(customerLayout);
		horizontalLayout.addComponent(invoiceInfoLayout);
		horizontalLayout.setSizeFull();
		horizontalLayout.setSpacing(true);
		horizontalLayout.setMargin(true);
		setContent(horizontalLayout);
	}

	public TextField getCompanyNameTextField() {
		return companyNameTextField;
	}

	public TextField getCompanyStreetTextField() {
		return companyStreetTextField;
	}

	public TextField getCompanyCityTextField() {
		return companyCityTextField;
	}

	public TextField getCompanyPostcodeTextField() {
		return companyPostcodeTextField;
	}

	public TextField getCustomerNameTextField() {
		return customerNameTextField;
	}

	public TextField getCustomerStreetTextField() {
		return customerStreetTextField;
	}

	public TextField getCustomerCityTextField() {
		return customerCityTextField;
	}

	public TextField getCustomerPostcodeTextField() {
		return customerPostcodeTextField;
	}

	public DateField getDueDateField() {
		return dueDateField;
	}

	public TextField getReferenceNumberField() {
		return referenceNumberField;
	}

	public TextField getInvoiceNumberField() {
		return invoiceNumberField;
	}
	
	
}
