-- Create and test three simple stored procedures:


-- 5 stored procedure get_majors


-- 6 stored procedure get_students_for_major


-- highlight (select) and execute the code from the following line:
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_all_departments`()
BEGIN
  SELECT CONCAT( dept_code, ' - ', department_name ) FROM department;
END $$
DELIMITER ;
-- through the previous line.


-- highlight (select) and execute the code from the following line:
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_all_employees`()
BEGIN
  SELECT CONCAT( employee_ID, ' , ', last_name, ',', first_name, ', ', job_type_code, ', ', work_loc_code, ',', department_code, ',', email_address, ',',  telephone, ',',  pay  , ',', pay_freq_code ) FROM employee;
END $$
DELIMITER ;

-- highlight (select) and execute the code from the following line:
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_employees_in_dept`( IN deptCodeParm TINYINT )
BEGIN
  SELECT employee_id, last_name, first_name, job_type_code, work_loc_code, department_code, telephone, pay, pay_freq_code 
  FROM employee INNER JOIN department
  ON dept_code = dept_code
  WHERE UCASE( department_code ) = deptCodeParm ;
END $$
DELIMITER ;

-- through the previous line.

-- highlight (select) and execute the code in the following line:
delimiter ;

-- test the first procedue by highlighting (selecting) and 
-- executing the code in the following line:
call sp_all_departments();

-- test the second procedue by highlighting (selecting) and 
-- executing the code in the following line:
call sp_all_employees();

call sp_employees_in_dept( 11);
-- test the third procedue by highlighting (selecting) and 
-- executing the code in the following line:
