package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class HomePageController  {
    private Stage addCustomerStage=new Stage();
    private Stage updateCustomerStage=new Stage();
    private Stage deleteCustomerStage=new Stage();
    private Stage veiwCustomerStage=new Stage();
    private Stage addItemStage=new Stage();
    private Stage updateItemStage=new Stage();
    private Stage deleteItemStage=new Stage();
    private Stage veiwItemStage=new Stage();

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUpdateCustomer;

    @FXML
    private Button btnUpdateItemDetails;

    @FXML
    private Button btnVeiwCustomerDetails;

    @FXML
    private Button btnVeiwItemDetails;


    @FXML
    void btnAddCustomer(ActionEvent event)  {

           Stage stage = (Stage) btnAddCustomer.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Add_Customer_Form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnAddItem(ActionEvent event) {
        Stage stage= (Stage) btnAddItem.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Add_Item_Form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnDeleteCustomer(ActionEvent event) {
        Stage stage= (Stage) btnDeleteCustomer.getScene().getWindow();

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Delete_Customer_Info.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnDeleteItem(ActionEvent event) {

        Stage stage= (Stage) btnDeleteItem.getScene().getWindow();

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Delete_Item_Details.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnExit(ActionEvent event) {


    }

    @FXML
    void btnUpdateCustomer(ActionEvent event)  {
        Stage stage= (Stage) btnUpdateCustomer.getScene().getWindow();

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Update_Customer_Form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    @FXML
    void btnUpdateItemDetails(ActionEvent event) {
        Stage stage= (Stage) btnUpdateItemDetails.getScene().getWindow();

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/Update_Item_Details.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void btnVeiwCustomerDetails(ActionEvent event) {
        Stage stage= (Stage) btnVeiwCustomerDetails.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/View_Customer_Details.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnVeiwItemDetails(ActionEvent event) {
        Stage stage= (Stage) btnVeiwItemDetails.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/View_Item_Details.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
