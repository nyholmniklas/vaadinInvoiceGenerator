package org.niklas.vaadininvoice.gui;

import java.io.File;

import org.niklas.vaadininvoice.controller.VaadinInvoiceController;
import org.niklas.vaadininvoice.model.Invoice;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

public class TitlePanel extends Panel{
	private BeanItem<Invoice> invoiceBean;
	private GridLayout layout;
	private Label titleLabel;
	private Button createButton;
	private Button downloadButton;
	private Button logoutButton;
	private FileDownloader fileDownloader;
	private VaadinInvoiceController controller;
	
	public TitlePanel(Button button, BeanItem<Invoice> invoiceBean, VaadinInvoiceController controller){
		this.invoiceBean = invoiceBean;
		this.controller = controller;
		layout = new GridLayout(5,1);
		titleLabel = new Label("<h1>Create New Invoice</h1>", ContentMode.HTML);
		titleLabel.setWidth(300, Unit.PIXELS);;
		logoutButton = new Button("Logout");
		downloadButton = new Button("Download PDF");
		createButton = button;
		setHeight(90, Unit.PIXELS);
		setLayout();
		setListeners();
	}
	
	private void setLayout() {
		layout.addComponent(titleLabel, 0, 0);
		layout.setComponentAlignment(titleLabel, Alignment.MIDDLE_LEFT);
		
		layout.addComponent(logoutButton, 1, 0);
		layout.setComponentAlignment(logoutButton, Alignment.MIDDLE_CENTER);
		
		layout.addComponent(createButton, 3, 0);
		layout.setComponentAlignment(createButton, Alignment.MIDDLE_RIGHT);

		downloadButton.setEnabled(false);
		layout.addComponent(downloadButton, 4,0);
		layout.setComponentAlignment(downloadButton, Alignment.MIDDLE_LEFT);
		layout.setSizeFull();
		layout.setSpacing(true);
		layout.setMargin(true);
		setContent(layout);		


	}

	protected void setLink(File file) {
		downloadButton.setEnabled(false);
		fileDownloader = new FileDownloader(new FileResource(file));
		fileDownloader.extend(downloadButton);
		downloadButton.setEnabled(true);
	}
	
	private void setListeners(){
		logoutButton.addClickListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				    SecurityContextHolder.clearContext();
			        getUI().getSession().close();
			        Page.getCurrent().getJavaScript().execute("window.location.reload();");
				
			}
		});
		downloadButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				downloadButton.setEnabled(false);
				
			}
		});
	}
}
