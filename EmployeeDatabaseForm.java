package employeeManagement;



import javafx.application.Application; // JavaFX Application
import javafx.scene.text.Font; // needed for JavaFX Font object
import javafx.scene.text.FontWeight; // needed for JavaFX FontWeight object
import javafx.scene.text.Text; // needed for JavaFX Text object
import javafx.scene.layout.HBox; // Needed for HBox object
import javafx.scene.Scene; // needed for JavaFX scene object
import javafx.scene.layout.GridPane; // needed for JavaFX GridPane object
import javafx.scene.control.Label; // needed for JavaFX Label object
import javafx.scene.control.TextField; // needed for JavaFX TextField control
import javafx.scene.control.PasswordField; // needed for JavaFX PasswordField control
import javafx.scene.control.Button; // needed for JavaFX Button control
import javafx.scene.paint.Color; // needed for JavaFX color object
import javafx.geometry.Insets; // needed for JavaFX Insets object
import javafx.geometry.Pos; // needed for JavaFX Pos object
import javafx.event.ActionEvent; // needed for JavaFX ActionEvent
import javafx.event.EventHandler; // needed for JavaFX EventHandler
import javafx.stage.Stage; // needed for JavaFX Stage object
import javafx.scene.control.TableView; // Needed for JavaFX TableView object
import javafx.scene.control.TableColumn; // Needed for JavaFX TableColumn object
import javafx.scene.control.ComboBox; // Needed for JavaFX ComboBox object
import javafx.scene.control.cell.PropertyValueFactory; // Needed for JavaFX PropertyValueFactory object
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.application.Platform; // Needed for Platform.exit()
import javafx.collections.ObservableList; // Needed for ObservableList
import javafx.collections.FXCollections; // Needed for FXCollections
import java.sql.*; // Needed for JDBC
import java.util.List; // Needed for List
import java.util.ArrayList; // Needed for ArrayList
import javafx.util.Callback;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;


public class EmployeeDatabaseForm extends Application
{
	private ComboBox <String> deptsDataCombo = new ComboBox<>();
	
	// Declare the Student Data TableView:
	private TableView <EmployeeData> employeeDataTableView = new TableView<>();
	
	// Declare the database connection object:
	private Connection connObject = null; 
	
	private ArrayList <Integer> changedObjectsList  = new ArrayList<>();; 
	
