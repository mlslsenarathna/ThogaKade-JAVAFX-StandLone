
package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.Customer;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClearForm;

    @FXML
    private Button btnConfirm;
    @FXML
    private DatePicker doBBirthday;

    @FXML
    private ComboBox<String> cmdCustomerTitle;

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

    private static Stage stage;
    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnClearForm(ActionEvent event) {
      clearFormData();

    }

    @FXML
    void btnConfirm(ActionEvent event) {

        try {
            String custId = txtCustomerId.getText();
            String custName = txtCustomerName.getText();
            String custTitle = cmdCustomerTitle.getSelectionModel().getSelectedItem();
            String custAddress = txtCustomerAddress.getText();
            Double custSalary = Double.valueOf(txtCustomerSalary.getText());
            String custCity = txtCustomerCity.getText();
            String custProvince = txtCustomerProvince.getText();
            String custPostalCode = txtCustomerPostalCode.getText();
            LocalDate custDob = doBBirthday.getValue();

            Customer customer = new Customer(custId, custTitle, custName, custDob, custSalary, custAddress, custCity, custProvince, custPostalCode);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX", "root", "1234");
            String SQL = "Insert into customer values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, custId);
            preparedStatement.setObject(2, custTitle);
            preparedStatement.setObject(3, custName);
            preparedStatement.setObject(4, custSalary);
            preparedStatement.setObject(5, custAddress);
            preparedStatement.setObject(6, custCity);
            preparedStatement.setObject(7, custProvince);
            preparedStatement.setObject(8, custPostalCode);
            preparedStatement.setObject(9, custDob);
            preparedStatement.executeUpdate();


            JOptionPane.showMessageDialog(null,"Successfully added...!");
            setCustomerId();
            clearFormData();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Please enter Values !!!!....");
            throw new RuntimeException(e);
        }catch(IllegalArgumentException | SQLException e){
            throw new RuntimeException(e);

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustTitles();
        setCustomerId();

    }

    private void setCustomerId() {
        if(getLastCustomerId()!=null){
            String lastCustomerId=getLastCustomerId();
            lastCustomerId = lastCustomerId.split("[A-Z]")[1]; // C001==> 001
            lastCustomerId= String.format("C%03d",(Integer.parseInt(lastCustomerId)+1));
            txtCustomerId.setText(lastCustomerId);

        }

    }
    private void clearFormData(){
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerCity.setText("");
        txtCustomerSalary.setText("");
        txtCustomerProvince.setText("");
        txtCustomerPostalCode.setText("");
    }

    private void loadCustTitles() {
        cmdCustomerTitle.getItems().addAll("Mr.", "Mrs.", "Ms.", "Miss", "Dr.", "Rev.");
    }
    private String getLastCustomerId(){

        try {
            Connection  connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX","root","1234");
            String SQL="SELECT custId FROM Customer ORDER BY custId DESC LIMIT 1;";
            PreparedStatement preparedStatement= connection.prepareStatement(SQL);
            ResultSet resultset= preparedStatement.executeQuery();
            return resultset.next() ? resultset.getString("custId") : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

