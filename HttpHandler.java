import java.net.*;
import java.io.*;

public class HttpHandler
{
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private HttpReqest httpReq;

  public HttpHandler(ServerSocket ss)
  {
    this.serverSocket = ss;
    try
    {
      this.clientSocket = this.serverSocket.accept();
      System.out.println("Connection Established");
    }
    catch (Exception e)
    {
      System.err.println("Error accepting request. Exception trace:");
      System.err.println(e.getMessage());
    }
  }

  public void processRequest()
  {
    try {
      BufferedReader in = new BufferedReader(new InputStreamnReader(this.clientSocket.getInputStream()));

      // read inital line of request
      String line = in.readLine();
      System.out.println(line);
      httpReq = new HttpRequest(line);

      // read the headers
      line = in.readLine();
      while(in.ready() && (line != "\n" || line != null))
      {
        this.parseHeader(line);
        line = in.readline();
      }

      // read body of request
    }
    catch(IOException e)
    {
      System.err.println("Error reading request. Exception trace:");
      System.err.println(e);
    }
  }

  private void parseHeader(String headerLine)
  {

  }

  public void respond()
  {
    try
    {
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

  }
}
