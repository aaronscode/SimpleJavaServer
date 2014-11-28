import java.util.*;
import java.text.SimpleDateFormat;
import java.net.*;
import java.io.*;

public class SimpleJavaServer
{
  private int portNumber;
  private ServerSocket serverSocket;

  public SimpleJavaServer(int portNum)
  {
    this.portNumber = portNum;
    try
    {
      this.serverSocket = new ServerSocket(this.portNumber); // try to open a socket
    }
    catch(IOException e)
    {
      // coudln't open socket, print error message
      System.err.println("Error opening specified port. Error trace: ");
      System.err.println(e);
      System.exit(1);
    }
    // everything went well, print message indicating server port
    System.out.println("Server opened on port " + this.portNumber + ". Waiting for requests... ");
    System.out.println();
  }

  // default port num is 80 for http
  public SimpleJavaServer()
  {
    this(80);
  }

  private void handleRequest()
  {
    HttpHandler requestHandler =  new HttpHandler(this.serverSocket);
    requestHandler.processRequest();
    requestHandler.respond();
    requestHandler.close();
  }

  public int portNumber()
  {
    return this.portNumber;
  }
  public String toString()
  {
    return "Server on port " + this.portNumber;
  }

  public static void main(String args[])
  {
    SimpleJavaServer sjs = null;
    int portNum = 0;
    if(args.length > 0)
    {
      try 
      {
        try
        {
          portNum = Integer.parseInt(args[0]); // parse port num from args
        } 
        catch(Exception e)
        {
          // error during parsing, rethrow with useful explinataion
          throw new Exception("Port must be a number.");
        }

        if(portNum <= 0)
        {
          // port number parsed correctly, but negative port numbers don't make sense
          throw new Exception("Port Number bust be positive.");
        }
      }
      catch (Exception e) // something went wrong in getting the port number
      {
        System.err.println(e.getMessage());
        System.err.println("Error. Usage is: java SimpleJavaServer <portNumber>");
        System.exit(1);
      }
    }
    else
    {
      sjs = new SimpleJavaServer();
    }

    while(true)
    {
      sjs.handleRequest();
    }
  }
}
