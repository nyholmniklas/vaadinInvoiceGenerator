package org.niklas.vaadininvoice.gui;

import org.niklas.vaadininvoice.controller.VaadinInvoiceController;
import org.niklas.vaadininvoice.model.Invoice;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class VaadinInvoiceGui extends Panel {
	private BeanItem<Invoice> invoiceBean;
	private Invoice invoice;
	private VerticalLayout layout;
	private Button createButton;
	private TitlePanel titlePanel;
	private InfoPanel infoPanel;
	private DescriptionPanel descriptionPanel;
	private UploadPanel uploadPanel;
	private Accordion descriptionAndUploadAccordion;
	private InvoiceRowPanel invoiceRowPanel;
	private VaadinInvoiceController controller;

	public VaadinInvoiceGui(VaadinInvoiceController controller) {
		super();
		this.controller = controller;
		invoice = new Invoice();
		invoiceBean = new BeanItem<Invoice>(invoice);

		createButton = new Button("Generate PDF");
		titlePanel = new TitlePanel(createButton, invoiceBean);
		infoPanel = new InfoPanel(invoiceBean);
		descriptionPanel = new DescriptionPanel(invoiceBean);
		invoiceRowPanel = new InvoiceRowPanel(invoiceBean);
		uploadPanel = new UploadPanel(invoiceBean, controller.getSessionId(), controller);
		descriptionAndUploadAccordion = new Accordion();
		setActionListeners();
		setLayout();
	}

	private void setLayout() {
		layout = new VerticalLayout();
		layout.addComponent(titlePanel);
		layout.addComponent(infoPanel);
		descriptionAndUploadAccordion.addTab(descriptionPanel, "Write description");
		descriptionAndUploadAccordion.addTab(uploadPanel, "Upload image for logo");
		layout.addComponent(descriptionAndUploadAccordion);
		layout.addComponent(invoiceRowPanel);
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
	}

	private void setActionListeners() {
		createButton.addClickListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				try {
					commitFields();
					createPdf();
					titlePanel.setLink(controller.getPdfFile());
				} catch (CommitException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void commitFields() throws CommitException {
		infoPanel.commitFields();
		descriptionPanel.commitFields();
	}

	private void createPdf() {
		controller.createPdf(invoiceBean.getBean());
	}

}
