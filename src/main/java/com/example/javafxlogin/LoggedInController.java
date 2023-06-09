package com.example.javafxlogin;


import com.example.database_utils.DatabaseHandler;
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
    private Label nicknameLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_log_out.setOnAction(event -> DBUtils.changeScene(event, "hello-view.fxml", "Log In"));
    }

    @FXML
    public void uploadPhoto(){

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter("Images (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            try{
                Image image = new Image(new FileInputStream(selectedFile));
                DatabaseHandler.addImage(selectedFile.getAbsolutePath().trim(), nicknameLabel.getText().trim());
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Error while downloading image: "+ e.getMessage());
            }
        }
        imageView.toFront();



    }


    public void setUserInformation(String name, String surname, String position, String nickname, String image){
        nameLabel.setText(nameLabel.getText()+" "+name+" "+surname);
        positionLabel.setText(positionLabel.getText()+" "+position);
        nicknameLabel.setText(nickname);
        try {
            imageView.setImage(new Image(image));
            imageView.toFront();
        }catch (Exception e){
            imageView.setImage(new Image("no-photo.png"));
        }

    }

}
