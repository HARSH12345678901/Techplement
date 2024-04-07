import java.io.*;
import java.net.*;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;
    
    public Client()
    {
    try {

        System.out.println("sending request to server ");
        socket=new Socket("128.0.10.1",7878);
        System.out.println("connection done!!!");

        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new PrintWriter(socket.getOutputStream());


        startReading();
        startWriting();
    } 
    catch (Exception e) 
    {
       
        e.printStackTrace();
    }
    }


    public void startReading()
    {
        Runnable r1=()->
        {
            System.out.println("reader started...");

            try{
            while(true)
            {   
                String msg = br.readLine();
                if(msg.equals("exit"))
                {
                        System.out.println("server terminated the chat");
                        break;
                }

                System.out.println("Server: "+msg);
            } 
        }catch(Exception e){
            e.printStackTrace();
        }
            
        };
        new Thread(r1).start();

    }

    public void startWriting()
    {
        Runnable r2=()->
        {
            System.out.println("writer started ....");
            try{
            while(true && !socket.isClosed())
            {
              
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content =br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        };

        new Thread(r2).start();
    }



    public static void main(String[] args) {
        System.out.println("this is client ....");
        new Client();
    }
}
