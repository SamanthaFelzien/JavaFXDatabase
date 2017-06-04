/*
** Program    : EmployeeData.java
**
** Purpose    : To provide objects with which to populate the studentDataTableView.
** 				It differs slightly from the Student class by having the major description.
**
** Developer  : F DAngelo
**
*/

package employeeManagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeData
{
	private SimpleStringProperty empID, lastName, firstName, jobCode, jobDesc, deptCode, periodicPay ;
	

	
	public EmployeeData()
	{
		this.empID = new SimpleStringProperty("0") ; // new SimpleIntegerProperty(0);
		this.lastName = new SimpleStringProperty("") ;
		this.firstName = new SimpleStringProperty("") ;
		this.jobCode = new SimpleStringProperty("0") ;
		//this.workLocCode= new SimpleStringProperty("0") ; // not the "regular" Student class.
		this.deptCode = new SimpleStringProperty("0") ;
		this.jobDesc = new SimpleStringProperty("0");
		//this.emailAddress = new SimpleStringProperty("");
		//this.telephone = new SimpleStringProperty(""); 
		this.periodicPay = new SimpleStringProperty("0.0");

	}

	// Custom constructor with parameters.
	public EmployeeData(String empID, String lastName, String firstName, String jobCode, String jobDesc, String deptCode, String periodicPay)
	{
		setEmpID(empID);
		setLastName(lastName) ;
		setFirstName(firstName) ;
		setJobCode(jobCode);
		setJobDesc(jobDesc);
		setDeptCode(deptCode);
		setPeriodicPay(periodicPay);

	}

	// Define "setter" a.k.a. mutator methods.
	public void setEmpID( String empID ) // int empID ) 
	{
		this.empID.set(empID)  ;
	}
	
	public void setLastName( String lastName ) 
	{
		this.lastName.set(lastName) ;
	}
	
	public void setFirstName( String firstName ) 
	{
		this.firstName.set(firstName) ;
	}
	
	public void setJobCode( String jobCode ) 
	{
		this.jobCode.set(jobCode) ;
	}
	
	public void setJobDesc( String jobDesc ) 
	{
		this.jobDesc.set(jobDesc) ;
	}
	

	
	public void setDeptCode( String deptCode ) 
	{
		this.deptCode.set(deptCode);
	}
	


	
	public void setPeriodicPay( String periodicPay ) 
	{
		this.periodicPay.set(periodicPay);
	}
	
	
	// Define "getter" a.k.a. accessor methods.
	public String getEmpID() 
	{
		return empID.get() ;
	}
	
	public String getLastName() 
	{
		return lastName.get() ;
	}
	
	public String getFirstName() 
	{
		return firstName.get() ;
	}
	
	public String getJobCode()  
	{
		return jobCode.get() ;
	}
	
	public String getJobDesc()  
	{
		return jobDesc.get() ;
	}
	
	
	public String getDeptCode()  
	{
		return deptCode.get() ;
	}
	

	
	public String getPeriodicPay() 
	{
		return periodicPay.get() ;
	}


	public String toString()
	{
		return	"\n" +
				" Employee ID      : " + empID.get() + "\n" +
				" Last name        : " + lastName.get() + "\n" +
				" First name       : " + firstName.get() + "\n" +
				" Job Code         : " + jobCode.get() + "\n" +
				"Job Desc          : " + jobDesc.get() + "\n" +
				" Dept Code        : " + deptCode.get() + "\n" +
				" Pay:               " + periodicPay.get() + "\n";

}
}