package kr.or.yi.board.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kr.or.yi.board.DTO.Board;
import kr.or.yi.board.Main;
import kr.or.yi.board.Service.BoardService;
import kr.or.yi.board.Service.BoardServiceImp;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.File;
import java.io.IOException;

public class InsertController {
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfWrite;
    @FXML
    private TextArea taContent;
    @FXML
    private FileChooser fileChooser;
    @FXML
    private TextField tfFilePath;
    @FXML
    private ImageView imageView;

    private Image image;
    private File file;

    Stage stage;
    Scene scene;
    Parent root;

    FXMLLoader loader;

    private BoardService boardService = new BoardServiceImp();

    public void insert(ActionEvent event) throws IOException {
        Board board = new Board(tfTitle.getText(), tfWrite.getText(), taContent.getText(), file);
        System.out.println("insert->" + board);
        int result = boardService.insert(board);
        if(result > 0){
            System.out.println("글쓰기 완료");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        }
    }

    public void moveToList(ActionEvent event) throws IOException {
        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
    }

    public void fileUpload(ActionEvent event){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Text Files", "*.text"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav","*.mp3","*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        file = fileChooser.showOpenDialog(stage);
        System.out.println(file);
        if(file != null){
            tfFilePath.setText(file.getAbsolutePath());
            image = new Image(file.toURI().toString(), 180,280,true,true);
            imageView.setImage(image);
        }
    }

    private double x = 0;
    private double y = 0;
    @FXML
    private AnchorPane rootPane;
    @FXML
    public void handleMouseClicked(MouseEvent event) {
        Platform.runLater(() -> {
            rootPane = (AnchorPane) event.getTarget();
            stage = (Stage) rootPane.getScene().getWindow();
            rootPane.setOnMousePressed((event1) -> {
                x = event1.getSceneX();
                y = event1.getSceneY();
            });
            rootPane.setOnMouseDragged((event1) -> {
                stage.setX(event1.getScreenX() - x);
                stage.setY(event1.getScreenY() - y);
                stage.setOpacity(0.8f); // 창 투명화
            });
            rootPane.setOnDragDone((event1) -> {
                stage.setOpacity(1.0f);
            });
            rootPane.setOnMouseReleased((event1) -> {
                stage.setOpacity(1.0f);
            });
        });
    }

    public void close(ActionEvent event) {
        SceneUtil.getInstance().close(event);
    }
}
