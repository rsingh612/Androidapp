<?php
    
    $connect = mysqli_connect("localhost", "osirispr_user", "ICanOnlyRead", "osirispr_Products");
    
    //variables coming from the volley request
    $search = $_POST["search"];
    $category = $_POST["category"];
    $start = $_POST["start"];
    $end = $_POST["end"];
    $test = ".@/[]^*(";
    $categorytest = "Category: All";
    
    //if search and category not selected run this if
    if((strcmp($search,$test)==0) && (strcmp($category,$categorytest)==0)){
    	$statement = mysqli_prepare($connect, "SELECT * FROM Products Limit ?, ?");
    	mysqli_stmt_bind_param($statement, "ss", $start, $end);
    	mysqli_stmt_execute($statement);
    
    	mysqli_stmt_store_result($statement);
    	mysqli_stmt_bind_result($statement, $id, $image, $name, $description, $type, $price);

    	$response = array();
    	$i=1;
    
    	while(mysqli_stmt_fetch($statement)){
		$response[$i++] = $id;
    		$response[$i++] = $image;
    		$response[$i++] = $name;
    		$response[$i++] = $description;
    		$response[$i++] = $group;
    		$response[$i++] = $price;
 
    	}
    	echo json_encode($response);
    }
    else if((strcmp($search,$test)==0) && (strcmp($category,$categorytest)!=0)){
    	$likeParam = "%" . $category . "%" ;  
    	$statement = mysqli_prepare($connect,"SELECT * FROM Products WHERE type LIKE ? Limit ?, ?");
    	mysqli_stmt_bind_param($statement, "sss", $likeParam, $start, $end);
    	mysqli_stmt_execute($statement);
    
    	mysqli_stmt_store_result($statement);
    	mysqli_stmt_bind_result($statement, $id, $image, $name, $description, $type, $price);

    	$response = array();
    	$i=1;
    	
    	while(mysqli_stmt_fetch($statement)){
		$response[$i++] = $id;
    		$response[$i++] = $image;
    		$response[$i++] = $name;
    		$response[$i++] = $description;
    		$response[$i++] = $group;
    		$response[$i++] = $price;
 
    	}
    	echo json_encode($response);
    }
    else if((strcmp($search,$test)!=0) && strcmp($category,$categorytest)==0){
	 $likeParam = "%" . $search . "%" ;  	
	 $statement = mysqli_prepare($connect, "SELECT * FROM Products WHERE name LIKE ? Limit ?, ?");
	 mysqli_stmt_bind_param($statement, "sss", $likeParam, $start, $end);
	 mysqli_stmt_execute($statement);
	    
	 mysqli_stmt_store_result($statement);
	 mysqli_stmt_bind_result($statement, $id, $image, $name, $description, $type, $price);
	
	 $response = array();
	 $i=1;
	    
	 while(mysqli_stmt_fetch($statement)){
		$response[$i++] = $id;
	 	$response[$i++] = $image;
	 	$response[$i++] = $name;
	 	$response[$i++] = $description;
	 	$response[$i++] = $group;
	 	$response[$i++] = $price;
	 }
	 echo json_encode($response);	
    	}
    else{
     	 $likeParam = "%" . $search . "%" ;  
    	 $categoryParam = "%" . $category . "%" ;  	
	 $statement = mysqli_prepare($connect, "SELECT * FROM Products WHERE name LIKE ? AND type = ? Limit ?, ?");
	 mysqli_stmt_bind_param($statement, "ssss",  $likeParam, $category, $start, $end);
	 mysqli_stmt_execute($statement);
	    
	 mysqli_stmt_store_result($statement);
	 mysqli_stmt_bind_result($statement, $id, $image, $name, $description, $type, $price);
	
	 $response = array();
	 $i=1;
	    
	 while(mysqli_stmt_fetch($statement)){
		$response[$i++] = $id;
	 	$response[$i++] = $image;
	 	$response[$i++] = $name;
	 	$response[$i++] = $description;
	 	$response[$i++] = $group;
	 	$response[$i++] = $price;
	 }
	 echo json_encode($response);	
    }
?>