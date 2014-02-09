package org.niklas.vaadininvoice.gui;


import java.util.ArrayList;
import java.util.HashMap;

import org.niklas.vaadininvoice.invoice.InvoiceRow;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
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
	TextField grandTotal = new TextField("Grand Total");
	private int nextAvailableRowId;

	public InvoiceRowPanel() {
		nextAvailableRowId = 0;

		HorizontalLayout mainLayout = new HorizontalLayout();

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		VerticalLayout secondLayout = new VerticalLayout();
		HorizontalLayout totalLayout = new HorizontalLayout();

		addComponent(createTable());
		
		buttonsLayout.addComponent(addRowButton);
		addRowButton.addClickListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				addRow();
			}
		});
		buttonsLayout.addComponent(editButton);
		editButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				editable = !editable;
				invoiceRowTable.setEditable(editable);
				if (editable) editButton.setCaption("Done");
				else editButton.setCaption("Edit");
				updateRows();
			}
		});
		
//		buttonsLayout.setSizeFull();
		secondLayout.setSizeFull();
		totalLayout.setSizeFull();
		invoiceRowTable.addValueChangeListener(new ValueChangeListener() {
			
			public void valueChange(ValueChangeEvent event) {
				updateRows();
				
			}
		});
		totalLayout.addComponent(createTotal());
		
		mainLayout.addComponent(buttonsLayout);
		mainLayout.addComponent(secondLayout);
		mainLayout.addComponent(totalLayout);
		addComponent(mainLayout);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
	}
	
	private Component createTotal() {
		VerticalLayout layout = new VerticalLayout();
		grandTotal.setReadOnly(true);
		grandTotal.setValue(calculateTotal(invoiceRows).toString());
		layout.addComponent(grandTotal);
		layout.setMargin(true);
		return layout;
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
		grandTotal.setReadOnly(false);
		grandTotal.setValue(calculateTotal(invoiceRows).toString());
		grandTotal.setReadOnly(true);
	}

	private int generateId() {
		int id = nextAvailableRowId;
		nextAvailableRowId++;
		return nextAvailableRowId;
	}
	
	private Double calculateTotal(HashMap<Integer, InvoiceRow> invoiceRows){
		Double total = (double) 0;
		for (InvoiceRow row:new ArrayList<InvoiceRow>(invoiceRows.values())) {
			total += row.getTotal();
		}
		return total;
	}

	public HashMap<Integer, InvoiceRow> getInvoiceRows() {
		return invoiceRows;
	}
	
	
}
