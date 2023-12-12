package kr.or.yi.board.DTO;


import javafx.scene.control.CheckBox;

import java.io.File;
import java.io.InputStream;

public class Board {
    private int board_no;
    private String title;
    private String writer;
    private String content;
    private String reg_date;
    private String upd_date;
    private File file;
    private InputStream isfile;
    private CheckBox cbDelete;


    public int getBoard_no() {
        return board_no;
    }

    public void setBoard_no(int board_no) {
        this.board_no = board_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getUpd_date() {
        return upd_date;
    }

    public void setUpd_date(String upd_date) {
        this.upd_date = upd_date;
    }

    public CheckBox getCbDelete() {
        return cbDelete;
    }

    public void setCbDelete(CheckBox cbDelete) {
        this.cbDelete = cbDelete;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public InputStream getIsfile() {
        return isfile;
    }

    public void setIsfile(InputStream isfile) {
        this.isfile = isfile;
    }

    public Board() {
        this("제목없음", "내용없음", "글쓴이없음");
    }

    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.cbDelete = new CheckBox();
    }

    public Board(String title, String content, String writer, File file) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.file = file;
        this.cbDelete = new CheckBox();
    }

    @Override
    public String toString() {
        return "Board{" +
                "board_no=" + board_no +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", file='" + file + '\'' +
                ", reg_date=" + reg_date +
                ", upd_date=" + upd_date +
                '}';
    }
}
