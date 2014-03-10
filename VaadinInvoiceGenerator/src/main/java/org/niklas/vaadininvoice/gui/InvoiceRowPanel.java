package org.niklas.vaadininvoice.gui;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.niklas.vaadininvoice.model.Invoice;
import org.niklas.vaadininvoice.model.InvoiceRow;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class InvoiceRowPanel extends Panel {
	private BeanItem<Invoice> invoiceBean;
	
	private Table invoiceRowTable;
	private Button editButton;
	private Button addRowButton;
	private Label totalLabel;
	private Label totalValueLabel;
	private Label subTotalLabel;
	private Label subTotalValueLabel;
	private Label vatTotalLabel;
	private Label vatTotalValueLabel;
	
	private boolean editable;
	private int nextAvailableRowId;


	public InvoiceRowPanel(BeanItem<Invoice> invoiceBean) {
		this.invoiceBean = invoiceBean;
		
		editable = false;
		nextAvailableRowId = 0;

		invoiceRowTable = new Table();
		editButton = new Button("Edit");
		addRowButton = new Button("Add Item");
//		invoiceRows = new HashMap<Integer, InvoiceRow>();
		totalLabel = new Label("<b>Total:</b>", ContentMode.HTML);
		subTotalLabel = new Label("Sub-total:");
		vatTotalLabel = new Label("VAT total:");
		
		totalValueLabel = new Label("", ContentMode.HTML);
		subTotalValueLabel = new Label();
		vatTotalValueLabel = new Label();
		
		setActionListeners();
		setLayout();
	}
	
	private void setLayout() {
		VerticalLayout mainLayout = new VerticalLayout();
		GridLayout buttonsAndTotalLayout = new GridLayout(3,1);
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		VerticalLayout secondLayout = new VerticalLayout();
		HorizontalLayout totalLayout = new HorizontalLayout();
		mainLayout.addComponent(createTable());
		buttonsLayout.addComponent(addRowButton);
		buttonsLayout.addComponent(editButton);
		secondLayout.setSizeFull();
		totalLayout.setSizeFull();
		totalLayout.addComponent(createTotal());

		buttonsAndTotalLayout.setHeight(100, Unit.PIXELS);
		buttonsAndTotalLayout.addComponent(buttonsLayout, 0, 0);
		buttonsAndTotalLayout.setComponentAlignment(buttonsLayout, Alignment.TOP_LEFT);
		buttonsAndTotalLayout.addComponent(totalLayout,2,0);
		buttonsAndTotalLayout.setComponentAlignment(totalLayout, Alignment.MIDDLE_RIGHT);
		buttonsLayout.setMargin(true);
		buttonsAndTotalLayout.setSizeFull();
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
		GridLayout layout = new GridLayout(2,3);
		updateTotal();
		layout.addComponent(subTotalValueLabel, 1,0);
		layout.addComponent(subTotalLabel,0,0);
		layout.addComponent(vatTotalValueLabel, 1,1);
		layout.addComponent(vatTotalLabel,0,1);
		layout.addComponent(totalValueLabel, 1,2);
		layout.addComponent(totalLabel,0,2);
		layout.setMargin(true);
		layout.setSpacing(true);
		return layout;
	}
	
	private void updateTotal(){
		subTotalValueLabel.setValue(invoiceBean.getBean().getSubTotalToString());
		vatTotalValueLabel.setValue(invoiceBean.getBean().getVatTotalToString());
		totalValueLabel.setValue("<b>"+invoiceBean.getBean().getTotalToString()+"</b>");
	}

	public Component createTable(){
		VerticalLayout layout = new VerticalLayout();
		invoiceRowTable.addContainerProperty("Description", String.class, "Product");
		invoiceRowTable.addContainerProperty("Quantity", Integer.class, 1);
		invoiceRowTable.addContainerProperty("Unit Price", Double.class, (double) 1.00);
		double defaultTaxRate = 23;
		invoiceRowTable.addContainerProperty("VAT %", Double.class, (double) defaultTaxRate);
		invoiceRowTable.addContainerProperty("SubTotal (0% VAT)", Double.class, (double) 1.00 * (defaultTaxRate/100));
		invoiceRowTable.addContainerProperty("Total", Double.class, (double) 1.00);
		invoiceRowTable.setColumnWidth("VAT %", 100);
		invoiceRowTable.setColumnWidth("SubTotal (0% VAT)", 100);
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
		invoiceBean.getBean().addRow(id, row);
		updateRows();
	}

	private void updateRows() {
		ArrayList<Integer> itemIdsToRemove = new ArrayList<Integer>();
		for (InvoiceRow row : invoiceBean.getBean().getRows().values()) {
			Item rowItem = invoiceRowTable.getItem(row.getId());
			row.setQuantity((Integer) rowItem.getItemProperty("Quantity")
					.getValue());
			row.setDescription((String) rowItem.getItemProperty("Description")
					.getValue());
			row.setPrice((Double) rowItem.getItemProperty("Unit Price").getValue());
			row.setTaxRate((Double) rowItem.getItemProperty("VAT %").getValue());
			rowItem.getItemProperty("SubTotal (0% VAT)").setValue((double) row.getSubTotal());
			rowItem.getItemProperty("Total").setValue((double) row.getTotal());
			rowItem.getItemProperty("Unit Price").setValue((double) row.getPrice());
			if (row.getQuantity() == 0) {
				itemIdsToRemove.add(row.getId());
			}
		}
		for (Integer id:itemIdsToRemove) {
			invoiceBean.getBean().removeRowById(id);
			invoiceRowTable.removeItem(id);
		}
		updateTotal();
	}

	private int generateId() {
		int id = nextAvailableRowId;
		nextAvailableRowId++;
		return id;
	}
	
//	private String calculateTotal(HashMap<Integer, InvoiceRow> invoiceRows){
//		Double total = (double) 0;
//		for (InvoiceRow row:new ArrayList<InvoiceRow>(invoiceRows.values())) {
//			total += row.getTotal();
//		}
//		return new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.US)).format(total);
//	}
//
//	
//	private String calculateSubTotal(HashMap<Integer, InvoiceRow> invoiceRows){
//		Double total = (double) 0;
//		for (InvoiceRow row:new ArrayList<InvoiceRow>(invoiceRows.values())) {
//			total += row.getSubTotal();
//		}
//		return new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.US)).format(total);
//	}
//	
//	private String calculateVat(HashMap<Integer, InvoiceRow> invoiceRows){
//		Double total = (double) 0;
//		for (InvoiceRow row:new ArrayList<InvoiceRow>(invoiceRows.values())) {
//			total += row.getVatTotal();
//		}
//		return new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.US)).format(total);
//	}
	
//	public HashMap<Integer, InvoiceRow> getInvoiceRows() {
//		return invoiceRows;
//	}
	
//	public void commitFields(){
//		
//	}
	
}
