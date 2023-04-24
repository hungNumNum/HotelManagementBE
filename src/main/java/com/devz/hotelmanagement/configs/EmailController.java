package com.devz.hotelmanagement.configs;


import com.devz.hotelmanagement.services.StorageService;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.services.impl.AccountServiceImpl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.UUID;
import javax.imageio.ImageIO;


@CrossOrigin("*")
@RestController
@RequestMapping("/reset-password")
public class EmailController {

    @Autowired
    private StorageService storageService;
    @Autowired
    private AccountServiceImpl acc ;
    
    @Autowired
    MyEmailService emailService;

    @PostMapping
    public ResponseEntity<EmailRequest> sendEmail(@RequestBody EmailRequest emailRequest) {
        String token = UUID.randomUUID().toString();
        try {
			acc.updateRePasswordToken(token, emailRequest.getEmail());
			String resetPasswordLink = "http://127.0.0.1:5500/#!/reset-password/" + token;
			emailService.sendSimpleMessage(emailRequest.getEmail(), "ResetPassword NamViet Hotel", "Click here : "+resetPasswordLink);
			return ResponseEntity.ok(emailRequest);
		} catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
    }
    
    @PostMapping("/done")
    public ResponseEntity<PasswordRequest> resetPass(@RequestBody PasswordRequest pss) {
    	Account account = acc.getAccByRepasswordToken(pss.getToken());
       if (account == null) {
    	   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}else {
		acc.updatePassword(account, pss.getNewPassword());
		return ResponseEntity.ok(pss);
	}
    }

    @PostMapping("/qrcode")
    public ResponseEntity<String> generateQRCodeAndSendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            String text = emailRequest.getBookingCode();
            String email = emailRequest.getEmail();

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            String fileName = text + ".jpg";
            System.out.println(fileName);
            InputStream inputStream = null;
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(qrCodeImage, "jpg", os);
                inputStream = new ByteArrayInputStream(os.toByteArray());
                storageService.saveFile2(inputStream, fileName);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            String urlQRCODE = storageService.getPresignedURL(fileName);
            String emailContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Document</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h2>Hãy sử dụng mã QR CODE này khi đến checkin khách sạn</h2>"+
                    "    <img src='" + urlQRCODE + "'/>\n" +
                    "</body>\n" +
                    "</html>";

            emailService.sendHtmlMessage(email, "QR code", emailContent);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate QR code and upload to S3");
        }
    }


}

