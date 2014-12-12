import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class HttpResponse
{
	private final String HTTP_VERSION = "HTTP/1.1";
	private String statusLine;
  private String contentRoot = "./content"; // TODO maybe read this from a .config

  private int buffSize = 1024 * 8; // buffers of 1024 bytes * 8 = 8kb
  private BufferedInputStream bis;
  private BufferedOutputStream bos;
  private byte[] readData = new byte[buffSize];

	public HttpResponse(OutputStream outStream, HttpRequest req)
  {
    bos = new BufferedOutputStream(outStream, buffSize);

    // try to make a file from req.
    if(req.isValid())
    {
      try
      {
        this.bis = getResBuff(req.rewrittenResLocation());
        setStatusLine(200);
      }
      catch(FileNotFoundException FnF) // file was not found
      {
        try // to make an error page using the 404.html file
        {
          this.bis = getResBuff("/404.html");
        }
        catch(FileNotFoundException FnF2)
        {
          this.bis = null;
        }

        setStatusLine(404);
      }
    }
    else // http request was not considered valid 
    {
      try // to make an error page using 500.html 
      {
        this.bis = getResBuff("/500.html");
      }
      catch(FileNotFoundException e)
      { // 500.html not found, we're really out of luck at this point
        this.bis = null;
      }

      setStatusLine(500);
    }
  }

  private BufferedInputStream getResBuff(String resLocation) throws FileNotFoundException
  {
    FileInputStream fis = new FileInputStream(contentRoot + resLocation);
    return new BufferedInputStream(fis, buffSize);
  }

  public void respond()
  {
    try {
    String statusLineCr = this.statusLine + "\n";
    String dateHeader = "Date: " + getServerTime() + "\n";
    String connectionStatus = "Connection: close\n";
    this.bos.write(statusLineCr.getBytes(), 0, statusLineCr.length());
    this.bos.write(dateHeader.getBytes(), 0, dateHeader.length());
    this.bos.write(connectionStatus.getBytes(), 0, connectionStatus.length());
    this.bos.write("\n".getBytes(), 0, 1);

    while(bis.available() != 0)
    {
      this.bis.read(readData, 0, buffSize);
      this.bos.write(readData, 0, buffSize); // TODO finsish line
    }
    this.bis.close();
    this.bos.close();
    }
    catch(IOException e)
    {
      
    }
  }

  /**
  private String makeBody(String resLoc) throws FileNotFoundException
  {
    String body = null;
    this.inFile = new Scanner(new File(contentRoot + resLoc));
    if(inFile.hasNext())
    {
      body = inFile.nextLine();
      while(inFile.hasNext())
      {
        body += inFile.nextLine();
      }
    }
    else 
      body = "";

    return body;
  }

	public String[] getResponse()
	{
    String[] response = new String[5];
    response[0] = this.statusLine + "\n";
    response[1] = "Date: " + getServerTime() + "\n";
    response[2] = "Connection: close\n";
    response[3] = "\n";
    if(this.hasBody)
      response[4] = this.body;
    else
      response[4] = "";

    return response;
	}
  */

  public String statusLine()
  {
    return this.statusLine;
  }

	public void setStatusLine(int statusCode)
	{
		this.statusLine = HTTP_VERSION + " " + statusCode + " " +  this.getEnglishStatus(statusCode);
	}

	public void setStatusLine(String statusLine)
	{
		this.statusLine = statusLine;
	}

	private String getEnglishStatus(int statusCode)
	{
		switch(statusCode)
		{
			case 200:
				return "OK";
			case 404:
				return "Not Found";
			case 500:
				return "Server Error";
			default:
				return "Unknown Status";
		}
	}

  private String getServerTime() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(calendar.getTime());
  }

  public String toString()
  {
    return "response served";
  }
}
