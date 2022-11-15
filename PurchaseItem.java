package com.ffh.application.data;

import com.ffh.application.Database;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

/*  The class PurchaseItem is used to store the objects that we pull from the 'inventory' table in the Database  */
public class PurchaseItem extends Data{

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private Integer
            item_no,
            quantity;
    private String
            item_name;
    private Double
            price;

    public PurchaseItem(Integer item_no, String item_name, Integer quantity, Double price) {
        this.item_no = item_no;
        this.item_name = item_name;
        this.quantity = quantity;
        this.price = price;
        this.addPropertyChangeListener(PurchaseItem::handlePropertyChangeEvent);  // ensures every PurchaseItem will have a property change listener
    }

    public int getItem_no() {
        return item_no;
    }

    public void setItem_no(int item_no) {
        this.item_no = item_no;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        String oldName = this.item_name;
        this.item_name = item_name;
        propertyChangeSupport.firePropertyChange("item_name", oldName, item_name);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public static void handlePropertyChangeEvent(PropertyChangeEvent propertyChangeEvent) {
        String propertyName = propertyChangeEvent.getPropertyName();

        try {
            /*  sloppy execution but its only proof of concept so who cares  */
            if ("item_name".equals(propertyName)) {
                String updateCommand = "UPDATE inventory SET " + propertyName + "='" +
                        propertyChangeEvent.getNewValue() + "' WHERE " +
                        propertyName + "='" + propertyChangeEvent.getOldValue() + "';";
                new Database<>().updateTable(updateCommand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "PurchaseItem{" +
                "item_no=" + item_no +
                ", quantity=" + quantity +
                ", item_name='" + item_name + '\'' +
                ", price=" + price +
                '}';
    }
}