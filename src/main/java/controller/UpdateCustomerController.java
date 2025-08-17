package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.Customer;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClearForm;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnSet;

    @FXML
    private ComboBox<String> cmdCustomerTitle;

    @FXML
    private DatePicker doBBirthday;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerCity;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerPostalCode;

    @FXML
    private TextField txtCustomerProvince;

    @FXML
    private TextField txtCustomerSalary;

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

    @FXML
    void btnClearForm(ActionEvent event) {
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerCity.setText("");
        txtCustomerSalary.setText("");
        txtCustomerProvince.setText("");
        txtCustomerPostalCode.setText("");
    }

    @FXML
    void btnSearch(ActionEvent event) {
        try {
                String checkID=txtCustomerId.getText();
                boolean isHave=isValidCustId(checkID);

        if(isValidCustId(checkID)) {

                Customer customer = getSearchCustomer(checkID);
                unlockTextFields();
                int index = getIndexCustomerTitleComboBox(customer);
                cmdCustomerTitle.getSelectionModel().select(index);
                txtCustomerName.setText(customer.getName());
                txtCustomerAddress.setText(customer.getAddress());
                txtCustomerCity.setText(customer.getCity());
                txtCustomerSalary.setText(String.valueOf(customer.getSalary()));
                txtCustomerProvince.setText(customer.getProvince());
                txtCustomerPostalCode.setText(customer.getPostalCode());
                doBBirthday.setValue(customer.getDob());
        }else{
            JOptionPane.showMessageDialog(null,"Please Check.. you have entered customer id...!!!");

        }
        }catch (SQLException e){

        }

    }

    private void unlockTextFields() {
        txtCustomerName.setEditable(true);
        txtCustomerAddress.setEditable(true);
        txtCustomerCity.setEditable(true);
        txtCustomerSalary.setEditable(true);
        txtCustomerProvince.setEditable(true);
        txtCustomerPostalCode.setEditable(true);

    }

    private int getIndexCustomerTitleComboBox(Customer customer){
        //"", "Mrs.", "Ms.", "Miss", "Dr.", "Rev."
        if(customer.getTitle().equals("Mr.")){
            return 0;
        }else if(customer.getTitle().equals("Mrs.")){
            return 1;
        }else if(customer.getTitle().equals("Ms.")){
            return 2;
        }else if(customer.getTitle().equals("Miss.")){
            return 3;
        }else if(customer.getTitle().equals("Dr.")){
            return 4;
        }else if(customer.getTitle().equals("Rev.")){
            return 5;
        }else{
            return -1;
        }

    }
    private Customer getSearchCustomer(String id) throws SQLException {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX", "root", "1234");
            String sql = "SELECT * FROM customer WHERE custId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            String custId = resultSet.getString("custId");
            String custTitle = resultSet.getString("custTitle");
            String custName = resultSet.getString("custName");
            LocalDate custDob = resultSet.getDate("custDoB").toLocalDate();
            Double custSalary = resultSet.getDouble("custSalary");
            String custAddress = resultSet.getString("custAddress");
            String custCity = resultSet.getString("custCity");
            String custProvince = resultSet.getString("custProvince");
            String custPostalCode = resultSet.getString("custPostalCode");
            Customer customer = new Customer(custId, custTitle, custName, custDob, custSalary, custAddress, custCity, custProvince, custPostalCode);
            return customer;
        }
        return null;

    }
    private boolean isValidCustId(String id) throws SQLException {

        Connection  connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX", "root", "1234");
        String sql = "SELECT COUNT(*) FROM customer WHERE custId = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){

            return resultSet.getInt(1)==1;
        }

        return false;


    }

    @FXML
    void btnSet(ActionEvent event) {
        try {
            String customerID = txtCustomerId.getText();
            String customerTitle = cmdCustomerTitle.getValue();
            String customerName = txtCustomerName.getText();
            String customerAddress = txtCustomerAddress.getText();
            String customerCity = txtCustomerCity.getText();
            String customerProvince = txtCustomerProvince.getText();
            String customerPostalCode = txtCustomerPostalCode.getText();
            Double customerSalary = Double.valueOf(txtCustomerSalary.getText());
            LocalDate customerDob = doBBirthday.getValue();

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX", "root", "1234");
            String SQL = "UPDATE customer SET custTitle=?, custName=?, custSalary=?, custAddress=?, custCity=?, custProvince=?, custPostalCode=?, custDoB=? WHERE custId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, customerTitle);
            preparedStatement.setString(2, customerName);
            preparedStatement.setDouble(3, customerSalary);
            preparedStatement.setString(4, customerAddress);
            preparedStatement.setString(5, customerCity);
            preparedStatement.setString(6, customerProvince);
            preparedStatement.setString(7, customerPostalCode);
            preparedStatement.setDate(8, Date.valueOf(customerDob));
            preparedStatement.setString(9, customerID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
        }
    }
    private void loadCustTitles() {
        cmdCustomerTitle.getItems().addAll("Mr.", "Mrs.", "Ms.", "Miss", "Dr.", "Rev.");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustTitles();
    }
}
