package controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DashboardFormController {

    @FXML
    private AreaChart<?, ?> areaChartSales;

    @FXML
    private TableColumn<?, ?> colRevAmount;

    @FXML
    private TableColumn<?, ?> colRevCustomer;

    @FXML
    private TableColumn<?, ?> colRevDate;

    @FXML
    private TableColumn<?, ?> colRevInvoiceNo;

    @FXML
    private TableColumn<?, ?> colRevStatus;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colUserRole;

    @FXML
    private TableColumn<?, ?> colUserSales;

    @FXML
    private Label lblConversion;

    @FXML
    private Label lblTodayVisit;

    @FXML
    private Label lblTotalRevenue;

    @FXML
    private Label lblTotalSales;

    @FXML
    private PieChart pieChartRevenue;

    @FXML
    private TableView<?> tblRevenueHistory;

    @FXML
    private TableView<?> tblTopUsers;

}
