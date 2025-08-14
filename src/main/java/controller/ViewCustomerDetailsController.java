package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.dto.Customer;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewCustomerDetailsController implements Initializable {
    ObservableList<Customer> customersList= FXCollections.observableArrayList();

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableView<Customer> tblCustomerInfo;

    @FXML
    void btnBack(ActionEvent event) {




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX", "root", "1234");
            String SQL = "SELECT * FROM customer ";
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(SQL);

            if(resultSet.next()){
                Customer customer=new Customer(
                        resultSet.getString("custId"),
                        resultSet.getString("custTitle"),
                        resultSet.getString("custName"),
                        resultSet.getDate("custDoB").toLocalDate(),
                        resultSet.getDouble("custSalary"),
                        resultSet.getString("custAddress"),
                        resultSet.getString("custCity"),
                        resultSet.getString("custProvince"),
                         resultSet.getString("custPostalCode")
                );
                customersList.add(customer);


            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