	private List <EmployeeData> employeeDataList = new ArrayList<>();  
	private String deptCodeParm = "";
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public void start(Stage primaryStage) 
	{
		final String FONT_NAME = "Georgia";
		
		initialzeDB(); 
		
		getDepts(); 

        primaryStage.setTitle("Employee Database Interface");
		        
		GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25)); 
		
		Label deptLabel = new Label("Department");
		deptLabel.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); 
		
		
		deptsDataCombo.setPrefWidth(250);
		
		deptsDataCombo.setOnAction((ActionEvent e)->
		{
			String selectedDept = deptsDataCombo.getValue(); 
			
	
			
			deptCodeParm = selectedDept.substring(0, 1);
			
			getEmployeeData(); 
		});
	
		grid.add(deptsDataCombo, 1, 0, 1, 1); 
		

		configureTableView();
		

		
		grid.add(employeeDataTableView, 0, 1, 3, 12); 
		
		Button btnUpdateDB = new Button("Update DB");    
		btnUpdateDB.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); 
		grid.add(btnUpdateDB, 0, 13, 1, 1);  //
		
		btnUpdateDB.setOnAction((ActionEvent e)->
		{
			updateEmployeeData();
		});
		
		// Exit button
		Button btnExit = new Button("Exit");    
		btnExit.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 20)); 
		grid.add(btnExit, 1, 13, 1, 1);  
		
		btnExit.setOnAction((ActionEvent e)->
		{
			Platform.exit();
		});

		Scene scene = new Scene(grid, 1050, 650); 
			
		primaryStage.setScene(scene);
		
        primaryStage.show();
		
    }
	
	private void configureTableView() 
	{
		
		
		TableColumn <EmployeeData, String> employeeIDTableCol = new TableColumn<>("Employee ID"); 
		
		employeeIDTableCol.setPrefWidth( 100);
		
		employeeIDTableCol.setCellValueFactory( new PropertyValueFactory<>("empID"));
			
		
		TableColumn <EmployeeData, String> lastNameTableCol = new TableColumn<>("Last Name"); 
		lastNameTableCol.setPrefWidth( 150);
		
		lastNameTableCol.setCellValueFactory( new PropertyValueFactory<>("lastName"));
		
		lastNameTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		
		lastNameTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
			            t.getTablePosition().getRow())
			            ).setLastName(t.getNewValue());
			
		
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}
			
		});

		TableColumn <EmployeeData, String> firstNameTableCol = new TableColumn<>("First Name"); 
		
		firstNameTableCol.setPrefWidth( 150);
		
		firstNameTableCol.setCellValueFactory( new PropertyValueFactory<>("firstName"));
		
		firstNameTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		
		firstNameTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
			            t.getTablePosition().getRow())
			            ).setFirstName(t.getNewValue());
			
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}
			
		});

		TableColumn <EmployeeData, String> jobCodeTableCol = new TableColumn<>("Job Code"); 
		
		jobCodeTableCol.setPrefWidth( 150);
		
		jobCodeTableCol.setCellValueFactory( new PropertyValueFactory<>("jobCode"));
		
		jobCodeTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		
		jobCodeTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
			            t.getTablePosition().getRow())
			            ).setJobCode(t.getNewValue());
			
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}
			
		});
		
	TableColumn <EmployeeData, String> jobDescTableCol = new TableColumn<>("Job Desc"); 
		
		jobDescTableCol.setPrefWidth( 150);
		
		jobDescTableCol.setCellValueFactory( new PropertyValueFactory<>("jobDesc"));
		
		jobDescTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		
		jobDescTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
			            t.getTablePosition().getRow())
			            ).setJobDesc(t.getNewValue());
			
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}
			
		});
		

		TableColumn <EmployeeData, String> deptCodeTableCol = new TableColumn<>("Department Code"); 
			
		deptCodeTableCol.setPrefWidth( 200);
		
		employeeDataTableView.setPrefWidth( 150);
		
		deptCodeTableCol.setCellValueFactory(new PropertyValueFactory<>("deptCode"));
		
		deptCodeTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		
		deptCodeTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
			            t.getTablePosition().getRow())
			            ).setDeptCode(t.getNewValue());
			
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}
			
		});
		

		

		
