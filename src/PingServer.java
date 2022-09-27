import java.io.IOException;
import java.net.*;

public class PingServer extends Thread{
    private DatagramSocket severSocket;
    private final byte[] buffer=new byte[2048];

    public void run(int port) {
        System.out.println("Ping Server Started\n");
        try {
            //Generate server-side socket instance
            severSocket=new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("Listening port"+port+"failure, the port is illegally occupied, please choose another one");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Keep listening to possible request
        while(true){
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            try {
                //Receive packet from client
                severSocket.receive(receivePacket);
            } catch (IOException e) {
                System.out.println("Group acceptance exception");
                e.printStackTrace();
            }
            ServerThread thread= new ServerThread(severSocket, receivePacket);
            //Start a thread
            thread.start();
        }
    }

    public static void main(String []args){
        //Initialize server
        PingServer pingServer=new PingServer();
        int port=Integer.parseInt(args[0]);
        pingServer.run(port);
    }

    static class ServerThread extends Thread{
        private final DatagramSocket severSocket;
        private final DatagramPacket receivePacket;
        public ServerThread(DatagramSocket severSocket,DatagramPacket receivePacket){
            //Initialize packet and socket
            this.receivePacket=receivePacket;
            this.severSocket=severSocket;
        }
        public void run(){
            byte[] buffer;
            String request;
            //Define the random time for delayed return messages
            long randomTime=(long)(Math.random()*2000);
            try {
                sleep(randomTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Determine if it is a delayed message
            if(randomTime>1000){
                request="Packet lost";
            }else{
                request=(new String(receivePacket.getData())).substring(0, 100);
            }
            //Get address from client
            InetAddress host=receivePacket.getAddress();
            //Get the port number of the client application process
            int port =receivePacket.getPort();
            buffer=request.getBytes();
            //The packet sent back to client
            DatagramPacket sendPacket=new DatagramPacket(buffer,buffer.length,host,port);
            try {
                //Send packet to client
                severSocket.send(sendPacket);
            } catch (IOException e) {
                System.out.print("Exception");
            }
            System.out.println(request);
        }
    }
}