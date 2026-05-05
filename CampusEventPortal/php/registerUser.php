<?php
include 'db.php';

$name = $_POST['name'];
$email = $_POST['email'];
$password = password_hash($_POST['password'], PASSWORD_DEFAULT);

$check = "SELECT * FROM users WHERE email='$email'";
$result = $conn->query($check);

if($result->num_rows > 0){
    header("Location: ../register.html?error=exists");
} else {

    $sql = "INSERT INTO users (name, email, password, role)
            VALUES ('$name', '$email', '$password', 'student')";

    if($conn->query($sql)){
        header("Location: ../login.html?success=registered");
    }
}
?>