package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.dto.Item;

import javax.swing.*;
import java.sql.*;

public class DeleteItemController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

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
    void btnDelete(ActionEvent event) {
        try {

            String itemCode=txtItemId.getText();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ThagakadeJavaFX", "root", "1234");
            String sql = "DELETE FROM Item WHERE itemCode=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, itemCode);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null,"Deleted SuccessFully!!!");
            resetAllInfo();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void resetAllInfo() {
        txtItemId.setText("");
        txtDescription.setText("");
        txtPackageSize.setText("");
        txtUnitPrice.setText("");
        txtQuantityOnHand.setText("");
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

            }else {
                JOptionPane.showMessageDialog(null,"Check entered ItemCode...!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
