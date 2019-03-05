<?php
	
	require_once '../../db_functions.php';
	$db = new DB_Functions();

	if (isset($_POST['name']) && isset($_POST['imgPath']) && isset($_POST['price']) && isset($_POST['menuId'])) 
	{
		$name = $_POST['name'];
		$imgPath = $_POST['imgPath'];
		$price = $_POST['price'];
		$menuId = $_POST['menuId'];


		$result = $db->insertNewBarang($name,$imgPath, $price, $menuId);
		
		if ($result)
			echo json_encode("Berhasil Menambah Barang");
		else
			echo json_encode("Error connect to database");
	}

	else
	{
		echo(json_encode("Require parameters (name, imgPath, price, menuId) is missing to add barang! "));
	}

?>