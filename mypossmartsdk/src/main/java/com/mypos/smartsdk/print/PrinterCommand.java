package com.mypos.smartsdk.print;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;

/**
 *
 */

public class PrinterCommand {

    public enum CommandType {
        /**
         * If type is header, the text should contain the receipt date in format "DD/MM/YY;HH:mm:ss"!
         */
        @SerializedName("HEADER")
        HEADER,
        @SerializedName("LOGO")
        LOGO,
        @SerializedName("TEXT")
        TEXT,
        @SerializedName("FOOTER")
        FOOTER,
        @SerializedName("IMAGE")
        IMAGE
    }

    public enum Alignment {
        @SerializedName("ALIGN_LEFT")
        ALIGN_LEFT,
        @SerializedName("ALIGN_CENTER")
        ALIGN_CENTER,
        @SerializedName("ALIGN_RIGHT")
        ALIGN_RIGHT,
    }

    private static final int     RECEIPT_MAX_CHARS_PER_LINE                 = 32;

    /**
     * The command's type
     */
    @SerializedName("type")
    private CommandType type;
    /**
     * The text to be printed
     */
    @SerializedName("text")
    private String text;
    /**
     * Should the text be printed with double width?
     */
    @SerializedName("doubleWidth")
    private boolean doubleWidth;
    /**
     * Should the text be printed with double height?
     */
    @SerializedName("doubleHeight")
    private boolean doubleHeight;
    /**
     * Should print double width/height correctly?
     */
    @SerializedName("doubleDimensionsSupported")
    private boolean doubleDimensionsSupported;
    /**
     * The encoding
     */
    @SerializedName("encoding")
    private String  encoding;
    /**
     * Image to be printed
     */
    @SerializedName("imageEncoded")
    private String imageEncoded;
    /**
     * Font size
     */
    @SerializedName("fontSize")
    private int fontSize;
    /**
     * Alignment of the text.
     */
    @SerializedName("alignment")
    private Alignment alignment = Alignment.ALIGN_LEFT;


    public PrinterCommand(CommandType type) {
        this.type = type;

        if (type == CommandType.TEXT) {
            text = " ";
        }
    }

    public PrinterCommand(String text) {
        this.type = CommandType.TEXT;
        this.text = text;
    }

    public PrinterCommand(CommandType type, String text) {
        this.type = type;
        this.text = text;
    }

    @Deprecated
    public PrinterCommand(String text, String encoding) {
        this.text = text;
        this.encoding = encoding;
    }

    @Deprecated
    public PrinterCommand(String text, boolean doubleWidth, boolean doubleHeight) {
        this.text = text;
        this.doubleWidth = doubleWidth;
        this.doubleHeight = doubleHeight;
    }

    public PrinterCommand(String text, boolean doubleWidth, boolean doubleHeight, boolean doubleDimensionsSupported) {
        this.text = text;
        this.doubleWidth = doubleWidth;
        this.doubleHeight = doubleHeight;
        this.doubleDimensionsSupported = doubleDimensionsSupported;
    }

    @Deprecated
    public PrinterCommand(CommandType type, String text, boolean doubleWidth, boolean doubleHeight) {
        this.type = type;
        this.text = text;
        this.doubleWidth = doubleWidth;
        this.doubleHeight = doubleHeight;
    }

    public PrinterCommand(CommandType type, String text, boolean doubleWidth, boolean doubleHeight, boolean doubleDimensionsSupported) {
        this.type = type;
        this.text = text;
        this.doubleWidth = doubleWidth;
        this.doubleHeight = doubleHeight;
        this.doubleDimensionsSupported = doubleDimensionsSupported;
    }

    public PrinterCommand(CommandType type, Bitmap image) {
        this.type = type;
        setImage(image);
    }

    public PrinterCommand(String text, int fontSize) {
        this.type = CommandType.TEXT;
        this.text = text;
        this.fontSize = fontSize;
    }

    public PrinterCommand(CommandType type, String text, int fontSize) {
        this.type = type;
        this.text = text;
        this.fontSize = fontSize;
    }

    public PrinterCommand(String text, Alignment alignment) {
        this.type = CommandType.TEXT;
        this.text = text;
        this.alignment = alignment;
    }

    public PrinterCommand(CommandType type, String text, Alignment alignment) {
        this.type = type;
        this.text = text;
        this.alignment = alignment;
    }

    public PrinterCommand(String text, int fontSize, Alignment alignment) {
        this.type = CommandType.TEXT;
        this.text = text;
        this.fontSize = fontSize;
        this.alignment = alignment;
    }

    public PrinterCommand(CommandType type, String text, int fontSize, Alignment alignment) {
        this.type = type;
        this.text = text;
        this.fontSize = fontSize;
        this.alignment = alignment;
    }

    public PrinterCommand(CommandType type, String leftText, String rightText) {
        this.type = type;
        this.text = formatRow(leftText, rightText, RECEIPT_MAX_CHARS_PER_LINE);
    }

    public String getText() {
        return text;
    }

    public PrinterCommand setText(String text) {
        this.text = text;
        return this;
    }

    @Deprecated
    public boolean isDoubleWidth() {
        return doubleWidth;
    }

    @Deprecated
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


    public boolean isDoubleDimensionsSupported() {
        return doubleDimensionsSupported;
    }

    public PrinterCommand setIsDoubleDimensionsSupported(boolean doubleDimensionsSupported) {
        this.doubleDimensionsSupported = doubleDimensionsSupported;
        return this;
    }


    @Deprecated
    public String getEncoding() {
        return encoding;
    }

    @Deprecated
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

    public int getFontSize() {
        return fontSize;
    }

    public PrinterCommand setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public PrinterCommand setAlignment(Alignment alignment) {
        this.alignment = alignment;
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

    private static String formatRow(String leftText, String rightText, int maxCharsPerLine){
        String formattedRow = "";
        if(leftText.length() + rightText.length() <= maxCharsPerLine){
            formattedRow = leftText;
            while (formattedRow.length() + rightText.length() < maxCharsPerLine){
                formattedRow += " ";
            }
            formattedRow += rightText;
        }
        else{
            int rowIndex = 0;
            while(rowIndex < maxCharsPerLine - 1 - rightText.length()){
                formattedRow += leftText.charAt(rowIndex);
                rowIndex++;
            }
            formattedRow += "\n";
            formattedRow += formatRow(leftText.charAt(rowIndex) == ' ' ? leftText.substring(rowIndex+1) : leftText.substring(rowIndex), rightText, maxCharsPerLine);
        }
        return formattedRow;
    }
}
