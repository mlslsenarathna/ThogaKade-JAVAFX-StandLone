package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddItemController  implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClearForm;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtPackageSize;

    @FXML
    private TextField txtQuantityOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAdd(ActionEvent event) {
        try {
        String itemCode=txtItemId.getText();
        String itemDescription=txtDescription.getText();
        String packageSize=txtPackageSize.getText();
        Double unitPrice= Double.valueOf(txtUnitPrice.getText());
        int quantityOnHand= Integer.parseInt(txtQuantityOnHand.getText());

            Connection  connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX","root","1234");
            String SQL = "Insert into item values(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, itemCode);
            preparedStatement.setObject(2, itemDescription);
            preparedStatement.setObject(3, packageSize);
            preparedStatement.setObject(4, unitPrice);
            preparedStatement.setObject(5,quantityOnHand );
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null,"Item Added SuccessFully..");
            setItemID();
            clearForm();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnClearForm(ActionEvent event) {
        clearForm();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setItemID();
    }

    private void setItemID() {
        if(getLastItemId()!=null){
            String lastItemId=getLastItemId();
            lastItemId = lastItemId.split("[A-Z]")[1]; // C001==> 001
            lastItemId= String.format("S%03d",(Integer.parseInt(lastItemId)+1));
            txtItemId.setText(lastItemId);

        }
    }

    private String getLastItemId() {
        try {
            Connection  connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX","root","1234");
            String SQL="SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1;";
            PreparedStatement preparedStatement= connection.prepareStatement(SQL);
            ResultSet resultset= preparedStatement.executeQuery();
            return resultset.next() ? resultset.getString("itemCode") : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void clearForm(){
        txtDescription.setText("");
        txtPackageSize.setText("");
        txtUnitPrice.setText("");
        txtQuantityOnHand.setText("");
    }
}
