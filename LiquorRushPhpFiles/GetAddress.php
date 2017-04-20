<?php
    
    $connect = mysqli_connect("localhost", "osirispr_team", "TeamOs1r1s", "osirispr_LiquorRush");
    
    $customerId = $_POST["customerId"];
    
    $statement = mysqli_prepare($connect, "SELECT * FROM CustomerAccount WHERE customerId = ?");
    mysqli_stmt_bind_param($statement, "s", $customerId);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $colId, $colName, $colLicense, $colEmail, $colPassword, $colAddress, $colCity, $colState, $colZip);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
    	    $response["success"] = true;  
            $response["name"] = $colName;
            $response["license"] = $colLicense;
            $response["address"] = $colAddress;
            $response["city"] = $colCity;
            $response["state"] = $colState;
            $response["zip"] = $colZip;
            
    }
    
    echo json_encode($response);
?>