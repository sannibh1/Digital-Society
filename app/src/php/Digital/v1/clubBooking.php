<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['mobileno']) and 
		isset($_POST['dateyear']) and
		isset($_POST['datemonth']) and
		isset($_POST['dateday']))
		{
		//operate the data further 

		$db = new DbOperations(); 
		
		$result = $db->bookClub($_POST['mobileno'],	$_POST['dateyear'], $_POST['datemonth'], $_POST['dateday']);
		
		if($result == 0){
			$response['error'] = true; 
			$response['message'] = "Booking is full!!!!!";
		}else if($result == 1){
			$response['error'] = false; 
			$response['message'] = "Booking done successfully!!!";
		}
		
	}else{
		$response['error'] = true; 
		$response['message'] = "Some error occurred please try later";
	}
}else{
	$response['error'] = true; 
	$response['message'] = "Invalid Request";
}


echo json_encode($response);
