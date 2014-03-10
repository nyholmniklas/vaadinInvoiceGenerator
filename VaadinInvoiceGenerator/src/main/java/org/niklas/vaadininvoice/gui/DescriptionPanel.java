package org.niklas.vaadininvoice.gui;

import org.niklas.vaadininvoice.model.Invoice;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class DescriptionPanel extends Panel {
	private BeanItem<Invoice> invoiceBean;
	private FieldGroup descriptionFieldGroup;
	private Label descriptionLabel;
	private TextArea descriptionTextArea;
	
	public DescriptionPanel(BeanItem<Invoice> invoiceBean){
		this.invoiceBean = invoiceBean;
		descriptionFieldGroup = new FieldGroup(invoiceBean);
		
		initComponents();
		doBind();
		setValidators();
		setLayout();
	}

	private void setValidators() {
		descriptionTextArea.addValidator(new BeanValidator(Invoice.class, "description"));
		
	}

	private void doBind() {
		descriptionFieldGroup.bind(descriptionTextArea, "description");
	}

	private void initComponents() {
		descriptionLabel = new Label("<b>Description</b>", ContentMode.HTML);
		descriptionTextArea = new TextArea();
	}

	private void setLayout() {
		setHeight(115, Unit.PIXELS);
		descriptionTextArea.setSizeFull();
		descriptionTextArea.setHeight(50, Unit.PIXELS);
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(descriptionLabel);
		layout.addComponent(descriptionTextArea);
		layout.setSpacing(true);
		layout.setMargin(true);
		setContent(layout);
	}

	public void commitFields() throws CommitException{
		descriptionFieldGroup.commit();
	}
}
