<?php
    require("password.php");
    $con = mysqli_connect("localhost", "osirispr_team", "TeamOs1r1s", "osirispr_LiquorRush");
	  
    $username = $_POST["username"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM EmployeeAccount WHERE username = ?");
    mysqli_stmt_bind_param($statement, "s", $username);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $colId, $colUsername, $colPassword, $colType);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){	
    	if(password_verify($password, $colPassword)){
    	    $response["success"] = true;  
            $response["userName"] = $colUsername;
            $response["userType"] = $colType;
        }
    }
    
    echo json_encode($response);
?>