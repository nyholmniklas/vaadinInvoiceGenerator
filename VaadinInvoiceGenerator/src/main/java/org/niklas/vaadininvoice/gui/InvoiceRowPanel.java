package org.niklas.vaadininvoice.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.niklas.vaadininvoice.invoice.InvoiceRow;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class InvoiceRowPanel extends Panel {
	private Table invoiceRowTable = new Table();
	
	private Button editButton = new Button("Edit");
	private boolean editable = false;
	private Button addRowButton = new Button("Add Item");
	private HashMap<Integer, InvoiceRow> invoiceRows = new HashMap<Integer, InvoiceRow>();
	private int nextAvailableRowId;

	public InvoiceRowPanel() {
		nextAvailableRowId = 0;

		HorizontalLayout mainLayout = new HorizontalLayout();
		VerticalLayout firstLayout = new VerticalLayout();
		VerticalLayout secondLayout = new VerticalLayout();
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		VerticalLayout totalLayout = new VerticalLayout();

		addComponent(createTable());
		
		buttonsLayout.addComponent(addRowButton);
		addRowButton.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				addRow();
			}
		});
		buttonsLayout.addComponent(editButton);
		editButton.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				editable = !editable;
				invoiceRowTable.setEditable(editable);
				if (editable) editButton.setCaption("Done");
				else editButton.setCaption("Edit");
				updateRows();
			}
		});
		
		firstLayout.setSizeFull();
		secondLayout.setSizeFull();
		buttonsLayout.setSizeFull();
		totalLayout.setSizeFull();
		
		mainLayout.addComponent(firstLayout);
		mainLayout.addComponent(secondLayout);
		mainLayout.addComponent(buttonsLayout);
		mainLayout.addComponent(totalLayout);
		addComponent(mainLayout);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
	}
	
	public Component createTable(){
		VerticalLayout layout = new VerticalLayout();
		invoiceRowTable.addContainerProperty("Quantity", Integer.class, 1);
		invoiceRowTable.addContainerProperty("Description", String.class, "Product");
		invoiceRowTable.addContainerProperty("Unit Price", Double.class, (double) 1);
		invoiceRowTable.addContainerProperty("Total", Double.class, (double) 1);
		invoiceRowTable.setEditable(false);
		invoiceRowTable.setSizeFull();
		invoiceRowTable.setHeight(130f, UNITS_PIXELS);
		addRow();
		layout.addComponent(invoiceRowTable);
		layout.setMargin(true);
		return layout;
	}

	private void addRow() {
		int id = generateId();
		invoiceRowTable.addItem(id);
		InvoiceRow row = new InvoiceRow(id, 1, "", (double) 1);
		invoiceRows.put(id, row);
		updateRows();
	}

	private void updateRows() {
		ArrayList<Integer> itemIdsToRemove = new ArrayList<Integer>();
		for (InvoiceRow row : invoiceRows.values()) {
			Item rowItem = invoiceRowTable.getItem(row.getId());
			row.setQuantity((Integer) rowItem.getItemProperty("Quantity")
					.getValue());
			row.setDescription((String) rowItem.getItemProperty("Description")
					.getValue());
			row.setPrice((Double) rowItem.getItemProperty("Unit Price").getValue());
			rowItem.getItemProperty("Total").setValue(row.getTotal());
			if (row.getQuantity() == 0) {
				itemIdsToRemove.add(row.getId());
			}
		}
		for (Integer id:itemIdsToRemove) {
			invoiceRows.remove(id);
			invoiceRowTable.removeItem(id);
		}
	}

	private int generateId() {
		int id = nextAvailableRowId;
		nextAvailableRowId++;
		return nextAvailableRowId;
	}

	public HashMap<Integer, InvoiceRow> getInvoiceRows() {
		return invoiceRows;
	}
	
	
}
