package ru.soknight.imgbb.converter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class ImageBase64Converters {

    /*
     * BUFFERED IMAGE -> BASE64
     */
    
    /**
     * Encodes a buffered image to base64 string using default image format 'png'.
     * @param bufferedImage a buffered image to encode.
     * @return The encoded base64 string from received buffered image.
     * 
     * @throws RuntimeException when anyone I/O exception catches.
     * @see ImageBase64Converters#fromBufferedImage(BufferedImage, String)
     */
    public static String fromBufferedImage(BufferedImage bufferedImage) {
        return fromBufferedImage(bufferedImage, "png");
    }
    
    /**
     * Encodes a buffered image to base64 string using specifed image format.
     * @param bufferedImage a buffered image to encode.
     * @param formatName a name of the image format (png, jpeg etc.)
     * @return The encoded base64 string from received buffered image.
     * 
     * @throws RuntimeException when anyone I/O exception catches.
     */
    public static String fromBufferedImage(BufferedImage bufferedImage, String formatName) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, formatName, output);
            return Base64.getEncoder().encodeToString(output.toByteArray());
        } catch (IOException ex) {
            throw new RuntimeException("I/O exception was catched during encoding a buffered image!", ex);
        }
    }
    
    /*
     * IMAGE FILE -> BASE64
     */
    
    /**
     * Encodes an image file to base64 string using the file format.
     * @param file an image file from the system.
     * @return The encoded base64 string from received buffered image.
     * 
     * @throws IllegalArgumentException if received file parameter is null.
     * @throws IllegalArgumentException if received file is not exists.
     * 
     * @throws RuntimeException when the {@link ImageIO} utility has no readers for this file format.
     * @throws RuntimeException when anyone I/O exception catches.
     */
    public static String fromImageFile(File file) {
        if(file == null)
            throw new IllegalArgumentException("Specified file is null!");
        
        if(!file.exists())
            throw new IllegalArgumentException("Specified file isn't exists in your system!");
        
        String filename = file.getName();
        String formatName = filename.substring(filename.lastIndexOf('.') + 1);
        
        if(!ImageIO.getImageReadersByFormatName(formatName).hasNext())
            throw new RuntimeException("Sorry, but we cannot read an image formatted as '" + formatName + "'!");
        
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            return fromBufferedImage(bufferedImage, formatName);
        } catch (IOException ex) {
            throw new RuntimeException("I/O exception was catched during encoding an image file!", ex);
        }
    }
    
}
