<?php
    require("password.php");
    
    $connect = mysqli_connect("localhost", "osirispr_team", "TeamOs1r1s", "osirispr_LiquorRush");
    
    $name = $_POST["name"];
    $license = $_POST["license"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $address = $_POST["address"];
    $city = $_POST["city"];
    $state = $_POST["state"];
    $zip = $_POST["zip"];
    
    	function registerUser() {
 	   	global $connect, $name, $license, $email, $password, $address, $city, $state, $zip;
 	   	$passwordHash = password_hash($password, PASSWORD_DEFAULT);
 	   	$statement = mysqli_prepare($connect, "INSERT INTO Account (name, license, email, password, address, city, state, zip) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
 	   	mysqli_stmt_bind_param($statement, "ssssssss", $name, $license, $email, $passwordHash, $address, $city, $state, $zip);
 	   	mysqli_stmt_execute($statement);
 	   	mysqli_stmt_close($statement);
 	}
 	
 	function usernameAvailable() {
 		global $connect, $username;
 		$statement = mysqli_prepare($connect, "SELECT * FROM Account WHERE email = ?");
 		mysqli_stmt_bind_param($statement, "s", $email);
 		mysqli_stmt_execute($statement);
 		mysqli_stmt_store_result($statement);
 		$count = mysqli_stmt_num_rows($statement);
 		mysqli_stmt_close($statement);
 		if($count < 1){
 			return true;
 		}
 		else {
 			return false;
 		}
 	}
    
    $response = array();
    $response["success"] = false;  
    
    if (usernameAvailable()){
    	registerUser();
    	$response["success"]= true;
    }
    
    echo json_encode($response);
?>