/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.niklas.vaadininvoice;

import org.niklas.vaadininvoice.controller.VaadinInvoiceController;
import org.niklas.vaadininvoice.gui.VaadinInvoiceGui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Entry point
 */
@SuppressWarnings("serial")
public class VaadinInvoiceApp extends UI
{
    private VaadinInvoiceGui gui;
    private VaadinInvoiceController controller;
    
	@Override
	protected void init(VaadinRequest request) {
    	controller = new VaadinInvoiceController();

        gui = new VaadinInvoiceGui(controller);
        setContent(gui);
        gui.setSizeFull();
		String id = getSession().getSession().getId();
    	controller.setSessionId(id);
		System.out.println("Id of this session is:" + id);
		
	}
    
}
