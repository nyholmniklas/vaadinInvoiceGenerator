package org.niklas.vaadininvoice.gui;


import com.vaadin.data.fieldgroup.FieldGroup;
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
	
	public UploadPanel(String sessionId) {
		this.sessionId = sessionId;
		initComponents();
		setLayout();
	}

	private void initComponents() {
		uploadLabel = new Label("<b>Upload Logo", ContentMode.HTML);
		uploadComponent = new UploadComponent("Upload logo", sessionId, 1000000) {
			
			@Override
			public void uploadSucceeded(SucceededEvent event) {
				
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
