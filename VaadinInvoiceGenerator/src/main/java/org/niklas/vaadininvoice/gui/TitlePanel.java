package org.niklas.vaadininvoice.gui;

import java.io.File;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;

public class TitlePanel extends Panel{
	private HorizontalLayout layout;
	private Label titleLabel;
	private Button createButton;
	
	public TitlePanel(Button button){
		layout = new HorizontalLayout();
		titleLabel = new Label("<h1>Create New Invoice</h1>", ContentMode.HTML);
		titleLabel.setWidth(300, Unit.PIXELS);;
		this.createButton = button;
		setLayout();
	}
	
	private void setLayout() {
		layout.addComponent(titleLabel);
		layout.addComponent(createButton);
		layout.setSpacing(true);
		layout.setMargin(true);
		setContent(layout);
	}

	protected void setLink(File file) {
		Link link = new DownloadLink(file);
		layout.addComponent(link);
	}
}
