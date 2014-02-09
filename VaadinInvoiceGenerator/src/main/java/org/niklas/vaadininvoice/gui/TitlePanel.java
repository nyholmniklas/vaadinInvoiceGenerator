package org.niklas.vaadininvoice.gui;

import com.google.gwt.layout.client.Layout;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;

public class TitlePanel extends Panel{
	private final Label titleLabel = new Label("<h1>Create New Invoice</h1>");
	private final HorizontalLayout layout = new HorizontalLayout();

	
	public TitlePanel(Button button){

		titleLabel.setContentMode(ContentMode.HTML);
		titleLabel.setWidth(300, Unit.PIXELS);;
		layout.addComponent(titleLabel);
		layout.addComponent(button);
		layout.setSpacing(true);
		layout.setMargin(true);
		addComponent(layout);
	}


	public void setLink() {
		Link link = new DownloadLink();
//		link.setIcon(new ThemeResource("http://www.vectorsland.com/imgd/l61205-adobe-pdf-logo-77966.png"));
		addComponent(link);
		layout.addComponent(link);
	}
}