TableColumn <EmployeeData, String> payTableCol = new TableColumn<>("Pay"); 
		
		payTableCol.setPrefWidth( 100);
		
		employeeDataTableView.setPrefWidth( 1000);
		
		payTableCol.setCellValueFactory(new PropertyValueFactory<>("periodicPay"));
		
		payTableCol.setCellFactory(TextFieldTableCell.<EmployeeData>forTableColumn());
		
		payTableCol.setOnEditCommit((CellEditEvent<EmployeeData, String> t) -> 
		{
			((EmployeeData) t.getTableView().getItems().get(
			            t.getTablePosition().getRow())
			            ).setPeriodicPay(t.getNewValue());
			
			if (!changedObjectsList.contains(t.getTablePosition().getRow()))
			{
				changedObjectsList.add(t.getTablePosition().getRow());
			}
			
		});
		

		employeeDataTableView.setEditable(true); 

	
		employeeDataTableView.getColumns().setAll(employeeIDTableCol, lastNameTableCol, firstNameTableCol, 
													jobCodeTableCol, jobDescTableCol,  deptCodeTableCol, payTableCol);


	}
	
   private void initialzeDB() 
   {
	   try
	   {
	
		   
		   Class.forName("com.mysql.jdbc.Driver"); 
		   
		  
		   connObject = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmployeeDB","Sfelzien", "Cheesedoodle37!"); 
	   }
	   
	   catch (Exception ex)
	   {
		   System.out.println("\nError in connecting to the database: " + ex.getMessage() + "\n");
		   
		   ex.printStackTrace();
	   }
	
   }
   
   private void getDepts()
   {
	   try
	   {
		   String procCallString = "{call sp_all_departments()}";
		   
		   CallableStatement deptsStoredProcStmt = connObject.prepareCall(procCallString); 
		      
		   ResultSet rsObject = deptsStoredProcStmt.executeQuery(); 
		   
		   List <String> deptsList = new ArrayList<>(); 
		   
		   while (rsObject.next())
		   {	   

				deptsList.add(rsObject.getString(1)); 	
		   }
		   
		   ObservableList <String> olDeptsList = FXCollections.observableArrayList( deptsList ); 	
		   
		   deptsDataCombo.setItems( olDeptsList); 
	   }
	   
	   catch(Exception ex)
	   {
		   System.out.println("\nSomething bad happened during the execution of sp_all_departments(): " + ex.getMessage());
		   
		   ex.printStackTrace();
	   }
   }
   
   private void getEmployeeData()
   {
	   try
	   {
		   String procCallString = "{call sp_get_employees_in_dept( ?)}"; 
		   
		   CallableStatement employeeDataStoredProcStmt = connObject.prepareCall(procCallString); 
		   
		   employeeDataStoredProcStmt.setString(1, deptCodeParm); 
		   
		   ResultSet rsObject = employeeDataStoredProcStmt.executeQuery(); 
		   
		   employeeDataList.clear(); 
		   
		   while (rsObject.next()) 
		   {			   
				EmployeeData employeeObject = new EmployeeData(); 
					

				employeeObject.setEmpID(Integer.toString(rsObject.getInt(1))); 		
				employeeObject.setLastName(rsObject.getString(2)); 					
				employeeObject.setFirstName(rsObject.getString(3)); 				
				employeeObject.setJobCode(Integer.toString(rsObject.getInt(4))); 	
				employeeObject.setJobDesc(rsObject.getString(5)); 
				//employeeObject.setWorkLocCode(rsObject.getString(5)); 				
				employeeObject.setDeptCode(Integer.toString(rsObject.getInt(6)));
				//employeeObject.setEmail(rsObject.getString(7)); 
				//employeeObject.setTelephone(rsObject.getString(8));
				employeeObject.setPeriodicPay(rsObject.getString(7)); 
	
				
				employeeDataList.add(employeeObject);
		   }
		   
		   ObservableList <EmployeeData> olEmployeeList = FXCollections.observableArrayList( employeeDataList ); 	
		
		   
		   employeeDataTableView.setItems(olEmployeeList); // Assign the ObservableList to the TableView.
		   
	   }
	   
	   catch(Exception ex)
	   {
		   System.out.println("\nSomething bad happened during the execution of the select query: {call sp_employees_in_dept( ?)}" + ex.getMessage());
		   
		   ex.printStackTrace();
	   }
   }
   
   private void updateEmployeeData()
   {
	   try
	   {
		
		   String procCallString = "{call sp_update_employee( ?, ?, ?, ?, ?)}"; 
		   
		   CallableStatement employeeDataStoredProcStmt = connObject.prepareCall(procCallString); 
		   
		   for (Integer i : changedObjectsList ) 
		   {			   
				EmployeeData employeeObject = new EmployeeData(); 
					
				
				int empIDParm = Integer.valueOf(employeeDataList.get(i).getEmpID()); 		
				String lastNameParm = employeeDataList.get(i).getLastName(); 						
				String firstNameParm = employeeDataList.get(i).getFirstName();			
				int jobCodeParm = Integer.valueOf(employeeDataList.get(i).getJobCode()); 
				String jobDescParm = employeeDataList.get(i).getJobDesc();
 
				int deptCodeParm = Integer.valueOf(employeeDataList.get(i).getDeptCode());
				
				double payParm = Double.valueOf(employeeDataList.get(i).getPeriodicPay()); 
				
				
				employeeDataStoredProcStmt.setInt(1, empIDParm); 
				employeeDataStoredProcStmt.setString(2, lastNameParm); 
				employeeDataStoredProcStmt.setString(3, firstNameParm); 
				employeeDataStoredProcStmt.setInt(4, jobCodeParm);
				employeeDataStoredProcStmt.setString(5, jobDescParm);
				//employeeDataStoredProcStmt.setString(5, workLocCodeParm);
				employeeDataStoredProcStmt.setInt(5, deptCodeParm);
				//employeeDataStoredProcStmt.setString(7, emailParm);
				//employeeDataStoredProcStmt.setString(8, telephoneParm);
				employeeDataStoredProcStmt.setDouble(6, payParm);
				//employeeDataStoredProcStmt.setString(10, payFreqCodeParm);
			
				employeeDataStoredProcStmt.executeQuery(); 
		   }
		   
		   changedObjectsList.clear(); 
		   
	   }
	   
	   catch(Exception ex)
	   {
		   System.out.println("\nSomething bad happened during the execution of the update query: " + ex.getMessage());
		   
		   ex.printStackTrace();
	   }
   }
	
}



