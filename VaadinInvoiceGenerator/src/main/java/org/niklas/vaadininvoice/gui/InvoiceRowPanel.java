package org.niklas.vaadininvoice.gui;


import java.text.DecimalFormat;
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
	private Table invoiceRowTable;
	private Button editButton;
	private Button addRowButton;
	private HashMap<Integer, InvoiceRow> invoiceRows;
	private TextField grandTotal;
	
	private boolean editable;
	private int nextAvailableRowId;

	public InvoiceRowPanel() {
		editable = false;
		nextAvailableRowId = 0;

		invoiceRowTable = new Table();
		editButton = new Button("Edit");
		addRowButton = new Button("Add Item");
		invoiceRows = new HashMap<Integer, InvoiceRow>();
		grandTotal = new TextField("Grand Total");
		
		setActionListeners();
		setLayout();
	}
	
	private void setLayout() {
		VerticalLayout mainLayout = new VerticalLayout();
		HorizontalLayout buttonsAndTotalLayout = new HorizontalLayout();
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		VerticalLayout secondLayout = new VerticalLayout();
		HorizontalLayout totalLayout = new HorizontalLayout();
		mainLayout.addComponent(createTable());
		buttonsLayout.addComponent(addRowButton);
		buttonsLayout.addComponent(editButton);
		secondLayout.setSizeFull();
		totalLayout.setSizeFull();
		totalLayout.addComponent(createTotal());
		buttonsLayout.setMargin(true);
		buttonsAndTotalLayout.addComponent(buttonsLayout);
		buttonsAndTotalLayout.addComponent(secondLayout);
		buttonsAndTotalLayout.addComponent(totalLayout);
		mainLayout.addComponent(buttonsAndTotalLayout);
		setContent(mainLayout);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		
	}

	private void setActionListeners() {
		editButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				editable = !editable;
				invoiceRowTable.setEditable(editable);
				if (editable) editButton.setCaption("Done");
				else editButton.setCaption("Edit");
				updateRows();
			}
		});
		
		invoiceRowTable.addValueChangeListener(new ValueChangeListener() {
			
			public void valueChange(ValueChangeEvent event) {
				updateRows();
				
			}
		});
		
		addRowButton.addClickListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				addRow();
			}
		});
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
		invoiceRowTable.addContainerProperty("Description", String.class, "Product");
		invoiceRowTable.addContainerProperty("Quantity", Integer.class, 1);
		invoiceRowTable.addContainerProperty("Unit Price", Double.class, (double) 1.00);
		invoiceRowTable.addContainerProperty("Tax Rate %", Double.class, (double) 23);
		invoiceRowTable.addContainerProperty("Total", Double.class, (double) 1.00);
		invoiceRowTable.setEditable(false);
		invoiceRowTable.setSizeFull();
		invoiceRowTable.setHeight(140f, Unit.PIXELS);
		addRow();
		layout.addComponent(invoiceRowTable);
		layout.setMargin(true);
		return layout;
	}

	private void addRow() {
		int id = generateId();
		invoiceRowTable.addItem(id);
		InvoiceRow row = new InvoiceRow(id, 1, "", (double) 1, (double) 23);
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
			row.setTaxRate((Double) rowItem.getItemProperty("Tax Rate %").getValue());
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
	
	private String calculateTotal(HashMap<Integer, InvoiceRow> invoiceRows){
		Double total = (double) 0;
		for (InvoiceRow row:new ArrayList<InvoiceRow>(invoiceRows.values())) {
			total += row.getTotal();
		}
		return new DecimalFormat("###.##").format(total);
	}

	public HashMap<Integer, InvoiceRow> getInvoiceRows() {
		return invoiceRows;
	}
	
	
}
