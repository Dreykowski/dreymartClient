<?php

require_once 'db_functions.php';
$db = new DB_Functions();

/*
 *  Endpoint : http://<domain>/dreymarket/cancelorder.php
 *  Method : POST
 *  Params : Order status, userphone
 *  Result : JSON
 */

if (isset($_POST['orderId']) && isset($_POST['userPhone'])) 
{
	$orderId = $_POST['orderId'];
	$userPhone = $_POST['userPhone'];

	$result = false;
	$result = $db->cancelOrder($orderId, $userPhone);
	if ($result) 
		echo json_encode("Pemesanan telah dibatalkan");
	else
		echo json_encode("Tidak terkoneksi ke Database");
}
else
{
	echo json_encode ("required parameters(OrderId, userphone) is Missing");
}

?>
