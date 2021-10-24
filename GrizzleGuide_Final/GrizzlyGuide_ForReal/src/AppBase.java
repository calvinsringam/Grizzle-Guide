import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Date;

/**
 * Grizzly Guide GUI.
 * @author Madeline Berns
 * @version 1.0
 */
public class AppBase extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    final Color CUSTOMDARKGREEN = Color.web("638c1c");
    final Color CUSTOMLIGHTGREEN = Color.web("86ad4d");
    final Font GILL = new Font("GILL SANS", 15);
    final Font BEBAS = Font.loadFont("file:BebasNeue-Regular.ttf", 20);

    @Override
    public void start(Stage primaryStage) {

        // Name stage.
        primaryStage.setTitle("Grizzly Guide");

        // Set up background image and StackPane.
        StackPane backgroundPic = new StackPane();
        Image greenBack = new Image("Background2.png");
        ImageView greenBackView = new ImageView(greenBack);
        greenBackView.setPreserveRatio(true);
        greenBackView.setFitHeight(2340);
        backgroundPic.getChildren().add(greenBackView);

        // Header, footer, and borderpane.
        HBox headerBox = new HBox();
        Image paw = new Image("GrizzleGuideHeader2.png");
        ImageView pawView = new ImageView(paw);
        pawView.setPreserveRatio(true);
        pawView.setFitHeight(30);
        //Label appName = new Label("GRIZZLE GUIDE");
        //appName.setFont(new Font("GILL SANS", 30));
        //appName.setTextFill(Color.WHITE);
        headerBox.getChildren().add(pawView);
        headerBox.setSpacing(10);
        Rectangle headerBack = new Rectangle(306, 50);
        headerBack.setFill(CUSTOMDARKGREEN);
        StackPane header = new StackPane(headerBack, headerBox);

        Rectangle footerBack = new Rectangle(306, 30);
        footerBack.setFill(CUSTOMDARKGREEN);
        Label footerText = new Label("Made with ♥ and Bear Hugs, HackGT 8");
        footerText.setFont(GILL);
        footerText.setTextFill(CUSTOMLIGHTGREEN);
        StackPane footer = new StackPane(footerBack);
        footer.getChildren().add(footerText);

        BorderPane headerAndFooter = new BorderPane();
        headerAndFooter.setTop(header);
        headerAndFooter.setBottom(footer);

        backgroundPic.getChildren().add(headerAndFooter);
        headerBox.setAlignment(Pos.CENTER);

        // Input
        Image hat = new Image("Hat.png");
        ImageView hatView = new ImageView(hat);
        hatView.setFitHeight(100);
        hatView.setPreserveRatio(true);
        Label inputPrompt = new Label("Where do you want to explore?");
        inputPrompt.setFont(new Font("GILL SANS", 20));
        inputPrompt.setTextFill(Color.WHITE);
        inputPrompt.setAlignment(Pos.CENTER);
        VBox inputAndOutputBox = new VBox();

        VBox addressInputs = new VBox();
        TextField city = new TextField();
        city.setPromptText("City");
        TextField state = new TextField();
        state.setPromptText("State");
        StackPane searchButtonPane = new StackPane();
        Rectangle searchButtonBase = new Rectangle(60,20);
        searchButtonBase.setFill(Color.WHITE);

        Label searchLabel = new Label("SEARCH");
        searchLabel.setTextFill(Color.BLACK);
        searchButtonPane.getChildren().addAll(searchButtonBase, searchLabel);
        searchButtonPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String cityName = city.getText();
                String stateName = state.getText();
                PlacesDriver searchLocation = new PlacesDriver();
                VBox listOfResults = new VBox();
                Label description = new Label("Here are some good spots for outdoor activities near " +
                    cityName + ", " + stateName + ":");
                description.setFont(GILL);
                description.setTextFill(Color.WHITE);
                description.setTextAlignment(TextAlignment.CENTER);
                description.setWrapText(true);
                description.setPrefSize(290, 50);
                listOfResults.getChildren().add(description);
                description.setAlignment(Pos.CENTER);
                try {
                    ArrayList <Object> results = searchLocation.getInfo(cityName + ", " + stateName);
                    ArrayList<String> names = (ArrayList<String>) results.get(0);
                    ArrayList<String> descriptions = (ArrayList<String>) results.get(1);
                    int[] temps = (int[]) results.get(2);
                    int[] humidities = (int[]) results.get(3);

                    for (int i = 0; i <= 3; i++) {
                        StackPane nearbyPlace = new StackPane();
                        Rectangle placeBack = new Rectangle(290, 100);
                        placeBack.setFill(CUSTOMLIGHTGREEN);
                        VBox placeInfo = new VBox();
                        Label placeName = new Label(names.get(i));
                        placeName.setTextFill(Color.WHITE);
                        placeName.setFont(new Font("BOLD GILL SANS", 15));
                        placeName.setStyle("-fx-font-weight: bold");
                        Label placeDescript = new Label("Weather: " + descriptions.get(i));
                        placeDescript.setTextFill(Color.WHITE);
                        placeDescript.setFont(GILL);
                        Label placeTemp = new Label("Temperature: " + temps[i] + " °F");
                        placeTemp.setTextFill(Color.WHITE);
                        placeTemp.setFont(GILL);
                        Label humidityPlace = new Label("Humidity: " + humidities[i] + "%");
                        humidityPlace.setTextFill(Color.WHITE);
                        humidityPlace.setFont(GILL);
                        placeInfo.getChildren().addAll(placeName, placeDescript, placeTemp, humidityPlace);
                        placeInfo.setAlignment(Pos.CENTER);
                        nearbyPlace.getChildren().addAll(placeBack, placeInfo);
                        listOfResults.getChildren().add(nearbyPlace);
                    }
                    listOfResults.setSpacing(20);
                    headerAndFooter.setCenter(listOfResults);
                    listOfResults.setAlignment(Pos.CENTER);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        addressInputs.getChildren().addAll(inputPrompt, city, state, searchButtonPane);
        addressInputs.setPadding(new Insets(10,10,10,10));
        addressInputs.setSpacing(10);

        inputAndOutputBox.getChildren().addAll(hatView, addressInputs);
        headerAndFooter.setCenter(inputAndOutputBox);
        inputAndOutputBox.setAlignment(Pos.CENTER);

        // Sets scene and shows stage.
        //positionalPane.setCenter(dockPane);
        Scene backgroundScene = new Scene(backgroundPic, 306, 655);
        //backgroundPic.getChildren().add(positionalPane);
        primaryStage.setScene(backgroundScene);
        primaryStage.show();

    }
}
