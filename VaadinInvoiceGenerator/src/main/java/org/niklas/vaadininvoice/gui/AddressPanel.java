package org.niklas.vaadininvoice.gui;

import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class AddressPanel extends Panel {
	private final TextField companyNameTextField = new TextField("Name", "Acme Inc.");
	private final TextField companyStreetTextField = new TextField("Street", "Main Street");
	private final TextField companyCityTextField = new TextField("City", "El Dorado");
	private final TextField companyPostcodeTextField = new TextField("Postcode", "31415");
	private final TextField customerNameTextField = new TextField("Name", "John Doe");
	private final TextField customerStreetTextField = new TextField("Street", "Elkroad 52");
	private final TextField customerCityTextField = new TextField("City", "Atlantis");
	private final TextField customerPostcodeTextField = new TextField("Postcode", "235711");
	private final InlineDateField dueDateField = new InlineDateField(" ");
	
	public AddressPanel() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		VerticalLayout companyLayout = new VerticalLayout();
		VerticalLayout customerLayout = new VerticalLayout();
		VerticalLayout dueDateLayout = new VerticalLayout();
		
		Label companyLabel = new Label("<b>Company</b>");
		companyLabel.setContentMode(Label.CONTENT_XHTML);
		companyLayout.addComponent(companyLabel);
		companyLayout.addComponent(companyNameTextField);
		companyLayout.addComponent(companyStreetTextField);
		companyLayout.addComponent(companyCityTextField);
		companyLayout.addComponent(companyPostcodeTextField);
		companyLayout.setSpacing(true);
		companyLayout.setSizeFull();
		
		Label customerLabel = new Label("<b>Customer</b>");
		customerLabel.setContentMode(Label.CONTENT_XHTML);
		customerLayout.addComponent(customerLabel);
		customerLayout.addComponent(customerNameTextField);
		customerLayout.addComponent(customerStreetTextField);
		customerLayout.addComponent(customerCityTextField);
		customerLayout.addComponent(customerPostcodeTextField);
		customerLayout.setSpacing(true);
		customerLayout.setSizeFull();
		
		dueDateLayout.setSizeFull();
		Label dueDateLabel = new Label("<b>Due Date</b>");
		dueDateLabel.setContentMode(Label.CONTENT_XHTML);
		dueDateField.setResolution(InlineDateField.RESOLUTION_DAY);
		dueDateField.setDateFormat("dd-MM-yyy");
		dueDateField.setValue(new java.util.Date());
		dueDateLayout.addComponent(dueDateLabel);
		dueDateLayout.addComponent(dueDateField);
		
		
		horizontalLayout.addComponent(companyLayout);
		horizontalLayout.addComponent(customerLayout);
		horizontalLayout.addComponent(dueDateLayout);
		horizontalLayout.setSizeFull();
		
		
		horizontalLayout.setSpacing(true);
		horizontalLayout.setMargin(true);
		addComponent(horizontalLayout);
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
	
	
	
}
