<?php
session_start();

if(!isset($_SESSION['admin'])){
    header("Location: adminLogin.html");
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="../css/style.css">
</head>

<body class="bg">

<div class="glass-card">
    <h2>Admin Dashboard</h2>

    <a href="addEvent.html">➕ Add Event</a><br><br>
    <a href="viewEvents.php">📋 View Events</a><br><br>
    

    <a href="logout.php">🚪 Logout</a>
</div>

</body>
</html>