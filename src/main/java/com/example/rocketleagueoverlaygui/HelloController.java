package com.example.rocketleagueoverlaygui;

import com.sun.jna.platform.unix.X11;
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

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
            welcomeText.setText("Running Overlay");
            openBakkesMod();
            openConsoleAndRunNode();
            openOBS(); // open OBS Studio
    }

    private void focusRocketLeagueWindow() throws InterruptedException {
        WinDef.HWND rocketLeagueWindow = User32.INSTANCE.FindWindow(null, "Rocket League (64-bit, DX11, Cooked)"); // Finds Rocket League window
        WinDef.HWND bakkesModConsoleWindow = User32.INSTANCE.FindWindow(null, "BakkesMod console (DirectX"); // Finds BakkesMod Console window?
        if (rocketLeagueWindow != null) {
            // Bring the Rocket League window to the foreground
            User32.INSTANCE.ShowWindow(rocketLeagueWindow, User32.SW_RESTORE);
            User32.INSTANCE.SetForegroundWindow(rocketLeagueWindow);

            // Wait for BakkesMod to inject the DLL files into Rocket League - *Necessary wait time*
            Thread.sleep(10000); // wait for 10 seconds

            // Typing "plugin load sos" into the BakkesMod console
            try {
                Robot robot = new Robot();
                // Pressing F6 to open BakkesMod console
                robot.keyPress(KeyEvent.VK_F6);
                robot.keyRelease(KeyEvent.VK_F6);
                Thread.sleep(2000); // wait for 2 seconds

                // Simulate typing the command "plugin load sos"
                String command = "plugin load sos";
                for (char c : command.toCharArray()) {
                    int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                    Thread.sleep(100);
                }

                // Press Enter to execute the command
                // Enter is being pressed, but not accepted into BakkesMod console
                System.out.println("Pressing Enter...");
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                System.out.println("Enter Pressed");

                // Wait for BakkesMod to process the command
                Thread.sleep(1000); // wait for 1 second

                // Press ESC to close BakkesMod console
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);

                Thread.sleep(100);

                Thread.sleep(1000); // wait for 1 second
            } catch (AWTException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Rocket League window not found.");
        }
    }

    //access the current username of the Windows profile logged in
    private static String getHostName() {
        return System.getProperty("user.name");
    }

    // Opens BakkesMod
    private void openBakkesMod() {
        try {
            String bakkesModPath = "C:/Program Files/BakkesMod/BakkesMod.exe";

            // Check if the BakkesMod exists
            File bakkesModFile = new File(bakkesModPath);
            if (bakkesModFile.exists()) {
                Runtime.getRuntime().exec(bakkesModPath);
                Thread.sleep(5000); // wait for 5 seconds
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
    // opens command prompt, cd to file path, and run node command for ws-relay.js
    public void openConsoleAndRunNode() {
        try {
            String relayPath = "C:\\Users\\" + getHostName() + "\\Documents\\Rocket_League_Overlay\\Rocket League DO\\relayserverandplugin\\SOS Relay Server (run in cmd with node)\\sos-ws-relay-master";

            // Create process builder for cmd
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start /d \"" + relayPath + "\" cmd /k node ./ws-relay.js");

            // Start cmd process
            Process process = processBuilder.start();

            // Wait for the process to complete
            process.waitFor();

            // Press enter 3 times
            Robot robot = new Robot();
            for (int i = 0; i < 3; i++) {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(1000); // Wait for 1 second between each Enter key press
            }

            Thread.sleep(3000); // wait 3 seconds

            focusRocketLeagueWindow(); // focus on rocket league

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
