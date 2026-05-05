<?php
include '../php/db.php';

$title = $_POST['title'];
$desc = $_POST['description'];
$date = $_POST['date'];
$venue = $_POST['venue'];

$sql = "INSERT INTO events (title, description, event_date, venue)
VALUES ('$title','$desc','$date','$venue')";

if($conn->query($sql)){
    echo "Event Added!";
} else {
    echo "Error: " . $conn->error;
}
?>