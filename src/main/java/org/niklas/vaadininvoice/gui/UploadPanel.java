package org.niklas.vaadininvoice.gui;


import org.niklas.vaadininvoice.model.Invoice;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Upload.SucceededEvent;

public class UploadPanel extends Panel{
	private Label uploadLabel;
	private UploadComponent uploadComponent;
	private String sessionId;
	private BeanItem<Invoice> invoiceBean;
	
	public UploadPanel(BeanItem<Invoice> invoiceBean, String sessionId) {
		this.invoiceBean = invoiceBean;
		this.sessionId = sessionId;
		initComponents();
		setLayout();
	}

	private void initComponents() {
		uploadLabel = new Label("<b>Upload Logo", ContentMode.HTML);
		uploadComponent = new UploadComponent("Upload logo", sessionId, 1000000) {
			
			@Override
			public void uploadSucceeded(SucceededEvent event) {
				invoiceBean.getBean().setLogoImageFilePath(getFile().getAbsolutePath());
			}
		};
	}

	private void setLayout() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.addComponent(uploadLabel);
		verticalLayout.addComponent(uploadComponent);
		verticalLayout.setSizeFull();
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);
		setContent(verticalLayout);
	}
	
}
