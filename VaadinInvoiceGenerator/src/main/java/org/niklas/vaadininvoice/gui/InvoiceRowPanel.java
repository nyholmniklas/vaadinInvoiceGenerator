package org.niklas.vaadininvoice.gui;

import java.util.HashMap;

import org.niklas.vaadininvoice.invoice.InvoiceRow;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public class InvoiceRowPanel extends Panel {
	private Table invoiceRowTable = new Table();
	
	private Button editButton = new Button("Edit");
	private boolean editable = false;
	private Button addRowButton = new Button("Add Item");
	private HashMap<Integer, InvoiceRow> invoiceRows = new HashMap<Integer, InvoiceRow>();
	private int nextAvailableRowId;

	public InvoiceRowPanel() {
		nextAvailableRowId = 0;
		invoiceRowTable.addContainerProperty("Quantity", Integer.class, 1);
		invoiceRowTable.addContainerProperty("Description", String.class, "Product");
		invoiceRowTable.addContainerProperty("Unit Price", Double.class, (double) 1);
		invoiceRowTable.addContainerProperty("Total", Double.class, (double) 1);
		invoiceRowTable.setEditable(false);
		invoiceRowTable.setSizeFull();
		addRow();
		addComponent(invoiceRowTable);
		addComponent(addRowButton);
		addRowButton.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				addRow();
			}
		});
		addComponent(editButton);
		editButton.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				editable = !editable;
				invoiceRowTable.setEditable(editable);
				if (editable) editButton.setCaption("Done");
				else editButton.setCaption("Edit");
				updateRows();
			}
		});

	}

	private void addRow() {
		int id = generateId();
		invoiceRowTable.addItem(id);
		InvoiceRow row = new InvoiceRow(id, 1, "", (double) 1);
		invoiceRows.put(id, row);
		updateRows();
	}

	private void updateRows() {
		for (InvoiceRow row : invoiceRows.values()) {
			Item rowItem = invoiceRowTable.getItem(row.getId());
			row.setQuantity((Integer) rowItem.getItemProperty("Quantity")
					.getValue());
			row.setDescription((String) rowItem.getItemProperty("Description")
					.getValue());
			row.setPrice((Double) rowItem.getItemProperty("Unit Price").getValue());
			rowItem.getItemProperty("Total").setValue(row.getTotal());
			if (row.getQuantity() == 0) {
				invoiceRowTable.removeItem(row.getId());
				invoiceRows.remove(row.getId());
			}
		}
	}

	private int generateId() {
		int id = nextAvailableRowId;
		nextAvailableRowId++;
		return nextAvailableRowId;
	}
}
