package kr.or.yi.board.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kr.or.yi.board.DTO.Board;
import kr.or.yi.board.Main;
import kr.or.yi.board.Service.BoardService;
import kr.or.yi.board.Service.BoardServiceImp;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.IOException;

public class UpdateController {
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfWriter;
    @FXML
    private TextArea taContent;

    private BoardService boardService = new BoardServiceImp();
    int boardNo;

    public void read(int boardNo){
        this.boardNo = boardNo;
        Board board = boardService.select(boardNo);
        tfTitle.setText(board.getTitle());
        tfWriter.setText(board.getWriter());
        taContent.setText(board.getContent());
    }

    public void update(ActionEvent event) throws IOException{
        Board board = new Board(tfTitle.getText(), taContent.getText(), tfWriter.getText());
        board.setBoard_no(boardNo);
        int result = boardService.update(board);
        if(result > 0) {
            System.out.println("글수정이 완료되었습니다.");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        }

    }

    public void moveToList(ActionEvent event) throws IOException {
        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
    }

    public void delete(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("경고");
        alert.setHeaderText("게시글을 삭제 하시겠습니까?");
        alert.setContentText("삭제 후에는 되돌릴 수 없습니다.");

        int result = 0;
        if(alert.showAndWait().get() == ButtonType.OK){
            result = boardService.delete(boardNo);
        }
        if( result > 0) {
            System.out.println("삭제 완료");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
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
            Stage stage = (Stage) rootPane.getScene().getWindow();
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
