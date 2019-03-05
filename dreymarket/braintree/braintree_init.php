<?php
	
	session_start();
	require_once ("lib/autoload.php");

	if (file_exists(__DIR__ . "/../.env")) 
	{
		$dotenv = new Dotenv\Dotenv(__DIR__ . "/../.env");
		$dotenv->load();	
	}

	Braintree_Configuration::environment('sanbox');
	Braintree_Configuration::merchantId('67k86z8v72rsdb6w');
	Braintree_Configuration::publicKey('9c398fzhsv28qqpj');
	Braintree_Configuration::privateKey('820ce2f1b77e93b56d9c4b4cd7511965');

?>