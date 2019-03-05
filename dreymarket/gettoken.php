<?php

require_once 'db_functions.php';
$db = new DB_Functions();

/*
 *  Endpoint : http://<domain>/dreymarket/gettoken.php
 *  Method : POST
 *  Params : phone, isServerToken
 *  Result : JSON
 */
$response = array();
if(isset($_POST['phone']) && isset($_POST['isServerToken']))
{
    $phone = $_POST['phone'];
    $isServerToken = $_POST['isServerToken'];

    $token = $db->getToken($phone, $isServerToken);

    echo json_encode($token);
}
else
{
    $response = "Required parameter (phone, isServerToken) is missing!";
    echo json_encode($response);
}

?>
