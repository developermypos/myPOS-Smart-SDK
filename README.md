# myPOS-Smart-SDK

This repository provides a guidance on integrating an Android app with a myPOS Smart device. Once integrated, the app will be able to communicate with the core device components in order to accept card payments (including but not limited to VISA, Mastercard, UnionPay International, JCB, Bancontact). myPOS-Smart-SDK complеments a Smart POS terminal in order to initiate transaction, complete all steps for processing payment, make refund to the customer card account and print a custom bill slip using the device printer. The built-in functionalities of myPOS-Smart-SDK allow you to send Payment Requests, manage operations for the first and second SAM component the myPOS Smart device has and print bitmap image integrated to the receipt (e.g. QR Code).

No sensitive card data is ever passed through or stored on myPOS Smart device. All data is encrypted by the core card terminal module, which has been fully certified to the highest industry standards (PCI, EMV I and II, Visa, MasterCard, Amex).

### Table of Contents

* [Installation](#installation)

  * [Build the library and add it as a dependency](#build-the-library-and-add-it-as-a-dependency)

  * [Add the library to your project](#add-the-library-to-your-project)

  * [As a git submodule](#as-a-git-submodule)

* [Usage](#Usage)

  * [Process checkout](#process-checkout)

  * [Refund request](#refund-request)
  
  * [Payment Request](#payment-request)
  
  * [SAM Module operation](#sam-module-operation)

  * [Print the last transaction receipt](#print-the-last-transaction-receipt)

  * [Print a custom receipt](#print-a-custom-receipt)
  
* [Response](#response)

## Installation

myPOS-Smart-SDK can be used by adding it as a git submodule to your project or building the .aar and using it.

### Build the library and add it as a dependency


1. Download the source code and import it in Android Studio
2. Build it
3. Navigate to the `build/outputs/aar` folder and retrieve the .aar file

### Add the library to your project

After building the library, add it as a dependency in Android Studio, create a new module.

![Create a new module](doc/new_module.png)

In the window that appears, select "Import JAR/.AAR Package" and click "Next"

![Import AAR](doc/import_jar_aar.png)

Then navigate to where the generated .aar file is located and select it, then click "Finish"

![Select AAR](doc/select_aar.png)


In your `build.gradle` file add the newly-created module as a dependency:
```groovy
dependencies {
    // [...]
    compile project(':mypossmartsdk-0.0.1')
}
```


### As a git submodule

If your project uses git for version control, run the following command in the project's root:
``` bash
git submodule add https://github.com/developermypos/myPOS-Smart-SDK.git smartsdk
```
__Note:__ Older versions of Git might need ``git submodule update --init --recursive`` to be called

After git finishes cloning the module, create a new module in your project:

![Create a new module](doc/new_module.png)

In the window that appears, select Import Gradle Project and click "Next":

![Import Gradle Project](doc/import_gradle_project.png)

Navigate to your project's root directory. Select the ``smartsdk`` directory:

![Import Gradle Project](doc/select_module.png)

After clicking "OK", click "Finish" and the module should be ready to use:

![Import Gradle Project](doc/module_selected.png)

Add the module to your ``build.gradle``'s dependencies section:
```groovy
dependencies {
    // [...]
    compile project(path: ':mypossmartsdk')
}
```
### Additional functions:
-	Payment Requests, 
-	Managing operations of first and second SAM component of myPOS Smart device
-	Print bitmap image integrated to the receipt (e.g. QR Code)
*In order to use additional functions listed above you need to have installed myPOS OS version 0.0.7. myPOS OS Version can be checked Only when device is in “Debug Mode” under “About” submenu in myPOS Terminal App.
 

# Usage

Once the SDK is added to your project, using the Payment API can be done with the provided helper classes.


### Process a checkout


##### 1. Make the payment

```java
// Build the payment call
 MyPOSPayment payment = MyPOSPayment.builder()
         // Mandatory parameters
         .productAmount(13.37)
         .currency(Currency.EUR)
         // Foreign transaction ID. Maximum length: 128 characters
         .foreignTransactionId(UUID.randomUUID().toString())
         .build();

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
        .refundAmount(1.23)
        .currency(Currency.EUR)
        .foreignTransactionId(UUID.randomUUID().toString())
        .build();

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
sendBroadcast(intent);
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

* HEADER – prints merchant data and time and date. The time and date must be sent with the broadcast
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

Intent intent = new Intent(Util.PRINT_BROADCAST);

// Add the commands
intent.putExtra("commands", json);

// Send broadcast
sendBroadcast(intent);


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
* card_brand - MASTERCARD, MAESTRO, VISA, PAYPASS, etc.
* card_entry_mode - ENTRY_MODE_MAGSTR, ENTRY_MODE_EMV, ENTRY_MODE_CONTACTLESS, etc.
* response_code
* authorization_code
* signature_required (boolean)
* TSI
* TVR
* AID
* STAN
* CVM
* application_name
* transaction_approved (boolean)
* merchant_data - Bundle with data from your myPOS profile used for printing the receipts. It contains:
  * billing_descriptor
  * address_line1
  * address_line2
  * MID
  * custom_receipt_row1
  * custom_receipt_row2


Note 1: Unless noted, extras in the bundle are Strings.

Note 2: Depending on the card and transaction type, some of the extras are not always present.
