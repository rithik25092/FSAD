<?php
$conn = new mysqli("localhost", "root", "", "campus_portal", 3307);

if($conn->connect_error){
    die("Connection failed: " . $conn->connect_error);
}
?>