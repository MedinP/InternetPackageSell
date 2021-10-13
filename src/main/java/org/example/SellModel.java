package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.*;
import javafx.scene.control.Alert;


public class SellModel {

    List<SellModel> sellList;

    private final IntegerProperty speed = new SimpleIntegerProperty(this,"speed");
    private final ObjectProperty bandwith = new SimpleObjectProperty(this,"bandwith");
    private final IntegerProperty contract = new SimpleIntegerProperty(this,"contract");
    private final IntegerProperty salesId = new SimpleIntegerProperty(this,"salesId");
    private final StringProperty FnameLname = new SimpleStringProperty(this,"FnameLname");
    private final StringProperty adress = new SimpleStringProperty(this,"adress");
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this,"date");
    private final ObjectProperty<ArrayList<String>> errorList = new SimpleObjectProperty<>(this, "errorList", new ArrayList<>());

    public SellModel() {}

    public SellModel(int speed,Object bandwith,int contract,int salesId,String FnameLname,String adress, LocalDate date) {
        this.speed.set(speed);
        this.bandwith.set(bandwith);
        this.contract.set(contract);
        this.salesId.set(salesId);
        this.FnameLname.set(FnameLname);
        this.adress.set(adress);
        this.date.set(date);
    }

    public int getSpeed() {return speed.get();}
    public void setSpeed(int speed) {this.speed.set(speed);}
    public IntegerProperty speedProperty() {return speed;}

    public Object getBandwith() {return bandwith.get();}
    public void setBandwith(Object bandwith) {this.bandwith.set(bandwith);}
    public ObjectProperty bandwithProperty() {return bandwith;}

    public int getContract() {return contract.get();}
    public void setContract(int contract) {this.contract.set(contract);}
    public IntegerProperty contractProperty() {return contract;}

    public int getId() {return salesId.get();}
    public void setId(int id) {this.salesId.set(id);}
    public IntegerProperty idProperty() {return salesId;}

    public String getFnameLname() {return FnameLname.get();}
    public void setFnameLname(String fnameLname) {this.FnameLname.set(fnameLname);}
    public StringProperty fnameLnameProperty() {return FnameLname;}

    public String getAdress() {return adress.get();}
    public void setAdress(String adress) {this.adress.set(adress);}
    public StringProperty adressProperty() {return adress;}

    public LocalDate getDate() {return date.get();}
    public void setDate(LocalDate date) {this.date.set(date);}
    public Property<LocalDate> dateProperty() {return date;}


    public ObjectProperty<ArrayList<String>> errorsProperty() {return errorList;}


    public boolean isValid() {
        boolean isValid = true;

        if (FnameLname.get() == null) {
            errorList.getValue().add(" Name and surname must be entered!");
            isValid = false;
        }
        if (adress.get() == null) {
            errorList.getValue().add(" Address must be entered!");
            isValid = false;
        }
        if (bandwith.get() == null) {
            errorList.getValue().add(" The flow must be selected!");
            isValid = false;
        }
        if (contract.get() == 0) {
            errorList.getValue().add(" The duration of the contract must be determined!");
            isValid = false;
        }
        if (speed.get() == 0) {
            errorList.getValue().add(" The flow rate must be selected!");
            isValid = false;
        }
        if (date.get() == null) {
            errorList.getValue().add(" The date must be selected!");
            isValid = false;
        }
        return isValid;
    }

    public String save() throws ClassNotFoundException {

        String message = "Entry completed successfully!";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The sale successfully realized!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        if (isValid()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/internet_prodaja","root","*****");) {
                PreparedStatement st = conn.prepareStatement("insert into prodaja (salesId,FnameLname,adress,contract,speed,bandwith,date) values (?,?,?,?,?,?,?)");
                st.setString(1, Integer.toString(salesId.get()));
                st.setString(2, FnameLname.get());
                st.setString(3, adress.get());
                st.setString(4, Integer.toString(contract.get()));
                st.setString(5, Integer.toString(speed.get()));
                st.setString(6, bandwith.get().toString());
                st.setString(7, date.get().toString());
                st.execute();

            } catch (SQLException e) {
                message = "ERROR! " + e.getMessage();
            }
        }
        return message;
    }

    @Override
    public String toString() {
        return
                "ID: " + salesId.get() + "\n" +
                        "Name and surname: " + FnameLname.get() + "\n" +
                        "Adress: " + adress.get() + "\n" +
                        "Contract: " + contract.get() + " /god" + "\n" +
                        "Speed: " + speed.get() + " /mbit" +"\n" +
                        "Bandwith: " + bandwith.get() + " /gb" + "\n" +
                        "Date: " + date.get();
    }

}
