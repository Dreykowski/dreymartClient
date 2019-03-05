<?php

require_once 'db_functions.php';
$db = new DB_Functions();

/*
 *  Endpoint : http://<domain>/dreymarket/getbarang.php
 *  Method : POST
 *  Params : phone, name, birthdate, address
 *  Result : JSON
 */
$response = array();
if(isset($_POST['menuid']))
{
    $menuid = $_POST['menuid'];
    $barangs = $db->getBarangByMenuID($menuid);

    echo json_encode($barangs);
    
}
else
{
    $response["error_msg"] = "Required parameter (menuid) is missing!";
    echo json_encode($response);
}

?>
