package com.example.rocketleagueoverlaygui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        String filePath = relayFile();
        if (filePath != null) {
            welcomeText.setText("File found");
            openFiles(filePath);
        } else {
            welcomeText.setText("File not found.");
        }
    }
//hello   asasasasas
    private static String relayFile() {
        return "C:\\Users\\Woottam\\Documents\\Rocket_League_Overlay\\Rocket League DO\\Rocket League Dynamic Overlay\\overlay.html";
    }

    private void openFiles(String filePath) {
        try {
            if (Desktop.isDesktopSupported()) {
                // Replace backslashes with forward slashes and encode spaces
                String encodedPath = filePath.replace("\\", "/").replace(" ", "%20");
                Desktop.getDesktop().browse(new URI("file:///" + encodedPath)); // Open HTML file


                String bakkesModPath = "C:/Program Files/BakkesMod/BakkesMod.exe"; // Example path, adjust as needed

                // Check if the BakkesMod executable exists
                File bakkesModFile = new File(bakkesModPath);
                if (bakkesModFile.exists()) {
                    Runtime.getRuntime().exec(bakkesModPath);
                } else {
                    System.out.println("BakkesMod not found at: " + bakkesModPath);
                }
            } else {
                System.out.println("Desktop not supported, cannot open files.");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    private void openF6PanelInBakkesMod() {
        try {
            // Specify the path to the BakkesMod executable
            String bakkesModPath = "C:/Program Files/BakkesMod/BakkesMod.exe"; // Example path, adjust as needed

            // Check if the BakkesMod executable exists
            File bakkesModFile = new File(bakkesModPath);
            if (bakkesModFile.exists()) {
                // Execute BakkesMod executable
                Process process = Runtime.getRuntime().exec(bakkesModPath);

                // Sleep for a short duration to ensure BakkesMod has initialized
                Thread.sleep(2000); // Adjust as needed

                // Send command to BakkesMod console to open F6 panel
                OutputStream outputStream = process.getOutputStream();
                String command = "togglemenu F6\n"; // Command to open F6 panel
                outputStream.write(command.getBytes());
                outputStream.flush();
            } else {
                System.out.println("BakkesMod not found at: " + bakkesModPath);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
