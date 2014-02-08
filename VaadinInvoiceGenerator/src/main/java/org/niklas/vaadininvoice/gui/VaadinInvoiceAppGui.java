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
	private final TextField nameTextField = new TextField("Name");
	private final Button sayHelloButton = new Button("Say hello");
	
	private final VaadinInvoiceManager manager;
	
	public VaadinInvoiceAppGui(VaadinInvoiceManager manager) {
		super("VaadinInvoiceApp");
		this.manager = manager;
		initComponents();
	}

	private void initComponents() {
		addComponent(nameTextField);
		addComponent(sayHelloButton);
		sayHelloButton.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				Label helloLabel = new Label("Hello "
						+ nameTextField.getValue() + " !!");
				addComponent(helloLabel);
				createPdf();
			}
		});
	}
	
	public void createPdf() {
		manager.createPdfTest(new InvoiceMapperImpl().getInvoiceFromForm(this));
	}

	public String getNameTextFieldValue() {
		return nameTextField.getValue().toString();
	}
}
