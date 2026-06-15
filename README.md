# myPOS Smart SDK

This repository provides guidance on integrating an Android app with a **myPOS Smart** device. Once integrated, the app will be able to communicate with the core device components to accept card payments (including VISA, Mastercard, UnionPay International, JCB, Bancontact, and more).

The SDK complements a Smart POS terminal to:
- Initiate and complete payment transactions
- Issue refunds to customer card accounts
- Perform void operations
- Handle pre-authorizations
- Process QR-based payments (TWINT, Satispay, Iris)
- Print custom bill slips using the device printer
- Scan barcodes and QR codes
- Manage SAM module operations

> **Security note:** No sensitive card data is ever passed through or stored on the myPOS Smart device. All data is encrypted by the core card terminal module, which is fully certified to the highest industry standards (PCI, EMV I and II, Visa, MasterCard, Amex).

---

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
  - [Common Builder Parameters](#common-builder-parameters)
  - [Receive POS Info](#receive-pos-info)
  - [Process a Checkout](#process-a-checkout)
  - [Refund Request](#refund-request)
  - [Payment Request](#payment-request)
  - [Void Request](#void-request)
  - [Pre-Authorization Request](#pre-authorization-request)
  - [Vending Payment](#vending-payment)
  - [PL GiftCard Request](#pl-giftcard-request)
  - [SAM Module Operation](#sam-module-operation)
  - [Print the Last Transaction Receipt](#print-the-last-transaction-receipt)
  - [Print a Custom Receipt](#print-a-custom-receipt)
  - [Barcode / QR Scanner](#barcodeqr-scanner)
  - [Get Last Transaction Data](#get-last-transaction-data)
  - [TWINT QR Payment](#twint-qr-payment)
  - [Satispay Payment](#satispay-payment)
  - [Iris Payment](#iris-payment)
  - [Complete / Cancel Pending Transaction](#complete--cancel-pending-transaction)
- [Response](#response)

---

## Installation

Add the repository to your project-level `build.gradle`:

```groovy
allprojects {
    repositories {
        mavenCentral()
    }
}
```

Add the dependency to your module-level `build.gradle`:

```groovy
implementation 'com.mypos:mypossmartsdk:1.0.8'
```

> **Note:** To use Payment Requests, SAM module management, and bitmap printing (e.g., QR Codes), you must have myPOS OS version **0.0.7** or higher installed on the device. The OS version can be checked in "Debug Mode" under the "About" submenu in the myPOS Terminal App.

---

## Usage

Once the SDK is added to your project, use the provided helper classes to interact with the Payment API.

---

### Common Builder Parameters

All transaction builders extend `MyPOSBase.BaseBuilder` and share the following optional parameters:

| Parameter | Type | Description |
|-----------|------|-------------|
| `foreignTransactionId(String)` | `String` | Your own unique transaction reference. Max 128 characters. |
| `language(Locale)` | `Locale` | Language for the payment UI (e.g. `Locale.ENGLISH`, `Locale.forLanguageTag("de")`). |
| `printMerchantReceipt(int)` | `int` | Merchant receipt print mode (see constants below). |
| `printCustomerReceipt(int)` | `int` | Customer receipt print mode (see constants below). |
| `baseColor(int)` | `int` | Accent color for the payment app UI. Pass an Android `@ColorInt` value. |
| `applicationId(String)` | `String` | Your app's package name used for origin tracking. Max 50 characters, alphanumeric + common punctuation. |

**Receipt print mode constants (`MyPOSUtil`):**

| Constant | Value | Description |
|----------|-------|-------------|
| `RECEIPT_ON` | `1` | Always print |
| `RECEIPT_OFF` | `2` | Never print |
| `RECEIPT_AFTER_CONFIRMATION` | `3` | Print after customer confirms (supports e-receipt) |
| `RECEIPT_E_RECEIPT` | `4` | Send digitally via email or phone (requires `eReceiptReceiver`) |

---

### Receive POS Info

Add the following to your `AndroidManifest.xml`:

```xml
<queries>
    <package android:name="com.mypos" />
</queries>
```

Query basic terminal information (TID, currency name, currency code, merchant info, etc.):

```java
MyPOSAPI.registerPOSInfo(MainActivity.this, new OnPOSInfoListener() {
    @Override
    public void onReceive(POSInfo info) {
        // info received
    }
});
```

---

### Process a Checkout

#### 1. Build and start the payment

```java
MyPOSPayment payment = MyPOSPayment.builder()
    // Mandatory
    .productAmount(13.37)
    .currency(Currency.EUR)
    .foreignTransactionId(UUID.randomUUID().toString())
    // Optional
    .tippingModeEnabled(true)
    .tipAmount(1.55)
    .operatorCode("1234")                                          // Max 4 digits
    .reference("asd123asd", ReferenceType.REFERENCE_NUMBER)       // Max 50 alphanumeric chars
    .fixedPinpad(true)
    .mastercardSonicBranding(true)
    .visaSensoryBranding(true)
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)                   // RECEIPT_ON | RECEIPT_OFF
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)                   // RECEIPT_ON | RECEIPT_OFF | RECEIPT_AFTER_CONFIRMATION | RECEIPT_E_RECEIPT
    .eReceiptReceiver("customer@example.com")                     // Email or phone; used with RECEIPT_E_RECEIPT or RECEIPT_AFTER_CONFIRMATION
    .language(Locale.ENGLISH)
    .baseColor(Color.parseColor("#FF5722"))                       // Custom accent color for the payment UI
    .applicationId("app-id")                                      // Your app package name (max 50 chars)
    .build();

// Optional flags (set after build)
payment.setMotoTransaction(true);       // MO/TO (Mail Order / Telephone Order)
payment.setGiftCardTransaction(true);   // Gift card payment

MyPOSAPI.openPaymentActivity(MainActivity.this, payment, PAYMENT_REQUEST_CODE /*, skipConfirmationScreen*/);
```

#### 2. Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PAYMENT_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            boolean approved = data.getBooleanExtra("transaction_approved", false);

            if (approved) {
                // Transaction approved
            } else {
                String responseCode = data.getStringExtra("response_code");
            }
        } else {
            // User cancelled
        }
    }
}
```

---

### Refund Request

#### 1. Build and start the refund

```java
MyPOSRefund refund = MyPOSRefund.builder()
    // Mandatory
    .refundAmount(1.23)
    .currency(Currency.EUR)
    .foreignTransactionId(UUID.randomUUID().toString())
    // Optional
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .eReceiptReceiver("customer@example.com")
    .language(Locale.ENGLISH)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id")
    .build();

// Optional flags
refund.setMotoTransaction(true);
refund.setGiftCardTransaction(true);

MyPOSAPI.openRefundActivity(MainActivity.this, refund, REFUND_REQUEST_CODE /*, skipConfirmationScreen*/);
```

#### 2. Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REFUND_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Refund successful
            }
        } else {
            // User cancelled
        }
    }
}
```

---

### Payment Request

Send a payment request via SMS directly from the myPOS Smart device.

#### 1. Build and start the payment request

```java
private static final int PAYMENT_REQUEST_REQUEST_CODE = 4;

MyPOSPaymentRequest paymentRequest = MyPOSPaymentRequest.builder()
    .productAmount(3.55)
    .currency(Currency.EUR)
    .expiryDays(60)
    .recipientName("John Doe")
    .GSM("0899070087")
    .eMail("")
    .reason("System test")
    .language(Locale.ENGLISH)
    .applicationId("app-id") 
    .build();

MyPOSAPI.createPaymentRequest(MainActivity.this, paymentRequest, PAYMENT_REQUEST_REQUEST_CODE);
```

#### 2. Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PAYMENT_REQUEST_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Payment request sent successfully
            }
        }
    }
}
```

---

### Void Request

#### 1. Build and start the void transaction

```java
private static final int VOID_REQUEST_CODE = 5;

MyPOSVoid voidTr = MyPOSVoid.builder()
    .STAN(27)
    .authCode("VISSIM")
    .dateTime("180129123753")
    // .voidLastTransactionFlag(true)  // Voids the last transaction on this terminal
    // Optional base params
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .language(Locale.ENGLISH)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id")
    .build();

MyPOSAPI.openVoidActivity(MainActivity.this, voidTr, VOID_REQUEST_CODE, true);
```

#### 2. Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == VOID_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Void successful
            }
        } else {
            // User cancelled
        }
    }
}
```

---

### Pre-Authorization Request

#### 1. Create a Pre-Authorization

```java
MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
    // Mandatory
    .productAmount(1.23)
    .currency(Currency.EUR)
    .foreignTransactionId(UUID.randomUUID().toString())
    // Optional
    .reference("asd123asd", ReferenceType.REFERENCE_NUMBER)   // Max 20 alphanumeric chars
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .eReceiptReceiver("customer@example.com")
    .language(Locale.ENGLISH)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id")
    .build();

MyPOSAPI.createPreauthorization(MainActivity.this, preauth, PREAUTH_REQUEST_CODE /*, skipConfirmationScreen*/);
```

#### 2. Complete a Pre-Authorization

```java
MyPOSPreauthorizationCompletion preauthCompletion = MyPOSPreauthorizationCompletion.builder()
    // Mandatory
    .productAmount(1.23)
    .currency(Currency.EUR)
    .preauthorizationCode("1111")
    .foreignTransactionId(UUID.randomUUID().toString())
    // Optional
    .reference("asd123asd", ReferenceType.REFERENCE_NUMBER)
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .language(Locale.ENGLISH)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id") 
    .build();

MyPOSAPI.completePreauthorization(MainActivity.this, preauthCompletion, PREAUTH_COMPLETION_REQUEST_CODE /*, skipConfirmationScreen*/);
```

#### 3. Cancel a Pre-Authorization

```java
MyPOSPreauthorizationCancellation preauthCancellation = MyPOSPreauthorizationCancellation.builder()
    // Mandatory
    .preauthorizationCode("1111")
    .foreignTransactionId(UUID.randomUUID().toString())
    // Optional
    .reference("asd123asd", ReferenceType.REFERENCE_NUMBER)
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .language(Locale.ENGLISH)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id") 
    .build();

MyPOSAPI.cancelPreauthorization(MainActivity.this, preauthCancellation, PREAUTH_CANCELLATION_REQUEST_CODE /*, skipConfirmationScreen*/);
```

#### 4. Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PREAUTH_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                String preauthCode = data.getStringExtra("preauth_code");
                // Use preauthCode for completion or cancellation
            }
        } else {
            // User cancelled
        }
    }
}
```

---

### Vending Payment

A specialized payment flow designed for unattended terminals (e.g., vending machines). Supports DCC, card detection timeout, and UI options to hide the amount or cancel button.

#### 1. Build and start the vending payment

```java
MyPOSVendingPayment vendingPayment = MyPOSVendingPayment.builder()
    // Mandatory
    .productAmount(2.50)
    .currency(Currency.EUR)
    .foreignTransactionId(UUID.randomUUID().toString())
    // Optional
    .dccEnabled(true)                // Enable Dynamic Currency Conversion
    .showAmount(true)                // Show the transaction amount on screen
    .showCancel(true)                // Show the cancel button
    .cardDetectionTimeout(30000)     // Card detection timeout in milliseconds
    .operatorCode("1234")
    .reference("ref001", ReferenceType.REFERENCE_NUMBER)
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .language(Locale.ENGLISH)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id") 
    .build();

MyPOSAPI.openVendingPaymentActivity(MainActivity.this, vendingPayment, VENDING_REQUEST_CODE, false);
```

#### 2. Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == VENDING_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Vending payment successful
            }
        } else {
            // User cancelled
        }
    }
}
```

---

### PL GiftCard Request

#### 1. Activate a GiftCard

```java
MyPOSGiftCardActivation activation = MyPOSGiftCardActivation.builder()
    // Mandatory
    .productAmount(1.23)
    .currency(Currency.EUR)
    .foreignTransactionId(UUID.randomUUID().toString())
    // Optional
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .language(Locale.ENGLISH)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id") 
    .build();

MyPOSAPI.openGiftCardActivationActivity(MainActivity.this, activation, ACTIVATION_REQUEST_CODE, false);
```

#### 2. Deactivate a GiftCard

```java
MyPOSAPI.openGiftCardDeactivationActivity(
    MainActivity.this,
    UUID.randomUUID().toString(),
    GIFTCARD_DEACTIVATION_REQUEST_CODE
);
```

#### 3. Check GiftCard Balance

```java
MyPOSAPI.openGiftCardCheckBalanceActivity(
    MainActivity.this,
    UUID.randomUUID().toString(),
    GIFTCARD_BALANCE_CHECK_REQUEST_CODE
);
```

#### 4. Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ACTIVATION_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // GiftCard operation successful
            }
        } else {
            // User cancelled
        }
    }
}
```

---

### SAM Module Operation

Interact with the built-in SAM 1 and SAM 2 modules. All SAM operations **must be called from a background thread**.

```java
private static final int SAM_SLOT_1 = 1;
private static final int SAM_SLOT_2 = 2;

private void startSAMTest() {
    new Thread(() -> {
        try {
            int slotNumber = SAM_SLOT_1;
            int timeoutMs = 1000;

            // SELECT command for GSM card master file (0x3F00)
            byte[] cmd = new byte[] {
                (byte)0x00, (byte)0xA4, (byte)0x00, (byte)0x00,
                (byte)0x02, (byte)0x3F, (byte)0x00
            };

            boolean hasCard = SAMCard.detect(context, slotNumber, timeoutMs);
            if (!hasCard) {
                showToast("No SAM card in slot " + slotNumber);
                return;
            }

            byte[] atr = SAMCard.open(context, slotNumber, timeoutMs);
            showToast("SAM initialized. Sending command...");

            byte[] response = SAMCard.isoCommand(context, slotNumber, timeoutMs, cmd);
            showToast("Response received. Closing SAM...");

            SAMCard.close(context, slotNumber, timeoutMs);
            showToast("SAM module closed.");

        } catch (Exception e) {
            showToast(e.getMessage());
        }
    }).start();
}

private void showToast(final String message) {
    runOnUiThread(() ->
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show()
    );
}
```

---

### Print the Last Transaction Receipt

#### 1. Send the broadcast

```java
Intent intent = new Intent(MyPOSUtil.PRINT_LAST_RECEIPT_BROADCAST);
intent.putExtra("print_customer_receipt", true);
MyPOSAPI.sendExplicitBroadcast(context, intent);
```

#### 2. Handle the printing result

Register a `BroadcastReceiver` for `com.mypos.broadcast.PRINTING_DONE`:

```java
public class PrinterResultBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean printingStarted = intent.getBooleanExtra("printing_started", false);
        int printerStatus = intent.getIntExtra("printer_status", PrinterStatus.PRINTER_STATUS_UNKNOWN_ERROR);

        if (printingStarted) {
            switch (printerStatus) {
                case PrinterStatus.PRINTER_STATUS_SUCCESS:
                    Toast.makeText(context, "Printing successful!", Toast.LENGTH_SHORT).show();
                    break;
                case PrinterStatus.PRINTER_STATUS_OUT_OF_PAPER:
                    Toast.makeText(context, "No paper in the printer", Toast.LENGTH_SHORT).show();
                    break;
                case PrinterStatus.PRINTER_STATUS_PRINTER_BUSY:
                    Toast.makeText(context, "Printer is busy", Toast.LENGTH_SHORT).show();
                    break;
                case PrinterStatus.PRINTER_STATUS_PRINTER_OVERHEATING:
                    Toast.makeText(context, "Printer is overheating", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, "Printing error: " + printerStatus, Toast.LENGTH_SHORT).show();
            }
        } else {
            // Printing did not start (e.g., no transaction data for last receipt reprint)
            Toast.makeText(context, "Error starting print: " + printerStatus, Toast.LENGTH_SHORT).show();
        }
    }
}
```

---

### Print a Custom Receipt

#### 1. Build and send the print broadcast

Printing a custom receipt requires a list of `PrinterCommand` objects serialized as JSON. Add Gson to your `build.gradle`:

```groovy
implementation 'com.google.code.gson:gson:2.10.1'
```

**PrinterCommand types:**

| Type     | Description |
|----------|-------------|
| `HEADER` | Prints merchant data and date/time. Text format: `"DD/MM/YY;HH:mm:ss"` |
| `LOGO`   | Prints the device logo |
| `TEXT`   | Prints arbitrary text with optional font size and alignment |
| `FOOTER` | Prints a "Thank you" footer |
| `IMAGE`  | Prints a custom bitmap (e.g., QR Code) |

**Text alignment options:** `ALIGN_LEFT`, `ALIGN_CENTER`, `ALIGN_RIGHT`

**Max characters per line:** 32 (myPOS Smart) / 39 (myPOS Hub)

```java
List<PrinterCommand> commands = new ArrayList<>();

// Header with date/time
commands.add(new PrinterCommand(PrinterCommand.CommandType.HEADER, "15/06/26;14:30:00"));

// Logo
commands.add(new PrinterCommand(PrinterCommand.CommandType.LOGO));

// Normal text
commands.add(new PrinterCommand(PrinterCommand.CommandType.TEXT, "Order #12345\n"));

// Text with custom font size
commands.add(new PrinterCommand(PrinterCommand.CommandType.TEXT, "TOTAL: 13.37 EUR\n", 24));

// Centered text
commands.add(new PrinterCommand(
    PrinterCommand.CommandType.TEXT,
    "Thank you!\n",
    PrinterCommand.Alignment.ALIGN_CENTER
));

// Two-column row (left text + right-aligned value on the same line)
commands.add(new PrinterCommand(PrinterCommand.CommandType.TEXT, "Item 1", "5.00 EUR"));

// Multi-column row with weights and alignment
commands.add(new PrinterCommand(
    PrinterCommand.CommandType.TEXT,
    PrinterCommand.columnRow(
        new String[]    { "Qty", "Item",      "Price"   },
        new int[]       {  1,     3,           1        },
        new Alignment[] { RIGHT,  ALIGN_LEFT,  ALIGN_RIGHT },
        " ",
        PrinterCommand.RECEIPT_SMART_MAX_CHARS_PER_LINE
    )
));

// Custom bitmap image (e.g., QR code)
Bitmap qrBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qr_code);
commands.add(new PrinterCommand(PrinterCommand.CommandType.IMAGE, qrBitmap));

// Footer
commands.add(new PrinterCommand(PrinterCommand.CommandType.FOOTER));

// Serialize and send
String json = new Gson().toJson(commands);
Intent intent = new Intent(MyPOSUtil.PRINT_BROADCAST);
intent.putExtra("commands", json);
MyPOSAPI.sendExplicitBroadcast(context, intent);
```

#### 2. Handle the printing result

Same as [handling the last receipt printing result](#2-handle-the-printing-result).

---

### Barcode / QR Scanner

#### 1. Send the scan broadcast

```java
Intent intent = new Intent(MyPOSUtil.SCANNER_BROADCAST);
MyPOSAPI.sendExplicitBroadcast(context, intent);
```

#### 2. Handle the scanning result

Register a `BroadcastReceiver` for `com.mypos.broadcast.SCANNER_RESULT_BROADCAST`:

```java
public class ScannerResultBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra("status", Activity.RESULT_CANCELED);
        String code = intent.getStringExtra("code");

        if (status == Activity.RESULT_OK) {
            Toast.makeText(context, "Scanned: " + code, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Scanner cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
```

---

### Get Last Transaction Data

```java
final Uri CONTENT_URI = Uri.parse("content://com.mypos.providers.LastTransactionProvider/last_transaction");

Cursor cursor = getContentResolver().query(
    CONTENT_URI,
    new String[] {
        "amount", "currency", "reference_number", "reference_number_type",
        "operator_code", "response_code", "stan", "date_time",
        "authorization_code", "card_brand", "transaction_approved",
        "cvm", "transaction_type", "rrn"
    },
    null, null, null
);

if (cursor == null) return; // No last transaction recorded

cursor.moveToFirst();

double  amount              = cursor.getDouble(cursor.getColumnIndex("amount"));
String  currency            = cursor.getString(cursor.getColumnIndex("currency"));
String  referenceNumber     = cursor.getString(cursor.getColumnIndex("reference_number"));
int     referenceNumberType = cursor.getInt(cursor.getColumnIndex("reference_number_type"));
String  operatorCode        = cursor.getString(cursor.getColumnIndex("operator_code"));
String  responseCode        = cursor.getString(cursor.getColumnIndex("response_code"));
String  stan                = cursor.getString(cursor.getColumnIndex("stan"));
String  dateTime            = cursor.getString(cursor.getColumnIndex("date_time"));
String  authorizationCode   = cursor.getString(cursor.getColumnIndex("authorization_code"));
String  cardBrand           = cursor.getString(cursor.getColumnIndex("card_brand"));
boolean transactionApproved = cursor.getInt(cursor.getColumnIndex("transaction_approved")) == 1;
String  cvm                 = cursor.getString(cursor.getColumnIndex("cvm"));
String  transactionType     = cursor.getString(cursor.getColumnIndex("transaction_type"));
String  rrn                 = cursor.getString(cursor.getColumnIndex("rrn"));

if (!cursor.isClosed()) cursor.close();
```

---

### TWINT QR Payment

TWINT is a popular mobile payment method in Switzerland. Users pay by scanning a QR code displayed on the terminal with the TWINT app.

#### Payment

```java
// Simple form
MyPOSAPI.openTwintPaymentActivity(
    MainActivity.this, 10.0, Currency.CHF, Locale.forLanguageTag("de"), TWINT_REQUEST_CODE
);

// With skip confirmation screen
MyPOSAPI.openTwintPaymentActivity(
    MainActivity.this, 10.0, Currency.CHF, Locale.forLanguageTag("de"), true, TWINT_REQUEST_CODE
);

// Using MyPOSQRPayment builder for full control
MyPOSQRPayment twintPayment = MyPOSQRPayment.builder()
    .productAmount(10.0)
    .currency(Currency.CHF)
    .language(Locale.forLanguageTag("de"))
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id")
    .build();
MyPOSAPI.openTwintPaymentActivity(MainActivity.this, twintPayment, false, TWINT_REQUEST_CODE);
```

#### Refund

```java
MyPOSAPI.openTwintRefundActivity(MainActivity.this, 10.0, Currency.CHF, TWINT_REFUND_REQUEST_CODE);

// With skip confirmation screen
MyPOSAPI.openTwintRefundActivity(MainActivity.this, 10.0, Currency.CHF, false, TWINT_REFUND_REQUEST_CODE);
```

#### Void

```java
MyPOSAPI.openTwintVoidActivity(
    MainActivity.this,
    10.0,
    Currency.CHF,
    "original-twint-reference",   // Reference returned from the original TWINT payment
    TWINT_VOID_REQUEST_CODE
);

// With skip confirmation screen
MyPOSAPI.openTwintVoidActivity(
    MainActivity.this,
    10.0,
    Currency.CHF,
    "original-twint-reference",
    false,
    TWINT_VOID_REQUEST_CODE
);
```

#### Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == TWINT_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // TWINT payment successful
            }
        } else {
            Toast.makeText(this, "TWINT cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
```

---

### Satispay Payment

Satispay is an Italian mobile payment method. Users pay by scanning a QR code with the Satispay app.

#### Payment

```java
MyPOSAPI.openSatispayPaymentActivity(MainActivity.this, 5.00, Currency.EUR, false, SATISPAY_REQUEST_CODE);

// Using MyPOSQRPayment builder
MyPOSQRPayment satispayPayment = MyPOSQRPayment.builder()
    .productAmount(5.00)
    .currency(Currency.EUR)
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id")
    .build();
MyPOSAPI.openSatispayPaymentActivity(MainActivity.this, satispayPayment, false, SATISPAY_REQUEST_CODE);
```

#### Refund

```java
MyPOSAPI.openSatispayRefundActivity(
    MainActivity.this,
    5.00,
    Currency.EUR,
    "original-satispay-reference",   // Reference returned from the original Satispay payment
    false,
    SATISPAY_REFUND_REQUEST_CODE
);
```

#### Void

```java
MyPOSAPI.openSatispayVoidActivity(
    MainActivity.this,
    5.00,
    Currency.EUR,
    false,
    "original-satispay-reference",
    SATISPAY_VOID_REQUEST_CODE
);
```

#### Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == SATISPAY_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Satispay payment successful
            }
        } else {
            Toast.makeText(this, "Satispay cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
```

---

### Iris Payment

Iris is a Greek interbank instant payment system for QR-based payments.

#### Payment

```java
MyPOSAPI.openIrisPaymentActivity(MainActivity.this, 8.00, Currency.EUR, false, IRIS_REQUEST_CODE);

// Using MyPOSQRPayment builder
MyPOSQRPayment irisPayment = MyPOSQRPayment.builder()
    .productAmount(8.00)
    .currency(Currency.EUR)
    .printMerchantReceipt(MyPOSUtil.RECEIPT_ON)
    .printCustomerReceipt(MyPOSUtil.RECEIPT_ON)
    .baseColor(Color.parseColor("#FF5722"))
    .applicationId("app-id")
    .build();
MyPOSAPI.openIrisPaymentActivity(MainActivity.this, irisPayment, false, IRIS_REQUEST_CODE);
```

#### Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == IRIS_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Iris payment successful
            }
        } else {
            Toast.makeText(this, "Iris cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
```

---

### Complete / Cancel Pending Transaction

Used to finalize or cancel a transaction in a pending state (e.g., when the result status is `COMPLETE_TRANSACTION_PENDING`).

#### Complete a pending transaction

```java
MyPOSAPI.openCompleteTxActivity(
    MainActivity.this,
    5.00,                                  // Partial amount (pass null if not applicable)
    "customer@example.com",                // Credential (e-receipt receiver or token)
    "original-foreign-transaction-id",
    Locale.ENGLISH,
    false,                                 // skipConfirmationScreen
    COMPLETE_TX_REQUEST_CODE
);
```

#### Cancel a pending transaction

```java
MyPOSAPI.openCancelTxActivity(
    MainActivity.this,
    "original-foreign-transaction-id",
    Locale.ENGLISH,
    false,                                 // skipConfirmationScreen
    CANCEL_TX_REQUEST_CODE
);
```

#### Handle the result

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == COMPLETE_TX_REQUEST_CODE) {
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
                return;
            }
            int status = data.getIntExtra("status", TransactionProcessingResult.TRANSACTION_FAILED);
            if (status == TransactionProcessingResult.TRANSACTION_SUCCESS) {
                // Pending transaction completed successfully
            }
        }
    }
}
```

---

## Response

When a transaction completes, an `Intent` is returned to the calling `Activity` with the following extras.

### Transaction Result Codes (`status`)

| Constant | Value | Description |
|----------|-------|-------------|
| `TRANSACTION_SUCCESS` | `0` | Transaction completed successfully |
| `TRANSACTION_CANCELED` | `1` | User cancelled the transaction |
| `TRANSACTION_DECLINED` | `2` | Transaction declined by host or issuer |
| `TRANSACTION_FAILED` | `3` | Connection timeout or other failure |
| `DEVICE_NOT_ACTIVATED` | `4` | Device is not activated |
| `NO_DATA_FOUND` | `5` | Required data is missing (e.g., no previous transaction for void) |
| `INVALID_CURRENCY` | `6` | Currency does not match the device currency |
| `INVALID_AMOUNT` | `7` | Amount is out of the allowed range |
| `COMMUNICATION_ERROR` | `8` | Communication error with host |
| `INVALID_E_RECEIPT_CREDENTIAL` | `9` | Invalid e-receipt email or phone number |
| `COMPLETE_TRANSACTION_PENDING` | `10` | Transaction is pending completion |

### Intent Extras

> Unless noted, all extras are `String` type. Some extras may not be present depending on card and transaction type.

| Extra | Type | Description |
|-------|------|-------------|
| `status` | `int` | Transaction result code (see table above) |
| `status_text` | `String` | Textual representation of the status |
| `transaction_approved` | `boolean` | `true` if approved, `false` if declined |
| `response_code` | `String` | Issuer response code. `"00"` = approved; other values indicate decline reason |
| `authorization_code` | `String` | Authorization code from issuer |
| `reference_number` | `String` | Internal myPOS reference number |
| `cardholder_name` | `String` | Emboss name on the card |
| `pan` | `String` | Obfuscated PAN, e.g. `"XXXX-XXXX-XXXX-8008"` |
| `pan_hash` | `String` | Hash of the PAN |
| `card_brand` | `String` | `MASTERCARD`, `MAESTRO`, `VISA`, `VISA ELECTRON`, `VPAY`, `JCB`, `PAYPASS`, `PAYWAVE`, `UNIONPAY`, `BANCONTACT` |
| `card_entry_mode` | `String` | Card entry method (see below) |
| `date_time` | `String` | Transaction date/time formatted as `YYMMDDHHmmss` |
| `signature_required` | `boolean` | Whether a signature row should appear on the receipt |
| `TSI` | `String` | Transaction Status Indicator |
| `TVR` | `String` | Terminal Verification Result |
| `AID` | `String` | Application Identifier (from card chip) |
| `STAN` | `String` | System Trace Audit Number |
| `CVM` | `String` | Cardholder Verification Method: `P` (PIN), `S` (Signature), `N` (No CVM) |
| `application_name` | `String` | Application Label from card chip |
| `TID` | `String` | Terminal ID |
| `update_pending` | `boolean` | `true` if a software update is available |
| `dcc_available` | `boolean` | Dynamic Currency Conversion is available |
| `amount_dcc` | `double` | DCC amount |
| `currency_dcc` | `String` | DCC currency |
| `exchange_rate` | `double` | DCC exchange rate |
| `preauth_code` | `String` | Pre-authorization code (Pre-Auth transactions only) |
| `resp_code` | `String` | Payment request response code |
| `expire_date` | `String` | Payment request expiry date |
| `merchant_data` | `Bundle` | Merchant profile data (see below) |
| `installment_data` | `Bundle` | Installment payment data (see below) |

### Card Entry Modes

| Value | Description |
|-------|-------------|
| `ENTRY_MODE_MAGSTR` | Magnetic stripe |
| `ENTRY_MODE_EMV` | Chip (EMV) |
| `ENTRY_MODE_CONTACTLESS` | Contactless magnetic stripe |
| `ENTRY_MODE_CONTACTLESS_MCHIP` | Contactless chip |
| `ENTRY_MODE_MANUAL` | Manual Key Entry (MO/TO) |

### `merchant_data` Bundle

| Key | Description |
|-----|-------------|
| `billing_descriptor` | Merchant billing descriptor |
| `address_line1` | Merchant address line 1 |
| `address_line2` | Merchant address line 2 |
| `MID` | Merchant ID |
| `custom_receipt_row1` | Custom receipt footer row 1 |
| `custom_receipt_row2` | Custom receipt footer row 2 |

### `installment_data` Bundle

| Key | Type | Description |
|-----|------|-------------|
| `number` | `int` | Number of installments selected |
| `interest_rate` | `double` | Installment interest rate |
| `fee` | `double` | Installment fee |
| `annual_percentage_rate` | `double` | Annual percentage rate |
| `total_amount` | `double` | Total amount including installments |
| `first_installment_amount` | `double` | First installment amount |
| `subsequent_installment_amount` | `double` | Subsequent installment amount |

````
<userPrompt>
Provide the fully rewritten file, incorporating the suggested code change. You must produce the complete file.
</userPrompt>
