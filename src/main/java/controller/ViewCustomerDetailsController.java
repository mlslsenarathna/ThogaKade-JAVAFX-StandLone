package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewCustomerDetailsController implements Initializable {
    ObservableList<Customer> customersList= FXCollections.observableArrayList();

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<?, ?> colCustomerTitle;


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
        Stage stage = (Stage) btnBack.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Home_Page_Form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX", "root", "1234");
            String SQL = "SELECT*FROM customer;";
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(SQL);
            while(resultSet.next()){
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


        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colCustomerTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tblCustomerInfo.setItems(customersList);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }
}
