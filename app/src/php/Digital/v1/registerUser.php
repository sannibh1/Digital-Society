<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(
		isset($_POST['mobileno']) and 
			isset($_POST['firstname']) and 
				isset($_POST['lastname']) and 
					isset($_POST['email']) and 
						isset($_POST['password'])
		){
		//operate the data further 

		$db = new DbOperations(); 
		
		$result = $db->createUser($_POST['mobileno'], $_POST['firstname'], $_POST['lastname'], $_POST['email'], $_POST['password']);
		if($result == 1){
			$response['error'] = false; 
			$response['message'] = "User registered successfully";
		}else if($result == 2){
			$response['error'] = true; 
			$response['message'] = "Some error occurred please try again, Please choose different mobileno";
		}else if($result == 0){
			$response['error'] = true; 
			$response['message'] = "It seems you already registered, Please choose different email";
		}
		
		
	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
