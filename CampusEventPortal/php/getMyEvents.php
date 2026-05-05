<?php
session_start();
include 'db.php';

if(!isset($_SESSION['user_id'])){
    echo json_encode([]);
    exit();
}

$user_id = $_SESSION['user_id'];

$sql = "SELECT events.title FROM events
        JOIN registrations 
        ON events.event_id = registrations.event_id
        WHERE registrations.user_id = '$user_id'";

$result = $conn->query($sql);

$events = [];

while($row = $result->fetch_assoc()){
    $events[] = $row;
}
echo json_encode($events);
?>