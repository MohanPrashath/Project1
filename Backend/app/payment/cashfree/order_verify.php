<?php

// Set your Cashfree credentials
$clientId = 'YOUR_CLIENT_ID';
$clientSecret = 'YOUR_CLIENT_SECRET';

// Set the API endpoint
$apiUrl = 'https://api.cashfree.com/api/v2/cftoken/order';

// Set order details
$orderData = array(
    'orderId' => $_POST['orderId'], // Order ID received from the form
    'orderAmount' => $_POST['orderAmount'], // Order amount received from the form
);

// Encode order data to JSON
$orderJsonData = json_encode($orderData);

// Generate token
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $apiUrl);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_POSTFIELDS, $orderJsonData);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, array(
    'Content-Type: application/json',
    'X-Client-Id: ' . $clientId,
    'X-Client-Secret: ' . $clientSecret,
));

$response = curl_exec($ch);
curl_close($ch);

// Decode JSON response
$responseData = json_decode($response, true);

// Check if token is generated successfully
if (isset($responseData['cftoken'])) {
    $cfToken = $responseData['cftoken'];
    // Verify the order using the generated token
    $verifyApiUrl = 'https://test.cashfree.com/api/v2/cftoken/order/' . $cfToken . '/verify';
    $verifyResponse = file_get_contents($verifyApiUrl);
    $verifyData = json_decode($verifyResponse, true);

    // Check if order is verified
    if ($verifyData['status'] === 'OK') {
        // Order is verified, proceed with further processing
        echo "Order verified successfully!";
    } else {
        // Order verification failed
        echo "Order verification failed: " . $verifyData['reason'];
    }
} else {
    // Handle error
    echo "Error: " . $responseData['message'];
}

?>
