package com.mypos.smartsdk.print;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 *
 */

public class PrinterCommand {

    public enum CommandType {
        /**
         * If type is header, the text should contain the receipt date in format "DD/MM/YY;HH:mm:ss".
         */
        HEADER,
        LOGO,
        TEXT,
        FOOTER,
        IMAGE
    }

    /**
     * The command's type
     */
    private CommandType type;

    /**
     * The text to be printed
     */
    private String  text;
    /**
     * Should the text be printed with double width?
     */
    private boolean doubleWidth;
    /**
     * Should the text be printed with double height?
     */
    private boolean doubleHeight;
    /**
     * The encoding
     */
    private String  encoding;
    /**
     * Image to be printed
     */
    private String imageEncoded;

    public PrinterCommand(CommandType type) {
        this.type = type;
    }

    public PrinterCommand(String text) {
        this.type = CommandType.TEXT;
        this.text = text;
    }

    public PrinterCommand(CommandType type, String text) {
        this.type = type;
        this.text = text;
    }

    public PrinterCommand(String text, String encoding) {
        this.text = text;
        this.encoding = encoding;
    }

    public PrinterCommand(String text, boolean doubleWidth, boolean doubleHeight) {
        this.text = text;
        this.doubleWidth = doubleWidth;
        this.doubleHeight = doubleHeight;
    }

    public PrinterCommand(CommandType type, String text, boolean doubleWidth, boolean doubleHeight) {
        this.type = type;
        this.text = text;
        this.doubleWidth = doubleWidth;
        this.doubleHeight = doubleHeight;
    }

    public PrinterCommand(CommandType type, Bitmap image) {
        this.type = type;
        setImage(image);
    }

    public String getText() {
        return text;
    }

    public PrinterCommand setText(String text) {
        this.text = text;
        return this;
    }

    public boolean isDoubleWidth() {
        return doubleWidth;
    }

    public PrinterCommand setDoubleWidth(boolean doubleWidth) {
        this.doubleWidth = doubleWidth;
        return this;
    }

    public boolean isDoubleHeight() {
        return doubleHeight;
    }

    public PrinterCommand setDoubleHeight(boolean doubleHeight) {
        this.doubleHeight = doubleHeight;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public PrinterCommand setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public CommandType getType() {
        return type;
    }

    public PrinterCommand setType(CommandType type) {
        this.type = type;
        return this;
    }

    public Bitmap getImage() {
        byte[] decodedString = Base64.decode(this.imageEncoded, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public PrinterCommand setImage(Bitmap image) {
        final int COMPRESSION_QUALITY = 100;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        this.imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return this;
    }
}
