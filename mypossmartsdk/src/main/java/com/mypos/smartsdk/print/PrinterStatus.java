package com.mypos.smartsdk.print;

/**
 * Contains all the statuses the print command can return
 */

public class PrinterStatus {
    /**
     * Printing completed successfully
     */
    public static final int PRINTER_STATUS_SUCCESS                 = 0;
    /**
     * The printer is busy with printing or something else
     */
    public static final int PRINTER_STATUS_PRINTER_BUSY            = 1;
    /**
     * There's no paper in the printer
     */
    public static final int PRINTER_STATUS_OUT_OF_PAPER            = 2;
    /**
     * Something's wrong with the data sent to the printer
     */
    public static final int PRINTER_STATUS_PRINT_DATA_PACKET_ERROR = 3;
    /**
     * Hardware malfunction
     */
    public static final int PRINTER_STATUS_PRINTER_MALFUNCTION     = 4;
    /**
     * The printer's been printing too much and is too hot to continue
     */
    public static final int PRINTER_STATUS_PRINTER_OVERHEATING     = 8;
    /**
     * Device's battery is too low to fire up the printer
     */
    public static final int PRINTER_STATUS_PRINTER_VOLTAGE_TOO_LOW = 9;
    /**
     * The previous request to the printer is not finished yet
     */
    public static final int PRINTER_STATUS_PRINTER_UNFINISHED      = 240;
    /**
     * The device is missing some required font library
     */
    public static final int PRINTER_STATUS_MISSING_FONT_LIBRARY    = 252;
    /**
     * You are trying to print too much data at once
     */
    public static final int PRINTER_STATUS_DATA_PACKET_TOO_LONG    = 254;
    /**
     * The timeout of the printer command expired before the printing was complete
     */
    public static final int PRINTER_NOT_FINISHED                   = 257;
    /**
     * Something else
     */
    public static final int PRINTER_STATUS_UNKNOWN_ERROR           = -1;
}
