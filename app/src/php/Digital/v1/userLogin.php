<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['email']) and isset($_POST['password'])){
		$db = new DbOperations(); 

		if($db->userLogin($_POST['email'], $_POST['password'])){
			$user = $db->getUserByEmail($_POST['email']);
			$response['error'] = false; 
			$response['mobileno'] = $user['mobileno'];
			$response['email'] = $user['email'];
			$response['firstname'] = $user['firstname'];
			$response['lastname'] = $user['lastname'];
			$response['message'] = "done";
		}else{
			$response['error'] = true; 
			$response['message'] = "Invalid email or password";			
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

echo json_encode($response);