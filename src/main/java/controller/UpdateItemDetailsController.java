package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.dto.Item;

import javax.swing.*;
import java.sql.*;

public class UpdateItemDetailsController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

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
    void btnBack(ActionEvent event) {


    }

    @FXML
    void btnSearch(ActionEvent event) {
    String itemCode=txtItemId.getText();
        try {
            if(getItem(itemCode)!=null){
               Item item=getItem(itemCode);
                txtDescription.setText(item.getDescription());
                txtPackageSize.setText(item.getPackSize());
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                txtQuantityOnHand.setText(String.valueOf(item.getQtyOnHand()));
                unlockTextFields();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void unlockTextFields() {
        txtDescription.setEditable(true);
        txtPackageSize.setEditable(true);
        txtUnitPrice.setEditable(true);
        txtQuantityOnHand.setEditable(true);
    }

    @FXML
    void btnUpdate(ActionEvent event) {

        try {
            String itemCode=txtItemId.getText();
            String description=txtDescription.getText();
            String packageSize=txtPackageSize.getText();
            double unitPrice= Double.parseDouble(txtUnitPrice.getText());
            int quantityOnHand= Integer.parseInt(txtQuantityOnHand.getText());
            Connection  connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX", "root", "1234");
            String SQL = "UPDATE Item SET  description=?,packageSize=?, unitPrice=?,quantityOnHand=? WHERE itemCode=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, description);
            preparedStatement.setString(2, packageSize);
            preparedStatement.setDouble(3, unitPrice);
            preparedStatement.setInt(4, quantityOnHand);
            preparedStatement.setString(5, itemCode);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null,"Updated SuccessFully");
            clearForm();
            lockFeilds();


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());


        }




    }

    private void lockFeilds() {
        txtDescription.setEditable(false);
        txtQuantityOnHand.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtPackageSize.setEditable(false);
    }

    private void clearForm() {
        txtItemId.setText("");
        txtDescription.setText("");
        txtPackageSize.setText("");
        txtUnitPrice.setText("");
        txtQuantityOnHand.setText("");
    }

    private Item getItem(String itemCode) throws SQLException {


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX", "root", "1234");
        String sql = "SELECT * FROM Item WHERE itemCode=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, itemCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            //| itemCode | description | packageSize | unitPrice | quantityOnHand
            String itemDescription = resultSet.getString("description");
            String packageSize=resultSet.getString("packageSize");
            double unitPrice=resultSet.getDouble("unitPrice");
            int quantityOnHand=resultSet.getInt("quantityOnHand");
            Item item=new Item(itemCode,itemDescription,packageSize,unitPrice,quantityOnHand);
            return item;
        }
        return null;
    }


}
