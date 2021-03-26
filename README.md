# myPOS-Smart-SDK

This repository provides a guidance on integrating an Android app with a myPOS Smart device. Once integrated, the app will be able to communicate with the core device components in order to accept card payments (including but not limited to VISA, Mastercard, UnionPay International, JCB, Bancontact). myPOS-Smart-SDK complеments a Smart POS terminal in order to initiate transaction, complete all steps for processing payment, make refund to the customer card account and print a custom bill slip using the device printer. The built-in functionalities of myPOS-Smart-SDK allow you to send Payment Requests, manage operations for the first and second SAM component the myPOS Smart device has and print bitmap image integrated to the receipt (e.g. QR Code).

No sensitive card data is ever passed through or stored on myPOS Smart device. All data is encrypted by the core card terminal module, which has been fully certified to the highest industry standards (PCI, EMV I and II, Visa, MasterCard, Amex).

### Table of Contents

* [Installation](#installation)

* [Usage](#Usage)

  * [Receive POS info](#receive-pos-info)

  * [Process a checkout](#process-a-checkout)

  * [Refund request](#refund-request)
  
  * [Payment Request](#payment-request)
  
  * [Void Request](#void-request)
  
  * [Pre-Authorization Request](#pre-authorization-request)
  
   * [PL GiftCard Request](#pl-giftcard-request)
  
  * [SAM Module operation](#sam-module-operation)

  * [Print the last transaction receipt](#print-the-last-transaction-receipt)

  * [Print a custom receipt](#print-a-custom-receipt)
  
* [Response](#response)

## Installation

Add the repository to your gradle dependencies:

```java
allprojects {
   repositories {
      jcenter()
   }
}
```

Add the dependency to a module:

```java
implementation 'com.mypos:mypossmartsdk:1.0.3'
```

### Additional functions:
-	Payment Requests, 
-	Managing operations of first and second SAM component of myPOS Smart device
-	Print bitmap image integrated to the receipt (e.g. QR Code)
*In order to use additional functions listed above you need to have installed myPOS OS version 0.0.7. myPOS OS Version can be checked Only when device is in “Debug Mode” under “About” submenu in myPOS Terminal App.
 

# Usage

Once the SDK is added to your project, using the Payment API can be done with the provided helper classes.


### Receive POS info


Here you can find simple info about myPOS terminal like	TID, currency name, currency code, merchant info, etc.

```java
MyPOSAPI.registerPOSInfo(MainActivity.this, new OnPOSInfoListener() {
            @Override
            public void onReceive(POSInfo info) {
                //info is received
            }
        });
```

### Process a checkout


##### 1. Perform the payment

```java
// Build the payment call
 MyPOSPayment payment = MyPOSPayment.builder()
         // Mandatory parameters
         .productAmount(13.37)
         .currency(Currency.EUR)
         // Foreign transaction ID. Maximum length: 128 characters
         .foreignTransactionId(UUID.randomUUID().toString())
	 // Optional parameters
	 // Enable tipping mode
	 .tippingModeEnabled(true)
         .tipAmount(1.55)
	 // Operator code. Maximum length: 4 characters
	 .operatorCode("1234")
	 // Reference number. Maximum length: 20 alpha numeric characters
	 .reference("asd123asd", ReferenceType.REFERENCE_NUMBER)
	 // Set print receipt mode
	 .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
	 .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
         .build();
	 
// If you want to initiate a moto transaction:
payment.setMotoTransaction(true)

// Or you want to initiate a giftcard transaction:
payment.setGiftCardTransaction(true)

 // Start the transaction
 MyPOSAPI.openPaymentActivity(MainActivity.this, payment, 1);
```


##### 2. Handle the result

In your calling Activity, override the ``onActivityResult`` method to handle the result of the payment:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // The same request code as when calling MyPOSAPI.openPaymentActivity
    if (requestCode == 1) {
        // The transaction was processed, handle the response
        if (resultCode == RESULT_OK) {
            // Something went wrong in the Payment core app and the result couldn't be returned properly
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int transactionResult = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);

            Toast.makeText(this, "Payment transaction has completed. Result: " + transactionResult, Toast.LENGTH_SHORT).show();

            // TODO: handle each transaction response accordingly
            if (transactionResult == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Transaction is successful
            }
        } else {
            // The user cancelled the transaction
            Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}

```

Checking if the transaction is approved can be done by reading the ``transaction_approved`` boolean extra from the response:

```java
boolean transaction_approved = data.getBooleanExtra("transaction_approved", false);

if (transaction_approved) {
    // Transaction is approved
} else {
    // Transaction was not approved
    // The response code is in the "response_code" string extra
}

```


### Refund request


##### 1. Perform the refund

``` java
// Build the refund request
MyPOSRefund refund = MyPOSRefund.builder()
	// Mandatoy parameters
        .refundAmount(1.23)
        .currency(Currency.EUR)
        .foreignTransactionId(UUID.randomUUID().toString())
	// Optional parameters
        // Set print receipt mode
	.printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
	.printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
        .build();
	
// If you want to initiate a moto transaction:
payment.setMotoTransaction(true)

// Or you want to initiate a giftcard transaction:
payment.setGiftCardTransaction(true)

// Start the transaction
MyPOSAPI.openRefundActivity(MainActivity.this, refund, 2);
```

##### 2. Handle the result

The same as with the payment, in your calling Activity, override the ``onActivityResult`` method to handle the result of the refund:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // The same request code as when calling MyPOSAPI.openRefundActivity
    if (requestCode == 2) {
        // The transaction was processed, handle the response
        if (resultCode == RESULT_OK) {
            // Something went wrong in the Payment core app and the result couldn't be returned properly
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int transactionResult = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);

            Toast.makeText(this, "Refund transaction has completed. Result: " + transactionResult, Toast.LENGTH_SHORT).show();

            // TODO: handle each transaction response accordingly
            if (transactionResult == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Transaction is successful
            }
        } else {
            // The user cancelled the transaction
            Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}

```

### Payment request
This functionality allows a merchant to create payment request and send it as a SMS directly from a myPOS Smart device. 
##### 1. Perform Payment Request

 ```java
 // Build the payment request transaction
 private static final int PAYMENT_REQUEST_REQUEST_CODE = 4;
    private void startPaymentRequest() {
        // Build the payment request
        MyPOSPaymentRequest paymentRequest = MyPOSPaymentRequest.builder()
                .productAmount(3.55)
                .currency(Currency.EUR)
                .expiryDays(60)
                .recipientName("John Doe")
                .GSM("0899070087")
                .eMail("")
                .reason("System test")
                .build();
				
// Start the payment request transaction
MyPOSAPI.createPaymentRequest(MainActivity.this, paymentRequest, PAYMENT_REQUEST_REQUEST_CODE);
    }
```

##### 2.  Handle the result

The same as with the payment, in your calling Activity, override the ``onActivityResult`` method to handle the result of the Payment request:

```java
@Override
	void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == PAYMENT_REQUEST_REQUEST_CODE) {
			// The transaction was processed, handle the response
			if (resultCode == RESULT_OK) {
				// Something went wrong in the Payment core app and the result couldn't be returned properly
				if (data == null) {
					Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
					return;
				}
				int transactionResult = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);

				Toast.makeText(this, "Payment request transaction has completed. Result: " + transactionResult, Toast.LENGTH_SHORT).show();

				// TODO: handle each transaction response accordingly
				if (transactionResult == TransactionProcessingResult.TRANSACTION_SUCCESS) {
					// Transaction is successful
				}
			} else {
				// The user cancelled the transaction
				Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
			}
		}
    }
```

### Void Request


##### 1. Perform void transaction

 ```java
 // Build the void transaction
 private static final int VOID_REQUEST_CODE = 4;
    private void startVoid() {
        // Build the void request
        MyPOSVoid voidEx = MyPOSVoid.builder()
                .STAN(27)
                .authCode("VISSIM")
                .dateTime("180129123753")
				//.voidLastTransactionFlag(true) // this may void last transaction initialized by this terminal
                .build();
				
		// Start the void transaction
		MyPOSAPI.openVoidActivity(MainActivity.this, voidEx, VOID_REQUEST_CODE, true);
    }
```

##### 2.  Handle the result

The same as with the payment, in your calling Activity, override the ``onActivityResult`` method to handle the result of the void request:

```java
@Override
	void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == VOID_REQUEST_CODE) {
			// The transaction was processed, handle the response
			if (resultCode == RESULT_OK) {
				// Something went wrong in the Payment core app and the result couldn't be returned properly
				if (data == null) {
					Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
					return;
				}
				int transactionResult = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);

				Toast.makeText(this, "Void transaction has completed. Result: " + transactionResult, Toast.LENGTH_SHORT).show();

				// TODO: handle each transaction response accordingly
				if (transactionResult == TransactionProcessingResult.TRANSACTION_SUCCESS) {
					// Transaction is successful
				}
			} else {
				// The user cancelled the transaction
				Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
			}
		}
    }
```

### Pre-Authorization Request


##### 1. Perform the Pre-Authorization

``` java
// Build the preauth request
MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
	// Mandatoy parameters
        .productAmount(1.23)
        .currency(Currency.EUR)
        .foreignTransactionId(UUID.randomUUID().toString())
	// Optional parameters
	// Reference number. Maximum length: 20 alpha numeric characters
	.reference("asd123asd", ReferenceType.REFERENCE_NUMBER)
        // Set print receipt mode
	.printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
	.printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
        .build();
	
// If you want to initiate a moto transaction:
payment.setMotoTransaction(true)

// Start the transaction
MyPOSAPI.createPreauthorization(MainActivity.this, preauth, PREAUTH_REQUEST_CODE);
```

##### 2. Perform the Pre-Authorization Completion

``` java
// Build the preauth completion
MyPOSPreauthorizationCompletion preauthCompletion = MyPOSPreauthorizationCompletion.builder()
	// Mandatoy parameters
        .productAmount(1.23)
        .currency(Currency.EUR)
	.preauthorizationCode("1111")
        .foreignTransactionId(UUID.randomUUID().toString())
	// Optional parameters
	// Reference number. Maximum length: 20 alpha numeric characters
	.reference("asd123asd", ReferenceType.REFERENCE_NUMBER)
        // Set print receipt mode
	.printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
	.printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
        .build();

// Start the transaction
MyPOSAPI.completePreauthorization(MainActivity.this, preauthCompletion, PREAUTH_COMPLETION_REQUEST_CODE);
```


##### 3. Perform the Pre-Authorization Cancellation

``` java
// Build the preauth cancellation
MyPOSPreauthorizationCancellation preauthCancellation = MyPOSPreauthorizationCancellation.builder()
	// Mandatoy parameters
	.preauthorizationCode("1111")
        .foreignTransactionId(UUID.randomUUID().toString())
	// Optional parameters
	// Reference number. Maximum length: 20 alpha numeric characters
	.reference("asd123asd", ReferenceType.REFERENCE_NUMBER)
        // Set print receipt mode
	.printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
	.printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
        .build();

// Start the transaction
MyPOSAPI.cancelPreauthorization(MainActivity.this, preauthCancellation, PREAUTH_CANCELLATION_REQUEST_CODE);
```

##### 4. Handle the result

In your calling Activity, override the ``onActivityResult`` method to handle the result of the payment:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // The same request code as when calling MyPOSAPI.createPreauthorization
    if (requestCode == PREAUTH_REQUEST_CODE) {
        // The transaction was processed, handle the response
        if (resultCode == RESULT_OK) {
            // Something went wrong in the Payment core app and the result couldn't be returned properly
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int transactionResult = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);

            Toast.makeText(this, "Pre-Auth transaction has completed. Result: " + transactionResult, Toast.LENGTH_SHORT).show();

            // TODO: handle each transaction response accordingly
            if (transactionResult == TransactionProcessingResult.TRANSACTION_SUCCESS) {
	    	String preauthCode = data.getStringExtra("preauth_code");
                // Transaction is successful
            }
        } else {
            // The user cancelled the transaction
            Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}

```

Read the pre-athorization code from transaction response:
```java
String preauthCode = data.getStringExtra("preauth_code");
```


### PL GiftCard Request


##### 1. Perform the GiftCard Activation

``` java
// Build the preauth request
MyPOSGiftCardActivation activation = MyPOSGiftCardActivation.builder()
	// Mandatoy parameters
        .productAmount(1.23)
        .currency(Currency.EUR)
        .foreignTransactionId(UUID.randomUUID().toString())
	// Optional parameters
        // Set print receipt mode
	.printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
	.printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
        .build();

// Start the transaction
MyPOSAPI.openGiftCardActivationActivity(MainActivity.this, activation, ACTIVATION_REQUEST_CODE, skipConfirmationScreen);
```

##### 2. Perform the GiftCard Dectivation

``` java
// Start the transaction
MyPOSAPI.openGiftCardDeactivationActivity(MainActivity.this, UUID.randomUUID().toString(), GIFTCARD_DEACTIVATION_REQUEST_CODE);
```


##### 3. Perform the GiftCard Balance Check

``` java
// Start the transaction
MyPOSAPI.openGiftCardCheckBalanceActivity(MainActivity.this, UUID.randomUUID().toString(), GIFTCARD_BALANCE_CHECK_REQUEST_CODE);
```

##### 4. Handle the result

In your calling Activity, override the ``onActivityResult`` method to handle the result of the payment:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // The same request code as when calling MyPOSAPI.openGiftCardActivationActivity
    if (requestCode == ACTIVATION_REQUEST_CODE) {
        // The transaction was processed, handle the response
        if (resultCode == RESULT_OK) {
            // Something went wrong in the Payment core app and the result couldn't be returned properly
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int transactionResult = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);

            Toast.makeText(this, "GiftCard transaction has completed. Result: " + transactionResult, Toast.LENGTH_SHORT).show();

            // TODO: handle each transaction response accordingly
            if (transactionResult == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Transaction is successful
            }
        } else {
            // The user cancelled the transaction
            Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}

```


### SAM Module operation

This functionality defines operations of built-in SAM 1 and SAM 2 modules.
<br>*In order to use the functions listed above you need to have installed myPOS OS version 0.0.8. myPOS OS Version can be checked Only when device is in “Debug Mode” under “About” submenu in myPOS Terminal App.

##### 1. SAM Module operation

 ```java

    //Build SAM module operation
    private static final int SAM_SLOT_1 = 1;
    private static final int SAM_SLOT_2 = 2;

    private void startSAMTest() {
        final Context context = this;
        Thread r = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    int slotNumber = SAM_SLOT_1;
                    int timeoutMs = 1000;

                    boolean hasCard;
                    byte[] resp;
                    byte[] cmd = new byte[] {(byte)0x00,(byte)0xA4,(byte)0x00,(byte)0x00,(byte)0x02, (byte) 0x3f, (byte) 0x00}; // SELECT command for file 0x3F00 (GSM card master file)

                    hasCard = SAMCard.detect(context, slotNumber, timeoutMs);
                    if (!hasCard) {
                        showToast("No SAM card detected in slot " + slotNumber);
                        return;
                    }
                    showToast("SAM card detected in slot " + slotNumber + ". Initializing");

                    resp = SAMCard.open(context, slotNumber, timeoutMs);
                    showToast("Initializing SAM successful. Sending command");

                    resp = SAMCard.isoCommand(context, slotNumber, timeoutMs, cmd);
                    showToast("Response to SAM command received. Closing SAM");

                    SAMCard.close(context, slotNumber, timeoutMs);
                    showToast("SAM module closed");

                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                }
            }
        });
        r.start();
    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
 ```

### Print the last transaction receipt

Printing the last transaction receipt is done by sending a broadcast.

##### 1. Send the broadcast
```java
Intent intent = new Intent(MyPOSUtil.PRINT_LAST_RECEIPT_BROADCAST);
// Whether or not a copy for the customer should be printed
intent.putExtra("print_customer_receipt", true);
MyPOSAPI.sendExplicitBroadcast(context, intent);
```

##### 2. Handle the printing result

When the printing is finished, the Payment core will return a broadcast with intent `com.mypos.broadcast.PRINTING_DONE`.

```java
public class PrinterResultBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean printing_started = intent.getBooleanExtra("printing_started", false);
        int printer_status = intent.getIntExtra("printer_status", PrinterStatus.PRINTER_STATUS_UNKNOWN_ERROR);

        // If the printing has actually started, handle the status
        if (printing_started) {

            // Handle success and errors
            if (printer_status == PrinterStatus.PRINTER_STATUS_SUCCESS) {
                Toast.makeText(context, "Printing successful!", Toast.LENGTH_SHORT).show();
                // Printing is successful
            } else if (printer_status == PrinterStatus.PRINTER_STATUS_OUT_OF_PAPER) {
                // Show "missing paper" dialog
                Toast.makeText(context, "No paper in the printer", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, String.format("Some error occurred while printing. Status: %d", printer_status), Toast.LENGTH_SHORT).show();
            }
            // etc.
        } else {
            // Some other error occurred. Maybe there's no transaction data (when printing last transaction receipt).
            Toast.makeText(context, String.format("Error occurred while printing. Status: %d", printer_status), Toast.LENGTH_SHORT).show();
        }
    }
}

```


### Print a custom receipt

Just like reprinting the last receipt, printing a custom receipt is done by sending a broadcast.

##### 1. Send the print broadcast

The printing broadcast accepts a list of PrinterCommand objects serialized as JSON. Gson can be used to serialize the objects.

A PrinterCommand can be one of the following types:

* HEADER – prints merchant data and time and date format:"DD/MM/YY;HH:mm:ss". The time and date must be sent with the broadcast
* LOGO – prints the device’s logo.
* TEXT – prints arbitrary text. Double width and height are available as parameters
* FOOTER – prints a footer with a “Thank you” message
* IMAGE - prints a custom bitmap image – e.g. QR Code

If you decide to use Gson, add it to your project’s `build.gradle` file:
`compile 'com.google.code.gson:gson:2.8.0'`

An example print broadcast can look like this:

```java
String json; // The serialized list of commands

List<PrinterCommand> commands = new ArrayList<>();

// Add commands to be sent
commands.add(new PrinterCommand(PrinterCommand.CommandType.TEXT, "Normal row 1\n"));
// [...]
commands.add(new PrinterCommand(PrinterCommand.CommandType.TEXT, "Double height\n\n\n", false, true));
// [...]
commands.add(new PrinterCommand(PrinterCommand.CommandType.FOOTER));
//[...]
Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);
commands.add(new PrinterCommand(PrinterCommand.CommandType.IMAGE, bitmap));

// Serialize the command list
Gson gson = new Gson();
json = gson.toJson(commands);

System.out.println("Sending print broadcast: " + json);

Intent intent = new Intent(MyPOSUtil.PRINT_BROADCAST);

// Add the commands
intent.putExtra("commands", json);

// Send broadcast
MyPOSAPI.sendExplicitBroadcast(context, intent);


```

##### 2. Handle the printing result

When the printing is finished, the Payment core will return a broadcast with intent `com.mypos.broadcast.PRINTING_DONE`.

```java
public class PrinterResultBroadcastReceiver extends BroadcastReceiver {

 @Override
 public void onReceive(Context context, Intent intent) {
     boolean printing_started = intent.getBooleanExtra("printing_started", false);
     int printer_status = intent.getIntExtra("printer_status", PrinterStatus.PRINTER_STATUS_UNKNOWN_ERROR);

     // If the printing has actually started, handle the status
     if (printing_started) {

         // Handle success and errors
         if (printer_status == PrinterStatus.PRINTER_STATUS_SUCCESS) {
             Toast.makeText(context, "Printing successful!", Toast.LENGTH_SHORT).show();
             // Printing is successful
         } else if (printer_status == PrinterStatus.PRINTER_STATUS_OUT_OF_PAPER) {
             // Show "missing paper" dialog
             Toast.makeText(context, "No paper in the printer", Toast.LENGTH_SHORT).show();
         } else {
             Toast.makeText(context, String.format("Some error occurred while printing. Status: %d", printer_status), Toast.LENGTH_SHORT).show();
         }
         // etc.
     } else {
         // Some other error occurred. Maybe there's no transaction data (when printing last transaction receipt).
         Toast.makeText(context, String.format("Error occurred while printing. Status: %d", printer_status), Toast.LENGTH_SHORT).show();
     }
 }
}

```


### Response

When a transaction has finished, an Intent with the following data is returned to the calling Activity:

* reference_number - Internal myPOS reference number for the transaction
* cardholder_name - Emboss name on the card
* date_time - Date and time of the transaction formatted as YYMMDDHHmmss
* pan - Obfuscated PAN, e.g. "XXXX-XXXX-XXXX-8008"
* pan_hash - a hash of the PAN
* status (int) - one of the constants in the [TransactionProcessingResult](mypossmartsdk/src/main/java/com/mypos/smartsdk/TransactionProcessingResult.java) class
* status_text - a textual representation of the status
* card_brand - MASTERCARD, MAESTRO, VISA, VISA ELECTRON, VPAY, JCB, PAYPASS, PAYWAVE, UNIONPAY, BANCONTACT
* card_entry_mode – method of presenting the card:
	-	ENTRY_MODE_MAGSTR – mag stripe transaction
	-	ENTRY_MODE_EMV – chip transaction
	-	ENTRY_MODE_CONTACTLESS – contactless mag stripe transaction
	-	ENTRY_MODE_CONTACTLESS_MCHIP – contactless chip transaction
	-	ENTRY_MODE_MANUAL – Manual Key Entry (MO/TO) transaction

* response_code - response code returned by issuer. Values, different from "00", represent the reason for a declined transaction
* authorization_code - authorization code returned by issuer
* signature_required (boolean) - true : signature row must be present on receipt , false : signature row must not be present on receipt
* TSI - Transaction Status Indicator
* TVR - Terminal Verification Result
* AID - Application Identifier (card)
* STAN - System Trace Audit Number (unique number of transaction by TID)
* CVM - Cardholder Verification Method (P – PIN, S – Signature , N – NO CVM)
* application_name - Application Label, read from the card chip
* transaction_approved (boolean) - – true : approved, false : declined
* dcc_available (boolean) - Dynamic currency conversion (DCC) available
* amount_dcc (double) - Dynamic currency conversion (DCC) amount
* currency_dcc - Dynamic currency conversion (DCC) currency
* exchange_rate (double) - Dynamic currency conversion (DCC) exchange rate
* TID - Terminal id
* update_pending (boolean) - New update is available
* resp_code - Payment request response code. Values, different from "00", represent the reason for a declined transaction
* expire_date - Payment request expire date
* merchant_data - Bundle with data from your myPOS profile used for printing the receipts. It contains:
  * billing_descriptor - merchant billing descriptor
  * address_line1 - merchant address line 1
  * address_line2 - merchant address line 2
  * MID - Merchant ID
  * custom_receipt_row1 - custom receipt footer row 1
  * custom_receipt_row2 - custom receipt footer row 2
* installment_data - Bundle with data if user paid in installments. It contains:
  * number (int) - selected number of installments
  * interest_rate (double) - installment interest rate
  * fee (double) - installment fee
  * annual_percentage_rate (double) - installment annual percantage rate
  * total_amount (double) - installments total amount
  * first_installment_amount (double) - first installment amount
  * subsequent_installment_amount (double) - subsequent installment amount


Note 1: Unless noted, extras in the bundle are Strings.

Note 2: Depending on the card and transaction type, some of the extras are not always present.
