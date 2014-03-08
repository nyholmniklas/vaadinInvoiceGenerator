package org.niklas.vaadininvoice.gui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class DescriptionPanel extends Panel {
	private Label descriptionLabel;
	private TextArea descriptionTextArea;
	
	public DescriptionPanel(){
		descriptionLabel = new Label("<b>Description</b>", ContentMode.HTML);
		descriptionTextArea = new TextArea();
		descriptionTextArea.setSizeFull();
		setLayout();
	}

	private void setLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(descriptionLabel);
		layout.addComponent(descriptionTextArea);
		layout.setSpacing(true);
		layout.setSizeFull();
		layout.setSpacing(true);
		layout.setMargin(true);
		setContent(layout);
	}

	public String getDescription() {
		return descriptionTextArea.getValue();
	}
}
