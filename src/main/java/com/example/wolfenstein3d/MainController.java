package com.example.wolfenstein3d;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.MainModel;

public class MainController {
    private MainModel model;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void init(){

    }
}