package com.codes.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4444);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // সাইনআপ টেস্ট
            out.println("SIGNUP|Tasnim|Zannat|tasnim@gmail.com|MyPass123|MyPass123|What is your pet name?|Tom");
            String response = in.readLine();
            System.out.println("Server reply: " + response);

            // লগইন টেস্ট
            out.println("LOGIN|tasnim@gmail.com|MyPass123");
            response = in.readLine();
            System.out.println("Login reply: " + response);

            // ভুল পাসওয়ার্ড দিয়ে লগইন টেস্ট
            out.println("LOGIN|tasnim@gmail.com|WrongPass");
            response = in.readLine();
            System.out.println("Wrong pass reply: " + response);

            // Forgot Password প্রশ্ন পাওয়া
            out.println("FORGOT_QUESTION|tasnim@gmail.com");
            response = in.readLine();
            System.out.println("Question reply: " + response);

            // রিসেট পাসওয়ার্ড টেস্ট
            out.println("RESET_PASSWORD|tasnim@gmail.com|Tom|NewPass456|NewPass456");
            response = in.readLine();
            System.out.println("Reset reply: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}