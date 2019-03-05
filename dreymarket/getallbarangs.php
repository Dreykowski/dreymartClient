<?php 
require_once 'db_functions.php';
$db = new DB_Functions();

$barangs = $db->getAllBarangs();
if ($barangs) 
	echo json_encode($barangs);
else
	echo json_encode("Error");
?>