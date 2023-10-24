import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.nio.Buffer;
import java.util.Scanner;

public class Main {
    public static BufferedImage convertToGrayScale(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height,
                BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                outputImage.setRGB(j, i, inputImage.getRGB(j, i));
            }
        }
        return outputImage;
    }

    public static BufferedImage ImageFlipping(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        for(int h =0; h<height; h++) {
            for (int w = 0; w < width / 2; w++) {
                //Getting pixel values
                Color pixels = new Color(image.getRGB(w, h));
                int temp = pixels.getRGB();
                Color Inverse_Pixel = new Color(image.getRGB(image.getWidth() - 1 - w, h));
                image.setRGB(w, h, Inverse_Pixel.getRGB());
                image.setRGB(image.getWidth() - 1 - w, h, temp);
            }
        }
        return image;
    }




    public static BufferedImage flippingImageVertically(BufferedImage image) {

        // Inversing Vertically While First Iterating on the rows
        for (int w = 0; w < image.getWidth(); w++) {
            for (int h = 0; h < image.getHeight() / 2; h++) {

                // Retriving Pixels
                Color pixels = new Color(image.getRGB(w, h));
                int temp = pixels.getRGB();
                Color Inverse_Pixel = new Color(image.getRGB(w, image.getHeight() - 1 - h));
                image.setRGB(w, h, Inverse_Pixel.getRGB());
                image.setRGB(w, image.getHeight() - 1 - h, temp);
            }
        }

        return image;

    }
    public static BufferedImage RotateAntiClockwise(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage ans = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        // interchanging height and width
        for (int w = 0; w < width/2; w++) {
            for (int h = 0; h < height; h++) {
                // getting pixel values and changing columns
                Color pixels = new Color(image.getRGB(w, h));
                int temp = pixels.getRGB();
                int Inverse_Pixel = image.getRGB(width - w - 1, h);
                image.setRGB(width - w - 1, h, temp);
                image.setRGB(w, h, Inverse_Pixel);
            }
        }
        //transposing the image
        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                // getting pixel values
                Color pixels = new Color(image.getRGB(w, h));
                int temp = pixels.getRGB();
                ans.setRGB(h, w, temp);
            }
        }
        return ans;
    }
    public static BufferedImage RotateClockwise(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage transpose = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                Color pixel = new Color(image.getRGB(w, h));
                int temp = pixel.getRGB();
                transpose.setRGB(h, w, temp);
            }
        }
        // Here WE Inverse The TransPosed Image
        for (int h = 0; h < transpose.getHeight(); h++) {
            for (int w = 0; w < transpose.getWidth() / 2; w++) {
                Color pixel = new Color(transpose.getRGB(w, h));
                int temp = pixel.getRGB();
                Color Inverse_Pixel = new Color(transpose.getRGB(transpose.getWidth() - 1 - w, h));
                transpose.setRGB(w, h, Inverse_Pixel.getRGB());
                transpose.setRGB(transpose.getWidth() - 1 - w, h, temp);
            }
        }
        return transpose;
    }
    public static BufferedImage increaseBrightness (BufferedImage inputImage, int increase){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color pixel = new Color(inputImage.getRGB(j, i));
                int red = pixel.getRed();
                int blue = pixel.getBlue();
                int green = pixel.getGreen();
                red = red+(increase*red/100);
                blue = blue+(increase*blue/100);
                green = green+(increase*green/100);
                if (red>255)red=255;
                if (green>255)green=255;
                if (blue>255)blue=255;
                if (red<0)red=0;
                if (green<0)green=0;
                if (blue<0)blue=0;
                Color newPixel = new Color(red, green, blue);
                outputImage.setRGB(j, i, newPixel.getRGB());
            }
            }
        return outputImage;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Command Line Image Editor");
        System.out.println("Press 1 to convert the image to GrayScale");
        System.out.println("Press 2 to change brightness of the image");
        System.out.println("Press 3 to flip the image Horizontally");
        System.out.println("Press 4 to rotate the image Clockwise");
        System.out.println("Press 5 to rotate the image Anti-Clockwise");
        System.out.println("Press 6 to flip the image vertically");
        int operation = sc.nextInt();
        System.out.println("Enter the path of the image file: ");
        String path = sc.next();
        if (operation == 1) {
            File inputFile = new File(path);
            try {
                BufferedImage inputImage = ImageIO.read(inputFile);
                BufferedImage convertToGrayScale = convertToGrayScale(inputImage);
                File outputFile = new File("GrayscaledImage.jpg");
                ImageIO.write(convertToGrayScale, "jpg", outputFile);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("The  image has been saved by the name GrayScaled");
        }
        if (operation == 2) {

            System.out.println("Enter the amount of change in brightness: ");
            int bright = sc.nextInt();
            File inputFile = new File(path);
            try {
                BufferedImage inputImage = ImageIO.read(inputFile);

                BufferedImage increasedBrightness = increaseBrightness(inputImage, bright );
                File outputFile = new File("BrightnessChanged.jpg");
                ImageIO.write(increasedBrightness, "jpg", outputFile);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("The brightness has been changed");
        }
        if (operation == 3) {

            File inputFile = new File(path);
            try {
                BufferedImage inputImage = ImageIO.read(inputFile);
                BufferedImage ImageFlipping = ImageFlipping(inputImage);
                File outputFile = new File("FlippedImage.jpg");
                ImageIO.write(ImageFlipping, "jpg", outputFile);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("The image has been flipped");
        }
        if (operation == 4) {
            File inputFile = new File(path);
            try {
                BufferedImage inputImage = ImageIO.read(inputFile);
                BufferedImage RotateCLockwise = RotateClockwise(inputImage);
                File outputFile = new File("RotatedClockwise.jpg");
                ImageIO.write(RotateCLockwise, "jpg", outputFile);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("The image has been rotated Clockwise");
        }
        if (operation == 5) {
            File inputFile = new File(path);
            try {
                BufferedImage inputImage = ImageIO.read(inputFile);
                BufferedImage RotateAnticlockwise = RotateAntiClockwise(inputImage);
                File outputFile = new File("AntiClockwiseImage.jpg");
                ImageIO.write(RotateAnticlockwise, "jpg", outputFile);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("The Image has been rotated AntiClockwise");
        }
        if (operation == 6) {
            File inputFile = new File(path);
            try {
                BufferedImage inputImage = ImageIO.read(inputFile);
                BufferedImage flippingImageVertically = flippingImageVertically(inputImage);
                File outputFile = new File("FlippedVertically.jpg");
                ImageIO.write(flippingImageVertically, "jpg", outputFile);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("The vetically flipped image has been saved by the name FlippedVertically");
        }
    }
}
