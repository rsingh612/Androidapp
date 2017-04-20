<?php
    
    $connect = mysqli_connect("localhost", "osirispr_team", "TeamOs1r1s", "osirispr_Orders");
    
    $update = $_POST["update"];
    $orderId = $_POST["orderId"];
    $clerk = "clerk";
    $delivery = "delivery";
    
    if(strcmp($update,$clerk)==0){
    	$statement = mysqli_prepare($connect, "UPDATE PlacedOrders SET status='Fulfilled' WHERE orderId= ?");
    	mysqli_stmt_bind_param($statement, "s", $orderId);
    	mysqli_stmt_execute($statement);
    	$response = array();
    	$response["success"] = true;
    	
    echo json_encode($response);
    }
    else if(strcmp($update,$delivery)==0){
    	$statement = mysqli_prepare($connect, "UPDATE PlacedOrders SET status='Delivered' WHERE orderId= ?");
    	mysqli_stmt_bind_param($statement, "s", $orderId);
    	mysqli_stmt_execute($statement);
    	$response = array();
    	$response["success"] = true;
    echo json_encode($response);
    }
?>