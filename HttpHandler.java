import java.util.Vector;
import java.net.*;
import java.io.*;

public class HttpHandler
{
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private HttpRequest httpReq;
  private HttpResponse httpResp;

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
      BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

      // read inital line of request
      String line = in.readLine();
      System.out.println(line);
      String initReqLine = line;

      // read the headers
      Vector<String> headersVect = new Vector<String>(8,2); 
      line = in.readLine();
      while(in.ready() && (line != "\n" || line != null))
      {
        headersVect.add(line);
        line = in.readLine();

      }
      headersVect.trimToSize();
      String[] headers = headersVect.toArray(new String[headersVect.size()]);

      // read body of request
      Vector<String> bodyVect = new Vector<String>(10,5); 
      if(in.ready())
      {
        line = in.readLine();
      }
      while(in.ready() && line != null)
      {
        bodyVect.add(line);
        line = in.readLine();
      }
      bodyVect.trimToSize();
      String[] body =  bodyVect.toArray(new String[bodyVect.size()]);

      // feed all this into a HttpRequest object
      this.httpReq = new HttpRequest(initReqLine, headers, body);
      System.out.print(httpReq);

    }
    catch(IOException e)
    {
      System.err.println("Error reading request. Exception trace:");
      System.err.println(e);
    }
  }

  public void respond()
  {
    try
    {
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      this.httpResp = new HttpResponse(this.httpReq);
      System.out.println(this.httpResp);
      String[] response = this.httpResp.getResponse();
      for(int i = 0; i < response.length; i++)
      {
        out.print(response[i]);
      }
      out.close();
    }
    catch(IOException e)
    {
      System.err.println("Error responding to request. Exception trace:");
      System.err.println(e);
    }
  }

  public void close()
  {
    try
    {
      this.clientSocket.close();
    }
    catch(IOException e)
    {

    }
  }
}
