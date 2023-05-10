package com.example.javafxlogin;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button button_log_out;

    @FXML
    private ImageView imageView;
    @FXML
    public StackPane stackPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label phoneNumberLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_log_out.setOnAction(event -> DBUtils.changeScene(event, "hello-view.fxml", "Log In"));
    }

    public void uploadPhoto(){


        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter("Images (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            try{
                Image image = new Image(new FileInputStream(selectedFile));
                addImageToTheDatabase(selectedFile.getAbsolutePath());
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Error while downloading image: "+ e.getMessage());
            }
        }
        imageView.toFront();



    }

    private void addImageToTheDatabase(String imagePath)  {
        Connection connection = null;
        PreparedStatement psUpdate = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javafx", "fenrirm", "fenrirm");
            psUpdate = connection.prepareStatement("UPDATE user SET image = ? WHERE phoneNumber = ?");
            psUpdate.setString(1, imagePath);
            psUpdate.setString(2, phoneNumberLabel.getText().substring(phoneNumberLabel.getText().indexOf(':') + 1));
            psUpdate.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (psUpdate != null) {
                try {
                    psUpdate.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void myTests(){

    }

    public void setUserInformation(String name, String surname, String position, int phoneNumber, String image){
        nameLabel.setText(nameLabel.getText()+" "+name+" "+surname);
        positionLabel.setText(positionLabel.getText()+" "+position);
        phoneNumberLabel.setText(phoneNumberLabel.getText()+phoneNumber);
        try {
            imageView.setImage(new Image(image));
            imageView.toFront();
        }catch (Exception e){
            imageView.setImage(new Image("no-photo.png"));
        }

    }
}
