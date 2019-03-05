<?php
	
	require_once '../../db_functions.php';
	$db = new DB_Functions();

	if (isset($_POST['id'])) 
	{
		$id = $_POST['id'];
		$name = $_POST['name'];
		$imgPath = $_POST['imgPath'];
		$price = $_POST['price'];
		$menuId = $_POST['menuId'];


		$result = $db->updateProduct($id, $name, $imgPath, $price, $menuId);
		if ($result)
			echo json_encode("Berhasil mengupdate Barang");
		else
			echo json_encode("Error connect to coeg");
	}

	else
	{
		echo(json_encode("Require parameters (id, name, imgPath, price, menuId) is missing! "));
	}

?>