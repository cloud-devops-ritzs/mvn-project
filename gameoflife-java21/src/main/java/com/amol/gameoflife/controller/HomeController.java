package com.amol.gameoflife.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Velocity Class</title>
            <style>
                body {
                    background: linear-gradient(135deg,#1e3c72,#2a5298);
                    color: white;
                    font-family: Arial, sans-serif;
                    text-align: center;
                    padding-top: 100px;
                }

                .card {
                    background: rgba(255,255,255,0.15);
                    padding: 40px;
                    border-radius: 15px;
                    width: 60%;
                    margin: auto;
                    box-shadow: 0px 0px 20px rgba(0,0,0,0.3);
                }

                h1 {
                    color: #FFD700;
                    font-size: 50px;
                }

                h2 {
                    color: #90EE90;
                }

                p {
                    font-size: 24px;
                }
            </style>
        </head>
        <body>

            <div class="card">
                <h1>🚀 Welcome to Velocity Class 🚀</h1>

                <h2>AWS / DevOps Training</h2>

                <p><strong>Batch:</strong> 31st Jan</p>

                <p><strong>Time:</strong> 7:30 PM - 8:30 PM</p>

                <p>Learn AWS, Linux, Git, Maven, Jenkins, Docker, Kubernetes & DevOps CI/CD</p>

                <h3>Happy Learning! 🎯</h3>
            </div>

        </body>
        </html>
        """;
    }
}
