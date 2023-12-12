package kr.or.yi.board.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kr.or.yi.board.DTO.Board;
import kr.or.yi.board.Service.BoardService;
import kr.or.yi.board.Service.BoardServiceImp;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ReadController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;

    FXMLLoader loader;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfWriter;
    @FXML
    private TextArea taContent;
    @FXML
    private ImageView imageView;
    @FXML
    private Button Next;
    @FXML
    private Button Prev;

    private Image image;
    private BoardService boardService = new BoardServiceImp();
    int boardNo;   // 현재 게시물 번호
    List<Integer> numArr;
    int targetValue = boardNo;
    int preValue = -1;
    int nextValue = -1;


    public void read(int boardNo) {
        numArr = listNum();
        this.targetValue = boardNo;
        this.boardNo = boardNo;
        Board board = boardService.select(boardNo);
        tfTitle.setText(board.getTitle());
        tfWriter.setText(board.getWriter());
        taContent.setText(board.getContent());
        if (board.getIsfile() != null) {
            image = new Image(board.getIsfile(), 180, 230, true, true);
            imageView.setImage(image);
            taContent.setPrefWidth(424);
        } else {
            imageView.setImage(null);
            taContent.setPrefWidth(688);
//            System.out.println(numArr.indexOf(boardNo));
        }
        int numArrIndex = numArr.indexOf(boardNo);
        int numArrSize = numArr.size();
//        System.out.println(numArrSize);
        Prev.setDisable(numArrIndex == 0);
        Next.setDisable(numArrIndex == numArrSize - 1);
    }

    public void moveToList(ActionEvent event) throws IOException {
        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
    }

    public void moveToUpdate(ActionEvent event) throws IOException {
        UpdateController updateController = (UpdateController) SceneUtil.getInstance().getController(UI.UPDATE.getPath());
        updateController.read(boardNo);
        Parent root = SceneUtil.getInstance().getRoot();
        SceneUtil.getInstance().switchScene(event, UI.UPDATE.getPath(), root);
    }

    public void delete(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("경고");
        alert.setHeaderText("게시글을 삭제 하시겠습니까?");
        alert.setContentText("삭제 후에는 되돌릴 수 없습니다.");

        int result = 0;
        if (alert.showAndWait().get() == ButtonType.OK) {
            result = boardService.delete(boardNo);
        }
        if (result > 0) {
            System.out.println("삭제 완료");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        }
    }

    public void deleteToList() {

    }

    public void moveToPrev(ActionEvent event) {
        numArr = listNum();
        read(preValue);
    }

    public void moveToNext(ActionEvent event) {
        numArr = listNum();
        read(nextValue);
    }

    public List<Integer> listNum() {
        List<Board> boardList = new ArrayList<>();
        boardList = boardService.list();
        numArr = new ArrayList<>();
        for (Board board : boardList) {
            numArr.add(board.getBoard_no());
        }
//        System.out.println(numArr);
        targetValue = boardNo;

        for (int i = 0; i < numArr.size(); i++) {
            if (numArr.get(i) == targetValue) {
                if (i > 0) {
                    preValue = numArr.get(i - 1);
                }
                if (i < numArr.size() - 1) {
                    nextValue = numArr.get(i + 1);
                }
                break;
            }
        }
        return numArr;
    }

    public int showNum(int index) {
        int num = 0;
        if (index >= 0 && index < numArr.size()) {
            num = numArr.indexOf(index);
        }
        return num;
    }

    public void pagaState() {

    }

    private double x = 0;
    private double y = 0;
    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
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

