<?php

$dbname = $_POST['dbname'];
$dbid = $_POST['dbid'];
$dbpw = $_POST['dbpw'];
$tablename = $_POST['table'];

$con=mysqli_connect("localhost", $dbid, $dbpw, $dbname);

if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$res = mysqli_query($con, "SELECT * FROM " . $tablename);

$result = array();

while($row = mysqli_fetch_array($res))
{
  array_push($result, array('ID'=>$row[0],'Name'=>$row[1],'Desc'=>$row[2]));
}

echo json_encode(array("result"=>$result));

mysqli_close($con);

?>