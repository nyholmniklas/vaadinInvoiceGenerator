package org.niklas.vaadininvoice.gui;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

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
				else {
					editButton.setCaption("Edit");
				}
				updateRows();
				updateTotal();
			}
		});
		
		invoiceRowTable.addValueChangeListener(new ValueChangeListener() {
			
			public void valueChange(ValueChangeEvent event) {
				updateRows();
				updateTotal();
			}
		});
		
		addRowButton.addClickListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				addRow();
				updateRows();
				updateTotal();
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
		subTotalValueLabel.setValue(invoiceBean.getBean().getSubTotal().toString());
		vatTotalValueLabel.setValue(invoiceBean.getBean().getVatTotal().toString());
		totalValueLabel.setValue("<b>"+invoiceBean.getBean().getTotal().toString()+"</b>");
	}

	public Component createTable(){
		InvoiceRow row = new InvoiceRow(generateId());
		invoiceRowTable.addItem(row.getId());
		invoiceBean.getBean().addRow(row.getId(), row);
		invoiceRowTable.addContainerProperty("Description", String.class, row.getDescription());
		invoiceRowTable.addContainerProperty("Quantity", Integer.class, row.getQuantity());
		invoiceRowTable.addContainerProperty("Unit Price", String.class, row.getPrice().toString());
		invoiceRowTable.addContainerProperty("VAT %", String.class, row.getTaxRate().toString());
		invoiceRowTable.addContainerProperty("SubTotal (0% VAT)", String.class, row.getSubTotal().toString());
		invoiceRowTable.addContainerProperty("Total", String.class, row.getTotal().toString());
		invoiceRowTable.setColumnWidth("VAT %", 100);
		invoiceRowTable.setColumnWidth("SubTotal (0% VAT)", 100);
		invoiceRowTable.setEditable(false);
		invoiceRowTable.setSizeFull();
		invoiceRowTable.setHeight(140f, Unit.PIXELS);
		updateRows();
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(invoiceRowTable);
		layout.setMargin(true);
		return layout;
	}

	private void addRow() {
		int id = generateId();
		invoiceRowTable.addItem(id);
		InvoiceRow row = new InvoiceRow(id);
		invoiceBean.getBean().addRow(id, row);
		updateRows();
	}

	private void updateRows() {
		ArrayList<Integer> itemIdsToRemove = new ArrayList<Integer>();
		for (InvoiceRow invoiceRow : invoiceBean.getBean().getRows().values()) {
			Item tableRow = invoiceRowTable.getItem(invoiceRow.getId());
			invoiceRow.setQuantity((Integer) tableRow.getItemProperty("Quantity")
					.getValue());
			invoiceRow.setDescription((String) tableRow.getItemProperty("Description")
					.getValue());
			invoiceRow.setPrice(new BigDecimal(tableRow.getItemProperty("Unit Price").getValue().toString()));
			invoiceRow.setTaxRate(new BigDecimal(tableRow.getItemProperty("VAT %").getValue().toString()));
			System.out.println("Row with id "+invoiceRow.getId()+ "has " + invoiceRow.getQuantity() + "products now.");
			System.out.println("Even in bean row with id "+invoiceBean.getBean().getRows().get(invoiceRow.getId()).getId()+ "has " + invoiceBean.getBean().getRows().get(invoiceRow.getId()).getQuantity() + "products now.");
			tableRow.getItemProperty("SubTotal (0% VAT)").setValue(invoiceRow.getSubTotal().toString());
			tableRow.getItemProperty("Total").setValue(invoiceRow.getTotal().toString());
			tableRow.getItemProperty("Unit Price").setValue(invoiceRow.getPrice().toString());
			if (invoiceRow.getQuantity() == 0) {
				itemIdsToRemove.add(invoiceRow.getId());
			}
		}
		for (Integer id:itemIdsToRemove) {
			invoiceBean.getBean().removeRowById(id);
			invoiceRowTable.removeItem(id);
		}
	}

	private int generateId() {
		int id = nextAvailableRowId;
		nextAvailableRowId++;
		return id;
	}
	
}
