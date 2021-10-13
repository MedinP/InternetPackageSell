package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class SellController {

            SellModel sales;

            public SellController() {
            }

    @FXML
    private ChoiceBox cbSpeed;
    @FXML
    private ChoiceBox cbBandwith;
    @FXML
    private ChoiceBox cbContract;
    @FXML
    private TextField tfFnameLname;
    @FXML
    private TextField tfAdress;
    @FXML
    private ListView listView;
    @FXML
    private DatePicker date;


          ObservableList<SellModel> sellList = FXCollections.observableArrayList();

           private ObservableList<SellModel> show() throws ClassNotFoundException {
              Class.forName("com.mysql.cj.jdbc.Driver");
             ObservableList<SellModel> sellList1 = FXCollections.observableArrayList();
             try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/internet_prodaja", "root", "meme123meme");) {
                    Statement st = conn.createStatement();
                    st.executeQuery("select * from prodaja");
                   ResultSet rs = st.getResultSet();

                    while (rs.next()) {
                          SellModel sales = new SellModel(
                                      Integer.parseInt(rs.getString("speed")),
                                     rs.getString("bandwith"),
                                      Integer.parseInt(rs.getString("contract")),
                                     Integer.parseInt(rs.getString("salesId")),
                                      rs.getString("FnameLname"),
                                     rs.getString("adress"),
                                  rs.getDate("date").toLocalDate());

                        sellList1.add(sales);
                         sellList = sellList1;
                        }

                  } catch (SQLException e) {
                    System.out.println("ERROR: " + e.getMessage());
                 }

              return sellList1;
            }
            @FXML
            private void initialize() throws ClassNotFoundException {
              sales = new SellModel();

              cbSpeed.getItems().addAll(2, 5, 10, 20, 50, 100);
            cbSpeed.valueProperty().bindBidirectional(sales.speedProperty());
              cbBandwith.getItems().addAll(1, 5, 10, 100, "Flat");
            cbBandwith.valueProperty().bindBidirectional(sales.bandwithProperty());
             cbContract.getItems().addAll(1, 2);
             cbContract.valueProperty().bindBidirectional(sales.contractProperty());
             tfFnameLname.textProperty().bindBidirectional(sales.fnameLnameProperty());
              tfAdress.textProperty().bindBidirectional(sales.adressProperty());
              date.valueProperty().bindBidirectional(sales.dateProperty());

              show();
             listView.setItems(sellList);

            }

           @FXML
            private void deleteSales() throws ClassNotFoundException {
             Class.forName("com.mysql.cj.jdbc.Driver");

              int selIdx = listView.getSelectionModel().getSelectedIndex();
              String modId = null;

              for (SellModel mod : sellList) {
                    if (selIdx == sellList.indexOf(mod)) {
                          modId = Integer.toString(mod.getId());
                        }
                  }

              try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/internet_prodaja", "root", "meme123meme");) {
                    PreparedStatement st = conn.prepareStatement("delete from prodaja where salesId=?");
                    st.setString(1, modId);
                   st.execute();
                    listView.setItems(show());

                  } catch (SQLException e) {
                    System.out.println("ERROR! " + e.getMessage());
                  }

           }

          @FXML
   private void sell() throws ClassNotFoundException {

              if (sales.isValid()) {

                    sales.setId(sellList.size() + 1);
                    sales.save();
                    listView.setItems(show());

                  } else {
                    StringBuilder errMsg = new StringBuilder();

                    ArrayList<String> errList = sales.errorsProperty().get();

                    for (String errList1 : errList) {
                          errMsg.append(errList1);
                        }

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("The sale cannot be made!");
                   alert.setHeaderText(null);
                    alert.setContentText(errMsg.toString());
                   alert.showAndWait();
                    errList.clear();

                 }
            }
        }
