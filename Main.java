package Applications;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private RoomManager roomManager = new RoomManager();
    private TextArea displayArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Room Booking System");

        TabPane tabPane = new TabPane();

        Tab addRoomTab = new Tab("Add Room");
        addRoomTab.setClosable(false);
        addRoomTab.setContent(createAddRoomPane());

        Tab viewRoomTab = new Tab("View Rooms");
        viewRoomTab.setClosable(false);
        viewRoomTab.setContent(createViewRoomPane());

        Tab bookRoomTab = new Tab("Book Room");
        bookRoomTab.setClosable(false);
        bookRoomTab.setContent(createBookRoomPane());

        Tab checkoutTab = new Tab("Checkout");
        checkoutTab.setClosable(false);
        checkoutTab.setContent(createCheckoutPane());

        tabPane.getTabs().addAll(addRoomTab, viewRoomTab, bookRoomTab, checkoutTab);

        Scene scene = new Scene(tabPane, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane createAddRoomPane() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label roomNoLabel = new Label("Room Number:");
        TextField roomNoField = new TextField();

        Label roomTypeLabel = new Label("Room Type:");
        ComboBox<String> roomTypeBox = new ComboBox<>();
        roomTypeBox.getItems().addAll("Single", "Double", "Deluxe");
        roomTypeBox.setValue("Single");

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Button addButton = new Button("Add Room");
        Label messageLabel = new Label();

        addButton.setOnAction(e -> {
            try {
                int roomNo = Integer.parseInt(roomNoField.getText());
                String type = roomTypeBox.getValue();
                double price = Double.parseDouble(priceField.getText());

                boolean added = roomManager.addRoom(roomNo, type, price);
                if (added) {
                    messageLabel.setText("Room added successfully.");
                    roomNoField.clear();
                    priceField.clear();
                } else {
                    messageLabel.setText("Room number already exists.");
                }
            } catch (Exception ex) {
                messageLabel.setText("Enter valid input.");
            }
        });

        grid.add(roomNoLabel, 0, 0);
        grid.add(roomNoField, 1, 0);
        grid.add(roomTypeLabel, 0, 1);
        grid.add(roomTypeBox, 1, 1);
        grid.add(priceLabel, 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(addButton, 1, 3);
        grid.add(messageLabel, 1, 4);

        return grid;
    }

    private Pane createViewRoomPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefHeight(350);

        Button refreshButton = new Button("Refresh Room List");
        refreshButton.setOnAction(e -> refreshRoomDisplay());

        vbox.getChildren().addAll(refreshButton, displayArea);
        return vbox;
    }

    private void refreshRoomDisplay() {
        displayArea.clear();
        for (Room room : roomManager.getAllRooms()) {
            displayArea.appendText(room.toString() + "\n");
        }
    }

    private Pane createBookRoomPane() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label roomNoLabel = new Label("Room Number:");
        TextField roomNoField = new TextField();

        Label customerLabel = new Label("Customer Name:");
        TextField customerField = new TextField();

        Button bookButton = new Button("Book Room");
        Label messageLabel = new Label();

        bookButton.setOnAction(e -> {
            try {
                int roomNo = Integer.parseInt(roomNoField.getText());
                String customerName = customerField.getText();

                if (customerName.isEmpty()) {
                    messageLabel.setText("Enter customer name.");
                    return;
                }

                boolean booked = roomManager.bookRoom(roomNo, customerName);
                if (booked) {
                    messageLabel.setText("Room booked successfully.");
                    roomNoField.clear();
                    customerField.clear();
                } else {
                    messageLabel.setText("Room not available or does not exist.");
                }
            } catch (Exception ex) {
                messageLabel.setText("Enter valid room number.");
            }
        });

        grid.add(roomNoLabel, 0, 0);
        grid.add(roomNoField, 1, 0);
        grid.add(customerLabel, 0, 1);
        grid.add(customerField, 1, 1);
        grid.add(bookButton, 1, 2);
        grid.add(messageLabel, 1, 3);

        return grid;
    }

    private Pane createCheckoutPane() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label roomNoLabel = new Label("Room Number:");
        TextField roomNoField = new TextField();

        Button checkoutButton = new Button("Checkout Room");
        Label messageLabel = new Label();

        checkoutButton.setOnAction(e -> {
            try {
                int roomNo = Integer.parseInt(roomNoField.getText());

                boolean checkedOut = roomManager.checkoutRoom(roomNo);
                if (checkedOut) {
                    messageLabel.setText("Checkout successful.");
                    roomNoField.clear();
                } else {
                    messageLabel.setText("Room is not booked or does not exist.");
                }
            } catch (Exception ex) {
                messageLabel.setText("Enter valid room number.");
            }
        });

        grid.add(roomNoLabel, 0, 0);
        grid.add(roomNoField, 1, 0);
        grid.add(checkoutButton, 1, 1);
        grid.add(messageLabel, 1, 2);

        return grid;
    }
    public static void main(String[] args) {
        launch(args);
    }
}