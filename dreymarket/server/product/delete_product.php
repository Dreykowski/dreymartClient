<?php
	
	require_once '../../db_functions.php';
	$db = new DB_Functions();

	if (isset($_POST['id'])) 
	{
		$id = $_POST['id'];

		$result = $db->deleteProduct($id);
		if ($result)
			echo json_encode("Berhasil menghapus Kategori");
		else
			echo json_encode("Error connect to database");
	}

	else
	{
		echo(json_encode("Require parameters (id) is missing! "));
	}

?>