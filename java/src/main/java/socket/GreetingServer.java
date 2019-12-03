package socket;
// 文件名 GreetingServer.java

import java.net.*;
import java.io.*;
import java.util.concurrent.ThreadPoolExecutor;

public class GreetingServer extends Thread
{
    private ServerSocket serverSocket;

    public GreetingServer(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            System.out.println("远程主机地址：" + server.getRemoteSocketAddress());
                            DataInputStream in = new DataInputStream(server.getInputStream());
                            System.out.println(in.readUTF());
                            DataOutputStream out = new DataOutputStream(server.getOutputStream());
                            out.writeUTF("谢谢连接我：" + server.getLocalSocketAddress() + "\nGoodbye!");
                            Thread.sleep(5000);
                            server.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }catch(SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
                //break;
            }catch(IOException e)
            {
                e.printStackTrace();
                //break;
            }
        }
    }
    public static void main(String [] args)
    {
        int port = 7070;
        try
        {
            Thread t = new GreetingServer(port);
            t.run();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}