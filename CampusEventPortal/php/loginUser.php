<?php
session_start();
include 'db.php';

$email = $_POST['email'];
$password = $_POST['password'];

$sql = "SELECT * FROM users WHERE email='$email'";
$result = $conn->query($sql);

if($result->num_rows > 0){
    $row = $result->fetch_assoc();

    if(password_verify($password, $row['password'])){
        $_SESSION['user_id'] = $row['user_id'];
        $_SESSION['name'] = $row['name'];

        header("Location: ../dashboard.php");
    } else {
        header("Location: ../login.html?error=wrong_password");
    }

} else {
    header("Location: ../login.html?error=user_not_found");
}
?>