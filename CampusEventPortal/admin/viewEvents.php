<?php
include '../php/db.php';

$result = $conn->query("SELECT * FROM events");

while($row = $result->fetch_assoc()){
    echo $row['title'] . " - " . $row['event_date'];
    echo "<br>";
}
?>