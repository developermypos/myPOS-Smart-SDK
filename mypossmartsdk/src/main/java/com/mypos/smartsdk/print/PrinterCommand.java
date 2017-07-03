package com.mypos.smartsdk.print;

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
        FOOTER
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
}
