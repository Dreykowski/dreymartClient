<?php

require_once 'db_functions.php';
$db = new DB_Functions();

/*
 *  Endpoint : http://<domain>/dreymarket/updateToken.php
 *  Method : POST
 *  Params : phone, token, isServerToken
 *  Result : JSON
 */
$response = array();
if(isset($_POST['phone']) && isset($_POST['token']) && isset($_POST['isServerToken']))
{
    $phone = $_POST['phone'];
    $token = $_POST['token'];
    $isServerToken = $_POST['isServerToken'];

    $user = $db->insertToken($phone, $token, $isServerToken);
    if ($user) 
        echo json_encode("Token update Success");
    else
        echo json_encode("Token update Failed");
    
    
}
else
{
    echo json_encode ("Required parameter (phone, token, isServerToken) is missing!") ;
    
}

?>
