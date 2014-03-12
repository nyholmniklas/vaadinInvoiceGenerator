package org.niklas.vaadininvoice.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public abstract class UploadComponent extends VerticalLayout
                        implements SucceededListener,
                                   FailedListener,
                                   Receiver,
                                   ProgressListener,
                                   FinishedListener {
    
    protected Upload upload;
    protected String directory;
    protected File file;
    protected long maxSize;
    protected ProgressBar progressBar; 
    protected boolean cancelled = false;
    protected Long contentLength;
    protected Button cancelProcessing;
    protected HorizontalLayout processingLayout;
    
    public UploadComponent(String buttonCaption, String sessionId, int maxSize) {
        upload = new Upload();
        this.addComponent(upload);
        this.maxSize = maxSize;
        upload.setReceiver( this); 
        this.directory = "C:\\temp\\";
        upload.setButtonCaption(buttonCaption);
        upload.addSucceededListener((Upload.SucceededListener) this);
        upload.addFailedListener((Upload.FailedListener) this);
        upload.addProgressListener((Upload.ProgressListener) this);
        upload.addFinishedListener((Upload.FinishedListener) this);

        processingLayout = new HorizontalLayout();
        processingLayout.setSpacing(true);
        processingLayout.setVisible(false);
        this.addComponent(processingLayout);

        progressBar = new ProgressBar();
        processingLayout.addComponent(progressBar);
        
        cancelProcessing = new Button("cancel", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				cancelled = true;
                upload.interruptUpload();
				
			}
        });
        processingLayout.addComponent(cancelProcessing);
    }

    @Override
    public OutputStream receiveUpload(String filename,  String MIMEType) {
        FileOutputStream fos = null;
        String extension = "";
		if (MIMEType.equals("image/png")){
			extension = ".png";
		}
		else if (MIMEType.equals("image/jpeg")) {
			extension = ".jpeg";
		}
		else if (MIMEType.equals("image/jpg")) {
			extension = ".jpg";
		}
		//TODO else throw exception!!!!
		
        file = new File(directory, getUI().getSession().getSession().getId()+extension);
 
        try {
            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fos;
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {
        this.contentLength = contentLength;
        if (maxSize < contentLength) {
            upload.interruptUpload();
            return;
        }
        
        processingLayout.setVisible(true);
        progressBar.setValue(new Float(readBytes / (float) contentLength));
    }
    
    @Override
    public void uploadFinished(FinishedEvent event) {
        processingLayout.setVisible(false);
    }
    
    
    @Override
    public void uploadFailed(FailedEvent event) {
        processingLayout.setVisible(false);
        if (contentLength != null && maxSize < contentLength) {
            Notification.show("File too large", 
                    "Your file "+contentLength/1000+"Kb long. Please select an file smaller than " + maxSize / 1000 + "Kb", Notification.Type.ERROR_MESSAGE);
        } else if (cancelled) {
            
        } else {
            Notification.show("There was a problem uploading your file.",
                    "<pre>"+event.getReason().getStackTrace().toString()+"</pre>", Notification.Type.ERROR_MESSAGE);
        }
        
        try{
            file.delete();
        } catch (Exception e) {
            
        }
        
        afterUploadFailed(event);
    }

    public void afterUploadFailed(FailedEvent event) {
    }
    
    public String getDirectory() {
        return directory;
    }

    public File getFile() {
        return file;
    }
    
    
}
