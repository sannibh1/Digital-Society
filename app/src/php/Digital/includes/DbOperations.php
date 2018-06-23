<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD -> C -> CREATE */

		public function createUser($mobileno, $firstname, $lastname, $email, $password){
			if($this->isUserExist($email)){
				return 0; 
			}else{
				$pass = md5($password);
				$stmt = $this->con->prepare("INSERT INTO `user` (`mobileno`, `firstname`, `lastname`, `email`, `password`) VALUES (?, ?, ?, ?, ?)");
				$stmt->bind_param("issss",$mobileno, $firstname, $lastname, $email, $pass);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}

		public function userLogin($email, $pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT mobileno FROM user WHERE email = ? AND password = ?");
			$stmt->bind_param("ss",$email, $password);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		public function getUserByEmail($email){
			$stmt = $this->con->prepare("SELECT * FROM user WHERE email = ?");
			$stmt->bind_param("s",$email);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		

		private function isUserExist($email){
			$stmt = $this->con->prepare("SELECT mobileno FROM user WHERE email = ?");
			$stmt->bind_param("s", $email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

		
		public function bookClub($mobileno, $dateyear, $datemonth, $dateday){
			$stmt = $this->con->prepare("SELECT * FROM clubbooking");
			$stmt->execute();
			$stmt->store_result();
			if($stmt->num_rows < 30){
				$insertbook = $this->con->prepare("INSERT INTO `clubbooking` (`mobileno`, `bookdate`) VALUES (?, ?)");
				$insertbook->bind_param("is",$mobileno, $bookdate);
				$date = "{$dateyear}-{$datemonth}-{$dateday}";
				$bookdate = date("y-m-d",strtotime($date));
				$insertbook->execute();
				return 1;
			}else{
				return 0;
			}

		}
	}