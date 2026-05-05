<?php include 'php/checkLogin.php'; ?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Events</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="navbar">
    <div class="logo">
        <img src="images/logo.png">
        <span>Campus Events</span>
    </div>
    <div class="nav-links">
        <a href="dashboard.php">Dashboard</a>
        <a href="logout.php">Logout</a>
    </div>
</div>

<h2 style="text-align:center;">Available Events 🎉</h2>
<p id="msg" style="text-align:center; color:yellow;"></p>
<div class="events-container glass" id="eventList"></div>
<div style="margin-top:40px; text-align:center;">

    <h2 style="color:white;"></h2>

    <ul id="myEvents" style="
        list-style:none;
        padding:0;
        color:white;
        font-size:18px;
        font-weight:bold;
    ">
    </ul>

</div>

<script src="js/script.js"></script>
</body>
</html>