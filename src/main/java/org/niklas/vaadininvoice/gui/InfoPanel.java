package org.niklas.vaadininvoice.gui;

import org.niklas.vaadininvoice.model.Address;
import org.niklas.vaadininvoice.model.Invoice;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class InfoPanel extends Panel {
	private BeanItem<Invoice> invoiceBean;
	private BeanItem<Address> customerBean;
	private BeanItem<Address> companyBean;
	
	private FieldGroup customerFieldGroup;
	private FieldGroup companyFieldGroup;
	private FieldGroup invoiceInfoFieldGroup;
	
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
	
	public InfoPanel(BeanItem<Invoice> invoiceBean) {
		this.invoiceBean = invoiceBean;

		Address companyAddress = invoiceBean.getBean().getCompany();
		Address customerAddress = invoiceBean.getBean().getCustomer();
		customerBean = new BeanItem<Address>(customerAddress);
		companyBean = new BeanItem<Address>(companyAddress);
		customerFieldGroup = new FieldGroup(customerBean);
		companyFieldGroup = new FieldGroup(companyBean);
		
		invoiceInfoFieldGroup = new FieldGroup(this.invoiceBean);

		initComponents();
		doBind();
		setValidators();
		setLayout();
	}

	private void initComponents() {
		companyNameTextField = new TextField("Name");
		companyStreetTextField = new TextField("Street");
		companyCityTextField = new TextField("City");
		companyPostcodeTextField = new TextField("Postcode");
		customerNameTextField = new TextField("Name");
		customerStreetTextField = new TextField("Street");
		customerCityTextField = new TextField("City");
		customerPostcodeTextField = new TextField("Postcode");
		invoiceDateField = new DateField("Invoice Date");
		dueDateField = new DateField("Due Date");
		companyLabel = new Label("<b>Company</b>", ContentMode.HTML);
		customerLabel = new Label("<b>Customer</b>", ContentMode.HTML);
		invoiceInfoLabel = new Label("<b>Invoice Information</b>", ContentMode.HTML);
		referenceNumberField = new TextField("Reference Number");
		invoiceNumberField = new TextField("Invoice Number");
	}

	private void doBind() {
		companyFieldGroup.bind(companyNameTextField, "name");
		companyFieldGroup.bind(companyStreetTextField, "address");
		companyFieldGroup.bind(companyPostcodeTextField, "postcode");
		companyFieldGroup.bind(companyCityTextField, "city");
		
		customerFieldGroup.bind(customerNameTextField, "name");
		customerFieldGroup.bind(customerStreetTextField, "address");
		customerFieldGroup.bind(customerPostcodeTextField, "postcode");
		customerFieldGroup.bind(customerCityTextField, "city");
		
		invoiceInfoFieldGroup.bind(invoiceDateField, "invoiceDate");
		invoiceInfoFieldGroup.bind(dueDateField, "dueDate");
		invoiceInfoFieldGroup.bind(referenceNumberField, "referenceNumber");
		invoiceInfoFieldGroup.bind(invoiceNumberField, "invoiceNumber");
	}
	
	private void setValidators() {
		companyNameTextField.addValidator(new BeanValidator(Address.class, "name"));
		companyStreetTextField.addValidator(new BeanValidator(Address.class, "address"));
		companyPostcodeTextField.addValidator(new BeanValidator(Address.class, "postcode"));
		companyCityTextField.addValidator(new BeanValidator(Address.class, "city"));
		customerNameTextField.addValidator(new BeanValidator(Address.class, "name"));
		customerStreetTextField.addValidator(new BeanValidator(Address.class, "address"));
		customerPostcodeTextField.addValidator(new BeanValidator(Address.class, "postcode"));
		customerCityTextField.addValidator(new BeanValidator(Address.class, "city"));
		
		invoiceDateField.addValidator(new BeanValidator(Invoice.class, "invoiceDate"));
		dueDateField.addValidator(new BeanValidator(Invoice.class, "dueDate"));
		referenceNumberField.addValidator(new BeanValidator(Invoice.class, "referenceNumber"));
		invoiceNumberField.addValidator(new BeanValidator(Invoice.class, "invoiceNumber"));
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
		invoiceDateField.setResolution(Resolution.DAY);
		invoiceDateField.setDateFormat("dd-MM-yyyy");
		dueDateField.setResolution(Resolution.DAY);
		dueDateField.setDateFormat("dd-MM-yyyy");
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
	
	public void commitFields() throws CommitException{
		customerFieldGroup.commit();
		companyFieldGroup.commit();
		invoiceInfoFieldGroup.commit();
	}
}
