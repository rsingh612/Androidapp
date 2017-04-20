<?php
    
    $connect = mysqli_connect("localhost", "osirispr_team", "TeamOs1r1s", "osirispr_Orders");
    
    //variables coming from the volley request
    $customerId = $_POST["customerId"];
    $job = $_POST["job"];
    $test = "!@#*#&#q3)i*gd;^w%m^";
    $clerk = "clerk";
    $delivery ="delivery";
    
    
    if(strcmp($customerId,$test)==0){
    	if(strcmp($job,$clerk)==0){
    		$likeParam = "%New Order%"; 
    		$statement = mysqli_prepare($connect,"SELECT * FROM PlacedOrders WHERE status LIKE ?");
    		mysqli_stmt_bind_param($statement, "s", $likeParam);
    		mysqli_stmt_execute($statement);
    		
    		mysqli_stmt_store_result($statement);
    		mysqli_stmt_bind_result($statement, $orderId, $customerId, $itemsOrdered, $orderPlaced, $status);

    		$response = array();
    		$i=1;
    	
    		while(mysqli_stmt_fetch($statement)){
			$response[$i++] = $orderId;
    			$response[$i++] = $customerId;
    			$response[$i++] = $itemsOrdered;
    			$response[$i++] = $orderPlaced;
    			$response[$i++] = $status;
    		}
    		echo json_encode($response);
    	}
    	else if(strcmp($job,$delivery)==0){
    		$likeParam = "%Fulfilled%";
    		$statement = mysqli_prepare($connect,"SELECT * FROM PlacedOrders WHERE status LIKE ?");
    		mysqli_stmt_bind_param($statement, "s", $likeParam);
    		mysqli_stmt_execute($statement);
    		
    		mysqli_stmt_store_result($statement);
    		mysqli_stmt_bind_result($statement, $orderId, $customerId, $itemsOrdered, $orderPlaced, $status);

    		$response = array();
    		$i=1;
    	
    		while(mysqli_stmt_fetch($statement)){
			$response[$i++] = $orderId;
    			$response[$i++] = $customerId;
    			$response[$i++] = $itemsOrdered;
    			$response[$i++] = $orderPlaced;
    			$response[$i++] = $status;
    		}
    		echo json_encode($response);
    	}
    	
    }
    else{
      	$statement = mysqli_prepare($connect,"SELECT * FROM PlacedOrders WHERE customerId LIKE ? ");
    	mysqli_stmt_bind_param($statement, "s", $customerId);
    	mysqli_stmt_execute($statement);
    
    	mysqli_stmt_store_result($statement);
    	mysqli_stmt_bind_result($statement, $orderId, $customerId, $itemsOrdered, $orderPlaced, $status);

    	$response = array();
    	$i=1;
    	
    	while(mysqli_stmt_fetch($statement)){
		$response[$i++] = $orderId;
    		$response[$i++] = $customerId;
    		$response[$i++] = $itemsOrdered;
    		$response[$i++] = $orderPlaced;
    		$response[$i++] = $status;
    	}
    	echo json_encode($response);
    }
?>