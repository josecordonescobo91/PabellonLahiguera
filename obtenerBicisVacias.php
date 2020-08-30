
<?php

include('../conexion.php'); 
//$fecha=date("Y-m-d");
//$nick = "pepe";
//$nick=$_POST['nick'];
//$nick=$_GET['nick'];

if ($resultset = getSQLResultSet("SELECT * FROM bicicletas WHERE nick = '' ")) {
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);		
    	
    	}
    	
   }
?>