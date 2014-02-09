package org.niklas.vaadininvoice.gui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;

public class DownloadLink extends Link {
	
	public DownloadLink() {
		super();
		setCaption("View Pdf");
		setDescription("View Pdf Invoice");
		setTargetName("_blank");
		
	}

	@Override
	public void attach() {
		super.attach();
		File file = new File("C:\\temp\\firstout.pdf");
		Resource resource = new FileResource(file);
		setResource(resource);
	}
}
