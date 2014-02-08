package org.niklas.vaadininvoice.gui;

import com.vaadin.Application;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.Paintable.RepaintRequestListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Component.Listener;
import com.vaadin.ui.ComponentContainer.ComponentAttachListener;
import com.vaadin.ui.ComponentContainer.ComponentDetachListener;

public class AddressPanel extends Panel {
	private final TextField companyNameTextField = new TextField("Name");
	private final TextField companyStreetTextField = new TextField("Street");
	private final TextField companyCityTextField = new TextField("City");
	private final TextField companyPostcodeTextField = new TextField("Postcode");
	private final TextField customerNameTextField = new TextField("Name");
	private final TextField customerStreetTextField = new TextField("Street");
	private final TextField customerCityTextField = new TextField("City");
	private final TextField customerPostcodeTextField = new TextField("Postcode");
	
	public AddressPanel() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		VerticalLayout companyLayout = new VerticalLayout();
		VerticalLayout customerLayout = new VerticalLayout();
		
		Label companyLabel = new Label("<b>Company</b>");
		companyLabel.setContentMode(Label.CONTENT_XHTML);
		companyLayout.addComponent(companyLabel);
		companyLayout.addComponent(companyNameTextField);
		companyLayout.addComponent(companyStreetTextField);
		companyLayout.addComponent(companyCityTextField);
		companyLayout.addComponent(companyPostcodeTextField);
		companyLayout.setSpacing(true);
		
		Label customerLabel = new Label("<b>Customer</b>");
		customerLabel.setContentMode(Label.CONTENT_XHTML);
		customerLayout.addComponent(customerLabel);
		customerLayout.addComponent(customerNameTextField);
		customerLayout.addComponent(customerStreetTextField);
		customerLayout.addComponent(customerCityTextField);
		customerLayout.addComponent(customerPostcodeTextField);
		customerLayout.setSpacing(true);
		
		horizontalLayout.addComponent(companyLayout);
		horizontalLayout.addComponent(customerLayout);
		
		
		horizontalLayout.setSpacing(true);
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
	
	
}
