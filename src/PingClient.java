import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PingClient
{
    public static void main(String[] args) throws Exception
    {
        String ip = args[0];
        int port = new Integer(args[1]);
        Long[] rtt = new Long[10];
        //Generate client-side socket instance
        DatagramSocket clientSocket = new DatagramSocket();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");
        InetAddress IPAddress = InetAddress.getByName(ip);
        for (int i = 1; i <= 10; i++)
        {
            byte[] receiveData = new byte[256];
            //Generate message
            String message = "PingUDP SequenceNumber " + i + "\n"+
                    "Sending time: " + format.format(new Date()) + "\n";
            byte[] sendData = message.getBytes();
            //Generate example of sending packet
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            Date sendBefore = new Date();
            //Sending to the server
            clientSocket.send(sendPacket);
            //Generate instance of receiving packet
            DatagramPacket receivePacket = new DatagramPacket(receiveData,	receiveData.length);
            //Receive packet returned from server
            clientSocket.receive(receivePacket);
            Date receiveAfter = new Date();
            String receiveMessage = new String(receivePacket.getData());
            //Calculate rtt = time received feedback - sending time
            rtt[i - 1] = receiveAfter.getTime() - sendBefore.getTime();
            System.out.println("rtt (round-trip time):" + rtt[i - 1]);
            //Print out data returned from server
            System.out.println(receiveMessage);
        }

        //Calculate the average rtt, maximum rtt and minimum rtt
        long sumRtt = 0;
        long maxRtt = 0;
        long minRtt = rtt[0];
        for (int i = 0; i < 10; i++)
        {
            if (rtt[i] > maxRtt)
            {
                maxRtt = rtt[i];
            }

            if (rtt[i] < minRtt)
            {
                minRtt = rtt[i];
            }

            sumRtt += rtt[i];
        }

        System.out.println("Total round-trip time: " + sumRtt / 10 + " millisecond");
        System.out.println("Maximum round-trip time: " + maxRtt);
        System.out.println("Minimum round-trip time: " + minRtt);
    }
}