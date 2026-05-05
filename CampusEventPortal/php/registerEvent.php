<?php
session_start();
include 'db.php';

$user_id = $_SESSION['user_id'];
$event_id = $_POST['event_id'];

// check if already registered
$check = "SELECT * FROM registrations 
          WHERE user_id='$user_id' AND event_id='$event_id'";

$result = $conn->query($check);

if($result->num_rows > 0){
    echo "already";
} else {
    $sql = "INSERT INTO registrations (user_id, event_id)
            VALUES ('$user_id','$event_id')";

    if($conn->query($sql)){
        echo "registered";
    }
}
?>