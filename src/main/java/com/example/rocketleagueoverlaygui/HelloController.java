package com.example.rocketleagueoverlaygui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        String filePath = overlayHTML();
        if (filePath != null) {
            welcomeText.setText("File found");
            openFiles(filePath); // open overlay.html
            openOBS(); // open OBS Studio
            openConsoleAndRunNode();
//            openShell(); // open PowerShell
        } else {
            welcomeText.setText("File not found.");
        }
    }

    //accessing the overlay.html file
    private static String overlayHTML() {
        System.out.println(getHostName() + "yeeeeeeeett"); // print users name to console
        return "C:\\Users\\" + getHostName() + "\\Documents\\Rocket_League_Overlay\\Rocket League DO\\Rocket League Dynamic Overlay\\overlay.html";
    }

    //access the current username of the Windows profile logged in
    private static String getHostName() {
        return System.getProperty("user.name");
    }

    // opens BakkesMod and overlay.html
    private void openFiles(String filePath) {
        try {
            if (Desktop.isDesktopSupported()) {
                // Replace backslashes with forward slashes and encode spaces
                String encodedPath = filePath.replace("\\", "/").replace(" ", "%20");
                Desktop.getDesktop().browse(new URI("file:///" + encodedPath)); // Open HTML file


                String bakkesModPath = "C:/Program Files/BakkesMod/BakkesMod.exe";

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

    // opens BakkesMod Console in Rocket League - NOT WORKING
    private void openF6PanelInBakkesMod() {
        try {
            // Specify the path to the BakkesMod executable
            String bakkesModPath = "C:/Program Files/BakkesMod/BakkesMod.exe";

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

    //opens OBS Studio
    public void openOBS() {
        try {
            String obsPath = "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\OBS Studio\\OBS Studio (64bit).lnk";
            File obsFile = new File(obsPath);
            if (obsFile.exists() && obsFile.isFile()) {
                // Open OBS file using default application
                Desktop.getDesktop().open(obsFile);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("OBS Studio executable not found at: " + obsPath);
                alert.showAndWait();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error opening OBS Studio: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    //opens PowerShell
    public void openShell() {
        try {

            String shellPath = "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\Windows PowerShell\\Windows PowerShell ISE.lnk"; // PowerShell file path
            String relayPath = "C:\\Users\\" + getHostName() + "\\Documents\\Rocket_League_Overlay\\Rocket League DO\\relayserverandplugin\\SOS Relay Server (run in cmd with node)\\sos-ws-relay-master";

            File shellFile = new File(shellPath);
            if (shellFile.exists() && shellFile.isFile()) {
                // Open PowerShell using default application
                Desktop.getDesktop().open(shellFile);
                Thread.sleep(2000); // sleep to make sure PowerShell is open



            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("PowerShell executable not found at: " + shellFile);
                alert.showAndWait();
            }
        }
        // catch for opening PowerShell
        catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error opening PowerShell: " + ex.getMessage());
            alert.showAndWait();

        }
        // catch for Thread.sleep
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void openConsoleAndRunNode() {
        try {
            String relayPath = "C:\\Users\\" + getHostName() + "\\Documents\\Rocket_League_Overlay\\Rocket League DO\\relayserverandplugin\\SOS Relay Server (run in cmd with node)\\sos-ws-relay-master";

            // Create process builder for cmd
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start /d \"" + relayPath + "\" cmd /k node ./ws-relay.js");

            // Start cmd process
            Process process = processBuilder.start();

            // Wait for the process to complete
            process.waitFor();

            // Simulate pressing Enter three times
            Robot robot = new Robot();
            for (int i = 0; i < 3; i++) {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(1000); // Wait for 1 second between each Enter key press
            }

        } catch (IOException | InterruptedException | AWTException ex) {
            // Exception occurred while executing commands
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error executing commands: " + ex.getMessage());
            alert.showAndWait();
        }
    }




}
