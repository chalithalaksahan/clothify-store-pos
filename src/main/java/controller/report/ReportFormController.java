package controller.report;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReportFormController {

    @FXML
    private JFXComboBox<?> cmbFilterBy;

    @FXML
    private TableColumn<?, ?> col1;

    @FXML
    private TableColumn<?, ?> col2;

    @FXML
    private TableColumn<?, ?> col3;

    @FXML
    private TableColumn<?, ?> col4;

    @FXML
    private TableColumn<?, ?> col5;

    @FXML
    private TableColumn<?, ?> col6;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private DatePicker dpStartDate;

    @FXML
    private Label lblReportTitle;

    @FXML
    private Label lblSummaryTotalText;

    @FXML
    private Label lblSummaryTotalValue;

    @FXML
    private Label lblTotalRecords;

    @FXML
    private TableView<?> tblReportData;

}
