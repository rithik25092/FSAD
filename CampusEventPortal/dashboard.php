<?php include 'php/checkLogin.php'; ?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="navbar">
    <div class="logo">
        <img src="images/logo.png">
        <span>Campus Events</span>
    </div>
    <div class="nav-links">
        <a href="events.php">View Events</a>
        <a href="logout.php">Logout</a>
    </div>
</div>

<div class="center-box">
    <div class="glass">
        <h1>Welcome 🎓</h1>
        <p>Explore and register for events</p>
        <a href="events.php"><button class="btn">View Events</button></a>
    </div>
</div>
</body>
</html>