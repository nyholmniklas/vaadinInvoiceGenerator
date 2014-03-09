package org.niklas.vaadininvoice.gui;

import java.io.File;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;

public class TitlePanel extends Panel{
	private GridLayout layout;
	private Label titleLabel;
	private Button createButton;
	
	public TitlePanel(Button button){
		layout = new GridLayout(3,1);
		titleLabel = new Label("<h1>Create New Invoice</h1>", ContentMode.HTML);
		titleLabel.setWidth(300, Unit.PIXELS);;
		this.createButton = button;
		setLayout();
	}
	
	private void setLayout() {
		layout.addComponent(titleLabel, 0, 0);
		layout.setComponentAlignment(titleLabel, Alignment.MIDDLE_LEFT);
		layout.addComponent(createButton, 1, 0);
		layout.setComponentAlignment(createButton, Alignment.MIDDLE_RIGHT);
		layout.setSizeFull();
		layout.setSpacing(true);
		layout.setMargin(true);
		setContent(layout);
	}

	protected void setLink(File file) {
		layout.removeComponent(2, 0);
		Button downloadButton = new Button("View PDF");
		FileDownloader fileDownloader = new FileDownloader(new FileResource(file));
		fileDownloader.extend(downloadButton);
		layout.addComponent(downloadButton, 2,0);
		layout.setComponentAlignment(downloadButton, Alignment.MIDDLE_LEFT);
	}
}
