package com.example.or.imageserviceapp;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TCPClient {

    private Socket socket;
    private OutputStream outputStream;
    InetAddress serverAddress;

    /**
     * Constructor
     */
    public TCPClient() {}
    public void connect(){
        try {
            serverAddress = InetAddress.getByName("10.0.2.2");
            socket = new Socket(serverAddress, 7999);
            outputStream = socket.getOutputStream();

        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }
    }

    /**
     * Convert the file into bytes
     * @param file file
     * @param inputStream stream
     * @return
     */
    public byte[] convertToByte(File file, FileInputStream inputStream) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        } catch (Exception e) {
            Log.e("On convert To Bytes", "C: Error", e);
        }
        return stream.toByteArray();
    }

    /**
     * Transfer the picture
     * @param file
     * @throws Exception
     */
    public void send(File file) throws Exception{
        FileInputStream inputStream = new FileInputStream(file);

        byte[] imgBytes = convertToByte(file, inputStream);
        if (imgBytes == null)
            return;
        try {

            outputStream.flush();
            // send the length of the photo name
            byte[] b1 = ByteBuffer.allocate(4).putInt(file.getName().getBytes().length).array();
            if (b1 == null)
                return;
            outputStream.write(b1);
            // send the photo name
            outputStream.flush();

            byte[] b2 = file.getName().getBytes();
            if (b2 == null)
                return;
            outputStream.write(b2);
            // send the length of image bytes
            outputStream.flush();

            byte[] b3 = ByteBuffer.allocate(4).putInt(imgBytes.length).array();
            if (b3 == null)
                return;
            outputStream.write(b3);
            // send the photo
            outputStream.flush();

            outputStream.write(imgBytes);

            outputStream.flush();
        } catch (Exception e) {
            Log.e("In Send", "Transfer photo failed", e);
        }
    }


    /**
     * Close the socket
     */
    public void closeSocket() {
        try {
            this.socket.close();
        } catch(Exception e) {
            Log.e("Close socket", "Failed", e);

        }
    }
}
