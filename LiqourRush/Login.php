<?php
    require("password.php");
    
    $con = mysqli_connect("localhost", "osirispr_team", "TeamOs1r1s", "osirispr_LiquorRush");
	  
    $email = $_POST["email"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM Account WHERE email = ?");
    mysqli_stmt_bind_param($statement, "s", $email);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $colId, $colName, $colLicense, $colEmail, $colPassword, $colAddress, $colCity, $colState, $colZip);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
    	if(password_verify($password, $colPassword)) {
    	    $response["success"] = true;  
            $response["name"] = $colName;
            $response["email"] = $colEmail;
            $response["License"] = $colLicense;
    	}
    }
    
    echo json_encode($response);
?>