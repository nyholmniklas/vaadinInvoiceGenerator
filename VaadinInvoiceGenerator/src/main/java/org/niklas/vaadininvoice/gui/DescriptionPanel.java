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
		setHeight(115, Unit.PIXELS);
		descriptionLabel = new Label("<b>Description</b>", ContentMode.HTML);
		descriptionTextArea = new TextArea();
		descriptionTextArea.setSizeFull();
		descriptionTextArea.setHeight(50, Unit.PIXELS);
		setLayout();
	}

	private void setLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(descriptionLabel);
		layout.addComponent(descriptionTextArea);
		layout.setSpacing(true);
		layout.setMargin(true);
		setContent(layout);
	}

	public String getDescription() {
		return descriptionTextArea.getValue();
	}
}
