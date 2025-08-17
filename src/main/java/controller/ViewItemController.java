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
import model.dto.Item;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewItemController implements Initializable {
    ObservableList<Item> ItemList= FXCollections.observableArrayList();

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colPackageSize;

    @FXML
    private TableColumn<?, ?> colQuantityOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblItemInfo;

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
            String SQL = "SELECT*FROM Item;";
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(SQL);
            while(resultSet.next()){
               Item item=new Item(
                       //itemCode | description | packageSize | unitPrice | quantityOnHand
                       resultSet.getString("itemCode"),
                       resultSet.getString("description"),
                       resultSet.getString("packageSize"),
                       resultSet.getDouble("unitPrice"),
                       resultSet.getInt("quantityOnHand")
                );
                ItemList.add(item);
            }
            colItemId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colPackageSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQuantityOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));


            tblItemInfo.setItems(ItemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }
}
