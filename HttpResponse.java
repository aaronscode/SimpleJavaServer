import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class HttpResponse
{
	private final String HTTP_VERSION = "HTTP/1.1";
	private String statusLine;
  private String serverRoot = "./content"; // TODO maybe read this from a .config

  private Scanner inFile = null;
	private boolean hasBody;
  private String body = null;

	public HttpResponse(HttpRequest req)
	{
		// try to make a file from req.
    if(req.isValid())
    {
      try
      {
        this.inFile = new Scanner(new File(serverRoot + req.rewrittenResLocation()));
        if(inFile.hasNext())
        {
          body = inFile.nextLine();
          while(inFile.hasNext())
          {
            this.body += inFile.nextLine();
          }
        }
        else 
          this.body = "";

        setStatusLine(200);
        this.hasBody = true;
      }
      catch(FileNotFoundException e)
      {
        setStatusLine(404);
        this.hasBody = false;
      }
    }
    else
    {
      setStatusLine(500);
      this.hasBody = false;
    }
	}

	public HttpResponse(int statusCode)
	{
		this.setStatusLine(statusCode);
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
    String response[] = getResponse();
    String retval = "Response:\n";
    retval += response[0];
    retval += response[1];
    return retval;
  }
}
