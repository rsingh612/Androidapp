<?php

    $connect = mysqli_connect("localhost", "osirispr_team", "TeamOs1r1s", "osirispr_Orders");
    
    $orderId = $_POST["orderId"];
    $customerId = $_POST["customerId"];
    $itemsOrdered = $_POST["itemsOrdered"];
    $orderPlaced = $_POST["timePlaced"];
    $status = $_POST["status"];
    
 
    $statement = mysqli_prepare($connect, "INSERT INTO PlacedOrders (orderId, customerId, itemsOrdered, orderPlaced, status) VALUES(?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssss", $orderId, $customerId, $itemsOrdered, $orderPlaced, $status);
    mysqli_stmt_execute($statement);
    mysqli_stmt_close($statement);
    
 
    $response = arrray();
    $response["success"] = true;
    echo json_encode($response);
?>